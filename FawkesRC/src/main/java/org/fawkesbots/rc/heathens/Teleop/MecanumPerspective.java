package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecPerspective;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * Created by hello_000 on 10/23/2016.
 */
@TeleOp(
        name="Mecanum Perspective",
        group="Qualifiers"
)
public class MecanumPerspective extends OpMode {
    public HardwareMecPerspective Drive;
    public HardwareCollector Sweeper;
    public HardwareLauncher Launcher;

    BNO055IMU imu;

    public void init() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        Drive = new HardwareMecPerspective(hardwareMap, imu);
        Sweeper = new HardwareCollector(hardwareMap);
        Launcher = new HardwareLauncher(hardwareMap);
        Drive.hardwareSetup();

        imu.initialize(parameters);
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

    }
    public void loop() {
        Drive.perspective(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
        Sweeper.collect(gamepad2.left_stick_y);
        Launcher.fire((gamepad2.right_trigger>0.3)?-0.78f:(gamepad2.left_trigger>0.3)?0.78f:0);
    }
}
