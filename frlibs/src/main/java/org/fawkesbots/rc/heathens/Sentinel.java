package org.fawkesbots.rc.heathens;

import com.qualcomm.robotcore.eventloop.opmode.*;
import org.fawkesbots.rc.heathens.FawkesMotor;

public class Sentinel extends OpMode {
    @Override
    public void init() { }
    @Override
    public void loop() { }

    FawkesMotor motor = new FawkesMotor(hardwareMap.dcMotor.get("motor"));


}
