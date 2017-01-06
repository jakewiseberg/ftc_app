package org.fawkesbots.rc.heathens.Autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

import org.fawkesbots.rc.heathens.Hardware.HardwareEncoder;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanum;
import org.fawkesbots.rc.heathens.Hardware.HardwareMecanumWithEncoders;
import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
import org.fawkesbots.rc.vendetta.Auton;

@Autonomous(
        name = "IRseeker",
        group = "testing"
)


/**
 * Created by shyam on 12/14/2016.
 */


public class IRSeeker extends Auton {
    public HardwareMecanum Drive;

    public void runOpMode() throws InterruptedException {
        IrSeekerSensor seeker;
        seeker = hardwareMap.irSeekerSensor.get("sensor ir");
        waitForStart();
        while (true) {
            
            if (seeker.getStrength() >= 155)
                Drive.mecanum(0, 0, 0.78f);
             else if ((float) (seeker.getStrength()) < 155) {
                Drive.mecanum(0, 0, -1 * 0.78f);
            } else {
                Log.e("Autonomous", "runOpMode: ");
            }
        }
    }


}









