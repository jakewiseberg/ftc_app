package org.fawkesbots.rc.heathens.Autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareGyro;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.heathens.Hardware.HardwareServoBasic;
import org.fawkesbots.rc.vendetta.Auton;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(
        name="Gyro Test",
        group="WIP"
)

public class GyroAuton extends Auton {
    public HardwareGyro Gyro;
    public AngularVelocity v; public float angle; public Orientation o;
    @Override
    public void runOpMode() throws InterruptedException {
        Gyro = new HardwareGyro(hardwareMap);
        log("Gyro","Hello?");
        Gyro.hardwareSetup();
        log("Gyro","Hello?????");
        Gyro.initialize();
        waitForStart();
        log("Gyro","Starting");
        while(true) {
            log("Gyro", "Entered Loop");
            o=Gyro.imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
            angle = o.firstAngle;
            telemetry.addData("Angle",angle);
            telemetry.update();
            log("Gyro","Finished a loop");
        }
    }
}
