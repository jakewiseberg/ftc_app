package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareLift;

@TeleOp (
        name="Lift",
        group="Single"
)
@Disabled
public class ScissorLift extends OpMode {
    public HardwareLift Lift;
    public void init() {
        Lift = new HardwareLift(hardwareMap);
        Lift.hardwareSetup();
    }
    public void loop() {
        Lift.rise(gamepad2.left_stick_y);
    }
}
