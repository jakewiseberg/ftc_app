package org.fawkesbots.rc.heathens.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;

@Autonomous(
        name="R_C Blue",
        group="Finished"
)

/**
 * Created by Priansh on 11/30/16.
 */
public class RoboCat_blue extends LinearOpMode {
    public HardwareMecanumWithEncoders Gargoyle;
    public FawkesCam Jenny;

    public float TILE=24;

    @Override
    public void runOpMode() throws InterruptedException {
        Gargoyle = new HardwareMecanumWithEncoders(hardwareMap,telemetry);
        Gargoyle.hardwareSetup(); Gargoyle.setSides(1, 1, 1, 1);
        Jenny = new FawkesCam(hardwareMap);
        waitForStart();
        waitForStart();

//        Gargoyle.forwardEncoded(TILE*4    ,0.78f);
        //Adam.log("Moving");
        Gargoyle.forwardEncoded(TILE, 0.78f);
        Gargoyle.strafeEncoded(-(TILE+3), 0.78f);
        Gargoyle.forwardEncoded(TILE, 0.78f);
        Gargoyle.rotateEncoded(-19, 0.78f);
        Gargoyle.forwardEncoded(24,0.78f);//was 3 not 20
        telemetry.addData("Beacon","TRacking");
        telemetry.update();
        //Adam.log("Tracking beacon");
        int[] colors = Jenny.getBeaconColors();
        telemetry.addData("Beacon",colors[0]+", "+colors[1]);
        Gargoyle.forwardEncoded(20,0.78f);
        if(colors[0]>colors[1]) { Gargoyle.rotateEncoded(18,0.78f); Gargoyle.rotateEncoded(-18,0.78f); }
        else if(colors[1]>colors[0]) { Gargoyle.rotateEncoded(-18,.78f); Gargoyle.rotateEncoded(18,0.78f); }
        else Gargoyle.forwardEncoded(-24,0.78f);
    }
}
