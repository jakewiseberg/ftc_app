package org.fawkesbots.rc.vendetta;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.BatteryChecker;

public abstract class Sentinel implements BatteryChecker.BatteryWatcher {

    private HardwareMap hardwareMap;
    public double min=-.78,max=.78;
//    public FawkesMotor[] motors; public String[] motor_names;
    public BatteryChecker batman;
    public float BATTERY_LEVEL=14.0f;

    public FawkesMotor retrieveMotor(String name) {
        return new FawkesMotor(this.hardwareMap.dcMotor.get(name)).setBounds(this.min,this.max);
    }

    public void updateBatteryLevel(float a) {
        this.BATTERY_LEVEL = a;
    }

    public Sentinel(HardwareMap hardwareMap) {
        Log.e("Sentinel","Making");
        this.hardwareMap = hardwareMap;
        Log.e("Sentinel","2");
    }

    public boolean monitorBattery() {
        this.batman.startBatteryMonitoring();
        return true;
    }

    public abstract boolean hardwareSetup();

}
