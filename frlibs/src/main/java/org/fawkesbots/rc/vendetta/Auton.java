//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.fawkesbots.rc.vendetta;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.ThreadPool;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.robotcore.internal.TelemetryInternal;

public abstract class Auton extends OpMode {
    private Auton.LinearOpModeHelper helper = null;
    private ExecutorService executorService = null;
    private volatile boolean isStarted = false;
    private volatile boolean stopRequested = false;

    public String tag = "Autonomous";

    public Auton() {
    }

    public abstract void runOpMode() throws InterruptedException;

    public synchronized void waitForStart() throws InterruptedException {
        while(!this.isStarted) {
            synchronized(this) {
                this.wait();
            }
        }

    }

    /** @deprecated */
    @Deprecated
    public void waitOneFullHardwareCycle() throws InterruptedException {
        this.waitForNextHardwareCycle();
        Thread.sleep(1L);
        this.waitForNextHardwareCycle();
    }

    /** @deprecated */
    @Deprecated
    public void waitForNextHardwareCycle() throws InterruptedException {
        synchronized(this) {
            this.wait();
        }
    }

    public final void idle() throws InterruptedException {
        if(this.isStopRequested()) {
            throw new InterruptedException();
        } else {
            Thread.yield();
        }
    }

    public final void sleep(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }

    public final boolean opModeIsActive() {
        return !this.isStopRequested() && this.isStarted();
    }

    public final boolean isStarted() {
        return this.isStarted;
    }

    public final boolean isStopRequested() {
        return this.stopRequested || Thread.currentThread().isInterrupted();
    }

    public final void init() {
        this.executorService = ThreadPool.newSingleThreadExecutor();
        this.helper = new Auton.LinearOpModeHelper();
        this.isStarted = false;
        this.stopRequested = false;
        this.executorService.execute(this.helper);
    }

    public final void init_loop() {
        this.handleLoop();
    }

    public final void start() {
        this.stopRequested = false;
        this.isStarted = true;
        synchronized(this) {
            this.notifyAll();
        }
    }

    public final void loop() {
        this.handleLoop();
    }

    public final void stop() {
        this.stopRequested = true;
        if(this.executorService != null) {
            onStop();
            this.executorService.shutdownNow();

            try {
                String e = "user linear op mode";
                ThreadPool.awaitTermination(this.executorService, 100L, TimeUnit.DAYS, e);
            } catch (InterruptedException var2) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public void onStop(){

    }

    public boolean log(String loggable) {
        telemetry.addData(tag,loggable);
        telemetry.update();
        Log.e(tag,loggable);
        return true;
    }

    public boolean log(String taggable, String loggable) {
        telemetry.addData(taggable,loggable);
        telemetry.update();
        Log.e(taggable,loggable);
        return true;
    }

    protected void handleLoop() {
        if(this.helper.hasRuntimeException()) {
            throw this.helper.getRuntimeException();
        } else {
            synchronized(this) {
                this.notifyAll();
            }
        }
    }

    protected void postInitLoop() {
        if(this.telemetry instanceof TelemetryInternal) {
            ((TelemetryInternal)this.telemetry).tryUpdateIfDirty();
        }

    }

    protected void postLoop() {
        if(this.telemetry instanceof TelemetryInternal) {
            ((TelemetryInternal)this.telemetry).tryUpdateIfDirty();
        }

    }

    protected class LinearOpModeHelper implements Runnable {
        protected RuntimeException exception = null;
        protected boolean isShutdown = false;

        public LinearOpModeHelper() {
        }

        public void run() {
            ThreadPool.logThreadLifeCycle("LinearOpMode main", new Runnable() {
                public void run() {
                    LinearOpModeHelper.this.exception = null;
                    LinearOpModeHelper.this.isShutdown = false;

                    try {
                        Auton.this.runOpMode();
                        Auton.this.requestOpModeStop();
                    } catch (InterruptedException var7) {
                        RobotLog.d("LinearOpMode received an InterruptedException; shutting down this linear op mode");
                    } catch (CancellationException var8) {
                        RobotLog.d("LinearOpMode received a CancellationException; shutting down this linear op mode");
                    } catch (RuntimeException var9) {
                        LinearOpModeHelper.this.exception = var9;
                    } finally {
                        LinearOpModeHelper.this.isShutdown = true;
                    }

                }
            });
        }

        public boolean hasRuntimeException() {
            return this.exception != null;
        }

        public RuntimeException getRuntimeException() {
            return this.exception;
        }

        public boolean isShutdown() {
            return this.isShutdown;
        }
    }
}
