package org.fawkesbots.rc.vendetta;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.qualcomm.robotcore.hardware.HardwareMap;
/**
 * Created by hello_000 on 10/23/2016.
 */
public class Gyro implements SensorEventListener {
    private SensorManager virgil; //let virgil guide us
    public Context app;
    float[] gData = new float[3];
    float[] mData = new float[3];
    public float[] orientation = new float[3];
    float[] Rmat = new float[9];
    float[] R2 = new float[9];
    float[] Imat = new float[9];
    boolean haveGrav = false;
    boolean haveAccel = false;
    boolean haveMag = false;
    public final float DEG = 180.0f/(float)Math.PI;
    public final String TAG = "GYRO//";

    public Gyro(HardwareMap h) {
        app = h.appContext; //HEHEHEHEHHEHHEHEHEHEH NIGBONIE
    }
    public boolean init() {
        virgil = (SensorManager) app.getSystemService(Context.SENSOR_SERVICE);
        Sensor gsensor = virgil.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Sensor asensor = virgil.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor msensor = virgil.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        virgil.registerListener(this, gsensor, SensorManager.SENSOR_DELAY_UI);
        virgil.registerListener(this, asensor, SensorManager.SENSOR_DELAY_UI);
        virgil.registerListener(this, msensor, SensorManager.SENSOR_DELAY_UI);
        return true;
    }
    public boolean stop() {
        virgil.unregisterListener(this);
        return true;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onSensorChanged(SensorEvent event) {
        float[] data;
        switch( event.sensor.getType() ) {
            case Sensor.TYPE_GRAVITY:
                gData[0] = event.values[0];
                gData[1] = event.values[1];
                gData[2] = event.values[2];
                haveGrav = true;
                break;
            case Sensor.TYPE_ACCELEROMETER:
                if (haveGrav) break;
                gData[0] = event.values[0];
                gData[1] = event.values[1];
                gData[2] = event.values[2];
                haveAccel = true;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mData[0] = event.values[0];
                mData[1] = event.values[1];
                mData[2] = event.values[2];
                haveMag = true;
                break;
            default:
                return;
        }

        if ((haveGrav || haveAccel) && haveMag) {
            SensorManager.getRotationMatrix(Rmat, Imat, gData, mData);
            SensorManager.remapCoordinateSystem(Rmat,
                    SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, R2);
            SensorManager.getOrientation(R2, orientation);
        }
    }

}
