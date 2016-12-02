package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
import org.fawkesbots.rc.heathens.Hardware.HardwareTest;
import org.fawkesbots.rc.vendetta.*;

@TeleOp (
        name="Rotor",
        group="rotate a servo"
)
public class Rotor extends OpMode {
    public HardwareCollector Gargoyle;
    public void init() {
        Gargoyle = new HardwareCollector(hardwareMap);
        Gargoyle.hardwareSetup();
    }
    public void loop() {
        Gargoyle.collect(gamepad1.left_trigger);
    }
}
