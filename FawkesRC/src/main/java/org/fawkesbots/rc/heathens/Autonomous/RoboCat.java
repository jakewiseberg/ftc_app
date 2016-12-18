package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareFlicker;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
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
public class RoboCat extends Auton {
    public HardwareMecanumWithEncoders EncodedDrive;
    public HardwareLauncher Launcher; public HardwareFlicker Flicker;
    public HardwareCollector Collector;
    public FawkesCam AutonCam; public BeaconUtil BeaconFinder;

    public float TILE=24; //length of a tile

    public int side = -1;

    @Override
    public void runOpMode() throws InterruptedException {
        side = ((FtcRobotControllerActivity)hardwareMap.appContext).color_side;
        EncodedDrive = new HardwareMecanumWithEncoders(hardwareMap,telemetry);
        EncodedDrive.hardwareSetup(); EncodedDrive.setSides(1, 1, 1, 1);
        Flicker = new HardwareFlicker(hardwareMap); Launcher = new HardwareLauncher(hardwareMap);
        Flicker.hardwareSetup(); Launcher.hardwareSetup();
        Collector = new HardwareCollector(hardwareMap); Collector.hardwareSetup();
        AutonCam = new FawkesCam(hardwareMap);
        BeaconFinder = new BeaconUtil(telemetry,EncodedDrive);
        log("initialized");
        waitForStart();
        BeaconFinder.moveToBeacon(side);
        int[] colors = AutonCam.getBeaconColors();
        log("red on " + ((colors[0] > colors[1]) ? "Left" : "Right"));
        BeaconFinder.hitBeacon(side, colors);
        EncodedDrive.forwardEncoded(-7f,.78f);
        EncodedDrive.rotateEncoded(-side*16.7f,.78f);
        EncodedDrive.forwardEncoded(2*TILE,.78f);
        EncodedDrive.rotateEncoded(side*16.7f,.78f);
        EncodedDrive.forwardEncoded(-7f,.78f);
        colors = AutonCam.getBeaconColors();
        BeaconFinder.hitBeacon(side, colors);
        EncodedDrive.strafeEncoded(2 * TILE, .78f);
        EncodedDrive.forwardEncoded(-2.5f * TILE, .78f);
        Launcher.fire(.78f); sleep(1400); Launcher.fire(0.0f);
        Collector.collect(-.78f); sleep(1000); Flicker.flick(true); sleep(400);
        Collector.collect(0.0f); Flicker.flick(false);
        Launcher.fire(.78f); sleep(1400); Launcher.fire(0.0f);
        EncodedDrive.forwardEncoded(-4*TILE,.78f);
        log("finished");
    }
}
