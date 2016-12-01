package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;
import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.FawkesServo;
import org.fawkesbots.rc.vendetta.Sentinel;

@DefSentinel(
        drive="Test",
        description="Test hardware")

public class HardwareTest extends Sentinel {

    public FawkesServo a;

    public HardwareTest(HardwareMap a) {
        super(a);
    }

    public boolean hardwareSetup() {
        a=retrieveServo("a");
        return true;
    }

    public boolean position(float q) {
        a.setPosition(q); return true;
    }


}
