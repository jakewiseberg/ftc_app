/* Copyright (c) 2014, 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.fawkes.fawkesrc;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.ftccommon.FtcRobotControllerService;
import com.qualcomm.ftccommon.FtcRobotControllerService.FtcRobotControllerBinder;
import com.qualcomm.ftccommon.LaunchActivityConstantsList;
import com.qualcomm.ftccommon.Restarter;
import com.qualcomm.ftccommon.UpdateUI;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.util.Dimmer;
import com.qualcomm.robotcore.util.ImmersiveMode;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.wifi.WifiDirectAssistant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class FawkesRCActivity extends Activity implements SurfaceHolder.Callback, SensorEventListener{

  SurfaceView mSurfaceView;
  SurfaceHolder mHolder;
  public Camera mCamera;

  Sensor accel, mag;
  TextView gyro;
  float[] xyz = {0,0,0};
  float[] rotationMatrix = {0,0,0,0,0,0,0,0,0};
  float[] gravity={0,0,0};
  public float[] geomagnetic={0,0,0};

  private static final int REQUEST_CONFIG_WIFI_CHANNEL = 1;
  private static final boolean USE_DEVICE_EMULATION = false;
  private static final int NUM_GAMEPADS = 2;

  public boolean tookPic=false;

  public static final String CONFIGURE_FILENAME = "CONFIGURE_FILENAME";

  protected SharedPreferences preferences;

  protected UpdateUI.Callback callback;
  protected Context context;
  private Utility utility;
  protected ImageButton buttonMenu;

  protected TextView textDeviceName;
  protected TextView textWifiDirectStatus;
  protected TextView textRobotStatus;
  protected TextView[] textGamepad = new TextView[NUM_GAMEPADS];
  protected TextView textOpMode;
  protected TextView textErrorMessage;
  protected ImmersiveMode immersion;

  protected UpdateUI updateUI;
  protected Dimmer dimmer;
  protected LinearLayout entireScreenLayout;

  protected FtcRobotControllerService controllerService;

  public static FtcEventLoop eventLoop;

  protected class RobotRestarter implements Restarter {

    public void requestRestart() {
      requestRobotRestart();
    }

  }

  protected ServiceConnection connection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      FtcRobotControllerBinder binder = (FtcRobotControllerBinder) service;
      onServiceBind(binder.getService());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      controllerService = null;
    }
  };

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(intent.getAction())) {
      // a new USB device has been attached
      DbgLog.msg("USB Device attached; app restart may be needed");
    }
  }

  @Override
  public final void onAccuracyChanged(Sensor sensor, int accuracy) {

  }

  @Override
  public final void onSensorChanged(SensorEvent event){
    gravity[0] = event.values[0];
    gravity[1] = event.values[1];
    gravity[2] = event.values[2];
  }

  public static float signify(float a){
    if(a<0.005)a=0;
    return (float)(((float)((int)(a*1000)))/1000.0);
  }

  public float[] getRotation() {
    SensorManager.getRotationMatrix(rotationMatrix,null,gravity,geomagnetic);
    SensorManager.getOrientation(rotationMatrix, xyz);
    xyz[0] = signify((float)(xyz[0]*(180/Math.PI)));
    xyz[1] = signify((float)(xyz[1]*(180/Math.PI)));
    xyz[2] = signify((float)(xyz[2]*(180/Math.PI)));
    return xyz;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SensorManager mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
    List<Sensor> sensors = mgr.getSensorList(Sensor.TYPE_ALL);
    for (Sensor sensor : sensors) {
      Log.d("Sensors", "" + sensor.getName());
    }

    accel = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    mag = mgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    setContentView(R.layout.activity_ftc_controller);

    mgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    mgr.registerListener(new Magneto(this), mag, SensorManager.SENSOR_DELAY_NORMAL);

    gyro = (TextView) findViewById(R.id.gyroView);
    utility = new Utility(this);
    context = this;
    entireScreenLayout = (LinearLayout) findViewById(R.id.entire_screen);
    buttonMenu = (ImageButton) findViewById(R.id.menu_buttons);
    buttonMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openOptionsMenu();
      }
    });

    textDeviceName = (TextView) findViewById(R.id.textDeviceName);
    textWifiDirectStatus = (TextView) findViewById(R.id.textWifiDirectStatus);
    textRobotStatus = (TextView) findViewById(R.id.textRobotStatus);
    textOpMode = (TextView) findViewById(R.id.textOpMode);
    textErrorMessage = (TextView) findViewById(R.id.textErrorMessage);
    textGamepad[0] = (TextView) findViewById(R.id.textGamepad1);
    textGamepad[1] = (TextView) findViewById(R.id.textGamepad2);
    immersion = new ImmersiveMode(getWindow().getDecorView());
    dimmer = new Dimmer(this);
    dimmer.longBright();
    Restarter restarter = new RobotRestarter();

    updateUI = new UpdateUI(this, dimmer);
    updateUI.setRestarter(restarter);
    updateUI.setTextViews(textWifiDirectStatus, textRobotStatus,
        textGamepad, textOpMode, textErrorMessage, textDeviceName);
    callback = updateUI.new Callback();

    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    preferences = PreferenceManager.getDefaultSharedPreferences(this);

    hittingMenuButtonBrightensScreen();

    if (USE_DEVICE_EMULATION) { HardwareFactory.enableDeviceEmulation(); }
  }

  @Override
  protected void onStart() {
    super.onStart();

    // save 4MB of logcat to the SD card
    RobotLog.writeLogcatToDisk(this, 4 * 1024);

    Intent intent = new Intent(this, FtcRobotControllerService.class);
    bindService(intent, connection, Context.BIND_AUTO_CREATE);

    utility.updateHeader(Utility.NO_FILE, R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);

    callback.wifiDirectUpdate(WifiDirectAssistant.Event.DISCONNECTED);

    entireScreenLayout.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        dimmer.handleDimTimer();
        return false;
      }
    });

  }

  Camera.PictureCallback camHolla = new Camera.PictureCallback() {
    public void onPictureTaken(byte[] data, Camera camera) {
      Log.e("Started","To log cam");
      String photoFile = "F_Auton.jpg";
      File sdDir = Environment
              .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

      String filename =sdDir + File.separator + photoFile;

      File pictureFile = new File(filename);
      Log.e("Camera","Was successmuch yes yes 100");
      try {
        FileOutputStream fos = new FileOutputStream(pictureFile);
        fos.write(data);
        fos.close();
      } catch (Exception error) {
        Log.d("File not saved: ", error.getMessage());
      }

    }// end onPictureTaken()
  };// jpegCallback implementation

  public void setCamera(Camera camera) {
    if (mCamera == camera) { return; }

    stopPreviewAndFreeCamera();

    mCamera = camera;

    if (mCamera != null) {
      List<Camera.Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
      try {
        mCamera.setPreviewDisplay(mHolder);
      } catch (IOException e) {
        e.printStackTrace();
      }

      // Important: Call startPreview() to start updating the preview
      // surface. Preview must be started before you can take a picture.
      mCamera.startPreview();
    }
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {

  }

  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    //mCamera.startPreview();
  }

  public void surfaceDestroyed(SurfaceHolder holder) {
    // Surface will be destroyed when we return, so stop the preview.
    if (mCamera != null) {
      // Call stopPreview() to stop updating the preview surface.
      mCamera.stopPreview();
    }
  }

  /**
   * When this function returns, mCamera will be null.
   */
  private void stopPreviewAndFreeCamera() {

    if (mCamera != null) {
      // Call stopPreview() to stop updating the preview surface.
      mCamera.stopPreview();

      // Important: Call release() to release the camera for use by other
      // applications. Applications should release the camera immediately
      // during onPause() and re-open() it during onResume()).
      mCamera.release();

      mCamera = null;
    }
  }

  public File getImageFile() {
    File sDir = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    File sFile = new File(sDir.getPath()+File.separator+"F_Auton.jpg");
    return sFile;
  }

  public void takePic() {
    tookPic = false;
    FawkesRCActivity.this.runOnUiThread(new Runnable() {
      public void run() {
        mSurfaceView = (SurfaceView) findViewById(R.id.cameraPreview);
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(FawkesRCActivity.this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        int camId = 0;
        for (int i = 0; i < numberOfCameras; i++) {
          Camera.CameraInfo info = new Camera.CameraInfo();
          Camera.getCameraInfo(i, info);
          if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            camId = i;
          }
        }
        if (safeCameraOpen(camId)) Log.e("Camera", "All Good");
        else Log.e("Camera", "Errored");
        try {
          SurfaceTexture surfaceTexture = new SurfaceTexture(0);
          mCamera.setPreviewTexture(surfaceTexture);
          //    mCamera.startPreview();
        } catch (Exception e) {
          e.printStackTrace();
        }

        try {
          Log.e("Camera", "Trying to take pic");
          Camera.Parameters parameters = mCamera.getParameters();
          List<String> focusModes = parameters.getSupportedFocusModes();
          if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
          }
          mCamera.setParameters(parameters);
          mCamera.stopPreview();
          mCamera.startPreview();
          mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {
              mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
                public void onPictureTaken(byte[] data, Camera camera) {
                  Log.e("Camera", "Started to log cam");
                  String photoFile = "F_Auton.jpg";
                  File sdDir = Environment
                          .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                  String filename = sdDir + File.separator + photoFile;

                  File pictureFile = new File(filename);
                  Log.e("Camera", "Was success much yes yes 100");

                  try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    Log.e("Image","Saved image.");
                    tookPic = true;
                  } catch (Exception error) {
                    Log.e("File", error.getMessage());
                  }

                }// end onPictureTaken()
              });// jpegCallback implementation);
              Log.e("Camera", "Took pic");
//          mCamera.stopPreview();
              //         mCamera.startPreview();

            }
          });
        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();

  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();

    if (controllerService != null) unbindService(connection);

    RobotLog.cancelWriteLogcatToDisk(this);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus){
    super.onWindowFocusChanged(hasFocus);
    // When the window loses focus (e.g., the action overflow is shown),
    // cancel any pending hide action. When the window gains focus,
    // hide the system UI.
    if (hasFocus) {
      if (ImmersiveMode.apiOver19()){
        // Immersive flag only works on API 19 and above.
        immersion.hideSystemUI();
      }
    } else {
      immersion.cancelSystemUIHide();
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.ftc_robot_controller, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_restart_robot:
        dimmer.handleDimTimer();
        Toast.makeText(context, "Restarting Robot", Toast.LENGTH_SHORT).show();
        requestRobotRestart();
        return true;
      case R.id.action_settings:
        // The string to launch this activity must match what's in AndroidManifest of FtcCommon for this activity.
        Intent settingsIntent = new Intent("com.qualcomm.ftccommon.FtcRobotControllerSettingsActivity.intent.action.Launch");
        startActivityForResult(settingsIntent, LaunchActivityConstantsList.FTC_ROBOT_CONTROLLER_ACTIVITY_CONFIGURE_ROBOT);
        return true;
      case R.id.action_exit_app:
        finish();
        return true;
      case R.id.action_view_logs:
        // The string to launch this activity must match what's in AndroidManifest of FtcCommon for this activity.
        Intent viewLogsIntent = new Intent("com.qualcomm.ftccommon.ViewLogsActivity.intent.action.Launch");
        viewLogsIntent.putExtra(LaunchActivityConstantsList.VIEW_LOGS_ACTIVITY_FILENAME, RobotLog.getLogFilename(this));
        startActivity(viewLogsIntent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    // don't destroy assets on screen rotation
  }

  @Override
  protected void onActivityResult(int request, int result, Intent intent) {
    if (request == REQUEST_CONFIG_WIFI_CHANNEL) {
      if (result == RESULT_OK) {
        Toast toast = Toast.makeText(context, "Configuration Complete", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        showToast(toast);
      }
    }
    if (request == LaunchActivityConstantsList.FTC_ROBOT_CONTROLLER_ACTIVITY_CONFIGURE_ROBOT) {
      if (result == RESULT_OK) {
        Serializable extra = intent.getSerializableExtra(FawkesRCActivity.CONFIGURE_FILENAME);
        if (extra != null) {
          utility.saveToPreferences(extra.toString(), R.string.pref_hardware_config_filename);
          utility.updateHeader(Utility.NO_FILE, R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
        }
      }
    }
  }

  public void onServiceBind(FtcRobotControllerService service) {
    DbgLog.msg("Bound to Ftc Controller Service");
    controllerService = service;
    updateUI.setControllerService(controllerService);

    callback.wifiDirectUpdate(controllerService.getWifiDirectStatus());
    callback.robotUpdate(controllerService.getRobotStatus());
    requestRobotSetup();
  }

  private void requestRobotSetup() {
    if (controllerService == null) return;

    FileInputStream fis = fileSetup();
    // if we can't find the file, don't try and build the robot.
    if (fis == null) { return; }

    HardwareFactory factory;

    // Modern Robotics Factory for use with Modern Robotics hardware
    HardwareFactory modernRoboticsFactory = new HardwareFactory(context);
    modernRoboticsFactory.setXmlInputStream(fis);
    factory = modernRoboticsFactory;

    eventLoop = new FtcEventLoop(factory, new FtcOpModeRegister(), callback, this);

    controllerService.setCallback(callback);
    controllerService.setupRobot(eventLoop);
  }

  private FileInputStream fileSetup() {

    final String filename = Utility.CONFIG_FILES_DIR
        + utility.getFilenameFromPrefs(R.string.pref_hardware_config_filename, Utility.NO_FILE) + Utility.FILE_EXT;

    FileInputStream fis;
    try {
      fis = new FileInputStream(filename);
    } catch (FileNotFoundException e) {
      String msg = "Cannot open robot configuration file - " + filename;
      utility.complainToast(msg, context);
      DbgLog.msg(msg);
      utility.saveToPreferences(Utility.NO_FILE, R.string.pref_hardware_config_filename);
      fis = null;
    }
    utility.updateHeader(Utility.NO_FILE, R.string.pref_hardware_config_filename, R.id.active_filename, R.id.included_header);
    return fis;
  }

  private void requestRobotShutdown() {
    if (controllerService == null) return;
    controllerService.shutdownRobot();
  }

  private void requestRobotRestart() {
    requestRobotShutdown();
    requestRobotSetup();
  }

  protected void hittingMenuButtonBrightensScreen() {
    ActionBar actionBar = getActionBar();
    if (actionBar != null) {
      actionBar.addOnMenuVisibilityListener(new ActionBar.OnMenuVisibilityListener() {
        @Override
        public void onMenuVisibilityChanged(boolean isVisible) {
          if (isVisible) {
            dimmer.handleDimTimer();
          }
        }
      });
    }
  }

  public void showToast(final Toast toast) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        toast.show();
      }
    });
  }

  private boolean safeCameraOpen(int id) {
    boolean qOpened = false;

    try {
      releaseCameraAndPreview();
      mCamera = Camera.open(id);
      qOpened = (mCamera != null);
    } catch (Exception e) {
      Log.e("Camera", "failed to open Camera");
      e.printStackTrace();
    }

    return qOpened;
  }

  private void releaseCameraAndPreview() {
    setCamera(null);
    if (mCamera != null) {
      mCamera.release();
      mCamera = null;
    }
  }

}
