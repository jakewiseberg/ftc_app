package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.vendetta.Auton;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

@Autonomous(
        name="R_C",
        group="Finished"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class RoboCatSAFE extends LinearOpMode {
    public HardwareMecanumWithEncoders EncodedDrive;
    public FawkesCam AutonCam; public BeaconUtil BeaconFinder;

    public float TILE=24; //length of a tile

    public int side = 1;

    public void log(String s) {
        telemetry.addData("Autonomous",s);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        side = ((FtcRobotControllerActivity)hardwareMap.appContext).color_side;
        EncodedDrive = new HardwareMecanumWithEncoders(hardwareMap,telemetry);
        EncodedDrive.hardwareSetup(); EncodedDrive.setSides(1, 1, 1, 1);
        AutonCam = new FawkesCam(hardwareMap);
        BeaconFinder = new BeaconUtil(telemetry);
        log("initialized");
        waitForStart();
        BeaconFinder.moveToBeacon(side);
        int[] colors = AutonCam.getBeaconColors();
        log("red on "+((colors[0]>colors[1])?"Left":"Right"));
        BeaconFinder.hitBeacon(side,colors);
        EncodedDrive.forwardEncoded(-TILE,0.78f);
        log("finished");
    }
}
