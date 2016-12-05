package org.fawkesbots.rc.vendetta.Tests;

import android.app.Activity;
import android.os.Looper;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.vendetta.Gyro;

/**
 * Created by hello_000 on 10/23/2016.
 */
@TeleOp(
        name="GyroTest",
        group="Tests"
)
public class GyroTest extends OpMode {
    public Gyro Dante;
    public float theta, drift, readings;
    public void init() {
        Dante = new Gyro(hardwareMap);
        Dante.init();
        readings = 0;
        drift = 0;
    }
    public void loop() {
        if(readings<500) drift+=Dante.orientation[0];
        if(readings==500) drift/=500;
        readings++;
        if(readings>500) {
            theta = Dante.orientation[0];
            if (theta < 0) theta = (float) 2.0f * (float) Math.PI + theta;
            theta = (theta - drift) * Dante.DEG;
            telemetry.addData("Gyro:", "X: " + theta + "\nDrift: " + (drift*Dante.DEG));
        }
    }
    public void stop() {
        Dante.stop();
    }
}
