package org.fawkesbots.rc.heathens;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.vendetta.*;
@TeleOp(name="Outreach", group="Outreach Drive")

/**
 * Created by hello_000 on 10/23/2016.
 */
public class Tank extends OpMode {
    public HardwareTank Gargoyle;
    public void init() {
        Gargoyle = new HardwareTank(hardwareMap);
        Gargoyle.hardwareSetup();
    }
    public void loop() {
        Gargoyle.fl.power(gamepad1.left_stick_y);
        Gargoyle.bl.power(gamepad1.left_stick_y);
        Gargoyle.fr.power(gamepad1.right_stick_y);
        Gargoyle.br.power(gamepad1.right_stick_y);
    }
}
