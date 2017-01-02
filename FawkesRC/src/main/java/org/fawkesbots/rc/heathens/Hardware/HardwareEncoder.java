package org.fawkesbots.rc.heathens.Hardware;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;
import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.Sentinel;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Priansh on 11/30/16.
 */

@DefSentinel(
        drive="Mecanum",
        description = "Basic mecanum drive, 4 wheels, no reversed"
)

public class HardwareEncoder extends Sentinel {

    float target = 0;

    FawkesMotor motor;

    public boolean hardwareSetup() {
        motor = retrieveMotor("test"); return true;
    }

    public HardwareEncoder(HardwareMap hwMap) {
        super(hwMap);
    }

    public boolean resetEncoders() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        return true;
    }

    public boolean setupEncoders() {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        return true;
    }

    public boolean runEncoders() {
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return true;
    }

    public boolean powerAll(float pwr) {
        motor.power(pwr);
        return true;
    }

    public boolean checkEncoders() {
        return motor.isBusy();
    }

    public boolean reverseMotors() {
        if(motor.getDirection()== DcMotorSimple.Direction.FORWARD)
            motor.setDirection(DcMotorSimple.Direction.REVERSE);
        else motor.setDirection(DcMotorSimple.Direction.FORWARD);
        return true;
    }

    public boolean moveEncoders(float ticks, float speed) {
        resetEncoders(); setupEncoders();
        target = motor.getCurrentPosition() + (ticks);
        motor.setTargetPosition((int) target);
        runEncoders();
        powerAll(speed);
        while(checkEncoders()) { }
        powerAll(0);
        return setupEncoders();
    }

}
