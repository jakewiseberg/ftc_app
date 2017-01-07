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
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(
        name="Gyro Test",
        group="WIP"
)

public class GyroAuton extends Auton {
    public HardwareGyro Gyro;
    public AngularVelocity v; public float angle;
    @Override
    public void runOpMode() throws InterruptedException {
        Gyro = new HardwareGyro(hardwareMap);
        Log.e("Gyro","Hello?");
        Gyro.hardwareSetup();
        Log.e("Gyro","Hello?????");
        Gyro.initialize();
        waitForStart();
        telemetry.addData("Gyro", "Starting");
        telemetry.update();
        Log.e("Gyro", "Starting");
        while(true) {
            Log.e("Gyro","Entered Loop");
            angle = Gyro.getAngle();
            telemetry.addData("Angle",angle);
            telemetry.update();
            Log.e("Gyro","Finished a loop");
        }
    }
}
