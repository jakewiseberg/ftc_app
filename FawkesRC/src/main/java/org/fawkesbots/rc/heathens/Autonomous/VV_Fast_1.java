package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.fawkesbots.rc.heathens.Hardware.AltEncoders;
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
        name="V_V ALT",
        group="Finished"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class VV_Fast_1 extends Auton {
    public AltEncoders EncodedDrive;
    public HardwareLauncher Launcher; public HardwareServoBasic Flicker;
    public HardwareCollector Collector;
    public FawkesCam AutonCam; public BeaconUtil BeaconFinder;
   // public HardwareSensors Ultra;
    public float TILE=24; //length of a tile

    public int side = -1;

    @Override
    public void runOpMode() throws InterruptedException {
        side = ((FtcRobotControllerActivity)hardwareMap.appContext).color_side;


        EncodedDrive = new AltEncoders(hardwareMap,telemetry,this);
        EncodedDrive.hardwareSetup(); EncodedDrive.setSides(1, 1, 1, 1);
        Flicker = new HardwareServoBasic(hardwareMap,0.82f,0.2f,"flicker"); Launcher = new HardwareLauncher(hardwareMap);
        Flicker.hardwareSetup(); Launcher.hardwareSetup();
        Collector = new HardwareCollector(hardwareMap); Collector.hardwareSetup();
        AutonCam = new FawkesCam(hardwareMap,this);
        //Ultra= new HardwareSensors(hardwareMap);
        log("initialized");

        waitForStart();

        EncodedDrive.moveStraight(TILE,.78f);
    }
}
