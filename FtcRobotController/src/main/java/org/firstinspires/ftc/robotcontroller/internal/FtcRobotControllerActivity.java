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

package org.firstinspires.ftc.robotcontroller.internal;

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
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.Image;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.blocks.ftcrobotcontroller.BlocksActivity;
import com.google.blocks.ftcrobotcontroller.ProgrammingModeActivity;
import com.google.blocks.ftcrobotcontroller.ProgrammingModeControllerImpl;
import com.google.blocks.ftcrobotcontroller.runtime.BlocksOpMode;
import com.qualcomm.ftccommon.AboutActivity;
import com.qualcomm.ftccommon.ClassManagerFactory;
import com.qualcomm.ftccommon.Device;
import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.ftccommon.FtcEventLoopIdle;
import com.qualcomm.ftccommon.FtcRobotControllerService;
import com.qualcomm.ftccommon.FtcRobotControllerService.FtcRobotControllerBinder;
import com.qualcomm.ftccommon.FtcRobotControllerSettingsActivity;
import com.qualcomm.ftccommon.LaunchActivityConstantsList;
import com.qualcomm.ftccommon.ProgrammingModeController;
import com.qualcomm.ftccommon.Restarter;
import org.firstinspires.ftc.ftccommon.external.SoundPlayingRobotMonitor;
import com.qualcomm.ftccommon.UpdateUI;
import com.qualcomm.ftccommon.configuration.EditParameters;
import com.qualcomm.ftccommon.configuration.FtcLoadFileActivity;
import com.qualcomm.ftccommon.configuration.RobotConfigFile;
import com.qualcomm.ftccommon.configuration.RobotConfigFileManager;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.hardware.configuration.Utility;
import com.qualcomm.robotcore.robocol.PeerAppRobotController;
import com.qualcomm.robotcore.util.Dimmer;
import com.qualcomm.robotcore.util.ImmersiveMode;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.wifi.NetworkConnectionFactory;
import com.qualcomm.robotcore.wifi.NetworkType;
import com.qualcomm.robotcore.wifi.WifiDirectAssistant;

