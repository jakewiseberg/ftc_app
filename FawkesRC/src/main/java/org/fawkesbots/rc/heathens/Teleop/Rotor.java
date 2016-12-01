package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
import org.fawkesbots.rc.heathens.Hardware.HardwareTest;
import org.fawkesbots.rc.vendetta.*;

@TeleOp (
        name="Rotor",
        group="rotate a servo"
)
public class Rotor extends OpMode {
    public HardwareTest Gargoyle;
    public void init() {
        Gargoyle = new HardwareTest(hardwareMap);
        Gargoyle.hardwareSetup();
    }
    public void loop() {

        if(gamepad1.a) {
            Gargoyle.position(1);
        }

        if(gamepad1.b) {
            Gargoyle.position(-1);
        }

        if(gamepad1.x) {
            Gargoyle.position(0);
        }

    }
}
