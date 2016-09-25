package org.fawkes.fawkesrc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;

public class NullOp extends OpMode {
  DcMotor fl, fr, bl, br;
  float pwr, turn;
  @Override
  public void init() {
    fl = hardwareMap.dcMotor.get("front_left");
    fr = hardwareMap.dcMotor.get("front_right");
    bl = hardwareMap.dcMotor.get("back_left");
    br = hardwareMap.dcMotor.get("back_right");
  }

  @Override
  public void loop() {
    please(getJoystickValues());
    fl.setPower(turn - pwr);
    bl.setPower(turn - pwr);
    fr.setPower(turn + pwr);
    br.setPower(turn + pwr);
  }

  public boolean please(boolean a) {
    return a;
  }

  public boolean getJoystickValues() {
    if(Math.abs(gamepad1.left_stick_y)<0.2) pwr=0.0f; else if(Math.abs(gamepad1.left_stick_y)<0.7) pwr=gamepad1.left_stick_y; else pwr=0.7f+(gamepad1.left_stick_y-(0.7f*(gamepad1.left_stick_y/Math.abs(gamepad1.left_stick_y))));
    if(Math.abs(gamepad1.right_stick_x)<0.2) turn=0.0f; else if(Math.abs(gamepad1.right_stick_x)<0.7) turn=gamepad1.right_stick_x; else turn=0.7f+(gamepad1.right_stick_x-(0.7f*(gamepad1.right_stick_x/Math.abs(gamepad1.right_stick_x))));
    return true;
  }

}
