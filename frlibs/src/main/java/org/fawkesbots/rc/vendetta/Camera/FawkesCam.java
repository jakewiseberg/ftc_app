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

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.io.File;

public class FawkesCam {

    FtcRobotControllerActivity main;
    HardwareMap hw;
    Bitmap bmp; File image;

    public FawkesCam(HardwareMap hwMap) {
        hw = hwMap;
        main = (FtcRobotControllerActivity)(hw.appContext);
    }

    public int[] getBeaconColors() {
        main.takePic();
        Log.e("Cam", "Making progress");
        while(!main.tookPic){ }
        image = main.getImageFile();
        Log.e("Cam","file image got");
        bmp = ImageUtil.bmpFromImage(image);
        Log.e("Cam","bumpy made");
        return BeaconClassifier.processBitmap(bmp);
    }

}
