package org.fawkesbots.rc.heathens;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.Sentinel;

/**
 * Created by hello_000 on 10/23/2016.
 */
@DefSentinel(
        drive="Tank",
        specialized = true,
        description = "For use with Res-Q season robot in outreach events, 4 wheel tank w/ sweeper " +
                "and 2 motor tape measure"
)
public class HardwareOutreach extends Sentinel {
    public FawkesMotor front_left, front_right, back_left, back_right, sweep, hook, jaw;
    public boolean hardwareSetup() {
        front_left = retrieveMotor("front left").unencode().power(0);
        front_right = retrieveMotor("front right").unencode().power(0);
        back_left = retrieveMotor("back left").unencode().power(0);
        back_right = retrieveMotor("back right").unencode().power(0);
        sweep = retrieveMotor("sweep").unencode().power(0);
        hook = retrieveMotor("hook").unencode().power(0);
        jaw = retrieveMotor("jaw").unencode().power(0);
        return true;
    }
    public HardwareOutreach(HardwareMap hwMap) {
        super(hwMap);
    }
}
