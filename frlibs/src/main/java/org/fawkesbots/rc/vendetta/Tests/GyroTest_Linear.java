package org.fawkesbots.rc.vendetta.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.fawkesbots.rc.vendetta.Autonomous;
import org.fawkesbots.rc.vendetta.Gyro;

/**
 * Created by hello_000 on 10/23/2016.
 */
@Disabled
@TeleOp(
        name="GyroTest Linear",group="Tests"
)
public class GyroTest_Linear extends Autonomous {
    public Gyro Dante;
    public float theta, drift=0, readings=0;
    public void initialize() {
        Dante = new Gyro(hardwareMap);
        Dante.init();
        super.init();
    }

    public void calibrate() {
        while(readings<500) {
            drift+=Dante.orientation[0];
            readings ++;
        } drift/=500.0f;
    }

    public void getTheta() {
        theta = Dante.orientation[0];
        if(theta<0) theta =(float) 2.0f*(float)Math.PI + theta;
        theta = (theta-drift)*Dante.DEG;
    }

    public void autonomous() {
        calibrate();
        getTheta();
        while(Math.abs(theta+30.0f)>3.0f) {
            getTheta();
        }
        telemetry.addData("Turned","90 degrees clockwise");
    }
}
