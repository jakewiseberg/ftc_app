package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;
import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.Sentinel;

@DefSentinel(
        drive="Lift",
        description="lift")

public class HardwareLift extends Sentinel {

    public FawkesMotor lift;

    public HardwareLift(HardwareMap a) {
        super(a);
    }

    public boolean hardwareSetup() {
        lift=retrieveMotor("lift");
        return true;
    }

    public boolean rise(float speed) {
        lift.power(speed);
        return true;
    }

}
