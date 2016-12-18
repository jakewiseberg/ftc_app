package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Priansh on 12/16/16.
 */
public class HardwareFlicker extends HardwareTest {

    public boolean pressed = false;

    public HardwareFlicker(HardwareMap hw) {
        super(hw);
    }

    public boolean flick(boolean pr) {
        if(pr) {
            pressed = true; up();
        }
        if(pressed && !pr) {
            pressed = false; down();
        }
        return true;
    }

    public boolean up() {
        return this.position((float)1.0f);
    }

    public boolean down() {
        return this.position((float)(0.0f));
    }

}
