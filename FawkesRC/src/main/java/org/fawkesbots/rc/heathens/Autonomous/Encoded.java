package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.fawkesbots.rc.heathens.Hardware.HardwareTank;

/**
 * Created by Priansh on 10/29/16.
 */
@Autonomous(name="Basic", group="Encoded")
public class Encoded extends LinearOpMode {

    public HardwareTank Gargoyle;

    //Encoder Stuff
    private ElapsedTime runtime = new ElapsedTime();
    static final double ticks = (1120.0 * (1.0/4.0)) / (Math.PI * 8.0);

    @Override
    public void runOpMode() throws InterruptedException {

        Gargoyle = new HardwareTank(hardwareMap);
        Gargoyle.hardwareSetup();
        Gargoyle.resetEncoders(); idle(); Gargoyle.setupEncoders();
        waitForStart();

        encoderDrive(0.7f,12,20);

    }

    //timeout in seconds
    public void encoderDrive(float v, float inches, float timeout) throws InterruptedException {
        if (opModeIsActive()) {
            Gargoyle.encodePosition(inches * ticks);
            Gargoyle.prepEncoders();
            runtime.reset();
            Gargoyle.tank(v,v);
            while (opModeIsActive() && (runtime.seconds() < timeout) && (Gargoyle.fl.isBusy() && Gargoyle.fr.isBusy())) idle();
            Gargoyle.tank(0.0f,0.0f);
            Gargoyle.setupEncoders();
        }
    }

}
