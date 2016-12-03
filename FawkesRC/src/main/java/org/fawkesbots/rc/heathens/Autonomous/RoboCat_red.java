package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;

@Autonomous(
        name="R_C Red",
        group="Encoded"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class RoboCat_red extends LinearOpMode {

    public HardwareMecanumWithEncoders Gargoyle;

    @Override
    public void runOpMode() throws InterruptedException {
        Gargoyle = new HardwareMecanumWithEncoders(hardwareMap,telemetry);
        Gargoyle.hardwareSetup(); Gargoyle.setSides(1,1,1,1);
        waitForStart();

        Gargoyle.forwardEncoded(24,0.78f);
        Gargoyle.strafeEncoded(-12,0.78f);
        Gargoyle.forwardEncoded(24,0.78f);
        Gargoyle.rotateEncoded(-18,0.78f);
        telemetry.addData("Encoders",Gargoyle.logEncoders());
        telemetry.update();

    }

}
