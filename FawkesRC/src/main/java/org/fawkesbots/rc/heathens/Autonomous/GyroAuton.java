package org.fawkesbots.rc.heathens.Autonomous;

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
    public AngularVelocity v; public Orientation o;
    @Override
    public void runOpMode() throws InterruptedException {
        Gyro = new HardwareGyro(hardwareMap);
        Gyro.hardwareSetup();
        Gyro.initialize();
        waitForStart();
        while(opModeIsActive()) {
            v=Gyro.getRate(); o=Gyro.getAngle();
            telemetry.addLine("Rate X: "+v.firstAngleRate+", Y: "+v.secondAngleRate+", Z: "+v.thirdAngleRate);
            telemetry.addLine("Angle X: "+o.firstAngle+", Y: "+o.secondAngle+", Z: "+o.thirdAngle);
            telemetry.update();
        }
    }
}
