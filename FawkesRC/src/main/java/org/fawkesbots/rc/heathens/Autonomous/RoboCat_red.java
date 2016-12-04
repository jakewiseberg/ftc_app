package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;

@Autonomous(
        name="R_C Red",
        group="Encoded"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class RoboCat_red extends LinearOpMode {
    public HardwareMecanumWithEncoders Gargoyle;
    public FawkesCam Jenny;

    @Override
    public void runOpMode() throws InterruptedException {
        Gargoyle = new HardwareMecanumWithEncoders(hardwareMap,telemetry);
        Gargoyle.hardwareSetup(); Gargoyle.setSides(1, 1, 1, 1);
        Jenny = new FawkesCam(hardwareMap);
        waitForStart();
        Gargoyle.forwardEncoded(24, 0.78f);
        Gargoyle.strafeEncoded(-27, 0.78f);
        Gargoyle.forwardEncoded(24, 0.78f);
        Gargoyle.rotateEncoded(-22, 0.78f);
        int[] colors = Jenny.getBeaconColors();
        telemetry.addData("Cam","worked");
        telemetry.update();
        Gargoyle.forwardEncoded(20,0.78f);
        if(colors[0]>colors[1]) { Gargoyle.rotateEncoded(18,0.78f); Gargoyle.rotateEncoded(-18,0.78f); }
        else if(colors[1]>colors[0]) { Gargoyle.rotateEncoded(-18,.78f); Gargoyle.rotateEncoded(18,0.78f); }
        else Gargoyle.forwardEncoded(-24,0.78f);
    }
}
