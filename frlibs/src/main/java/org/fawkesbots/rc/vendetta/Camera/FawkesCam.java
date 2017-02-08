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
        while(!main.tookPic && at.opModeIsActive()){ }
        at.log("Camera","Took picture");
        image = main.getImageFile();
        at.log("Camera","Got image file");
        main.mCamera.release();
        main.mCamera = null;
        at.log("Camera","Safely released");
        bmp = ImageUtil.bmpFromImage(image);
        at.log("Camera","Made bitmap");
        return BeaconClassifier.processBitmap(bmp);
    }

}
