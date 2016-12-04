package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.fawkesbots.rc.heathens.Hardware.HardwareEncoder;

/**
 * Created by Priansh on 12/3/16.
 */

@Autonomous (
        name="Simple Encoded",group="Tests"
)

public class SimpleEncoded extends LinearOpMode {
    public HardwareEncoder Gargoyle;

    @Override
    public void runOpMode() throws InterruptedException {
        Gargoyle = new HardwareEncoder(hardwareMap);
        Gargoyle.hardwareSetup();
        waitForStart();
        Gargoyle.moveEncoders(5000, 0.78f);
        Gargoyle.moveEncoders(-5000, 0.78f);
        Gargoyle.moveEncoders(5000, -0.78f);
        Gargoyle.moveEncoders(-5000, -0.78f);
        Gargoyle.reverseMotors();
        Gargoyle.moveEncoders(5000, 0.78f);
        Gargoyle.moveEncoders(-5000, 0.78f);
        Gargoyle.moveEncoders(5000, -0.78f);
        Gargoyle.moveEncoders(-5000,-0.78f);
    }
}
