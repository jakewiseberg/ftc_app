package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareServoBasic;
import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareLift;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanum;

import java.sql.DriverManager;

/**
 * Created by hello_000 on 10/23/2016.
 */
@TeleOp (
        name="Competition",
        group="Finished"
)
public class VV_Teleop extends OpMode {

    /*

    CONTROLS

    Gamepad 1:
    - movement (left joystick all directions)
    - turning (right joystick)
    - collection (left trigger)

    Gamepad 2:
    - launcher (right trigger)
    - collector (left joy stick)
    - flicker (left bumper)
    - arms (right bumper)
    - scissor lifts  (right joystick)

     */

    public HardwareMecanum Drive; public HardwareCollector Sweeper;
    public HardwareLauncher Launcher; public HardwareLift Lift;
    public HardwareServoBasic Flicker; public HardwareServoBasic Arms;

    public void init() {
        Drive = new HardwareMecanum(hardwareMap);
        Sweeper = new HardwareCollector(hardwareMap); Launcher = new HardwareLauncher(hardwareMap);
        Lift = new HardwareLift(hardwareMap);

        Flicker = new HardwareServoBasic(hardwareMap,0.82f,0.0f,"flicker"); Arms = new HardwareServoBasic (hardwareMap,1.0f,0.0f,"arms");
        Drive.hardwareSetup(); Sweeper.hardwareSetup(); Launcher.hardwareSetup();
        Lift.hardwareSetup();
        Flicker.hardwareSetup(); Arms.hardwareSetup();
        Flicker.flick(false); Arms.flick(false);
    }
    public void loop() {
        Drive.mecanum(-gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        Sweeper.collect(gamepad2.left_stick_y);
        Launcher.fire((gamepad2.right_trigger > 0.3) ? -0.78f :0);
        Flicker.flick(gamepad2.left_bumper||gamepad2.right_bumper); Arms.flick(gamepad2.a);
        Lift.rise(-1*gamepad2.right_stick_y);
    }
}
