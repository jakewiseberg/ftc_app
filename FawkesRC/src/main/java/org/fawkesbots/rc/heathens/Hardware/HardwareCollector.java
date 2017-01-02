package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;
import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.FawkesServo;
import org.fawkesbots.rc.vendetta.Sentinel;

@DefSentinel(
        drive="Collector",
        description="collection")

public class HardwareCollector extends Sentinel {

    public FawkesMotor collector;

    public HardwareCollector(HardwareMap a) {
        super(a);
    }

    public boolean hardwareSetup() {
        collector=retrieveMotor("collector");
        return true;
    }

    public boolean collect(float speed) {
        collector.power(-speed);
        return true;
    }

}
