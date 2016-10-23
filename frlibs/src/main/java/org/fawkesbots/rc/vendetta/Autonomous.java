package org.fawkesbots.rc.vendetta;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by hello_000 on 10/23/2016.
 */
public abstract class Autonomous extends LinearOpMode {
    boolean ran = false;

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        autonomous();
    }

    public abstract void initialize();

    public abstract void autonomous() throws InterruptedException;
}
