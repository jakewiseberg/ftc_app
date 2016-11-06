package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
import org.fawkesbots.rc.vendetta.Autonomous;

/**
 * Created by hello_000 on 10/23/2016.
 */
@Disabled
public class Basic extends Autonomous {
    HardwareTank Gargoyle;

    public void initialize() {
        Gargoyle = new HardwareTank(hardwareMap);
        Gargoyle.hardwareSetup();
    }
    public void autonomous() throws InterruptedException {
        Gargoyle.tank(.6f,.6f); sleep(500); //move forward
        Gargoyle.tank(1f,-1f); sleep(600); //turn a bit
        Gargoyle.tank(1f,1f); sleep(4000); //hit the cap ball
        Gargoyle.tank(0f,0f); //stop moving
    }
}
