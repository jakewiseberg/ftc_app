package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.heathens.Hardware.HardwareServoBasic;
import org.fawkesbots.rc.vendetta.Auton;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

/**
 * Created by krishenseth on 1/20/17.
 */



@Autonomous(
        name="V_V Fast",
        group="Finished"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class VV_Fast extends Auton {
    public HardwareMecanumWithEncoders EncodedDrive;
    public HardwareLauncher Launcher; public HardwareServoBasic Flicker;
    public HardwareCollector Collector;
    public FawkesCam AutonCam; public BeaconUtil BeaconFinder;

    public float TILE=24; //length of a tile

    public int side = -1;

    @Override
    public void runOpMode() throws InterruptedException {
        side = ((FtcRobotControllerActivity)hardwareMap.appContext).color_side;

        EncodedDrive = new HardwareMecanumWithEncoders(hardwareMap,telemetry,this);
        EncodedDrive.hardwareSetup(); EncodedDrive.setSides(1, 1, 1, 1);
        Flicker = new HardwareServoBasic(hardwareMap,0.82f,0.2f,"flicker"); Launcher = new HardwareLauncher(hardwareMap);
        Flicker.hardwareSetup(); Launcher.hardwareSetup();
        Collector = new HardwareCollector(hardwareMap); Collector.hardwareSetup();
        AutonCam = new FawkesCam(hardwareMap,this);
        BeaconFinder = new BeaconUtil(telemetry,EncodedDrive);
        log("initialized");

        waitForStart();

        EncodedDrive.forwardEncoded(0.4f*TILE, 0.78f);
        Launcher.fire(-.78f); sleep(600); Launcher.fire(0.0f); //launch once
        Collector.collect(.78f); sleep(1200); Launcher.fire(-.78f);sleep(300); Launcher.fire(0.0f); Flicker.flick(true);
        sleep(1500);
        Collector.collect(0.0f); Flicker.flick(false); //collect another ball
        ;Launcher.fire(-.78f); sleep(600); Launcher.fire(0.0f);
        EncodedDrive.rotateEncoded(-1, 0.78f);//launch again
        EncodedDrive.forwardEncoded(2.030f*TILE, 0.78f);
        EncodedDrive.rotateEncoded(-6.5f, 0.78f);




       int[] colors;
        try {
            colors = AutonCam.getBeaconColors();
            log((((side==-1)?"Red":"Blue"))+" on " + ((colors [0] > colors[1]) ? "Left" : "Right"));
            BeaconFinder.hitBeacon(side, colors);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        EncodedDrive.forwardEncoded(-0.5f*TILE,0.78f);
        EncodedDrive.strafeEncoded(2.2f*TILE, 0.78f);
        EncodedDrive.rotateEncoded(1.420f, 0.78f);


       try {
            colors = AutonCam.getBeaconColors();
            log((((side==-1)?"Red":"Blue"))+" on " + ((colors[0] > colors[1]) ? "Left" : "Right"));
            BeaconFinder.hitBeacon(side, colors);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        EncodedDrive.forwardEncoded(-0.5f*TILE,0.78f);
        EncodedDrive.rotateEncoded(-20.7f,.78f);
        EncodedDrive.forwardEncoded(TILE*2.5f,0.78f);
        log("finished");
    }
}
