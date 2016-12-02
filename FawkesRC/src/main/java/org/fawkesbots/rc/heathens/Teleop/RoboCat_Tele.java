package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanum;

/**
 * Created by hello_000 on 10/23/2016.
 */
@TeleOp (
        name="RoboCat Teleop",
        group="Qualifiers"
)
public class RoboCat_Tele extends OpMode {
    public HardwareMecanum Gargoyle;
    public HardwareCollector Marilyn;
    public void init() {
        Gargoyle = new HardwareMecanum(hardwareMap);
        Marilyn = new HardwareCollector(hardwareMap);
        Gargoyle.hardwareSetup();
    }
    public void loop() {
        Gargoyle.mecanum(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
        Marilyn.collect(gamepad1.left_trigger);
    }
}
