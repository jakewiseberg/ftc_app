package org.fawkesbots.rc.heathens;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.*;
@TeleOp(name="Outreach", group="Outreach Drive")
/**
 * Created by hello_000 on 10/1/2016.
 */
public class Outreach extends OpMode {
    public HardwareOutreach Gargoyle;
    public void init() {
        Gargoyle = new HardwareOutreach(hardwareMap);
        Log.e("fawkes", "made");
        Gargoyle.hardwareSetup();
        Log.e("fawkes","did");
    }
    public void loop() {
            Gargoyle.front_left.power(gamepad1.left_stick_y + gamepad1.right_stick_x);
            Gargoyle.front_right.power(gamepad1.left_stick_y - gamepad1.right_stick_x);
            Gargoyle.back_left.power(gamepad1.left_stick_y + gamepad1.right_stick_x);
            Gargoyle.back_right.power(gamepad1.left_stick_y - gamepad1.right_stick_x);
            Gargoyle.sweep.power(gamepad1.right_trigger);
            Gargoyle.hook.power(gamepad1.right_stick_y);
            Gargoyle.jaw.power(-1 * gamepad1.right_stick_y);
    }
}
