/**
  ███████╗ █████╗ ██╗    ██╗██╗  ██╗███████╗███████╗
  ██╔════╝██╔══██╗██║    ██║██║ ██╔╝██╔════╝██╔════╝
  █████╗  ███████║██║ █╗ ██║█████╔╝ █████╗  ███████╗
  ██╔══╝  ██╔══██║██║███╗██║██╔═██╗ ██╔══╝  ╚════██║
  ██║     ██║  ██║╚███╔███╔╝██║  ██╗███████╗███████║
 ╚ ═╝     ╚═╝  ╚═╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚══════╝
 */

package org.fawkes.fawkesrc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TeleOp Mode
 * <p>
 *Enables control of the robot via the gamepad
 */
public class NullOp extends OpMode {
  Servo a;
  DcMotor b;

  @Override
  public void init() {
    a = hardwareMap.servo.get("a");
    a.setPosition(0.5);
    b = hardwareMap.dcMotor.get("b");
  }

  @Override
  public void loop() {
    if(gamepad1.left_stick_button) a.setPosition(0.0);
    if(gamepad1.right_stick_button) a.setPosition(0.5);
    telemetry.addData("Pwr",b.getPower());
    b.setPower(Range.clip(-gamepad1.left_stick_y,-.78,.78));
  }

}
