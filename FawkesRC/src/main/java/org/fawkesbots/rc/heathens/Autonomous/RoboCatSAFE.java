
        package org.fawkesbots.rc.heathens.Autonomous;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

        import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
        import org.fawkesbots.rc.heathens.Hardware.HardwareFlickr;
        import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
        import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
        import org.fawkesbots.rc.vendetta.Auton;
        import org.fawkesbots.rc.vendetta.Camera.FawkesCam;
        import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

@Autonomous(
        name="R_C Safe",
        group="Finished"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class RoboCatSAFE extends Auton {
    public HardwareMecanumWithEncoders EncodedDrive;
    public HardwareLauncher Launcher; public HardwareFlickr Flicker;
    public HardwareCollector Collector;

    public float TILE=24; //length of a tile

    @Override
    public void runOpMode() throws InterruptedException {

        EncodedDrive = new HardwareMecanumWithEncoders(hardwareMap,telemetry);
        EncodedDrive.hardwareSetup(); EncodedDrive.setSides(1, 1, 1, 1);
        Flicker = new HardwareFlickr(hardwareMap); Launcher = new HardwareLauncher(hardwareMap);
        Flicker.hardwareSetup(); Launcher.hardwareSetup();
        Collector = new HardwareCollector(hardwareMap); Collector.hardwareSetup();
        log("initialized");

        waitForStart();

        EncodedDrive.forwardEncoded(TILE*1.7f, .78f); //move up one tile to launch

        Collector.collect(+.78f); sleep(1400);
        Launcher.fire(-.78f); sleep(1400); Launcher.fire(0.0f); //shoots ball

        Collector.collect(+.78f); sleep(1000);
        Flicker.flickUp(); sleep(1000); Flicker.flickDown();
        Collector.collect(0.0f);

        Launcher.fire(-.78f); sleep(1400); Launcher.fire(0.0f); //shoots again

        EncodedDrive.forwardEncoded(TILE*2,.78f); //move to center piece

        log("finished");
    }
}