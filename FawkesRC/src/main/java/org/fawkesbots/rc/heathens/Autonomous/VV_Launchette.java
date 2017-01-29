package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.fawkesbots.rc.heathens.Autonomous.BeaconUtil;
import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.heathens.Hardware.HardwareServoBasic;
import org.fawkesbots.rc.vendetta.Auton;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

@Autonomous(
        name= "VV Launch",
        group="Finished"
)

public class VV_Launchette extends Auton {
    public HardwareMecanumWithEncoders EncodedDrive;
    public HardwareLauncher Launcher; public HardwareServoBasic Flicker;
    public HardwareCollector Collector;


    public float TILE=24; //length of a tile

    public int side = -1;

    @Override
    public void runOpMode() throws InterruptedException {
        side = ((FtcRobotControllerActivity) hardwareMap.appContext).color_side;

        EncodedDrive = new HardwareMecanumWithEncoders(hardwareMap, telemetry, this);
        EncodedDrive.hardwareSetup();
        EncodedDrive.setSides(1, 1, 1, 1);
        Flicker = new HardwareServoBasic(hardwareMap, 0.82f, 0.2f, "flicker");
        Launcher = new HardwareLauncher(hardwareMap);
        Flicker.hardwareSetup();
        Launcher.hardwareSetup();
        Collector = new HardwareCollector(hardwareMap);
        Collector.hardwareSetup();


        waitForStart();
        sleep(5000);

        EncodedDrive.forwardEncoded(0.2f * TILE, 0.78f);
        Launcher.fire(-.78f);
        sleep(600);
        Launcher.fire(0.0f); //launch once
        Collector.collect(.78f);
        sleep(1200);
        Launcher.fire(-.78f);
        sleep(300);
        Launcher.fire(0.0f);
        Flicker.flick(true);
        sleep(1500);
        Collector.collect(0.0f);
        Flicker.flick(false); //collect another ball
        ;
        Launcher.fire(-.78f);
        sleep(600);
        Launcher.fire(0.0f);
        EncodedDrive.forwardEncoded(1.7f * TILE, 0.78f);//launch again
    }}