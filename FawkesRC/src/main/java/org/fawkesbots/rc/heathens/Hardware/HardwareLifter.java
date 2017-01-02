package org.fawkesbots.rc.heathens.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.fawkesbots.rc.vendetta.Sentinel;
import org.fawkesbots.rc.vendetta.FawkesMotor;
import org.fawkesbots.rc.vendetta.Sentinel;

/**
 * Created by krishenseth on 1/2/17.
 */

public class HardwareLifter extends Sentinel{

        public  FawkesMotor lifter;


        public HardwareLifter(HardwareMap a) {super(a);}

        public boolean hardwareSetup() {
            lifter=retrieveMotor("lifter");

            return true;
        }

        public boolean rise(float speed) {
            lifter.power(speed);

            return true;
        }}

