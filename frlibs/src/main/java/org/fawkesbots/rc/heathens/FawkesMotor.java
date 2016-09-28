package org.fawkesbots.rc.heathens;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import com.qualcomm.robotcore.hardware.HardwareDevice.Manufacturer;

/**
 * Created by hello_000 on 9/28/2016.
 */
public class FawkesMotor implements DcMotor {

    protected DcMotorController controller;
    protected int portNumber;
    protected Direction direction;
    protected RunMode mode;
    protected double min=-1,max=1;
    public FawkesMotor setBounds(double min, double max){
        this.min=min; this.max=max; return this;
    }

    public FawkesMotor(DcMotor motor){
        this(motor.getController(), motor.getPortNumber());
    }

    public FawkesMotor(DcMotorController controller, int portNumber) {
        this(controller, portNumber, Direction.FORWARD);
    }

    public FawkesMotor(DcMotorController controller, int portNumber, Direction direction) {
        this.controller = null;
        this.portNumber = -1;
        this.direction = Direction.FORWARD;
        this.mode = RunMode.RUN_WITHOUT_ENCODER;
        this.controller = controller;
        this.portNumber = portNumber;
        this.direction = direction;
    }

    public Manufacturer getManufacturer() {
        return this.controller.getManufacturer();
    }

    public String getDeviceName() {
        return "DC Motor";
    }

    public String getConnectionInfo() {
        return this.controller.getConnectionInfo() + "; port " + this.portNumber;
    }

    public int getVersion() {
        return 1;
    }

    public void resetDeviceConfigurationForOpMode() {
        this.setDirection(Direction.FORWARD);
    }

    public void close() {
        this.setPowerFloat();
    }

    public DcMotorController getController() {
        return this.controller;
    }

    public synchronized void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getPortNumber() {
        return this.portNumber;
    }

    public synchronized void setPower(double power) {
        if (this.direction == Direction.REVERSE) {
            power = -power;
        }

        if (this.mode == RunMode.RUN_TO_POSITION) {
            power = Math.abs(power);
        }

        this.internalSetPower(power);
    }

    protected void internalSetPower(double power) {
        this.controller.setMotorPower(this.portNumber, power);
    }

    public void setMaxSpeed(int encoderTicksPerSecond) {
        this.controller.setMotorMaxSpeed(this.portNumber, encoderTicksPerSecond);
    }

    public int getMaxSpeed() {
        return this.controller.getMotorMaxSpeed(this.portNumber);
    }

    public synchronized double getPower() {
        double power = this.controller.getMotorPower(this.portNumber);
        if (this.direction == Direction.REVERSE) {
            power = -power;
        }

        return power;
    }

    public boolean isBusy() {
        return this.controller.isBusy(this.portNumber);
    }

    public synchronized void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
        this.controller.setMotorZeroPowerBehavior(this.portNumber, zeroPowerBehavior);
    }

    public synchronized ZeroPowerBehavior getZeroPowerBehavior() {
        return this.controller.getMotorZeroPowerBehavior(this.portNumber);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public synchronized void setPowerFloat() {
        this.setZeroPowerBehavior(ZeroPowerBehavior.FLOAT);
        this.setPower(0.0D);
    }

    public synchronized boolean getPowerFloat() {
        return this.getZeroPowerBehavior() == ZeroPowerBehavior.FLOAT && this.getPower() == 0.0D;
    }

    public synchronized void setTargetPosition(int position) {
        if (this.direction == Direction.REVERSE) {
            position *= -1;
        }

        this.internalSetTargetPosition(position);
    }

    protected void internalSetTargetPosition(int position) {
        this.controller.setMotorTargetPosition(this.portNumber, position);
    }

    public synchronized int getTargetPosition() {
        int position = this.controller.getMotorTargetPosition(this.portNumber);
        if (this.direction == Direction.REVERSE) {
            position *= -1;
        }

        return position;
    }

    public synchronized int getCurrentPosition() {
        int position = this.controller.getMotorCurrentPosition(this.portNumber);
        if (this.direction == Direction.REVERSE) {
            position *= -1;
        }

        return position;
    }

    public synchronized void setMode(RunMode mode) {
        mode = mode.migrate();
        this.mode = mode;
        this.internalSetMode(mode);
    }

    protected void internalSetMode(RunMode mode) {
        this.controller.setMotorMode(this.portNumber, mode);
    }

    public RunMode getMode() {
        return this.controller.getMotorMode(this.portNumber);
    }

    public synchronized FawkesMotor power(double pwr) {
        pwr=(pwr<this.min)?this.min:(pwr>this.max)?this.max:pwr;
        this.setPower(pwr);
        return this;
    }
}
