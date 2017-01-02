package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;
import org.fawkesbots.rc.vendetta.FawkesServo;
import org.fawkesbots.rc.vendetta.Sentinel;

/**
 * Created by krishenseth on 1/2/17.
 */

public class HardwareArms extends HardwareTest{


    public FawkesServo arms;
    double pos;

    public HardwareArms(HardwareMap a) {
        super(a);
    }

    public boolean flickUp() {
        pos = arms.getPosition();
        arms.setPosition(1.0);
        return true;
    }
    public boolean flickDown() {
        pos = arms.getPosition();
        arms.setPosition(0);
        return true;
    }

    public double getPosition() {
        return arms.getPosition();
    }

    public boolean hardwareSetup() {
        arms=retrieveServo("arms");
        pos = arms.getPosition();
        return true;
    }
}

