package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.Sentinel;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * Created by Priansh on 1/6/17.
 */
public class HardwareGyro extends Sentinel {
    public BNO055IMU imu;
    Orientation angles;
    Acceleration gravity;
    HardwareMap hM;
    public HardwareGyro(HardwareMap h) {
        super(h); hM=h;
    }
    public boolean hardwareSetup() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        parameters.loggingEnabled=true; parameters.loggingTag="IMU";
        imu = hM.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        return true;
    }
    public boolean initialize() {
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        return imu.isGyroCalibrated();
    }
    public float getAngle() {return imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX).firstAngle;}
    public Orientation getOrientation() {
        return imu.getAngularOrientation();
    }
    public AngularVelocity getRate() {
        return imu.getAngularVelocity();
    }
}
