package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanum;

/**
 * Created by hello_000 on 10/23/2016.
 */
@TeleOp (
        name="RoboCat Teleop",
        group="Finished"
)
public class RoboCat_Tele extends OpMode {
    /*

    CONTROLS

    Gamepad 1:
    - movement (left joystick all directions)
    - turning (right joystick)
    - collection (left trigger)

    Gamepad 2:
    - launcher (right trigger)

     */

    public HardwareMecanum Gargoyle;
    public HardwareCollector Marilyn;
    public HardwareLauncher ItsJustAClock;
    public void init() {
        Gargoyle = new HardwareMecanum(hardwareMap);
        Marilyn = new HardwareCollector(hardwareMap);
        ItsJustAClock = new HardwareLauncher(hardwareMap);
        Gargoyle.hardwareSetup();
        Marilyn.hardwareSetup();
        ItsJustAClock.hardwareSetup();
    }
    public void loop() {
        Gargoyle.mecanum(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
        Marilyn.collect(gamepad2.left_stick_y);
        ItsJustAClock.fire((gamepad2.right_trigger>0.3)?-0.67f:0);
    }
}
