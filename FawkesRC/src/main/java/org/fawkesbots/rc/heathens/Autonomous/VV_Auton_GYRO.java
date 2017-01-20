package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.heathens.Hardware.HardwareServoBasic;
import org.fawkesbots.rc.vendetta.Auton;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;
import org.fawkesbots.rc.vendetta.Gyro;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

@Autonomous(
        name="V_V GYRO",
        group="Finished"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class VV_Auton_GYRO extends Auton {
    public HardwareMecanumWithEncoders EncodedDrive;
    public HardwareLauncher Launcher; public HardwareServoBasic Flicker;
    public HardwareCollector Collector;
    public FawkesCam AutonCam; public BeaconUtil BeaconFinder;
    public Gyro g;
    public float TILE=24; //length of a tile

    public int side = -1;

    @Override
    public void runOpMode() throws InterruptedException {
        side = ((FtcRobotControllerActivity)hardwareMap.appContext).color_side;

        g = new Gyro(hardwareMap, telemetry);
        g.init();
        float[] offset = {0,0,0};
        g.setOffset(offset);

        EncodedDrive = new HardwareMecanumWithEncoders(hardwareMap,telemetry,this);
        EncodedDrive.hardwareSetup(); EncodedDrive.setSides(1, 1, 1, 1);
        Flicker = new HardwareServoBasic(hardwareMap,0.82f,0.0f,"flicker"); Launcher = new HardwareLauncher(hardwareMap);
        Flicker.hardwareSetup(); Launcher.hardwareSetup();
        Collector = new HardwareCollector(hardwareMap); Collector.hardwareSetup();
        AutonCam = new FawkesCam(hardwareMap,this);
        BeaconFinder = new BeaconUtil(telemetry,EncodedDrive);
        log("initialized");

        waitForStart();

        g.setOffset(g.getRotation());
        EncodedDrive.powerAll(-.78f, -.78f, -.78f, -.78f);
        while(Math.abs(g.getRotation()[0]-30)>5 && Math.abs(g.getRotation()[0]-360-30)>5);
        EncodedDrive.powerAll(0,0,0,0);
/*
        EncodedDrive.forwardEncoded(4.0f,.78f);

        Launcher.fire(-.78f); sleep(600); Launcher.fire(0.0f); //launch once

        Collector.collect(.78f); sleep(1200); Launcher.fire(-.78f);
        sleep(300); Launcher.fire(0.0f);
        Flicker.flick(true);
        sleep(1500);
        Collector.collect(0.0f); Flicker.flick(false); //collect another ball
        Launcher.fire(-.78f); sleep(600); Launcher.fire(0.0f); //launch again
        EncodedDrive.forwardEncoded( 20.0f, 0.78f);
        //log("moved forward");

        BeaconFinder.moveToBeacon(side);

        int[] colors;
        try {
            colors = AutonCam.getBeaconColors();
            log((((side==-1)?"Red":"Blue"))+" on " + ((colors[0] > colors[1]) ? "Left" : "Right"));
            BeaconFinder.hitBeacon(side, colors);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
/*
        EncodedDrive.forwardEncoded(-TILE,0.78f);
        EncodedDrive.rotateEncoded(-side*16.7f,.78f);
        EncodedDrive.forwardEncoded(2*TILE,.78f);
        EncodedDrive.rotateEncoded(side*16.7f,.78f);

        try {
            colors = AutonCam.getBeaconColors();
            log((((side==-1)?"Red":"Blue"))+" on " + ((colors[0] > colors[1]) ? "Left" : "Right"));
            BeaconFinder.hitBeacon(side, colors);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        EncodedDrive.forwardEncoded(-TILE,0.78f);
        EncodedDrive.rotateEncoded(side*24.7f,.78f);
        EncodedDrive.forwardEncoded(TILE*3,0.78f);
        log("finished");*/


        //EncodedDrive.forwardEncoded(-1.5f*TILE,.78f);
        //EncodedDrive.rotateEncoded(side*3.7f,.78f);
    }

    @Override
    public void onStop() {
        g.stop();
    }

}
