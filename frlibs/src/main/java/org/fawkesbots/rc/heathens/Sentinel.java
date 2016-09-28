package org.fawkesbots.rc.heathens;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.fawkesbots.rc.heathens.FawkesMotor;

public class Sentinel {

    private HardwareMap hardwareMap;
    public double min=-.78,max=.78;
    public FawkesMotor front_left, front_right, back_left, back_right;

    private FawkesMotor retrieveMotor(String name) {
        return new FawkesMotor(this.hardwareMap.dcMotor.get(name)).setBounds(this.min,this.max);
    }

    public Sentinel(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public boolean hardwareSetup() {
        front_left = retrieveMotor("front left");
        front_right = retrieveMotor("front right");
        back_left = retrieveMotor("back left");
        back_right = retrieveMotor("back right");
        return true;
    }

}
