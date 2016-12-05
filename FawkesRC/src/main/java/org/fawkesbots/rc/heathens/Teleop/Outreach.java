package org.fawkesbots.rc.heathens.Teleop;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.Hardware.HardwareOutreach;
import org.fawkesbots.rc.vendetta.*;
@TeleOp(name="Outreach", group="Finished")
/**
 * Created by hello_000 on 10/1/2016.
 */
@Disabled
public class Outreach extends OpMode {
    public HardwareOutreach Robot;
    public void init() {
        Robot = new HardwareOutreach(hardwareMap);
        Log.e("fawkes", "made");
        Robot.hardwareSetup();
        Log.e("fawkes","did");
    }
    public void loop() {
            Robot.front_left.power(gamepad1.left_stick_y + gamepad1.right_stick_x);
            Robot.front_right.power(gamepad1.left_stick_y - gamepad1.right_stick_x);
            Robot.back_left.power(gamepad1.left_stick_y + gamepad1.right_stick_x);
            Robot.back_right.power(gamepad1.left_stick_y - gamepad1.right_stick_x);
            Robot.sweep.power(gamepad1.right_trigger);
            Robot.hook.power(gamepad1.right_stick_y);
            Robot.jaw.power(-1 * gamepad1.right_stick_y);
    }
}
