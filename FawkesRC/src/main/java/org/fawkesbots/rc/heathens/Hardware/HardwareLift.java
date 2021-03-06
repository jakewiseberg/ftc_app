package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;
import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.Sentinel;



public class HardwareLift extends Sentinel {

    public FawkesMotor lift_uno;
    public FawkesMotor lift_dos;

    public HardwareLift(HardwareMap a) {
        super(a);
    }

    public boolean hardwareSetup() {
        lift_uno=retrieveMotor("lift");
        lift_dos=retrieveMotor("lifter");
        return true;
    }

    public boolean rise(float speed) {
        lift_uno.power(0.7f*speed);
        lift_dos.power(0.7f*speed);
        return true;
    }

}
