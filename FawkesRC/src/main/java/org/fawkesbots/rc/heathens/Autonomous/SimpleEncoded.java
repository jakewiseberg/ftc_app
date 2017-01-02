package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.fawkesbots.rc.heathens.Hardware.HardwareEncoder;
import org.fawkesbots.rc.vendetta.Auton;

/**
 * Created by Priansh on 12/3/16.
 */

@Autonomous (
        name="Simple Encoded",group="Encoded"
)
@Disabled
public class SimpleEncoded extends Auton {
    public HardwareEncoder EncodedDrive;

    @Override
    public void runOpMode() throws InterruptedException {
        EncodedDrive = new HardwareEncoder(hardwareMap);
        EncodedDrive.hardwareSetup();
        waitForStart();
        EncodedDrive.moveEncoders(5000, 0.78f);
        EncodedDrive.moveEncoders(-5000, 0.78f);
        EncodedDrive.moveEncoders(5000, -0.78f);
        EncodedDrive.moveEncoders(-5000, -0.78f);
        EncodedDrive.reverseMotors();
        EncodedDrive.moveEncoders(5000, 0.78f);
        EncodedDrive.moveEncoders(-5000, 0.78f);
        EncodedDrive.moveEncoders(5000, -0.78f);
        EncodedDrive.moveEncoders(-5000,-0.78f);
    }
}
