package org.fawkesbots.rc.heathens.Hardware;

import org.fawkesbots.rc.vendetta.Sentinel;
import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbLegacyModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LegacyModule;



/**
 * Created by Krishen 2/1/17
 */
public class HardwareSensors extends Sentinel {

    ModernRoboticsUsbLegacyModule legacyModule;
    int ULTRASONIC_PORT = 4;
    HardwareMap hM;
    public HardwareSensors(HardwareMap h) {
        super(h); hM=h;}


   public boolean hardwareSetup(){
       return true;
    }
    public boolean initialize(){
        //legacyModule = hardwareMap.legacyModule.get("legacy");
        legacyModule.enable9v(ULTRASONIC_PORT, true);
        return legacyModule.isArmed();
    }
}


