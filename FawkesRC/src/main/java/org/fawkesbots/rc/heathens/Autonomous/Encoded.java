package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
import org.fawkesbots.rc.vendetta.Auton;

/**
 * Created by Priansh on 10/29/16.
 */
@Disabled
@Autonomous(name="Basic", group="Encoded")
public class Encoded extends Auton {

    public HardwareTank EncodedDrive;

    //Encoder Stuff
    private ElapsedTime runtime = new ElapsedTime();
    static final double ticks = (1120.0 * (1.0/4.0)) / (Math.PI * 8.0);

    @Override
    public void runOpMode() throws InterruptedException {

        EncodedDrive = new HardwareTank(hardwareMap);
        EncodedDrive.hardwareSetup();
        EncodedDrive.resetEncoders(); idle(); EncodedDrive.setupEncoders();
        waitForStart();

        encoderDrive(0.7f,12,20);

    }

    //timeout in seconds
    public void encoderDrive(float v, float inches, float timeout) throws InterruptedException {
        if (opModeIsActive()) {
            EncodedDrive.encodePosition(inches * ticks);
            EncodedDrive.prepEncoders();
            runtime.reset();
            EncodedDrive.tank(v,v);
            while (opModeIsActive() && (runtime.seconds() < timeout) && (EncodedDrive.fl.isBusy() && EncodedDrive.fr.isBusy())) idle();
            EncodedDrive.tank(0.0f,0.0f);
            EncodedDrive.setupEncoders();
        }
    }

}
