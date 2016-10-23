package org.fawkesbots.rc.vendetta;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by hello_000 on 10/23/2016.
 */
public abstract class Autonomous extends OpMode {
    boolean ran = false;
    public void loop() {
        if(!ran) {
            try {
                autonomous();
            } catch(InterruptedException e) {
                Log.e("Interrupted", e.getMessage());
            }
            ran = true;
        }
    }
    public abstract void autonomous() throws InterruptedException;
    public final void sleep(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }
}
