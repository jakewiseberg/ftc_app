package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.Sentinel;

/**
 * Created by krishenseth on 12/4/16.
 */
public class HardwareLauncher extends Sentinel {
    public FawkesMotor launcher;
    public HardwareLauncher(HardwareMap hw) {
        super(hw);
    }
    public boolean hardwareSetup() {
        launcher=retrieveMotor("launcher"); return true;
    }
    public boolean fire(float a) {
        launcher.power(a); return true;
    }
}
