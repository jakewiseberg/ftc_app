package com.qualcomm.ftccommon;

import android.content.Context;
import com.qualcomm.ftccommon.UpdateUI.Callback;
import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.modernrobotics.ModernRoboticsUsbUtil;
import com.qualcomm.robotcore.eventloop.EventLoop;
import com.qualcomm.robotcore.eventloop.EventLoopManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.Command;
import com.qualcomm.robotcore.util.Util;

public class FtcEventLoop implements EventLoop {
    FtcEventLoopHandler f0a;
    OpModeManager f1b;
    OpModeRegister f2c;

    public FtcEventLoop(HardwareFactory hardwareFactory, OpModeRegister register, Callback callback, Context robotControllerContext) {
        this.f1b = new OpModeManager(new HardwareMap());
        this.f0a = new FtcEventLoopHandler(hardwareFactory, callback, robotControllerContext);
        this.f2c = register;
    }

    public OpModeManager getOpModeManager() {
        return this.f1b;
    }

    public void init(EventLoopManager eventLoopManager) throws RobotCoreException, InterruptedException {
        DbgLog.msg("======= INIT START =======");
        this.f1b.registerOpModes(this.f2c);
        this.f0a.init(eventLoopManager);
        HardwareMap hardwareMap = this.f0a.getHardwareMap();
        ModernRoboticsUsbUtil.init(hardwareMap.appContext, hardwareMap);
        this.f1b.setHardwareMap(hardwareMap);
        hardwareMap.logDevices();
        DbgLog.msg("======= INIT FINISH =======");
    }

    public void loop() throws RobotCoreException {
        this.f0a.displayGamePadInfo(this.f1b.getActiveOpModeName());
        this.f1b.runActiveOpMode(this.f0a.getGamepads());
        this.f0a.sendTelemetryData(this.f1b.getActiveOpMode().telemetry);
    }

    public void teardown() throws RobotCoreException {
        DbgLog.msg("======= TEARDOWN =======");
        this.f1b.stopActiveOpMode();
        this.f0a.shutdownMotorControllers();
        this.f0a.shutdownServoControllers();
        this.f0a.shutdownLegacyModules();
        this.f0a.shutdownCoreInterfaceDeviceModules();
        DbgLog.msg("======= TEARDOWN COMPLETE =======");
    }

    public void processCommand(Command command) {
        DbgLog.msg("Processing Command: " + command.getName() + " " + command.getExtra());
        this.f0a.sendBatteryInfo();
        String name = command.getName();
        String extra = command.getExtra();
        if (name.equals("CMD_RESTART_ROBOT")) {
            m0a();
        } else if (name.equals("CMD_REQUEST_OP_MODE_LIST")) {
            m2b();
        } else if (name.equals("CMD_INIT_OP_MODE")) {
            m1a(extra);
        } else if (name.equals("CMD_RUN_OP_MODE")) {
            m3c();
        } else {
            DbgLog.msg("Unknown command: " + name);
        }
    }

    private void m0a() {
        this.f0a.restartRobot();
    }

    private void m2b() {
        String str = "";
        for (String str2 : this.f1b.getOpModes()) {
            if (!str2.equals("Stop Robot")) {
                if (!str.isEmpty()) {
                    str = str + Util.ASCII_RECORD_SEPARATOR;
                }
                str = str + str2;
            }
        }
        this.f0a.sendCommand(new Command("CMD_REQUEST_OP_MODE_LIST_RESP", str));
    }

    private void m1a(String str) {
        String opMode = this.f0a.getOpMode(str);
        this.f0a.resetGamepads();
        this.f1b.initActiveOpMode(opMode);
        this.f0a.sendCommand(new Command("CMD_INIT_OP_MODE_RESP", opMode));
    }

    private void m3c() {
        this.f1b.startActiveOpMode();
        this.f0a.sendCommand(new Command("CMD_RUN_OP_MODE_RESP", this.f1b.getActiveOpModeName()));
    }
}
