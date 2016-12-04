package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareLift;

@TeleOp (
        name="Lift",
        group="raise da lift"
)
public class ScissorLift extends OpMode {
    public HardwareLift Gargoyle;
    public void init() {
        Gargoyle = new HardwareLift(hardwareMap);
        Gargoyle.hardwareSetup();
    }
    public void loop() {
        Gargoyle.rise(gamepad2.left_stick_y);
    }
}
