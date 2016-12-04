package org.fawkesbots.rc.heathens.Autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;

@Autonomous(
        name="CameraTest",
        group="Test"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class Simple_CV extends LinearOpMode {
    public FawkesCam Jenny;

    @Override
    public void runOpMode() throws InterruptedException {
        Jenny = new FawkesCam(hardwareMap);
        waitForStart();
        Log.e("Cam","Taking");
        int[] colors = Jenny.getBeaconColors();
        telemetry.addData("Left/Right",colors[0]+", "+colors[1]);
        telemetry.update();
        Log.e("Cam",colors[0]+", "+colors[1]);
    }

}
