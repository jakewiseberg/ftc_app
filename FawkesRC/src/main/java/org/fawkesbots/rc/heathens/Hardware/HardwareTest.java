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

    public FawkesServo flicker;

    public HardwareTest(HardwareMap hw) {
        super(hw);
    }

    public boolean hardwareSetup() {
        flicker=retrieveServo("flicker");
        return true;
    }

    public boolean position(float q) {
        flicker.setPosition(q); return true;
    }


}
