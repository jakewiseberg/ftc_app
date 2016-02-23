package org.fawkes.fawkesrc;

import android.content.Context;
import android.hardware.*;
import android.hardware.SensorEventListener;

/**
 * Created by hello_000 on 1/18/2016.
 */
public class Magneto implements SensorEventListener {

    FawkesRCActivity thread;

    public Magneto(Context main){
        thread = (FawkesRCActivity) main;
    }
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        thread.geomagnetic[0] = event.values[0];
        thread.geomagnetic[1] = event.values[1];
        thread.geomagnetic[2] = event.values[2];
    }

}
