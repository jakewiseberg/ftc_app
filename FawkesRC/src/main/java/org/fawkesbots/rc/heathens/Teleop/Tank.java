package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
import org.fawkesbots.rc.vendetta.*;

@TeleOp (
        name="Tank",
        group="Basic Drive"
)
@Disabled
public class Tank extends OpMode {
    public HardwareTank Drive;
    public void init() {
        Drive = new HardwareTank(hardwareMap);
        Drive.hardwareSetup();
        Drive.reverseSide('l');
    }
    public void loop() {
        Drive.tank(gamepad1.left_stick_y,gamepad1.right_stick_y);
    }
}
