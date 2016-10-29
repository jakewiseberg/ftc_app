package org.fawkesbots.rc.heathens;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.Sentinel;

@DefSentinel(
        drive="Tank",
        description="Basic Tank drive systems, 4 wheel drive"
)

/**
 * Created by hello_000 on 10/23/2016.
 */
public class HardwareTank extends Sentinel {
    public FawkesMotor fl, fr, bl, br;
    public boolean arcade(float pwr, float turn) {
        fl.power(pwr+turn); fr.power(pwr-turn);
        bl.power(pwr+turn); br.power(pwr-turn);
        return true;
    }
    public boolean tank(float left, float right) {
        fl.power(left); bl.power(left);
        fr.power(right); br.power(right);
        return true;
    }
    public boolean hardwareSetup() {
        fl = retrieveMotor("front left").unencode().power(0);
        fr = retrieveMotor("front right").unencode().power(0);
        bl = retrieveMotor("back left").unencode().power(0);
        br = retrieveMotor("back right").unencode().power(0);
        return true;
    }

    public boolean resetEncoders() {
        this.fl.setMode(FawkesMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.fr.setMode(FawkesMotor.RunMode.STOP_AND_RESET_ENCODER);
        return true;
    }

    public boolean setupEncoders() {
        this.fl.setMode(FawkesMotor.RunMode.RUN_USING_ENCODER);
        this.fr.setMode(FawkesMotor.RunMode.RUN_USING_ENCODER);
        return true;
    }

    public boolean encodePosition(float l, float r) {
        this.fl.setTargetPosition(this.fl.getCurrentPosition() + (int) l);
        this.fr.setTargetPosition(this.fr.getCurrentPosition() + (int) r);
        return true;
    }
    public boolean encodePosition(double l, double r) { return encodePosition((float)l, (float) r); }
    public boolean encodePosition(float a) {return encodePosition(a,a);}
    public boolean encodePosition(double a) {return encodePosition(a,a);}

    public boolean prepEncoders() {
        this.fl.setMode(FawkesMotor.RunMode.RUN_TO_POSITION);
        this.fr.setMode(FawkesMotor.RunMode.RUN_TO_POSITION);
        return true;
    }
    public HardwareTank(HardwareMap hwMap) {
        super(hwMap);
    }
}
