package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;

@Autonomous(
        name="Mecanum Encoded",
        group="Encoded"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class MecanumEncoded extends LinearOpMode {

    public HardwareMecanumWithEncoders Gargoyle;

    @Override
    public void runOpMode() throws InterruptedException {
        Gargoyle = new HardwareMecanumWithEncoders(hardwareMap,telemetry);
        Gargoyle.hardwareSetup(); Gargoyle.setSides(1,1,1,1);
        waitForStart();

        Gargoyle.forwardEncoded(18, 0.7f);
        Gargoyle.strafeEncoded(18,-.7f);
    }

}
