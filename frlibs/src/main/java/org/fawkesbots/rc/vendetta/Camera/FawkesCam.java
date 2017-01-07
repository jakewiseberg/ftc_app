package org.fawkesbots.rc.vendetta.Camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.Image;
import android.util.Log;
import android.view.*;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.Auton;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.io.File;

public class FawkesCam {

    FtcRobotControllerActivity main;
    HardwareMap hw;
    Bitmap bmp; File image;
    Auton at;

    public FawkesCam(HardwareMap hwMap, Auton a) {
        hw = hwMap;
        at=a;
        main = (FtcRobotControllerActivity)(hw.appContext);
    }

    public int[] getBeaconColors() {
        at.log("Camera", "Setting up");
        main.takePic();
        at.log("Camera", "Taking pic");
        Log.e("Camera", "Shutter activated");
        while(!main.tookPic && at.opModeIsActive()){ }
        at.log("Camera","Took picture");
        Log.e("Camera", "Took picture");
        image = main.getImageFile();
        at.log("Camera","Got image file");
        Log.e("Camera","Got the image file");
        bmp = ImageUtil.bmpFromImage(image);
        at.log("Camera","Made bitmap");
        Log.e("Camera","Made bitmap");
        return BeaconClassifier.processBitmap(bmp);
    }

}
