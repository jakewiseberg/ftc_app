package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareFlickr;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanum;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * Created by hello_000 on 10/23/2016.
 */
@TeleOp(
        name="RoboCat Teleop",
        group="Finished"
)
public class RoboCat_Tele extends OpMode {
    /*

    CONTROLS

    Gamepad 1:
    - movement (left joystick all directions)
    - turning (right joystick)
    - collection (left trigger)

    Gamepad 2:
    - launcher (right trigger)

     */

    public HardwareMecanum Drive;
    public HardwareCollector Sweeper;
    public HardwareLauncher Launcher;
    public HardwareFlickr Flickr;
    //BNO055IMU imu;
    public void init() {

      /*  BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSgit pEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();*/

        //imu = hardwareMap.get(BNO055IMU.class, "imu");

        Drive = new HardwareMecanum(hardwareMap);
        Sweeper = new HardwareCollector(hardwareMap);
        Launcher = new HardwareLauncher(hardwareMap);
        Flickr = new HardwareFlickr(hardwareMap);
        Drive.hardwareSetup();
        Sweeper.hardwareSetup();
        Launcher.hardwareSetup();
        Flickr.hardwareSetup();
        Flickr.flickDown();

        //imu.initialize(parameters);
        //imu.startAccelerationIntegration(new Posi tion(), new Velocity(), 1000);*/
    }
    public void loop() {
        Drive.mecanum(-1*gamepad1.left_stick_x,-1*gamepad1.left_stick_y,gamepad1.right_stick_x);
        Sweeper.collect(gamepad2.left_stick_y);
        Launcher.fire((gamepad2.right_trigger>0.3)?-0.78f:(gamepad2.left_trigger>0.3)?0.78f:0);
        if (gamepad2.a) {
            Flickr.flickUp();
        }
        if (gamepad2.b) {
            Flickr.flickDown();
        }

        telemetry.addData("Flickr position: ", Flickr.getPos());
        telemetry.addData("A: ", gamepad2.a);
        //telemetry.addData("Gyro X:", imu.getPosition().x);
        //telemetry.addData("Gyro Y:", imu.getPosition().y);
       // telemetry.addData("Gyro Z:", imu.getPosition().z);

    }
}
