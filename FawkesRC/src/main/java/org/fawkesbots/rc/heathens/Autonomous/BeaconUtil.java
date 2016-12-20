package org.fawkesbots.rc.heathens.Autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.fawkesbots.rc.heathens.Hardware.HardwareMecanum;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.vendetta.Auton;
import org.fawkesbots.rc.vendetta.Camera.FawkesCam;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Disabled

/**
 * Created by Priansh on 11/30/16.
 */
public class BeaconUtil {
    public HardwareMecanumWithEncoders EncodedDrive;
    public FawkesCam AutonCam;

    public float TILE=24; //length of a tile
    public String tag = "Beacon";
    public Telemetry telemetry;

    public BeaconUtil(Telemetry tele, HardwareMecanumWithEncoders enc) {
        telemetry = tele;EncodedDrive = enc;
    }


    public boolean log(String loggable) {
        telemetry.addData(tag, loggable);
        Log.e(tag,loggable);
        telemetry.update();
        return true;
    }

    public boolean log(String taggable, String loggable) {
        telemetry.addData(taggable,loggable);
        telemetry.update();
        Log.e(taggable,loggable);
        return true;
    }

    //Red is 1, Blue is -1
    public void moveToBeacon(int side) {
        log("started motion");
        EncodedDrive.forwardEncoded(TILE, 0.78f);
        log("moved forward");
        EncodedDrive.rotateEncoded(side * 16.7f, 0.78f);
        EncodedDrive.forwardEncoded(TILE, 0.78f);
        EncodedDrive.rotateEncoded(side * -16.7f, 0.78f);
        log("strafed to next to mountain");
        EncodedDrive.forwardEncoded(TILE, 0.78f);
        log("moved another tile up");
        EncodedDrive.rotateEncoded(side * 16.7f, 0.78f);
        log("turn to face beacon");
    }

    //Red is 1, Blue is -1
    public void hitBeacon(int side, int[] colors) {
        EncodedDrive.forwardEncoded(20, 0.78f);
        log("pressing beacon");
        if(colors[0]>colors[1]) { EncodedDrive.rotateEncoded(side*18,0.78f); EncodedDrive.rotateEncoded(side*-18,0.78f); }
        else if(colors[1]>colors[0]) { EncodedDrive.rotateEncoded(side*-18,.78f); EncodedDrive.rotateEncoded(side*18,0.78f); }
    }

}
