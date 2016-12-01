package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
import org.fawkesbots.rc.vendetta.*;

@TeleOp (
        name="Blank",
        group="Tests"
)
public class Blank extends OpMode {
    public HardwareTank Gargoyle;
    public void init() {
        Gargoyle = new HardwareTank(hardwareMap);
        Gargoyle.hardwareSetup();
        Gargoyle.reverseSide('l');
    }
    public void loop() {
        Gargoyle.tank(gamepad1.left_stick_y,gamepad1.right_stick_y);
    }
}
