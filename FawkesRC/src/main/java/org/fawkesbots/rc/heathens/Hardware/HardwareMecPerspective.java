package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by jakew on 12/3/2016.
 */

public class HardwareMecPerspective extends HardwareMecanum {
    double theta;
    BNO055IMU imu;

    public HardwareMecPerspective(HardwareMap hwMap, BNO055IMU imu) {
        super(hwMap);
        this.imu = imu;
    }

    public boolean perspective(float x, float y, float z) {
        theta = imu.getPosition().x;
        x = (x*(float)(Math.cos(theta))) + (y*(float)(Math.sin(theta)));
        y = (-x*(float)(Math.sin(theta))) + (y*(float)(Math.cos(theta)));
        mecanum(x,y,z);
        return true;
    }
}