import org.firstinspires.ftc.robotcore.internal.AppUtil;
import org.firstinspires.inspection.RcInspectionActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FtcRobotControllerActivity extends Activity implements SurfaceHolder.Callback {

  public static final String TAG = "RCActivity";

  private static final int REQUEST_CONFIG_WIFI_CHANNEL = 1;
  private static final boolean USE_DEVICE_EMULATION = false;
  private static final int NUM_GAMEPADS = 2;

  public static final String NETWORK_TYPE_FILENAME = "ftc-network-type.txt";

  protected WifiManager.WifiLock wifiLock;
  protected RobotConfigFileManager cfgFileMgr;

  protected ProgrammingModeController programmingModeController;

  protected UpdateUI.Callback callback;
  protected Context context;
  protected Utility utility;
  protected AppUtil appUtil = AppUtil.getInstance();

  protected ImageButton buttonMenu;
  protected TextView textDeviceName;
  protected TextView textNetworkConnectionStatus;
  protected TextView textRobotStatus;
  protected TextView[] textGamepad = new TextView[NUM_GAMEPADS];
  protected TextView textOpMode;
  protected TextView textErrorMessage;
  protected ImmersiveMode immersion;

  protected UpdateUI updateUI;
  protected Dimmer dimmer;
  protected LinearLayout entireScreenLayout;

  protected FtcRobotControllerService controllerService;
  protected NetworkType networkType;

  protected FtcEventLoop eventLoop;
  protected Queue<UsbDevice> receivedUsbAttachmentNotifications;

  public int color_side = -1;

  SurfaceView mSurfaceView;
  SurfaceHolder mHolder;
  public Camera mCamera;
  public boolean tookPic=false;
  public RobotConfigFile configFile;


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
      RobotLog.vv(FtcRobotControllerService.TAG, "%s.controllerService=null", TAG);
      controllerService = null;
    }
  };

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(intent.getAction())) {
      UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
      if (usbDevice != null) {  // paranoia
        // We might get attachment notifications before the event loop is set up, so
        // we hold on to them and pass them along only when we're good and ready.
        if (receivedUsbAttachmentNotifications != null) { // *total* paranoia
          receivedUsbAttachmentNotifications.add(usbDevice);
          passReceivedUsbAttachmentsToEventLoop();
        }
      }
    }
  }

  protected void passReceivedUsbAttachmentsToEventLoop() {
    if (this.eventLoop != null) {
      for (;;) {
        UsbDevice usbDevice = receivedUsbAttachmentNotifications.poll();
        if (usbDevice == null)
          break;
        this.eventLoop.onUsbDeviceAttached(usbDevice);
      }
    }
    else {
      // Paranoia: we don't want the pending list to grow without bound when we don't
      // (yet) have an event loop
      while (receivedUsbAttachmentNotifications.size() > 100) {
        receivedUsbAttachmentNotifications.poll();
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    RobotLog.writeLogcatToDisk();
    RobotLog.vv(TAG, "onCreate()");

    receivedUsbAttachmentNotifications = new ConcurrentLinkedQueue<UsbDevice>();
    eventLoop = null;

    setContentView(R.layout.activity_ftc_controller);

    context = this;
    utility = new Utility(this);
    appUtil.setThisApp(new PeerAppRobotController(context));

    entireScreenLayout = (LinearLayout) findViewById(R.id.entire_screen);
    buttonMenu = (ImageButton) findViewById(R.id.menu_buttons);
    buttonMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AppUtil.getInstance().openOptionsMenuFor(FtcRobotControllerActivity.this);
      }
    });

    BlocksOpMode.setActivityAndWebView(this, (WebView) findViewById(R.id.webViewBlocksRuntime));

    ClassManagerFactory.processClasses();
    cfgFileMgr = new RobotConfigFileManager(this);

    // Clean up 'dirty' status after a possible crash
    configFile = cfgFileMgr.getActiveConfig();
    if (configFile.isDirty()) {
      configFile.markClean();
      cfgFileMgr.setActiveConfig(false, configFile);
    }
    color_side = ((configFile.getName().contains("ed"))?-1:1);
    Log.e("CONFIGFILE",configFile.getName()+color_side);

    textDeviceName = (TextView) findViewById(R.id.textDeviceName);
    textNetworkConnectionStatus = (TextView) findViewById(R.id.textNetworkConnectionStatus);
    textRobotStatus = (TextView) findViewById(R.id.textRobotStatus);
    textOpMode = (TextView) findViewById(R.id.textOpMode);
    textErrorMessage = (TextView) findViewById(R.id.textErrorMessage);
    textGamepad[0] = (TextView) findViewById(R.id.textGamepad1);
    textGamepad[1] = (TextView) findViewById(R.id.textGamepad2);
    immersion = new ImmersiveMode(getWindow().getDecorView());
    dimmer = new Dimmer(this);
    dimmer.longBright();

    programmingModeController = new ProgrammingModeControllerImpl(
        this, (TextView) findViewById(R.id.textRemoteProgrammingMode));

    updateUI = createUpdateUI();
    callback = createUICallback(updateUI);

    PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "");

    hittingMenuButtonBrightensScreen();

    if (USE_DEVICE_EMULATION) { HardwareFactory.enableDeviceEmulation(); }

    // save 4MB of logcat to the SD card
    wifiLock.acquire();
    callback.networkConnectionUpdate(WifiDirectAssistant.Event.DISCONNECTED);
    readNetworkType(NETWORK_TYPE_FILENAME);
    bindToService();
  }

  protected UpdateUI createUpdateUI() {
    Restarter restarter = new RobotRestarter();
    UpdateUI result = new UpdateUI(this, dimmer);
    result.setRestarter(restarter);
    result.setTextViews(textNetworkConnectionStatus, textRobotStatus, textGamepad, textOpMode, textErrorMessage, textDeviceName);
    return result;
  }

  protected UpdateUI.Callback createUICallback(UpdateUI updateUI) {
    UpdateUI.Callback result = updateUI.new Callback();
    result.setStateMonitor(new SoundPlayingRobotMonitor());
    return result;
  }

  @Override
  protected void onStart() {
    super.onStart();
    RobotLog.vv(TAG, "onStart()");

    // Undo the effects of shutdownRobot() that we might have done in onStop()
    updateUIAndRequestRobotSetup();

    cfgFileMgr.getActiveConfigAndUpdateUI();
    color_side = ((configFile.getName().contains("ed"))?-1:1);
    Log.e("CONFIGFILE", configFile.getName() + color_side);
    entireScreenLayout.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        dimmer.handleDimTimer();
        return false;
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    color_side = ((configFile.getName().contains("ed"))?-1:1);
    Log.e("CONFIGFILE", configFile.getName() + color_side);
    RobotLog.vv(TAG, "onResume()");
  }

  @Override
  public void onPause() {
    super.onPause();
    RobotLog.vv(TAG, "onPause()");
    if (programmingModeController.isActive()) {
      programmingModeController.stopProgrammingMode();
    }
  }

  @Override
  protected void onStop() {
    // Note: this gets called even when the configuration editor is launched. That is, it gets
    // called surprisingly often.
    super.onStop();
    RobotLog.vv(TAG, "onStop()");

    // We *do* shutdown the robot even when we go into configuration editing
    controllerService.shutdownRobot();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    RobotLog.vv(TAG, "onDestroy()");

    unbindFromService();
    wifiLock.release();
    RobotLog.cancelWriteLogcatToDisk();
  }

  protected void bindToService() {
    readNetworkType(NETWORK_TYPE_FILENAME);
    Intent intent = new Intent(this, FtcRobotControllerService.class);
    intent.putExtra(NetworkConnectionFactory.NETWORK_CONNECTION_TYPE, networkType);
    bindService(intent, connection, Context.BIND_AUTO_CREATE);
  }

  protected void unbindFromService() {
    if (controllerService != null) {
      unbindService(connection);
    }
  }



  Camera.PictureCallback camHolla = new Camera.PictureCallback() {
    public void onPictureTaken(byte[] data, Camera camera) {
      Log.e("Camera","Begin writing picture to file");
      String photoFile = "F_Auton.jpg";
      File sDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

      String filename = sDir + File.separator + photoFile;

      File pictureFile = new File(filename);
      try {
        FileOutputStream fos = new FileOutputStream(pictureFile);
        fos.write(data);
        fos.close();
      } catch (Exception error) {
        Log.e("Camera","File error: "+error.getMessage());
      }
    }
  };

  public void setCamera(Camera camera) {
    Log.e("Camera","Setting up camera");
    if(mCamera == camera) { return; }
    Log.e("Camera","Releasing camera in setcamera");
    stopPreviewAndFreeCamera();
    mCamera = camera;

    if(mCamera!=null) {
      List<Camera.Size> localSizes = mCamera.getParameters().getSupportedPreviewSizes();
      try {
        mCamera.setPreviewDisplay(mHolder);
      } catch(IOException e) {
        e.printStackTrace();
      }
      mCamera.startPreview();
      Log.e("Camera","Now using the camera");
    }
  }

  //@Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) { }

  public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

  }

  public void surfaceDestroyed(SurfaceHolder holder) {
    if(mCamera!=null) {
      mCamera.stopPreview();
      mCamera.release();
      Log.e("Camera","Releasing camera in surfacedestroyed");
      mCamera = null;
    }
  }

  public File getImageFile() {
    Log.e("Camera","Withdrawing image");
    File sDir = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    File sFile = new File(sDir.getPath()+File.separator+"F_Auton.jpg");
    Log.e("Camera","Returning image file");
    return sFile;
  }

  public void takePic() {
    Log.e("Camera","Began to take picture");
    tookPic = false;
    FtcRobotControllerActivity.this.runOnUiThread(new Runnable() {
      public void run() {
        mSurfaceView = (SurfaceView) findViewById(R.id.cameraPreview);
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(FtcRobotControllerActivity.this);
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
        if (safeCameraOpen(camId)) Log.e("Camera", "Opened camera");
        else Log.e("Camera", "Error opening camera");
        try {
          SurfaceTexture surfaceTexture = new SurfaceTexture(0);
          mCamera.setPreviewTexture(surfaceTexture);
          //    mCamera.startPreview();
        } catch (Exception e) {
          e.printStackTrace();
        }

        try {
          Log.e("Camera", "Trying to take picture");
          Camera.Parameters parameters = mCamera.getParameters();
          List<String> focusModes = parameters.getSupportedFocusModes();
          if (focusModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
            parameters.setFocusMode(Camera.Parameters.FLASH_MODE_TORCH);
          }
          mCamera.setParameters(parameters);
          mCamera.stopPreview();
          mCamera.startPreview();
          mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean b, Camera camera) {
              Log.e("Camera","Focused lens");
              mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
                public void onPictureTaken(byte[] data, Camera camera) {
                  Log.e("Camera", "Took picture");
                  String photoFile = "F_Auton.jpg";
                  File sdDir = Environment
                          .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                  String filename = sdDir + File.separator + photoFile;

                  File pictureFile = new File(filename);
                  Log.e("Camera", "Attempting to store image file");

                  try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    Log.e("Camera","Saved image file");
                    tookPic = true;
                  } catch (Exception error) {
                    Log.e("Camera", "File save error: "+error.getMessage());
                  }

                }// end onPictureTaken()
              });
              Log.e("Camera", "Done using camera");
            }
          });
        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    });
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
      Log.e("Camera","Releasing camera in stoppreviewandfreecamera");
      mCamera = null;
    }
  }
  public void writeNetworkTypeFile(String fileName, String fileContents){
    ReadWriteFile.writeFile(AppUtil.FIRST_FOLDER, fileName, fileContents);
  }

  public void log(String text) {
    ((TextView)(findViewById(R.id.loggable))).setText(text);
  }

  protected void readNetworkType(String fileName) {
    NetworkType defaultNetworkType;
    File directory = RobotConfigFileManager.CONFIG_FILES_DIR;
    File networkTypeFile = new File(directory, fileName);
    if (!networkTypeFile.exists()) {
      if (Build.MODEL.equals(Device.MODEL_410C)) {
        defaultNetworkType = NetworkType.SOFTAP;
      } else {
        defaultNetworkType = NetworkType.WIFIDIRECT;
      }
      writeNetworkTypeFile(NETWORK_TYPE_FILENAME, defaultNetworkType.toString());
    }

    String fileContents = readFile(networkTypeFile);
    networkType = NetworkConnectionFactory.getTypeFromString(fileContents);
    programmingModeController.setCurrentNetworkType(networkType);
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(NetworkConnectionFactory.NETWORK_CONNECTION_TYPE, networkType.toString());
    editor.commit();
  }

  private String readFile(File file) {
    return ReadWriteFile.readFile(file);
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
    int id = item.getItemId();

    if (id == R.id.action_programming_mode) {
      if (cfgFileMgr.getActiveConfig().isNoConfig()) {
        // Tell the user they must configure the robot before starting programming mode.
        AppUtil.getInstance().showToast(
            context, context.getString(R.string.toastConfigureRobotBeforeProgrammingMode));
      } else {
        Intent programmingModeIntent = new Intent(ProgrammingModeActivity.launchIntent);
        programmingModeIntent.putExtra(
            LaunchActivityConstantsList.PROGRAMMING_MODE_ACTIVITY_NETWORK_TYPE, networkType);
        startActivity(programmingModeIntent);
      }
      return true;
    } else if (id == R.id.action_inspection_mode) {
      Intent inspectionModeIntent = new Intent(RcInspectionActivity.rcLaunchIntent);
      startActivity(inspectionModeIntent);
      return true;
    }
    else if (id == R.id.action_blocks) {
      Intent blocksIntent = new Intent(BlocksActivity.launchIntent);
      startActivity(blocksIntent);
      return true;
    }
    else if (id == R.id.action_restart_robot) {
      dimmer.handleDimTimer();
      AppUtil.getInstance().showToast(context, context.getString(R.string.toastRestartingRobot));
      requestRobotRestart();
      return true;
    }
    else if (id == R.id.action_configure_robot) {
      EditParameters parameters = new EditParameters();
      Intent intentConfigure = new Intent(FtcLoadFileActivity.launchIntent);
      parameters.putIntent(intentConfigure);
      startActivityForResult(intentConfigure, LaunchActivityConstantsList.FTC_CONFIGURE_REQUEST_CODE_ROBOT_CONTROLLER);
    }
    else if (id == R.id.action_settings) {
      Intent settingsIntent = new Intent(FtcRobotControllerSettingsActivity.launchIntent);
      startActivityForResult(settingsIntent, LaunchActivityConstantsList.FTC_CONFIGURE_REQUEST_CODE_ROBOT_CONTROLLER);
      return true;
    }
    else if (id == R.id.action_about) {
      Intent intent = new Intent(AboutActivity.launchIntent);
      intent.putExtra(LaunchActivityConstantsList.ABOUT_ACTIVITY_CONNECTION_TYPE, networkType);
      startActivity(intent);
      return true;
    }
    else if (id == R.id.action_exit_app) {
      finish();
      return true;
    }

   return super.onOptionsItemSelected(item);
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
        AppUtil.getInstance().showToast(context, context.getString(R.string.toastWifiConfigurationComplete));
      }
    }
    if (request == LaunchActivityConstantsList.FTC_CONFIGURE_REQUEST_CODE_ROBOT_CONTROLLER) {
      // We always do a refresh, whether it was a cancel or an OK, for robustness
      cfgFileMgr.getActiveConfigAndUpdateUI();
    }
  }

  public void onServiceBind(FtcRobotControllerService service) {
    RobotLog.vv(FtcRobotControllerService.TAG, "%s.controllerService=bound", TAG);
    controllerService = service;
    updateUI.setControllerService(controllerService);

    updateUIAndRequestRobotSetup();
  }

  private void updateUIAndRequestRobotSetup() {
    if (controllerService != null) {
      callback.networkConnectionUpdate(controllerService.getNetworkConnectionStatus());
      callback.updateRobotStatus(controllerService.getRobotStatus());
      requestRobotSetup();
    }
  }

  private void requestRobotSetup() {
    if (controllerService == null) return;

    HardwareFactory factory;
    RobotConfigFile file = cfgFileMgr.getActiveConfigAndUpdateUI();
    HardwareFactory hardwareFactory = new HardwareFactory(context);
    hardwareFactory.setXmlPullParser(file.getXml());
    factory = hardwareFactory;

    eventLoop = new FtcEventLoop(factory, createOpModeRegister(), callback, this, programmingModeController);
    FtcEventLoopIdle idleLoop = new FtcEventLoopIdle(factory, callback, this, programmingModeController);

    controllerService.setCallback(callback);
    controllerService.setupRobot(eventLoop, idleLoop);

    passReceivedUsbAttachmentsToEventLoop();
  }

  public FtcEventLoop getEventLoop() {
    return this.eventLoop;
  }

  protected OpModeRegister createOpModeRegister() {
    return new FtcOpModeRegister();
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

  private boolean safeCameraOpen(int id) {
    boolean qOpened = false;
    Log.e("Camera","Attempting to open the camera");
    try {
      releaseCameraAndPreview();
      mCamera = Camera.open(id);
      qOpened = (mCamera != null);
    } catch (Exception e) {
      Log.e("Camera", "Failed to open camera");
      e.printStackTrace();
    }
    return qOpened;
  }

  private void releaseCameraAndPreview() {
    setCamera(null);
    if(mCamera!=null) {

      mCamera.release(); mCamera = null;
      Log.e("Camera","Releasing camera");
    }
  }

}
