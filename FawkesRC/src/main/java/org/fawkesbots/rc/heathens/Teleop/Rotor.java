package org.fawkesbots.rc.heathens.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.heathens.Hardware.HardwareCollector;
import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
import org.fawkesbots.rc.heathens.Hardware.HardwareTest;
import org.fawkesbots.rc.vendetta.*;

@TeleOp (
        name="Rotor",
        group="Single"
)
@Disabled
public class Rotor extends OpMode {
    public HardwareCollector Sweeper;
    public void init() {
        Sweeper = new HardwareCollector(hardwareMap);
        Sweeper.hardwareSetup();
    }
    public void loop() {
        Sweeper.collect(gamepad1.left_trigger);
    }
}
