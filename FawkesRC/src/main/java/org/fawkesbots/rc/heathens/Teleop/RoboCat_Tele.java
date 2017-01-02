package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareFlicker;
import org.fawkesbots.rc.heathens.Hardware.HardwareLauncher;
import org.fawkesbots.rc.heathens.Hardware.HardwareLift;
import org.fawkesbots.rc.heathens.Hardware.HardwareLifter;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanum;

import java.sql.DriverManager;

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
    -collector (left joy stick)
    -flicker (right and left bumpers)
    -Scissor Lifts  (right joystick)

     */

    public HardwareMecanum Drive;
    public HardwareCollector Sweeper;
    public HardwareLauncher Launcher;
    public HardwareFlicker Flicker;
    public HardwareLift Lift;
    public HardwareLifter Lifter;

    public void init() {
        Drive = new HardwareMecanum(hardwareMap);
        Sweeper = new HardwareCollector(hardwareMap);
        Launcher = new HardwareLauncher(hardwareMap);
        Flicker = new HardwareFlicker(hardwareMap);
        Lift = new HardwareLift(hardwareMap);
        Lifter = new HardwareLifter(hardwareMap);
        Drive.hardwareSetup();
        Sweeper.hardwareSetup();
        Launcher.hardwareSetup();
        Flicker.hardwareSetup();
        Lift.hardwareSetup();
        Lifter.hardwareSetup();
    }
    public void loop() {
        Drive.mecanum(-1*gamepad1.left_stick_x,-1*gamepad1.left_stick_y,gamepad1.right_stick_x);
        Sweeper.collect(gamepad2.left_stick_y);
        Launcher.fire((gamepad2.right_trigger>0.3)?-0.78f:(gamepad2.left_trigger>0.3)?0.78f:0);
        Flicker.flick(gamepad2.left_bumper || gamepad2.right_bumper);
        Lift.rise(-1*gamepad2.right_stick_y);
        Lifter.rise(-1*gamepad2.right_stick_y);

    }
}
