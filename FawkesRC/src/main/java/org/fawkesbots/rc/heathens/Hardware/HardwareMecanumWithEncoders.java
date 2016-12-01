package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;

/**
 * Created by Priansh on 11/30/16.
 */

@DefSentinel(
        drive="Mecanum",
        description = "Basic mecanum drive, 4 wheels, no reversed"
)

public class HardwareMecanumWithEncoders extends HardwareMecanum {

    float fl_target=0, fr_target=0, bl_target=0, br_target=0;
    public float COUNTS_PER_INCH = 0;


    float GEAR_REDUCTION = 4;
    float WHEEL_DIAMETER = 6; //inches, smaller than my dick
/*  GET THIS MEASUREMENT */

    public float ticks = 1440;
/* NORMAL = 1120, NEVEREST40 = 1440, ETC */

    public HardwareMecanumWithEncoders(HardwareMap hwMap) {
        super(hwMap);
        COUNTS_PER_INCH = (float)((ticks*GEAR_REDUCTION)/(WHEEL_DIAMETER * Math.PI));
    }

    public boolean resetEncoders() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        return true;
    }

    public boolean setupEncoders() {
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        return true;
    }

    public boolean runEncoders() {
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        return true;
    }

    public boolean powerAll(float f_l, float f_r, float b_l, float b_r) {
        fl.power(f_l); fr.power(f_r); bl.power(b_l); br.power(b_r);
        return true;
    }

    public boolean checkEncoders() {
        return fl.isBusy() && fr.isBusy() && bl.isBusy() && br.isBusy();
    }

    public boolean forwardEncoded(float inches, float speed) {
        moveEncoders(inches,inches,inches,inches,speed,-speed,speed,-speed);
        return true;
    }

    public boolean strafeEncoded(float inches, float speed) {
        moveEncoders(inches,inches,inches,inches,-speed,speed,speed,-speed);
        return true;
    }

    public boolean moveEncoders(float fl_inches, float fr_inches, float bl_inches, float br_inches,
                                float fl_speed, float fr_speed, float bl_speed, float br_speed) {

        resetEncoders(); setupEncoders();
        fl_target = fl.getCurrentPosition() + (fl_inches * COUNTS_PER_INCH);
        bl_target = bl.getCurrentPosition() + (bl_inches * COUNTS_PER_INCH);
        br_target = br.getCurrentPosition() + (br_inches * COUNTS_PER_INCH);
        fr_target = fr.getCurrentPosition() + (fr_inches * COUNTS_PER_INCH);
        fl.setTargetPosition((int)fl_target);
        fr.setTargetPosition((int)fr_target);
        bl.setTargetPosition((int)bl_target);
        br.setTargetPosition((int)br_target);
        runEncoders();
        powerAll(fl_speed,-fr_speed,bl_speed,-br_speed);
        while(checkEncoders()) { }
        powerAll(0,0,0,0);
        return setupEncoders();
    }

}
