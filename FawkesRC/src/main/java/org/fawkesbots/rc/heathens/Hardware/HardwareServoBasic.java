package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.FawkesServo;
import org.fawkesbots.rc.vendetta.Sentinel;

/**
 * Created by Priansh on 12/16/16.
 */
public class HardwareServoBasic extends Sentinel {

    public FawkesServo a;
    public float min=0,max=1.0f; String serv = "a";

    public HardwareServoBasic(HardwareMap hw, float ex_max, float ex_min, String servo_name) {
        super(hw); serv=servo_name;
        min=ex_min; max=ex_max;
    }

    public boolean hardwareSetup() {
        a=retrieveServo(serv);
        return true;
    }

    public boolean flick(boolean pr) {
        a.setPosition((pr)?max:min);
        return true;
    }

}
