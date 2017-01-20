package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.fawkesbots.rc.vendetta.Auton;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;

@Autonomous(
        name="CameraTest",
        group="Sensor"
)
/**
 * Created by Priansh on 11/30/16.
 */
public class Simple_CV extends Auton {
    public FawkesCam AutonCam;

    @Override
    public void runOpMode() throws InterruptedException {
        AutonCam = new FawkesCam(hardwareMap, this);
        waitForStart();
        int[] colors = AutonCam.getBeaconColors();
        int side=-1;
        log((((side==-1)?"Red":"Blue"))+" on " + ((colors[0] > colors[1]) ? "Left" : "Right"));
        telemetry.addData("Left/Right", colors[0] + ", " + colors[1]);
        telemetry.update();
    }

}
