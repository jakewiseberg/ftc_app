package org.fawkesbots.rc.heathens.Autonomous;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.hardware.IrSeekerSensor;
        import org.fawkesbots.rc.heathens.Hardware.HardwareTank;
        import org.fawkesbots.rc.vendetta.Auton;

@Autonomous(
    name= "IRseeker",
        group = "testing"
)

/**
 * Created by shyam on 12/14/2016.
 */


public class IRSeeker  extends Auton{
    public void runOpMode() throws InterruptedException {
        IrSeekerSensor seeker;
        seeker= hardwareMap.irSeekerSensor.get("sensor ir");
        waitForStart();
        while(opModeIsActive()){
            if(seeker.signalDetected()){
                telemetry.addData("Angle", seeker.getAngle());
                telemetry.addData("Strength", seeker.getStrength());
            }
            else{
                telemetry.addData("Seeker", "Signal Lost");
            }
            telemetry.update();
        }
    }

}




