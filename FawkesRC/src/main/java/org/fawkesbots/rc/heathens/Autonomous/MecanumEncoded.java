package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.vendetta.Auton;

@Autonomous(
        name="Mecanum Encoded",
        group="Encoded"
)
@Disabled
/**
 * Created by Priansh on 11/30/16.
 */
public class MecanumEncoded extends Auton {

    public HardwareMecanumWithEncoders EncodedDrive;

    @Override
    public void runOpMode() throws InterruptedException {
        EncodedDrive = new HardwareMecanumWithEncoders(hardwareMap,telemetry,this);
        EncodedDrive.hardwareSetup(); EncodedDrive.setSides(1,1,1,1);
        waitForStart();

        EncodedDrive.forwardEncoded(18, 0.78f);
        EncodedDrive.strafeEncoded(18,0.78f);
        EncodedDrive.rotateEncoded(18,0.78f);

    }

}
