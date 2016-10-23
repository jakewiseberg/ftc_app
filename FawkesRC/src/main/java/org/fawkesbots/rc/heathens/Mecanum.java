package org.fawkesbots.rc.heathens;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by hello_000 on 10/23/2016.
 */
@TeleOp (
        name="Mecanum",
        group="Basic Drive"
)
public class Mecanum extends OpMode {
    public HardwareMecanum Gargoyle;
    public void init() {
        Gargoyle = new HardwareMecanum(hardwareMap);
        Gargoyle.hardwareSetup();
    }
    public void loop() {
        Gargoyle.mecanum(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
    }
}
