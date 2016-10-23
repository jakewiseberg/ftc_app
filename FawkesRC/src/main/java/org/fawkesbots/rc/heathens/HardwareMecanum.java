package org.fawkesbots.rc.heathens;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.FawkesMotor;

/**
 * Created by hello_000 on 10/23/2016.
 */

@DefSentinel(
        drive="Mecanum",
        description = "Basic mecanum drive, 4 wheels, no reversed"
)

public class HardwareMecanum extends HardwareTank{
    public HardwareMecanum(HardwareMap hwMap) {super(hwMap);}

    public boolean mecanum(float x, float y, float z) {
        fl.power(y+x+z);
        fr.power(-y+x+z);
        bl.power(y-x+z);
        br.power(-y-x+z);
        return true;
    }

}
