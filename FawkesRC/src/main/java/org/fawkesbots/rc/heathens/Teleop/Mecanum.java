package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanum;

/**
 * Created by hello_000 on 10/23/2016.
 */
@TeleOp (
        name="Mecanum",
        group="Basic Drive"
)
@Disabled
public class Mecanum extends OpMode {
    public HardwareMecanum Drive;
    public void init() {
        Drive = new HardwareMecanum(hardwareMap);
        Drive.hardwareSetup();
    }
    public void loop() {
        Drive.mecanum(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
    }
}
