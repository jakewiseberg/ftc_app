package org.fawkesbots.rc.heathens;

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
    public HardwareTank(HardwareMap hwMap) {
        super(hwMap);
    }
}
