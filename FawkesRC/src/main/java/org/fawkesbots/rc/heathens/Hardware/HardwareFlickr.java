package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.heathens.DefSentinel;
import org.fawkesbots.rc.vendetta.FawkesServo;
import org.fawkesbots.rc.vendetta.Sentinel;

/**
 * Created by jakew on 12/16/2016.
 */

@DefSentinel(
        drive="Collector",
        description="collection")
public class HardwareFlickr extends Sentinel {

    public FawkesServo flickr;
    double pos;

    public HardwareFlickr(HardwareMap a) {
        super(a);
    }

    public boolean flickUp() {
        pos = flickr.getPosition();
        flickr.setPosition(.820);
        return true;
    }
    public boolean flickDown() {
        pos = flickr.getPosition();
        flickr.setPosition(.200);
        return true;
    }

    public double getPos() {
        return flickr.getPosition();
    }

    public boolean hardwareSetup() {
        flickr=retrieveServo("flickr");
        pos = flickr.getPosition();
        return true;
    }
}
