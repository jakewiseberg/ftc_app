package org.fawkesbots.rc.vendetta;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.HardwareDevice.Manufacturer;
import com.qualcomm.robotcore.hardware.Servo.Direction;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by Priansh on 11/23/16.
 */
public class FawkesServo implements Servo {
    protected ServoController controller;
    protected int portNumber;
    protected Direction direction;
    protected double limitPositionMin;
    protected double limitPositionMax;

    public FawkesServo(ServoController controller, int portNumber) {
        this(controller, portNumber, Direction.FORWARD);
    }

    public FawkesServo(Servo a) {
        this(a.getController(),a.getPortNumber());
    }

    public FawkesServo(ServoController controller, int portNumber, Direction direction) {
        this.controller = null;
        this.portNumber = -1;
        this.direction = Direction.FORWARD;
        this.limitPositionMin = 0.0D;
        this.limitPositionMax = 1.0D;
        this.direction = direction;
        this.controller = controller;
        this.portNumber = portNumber;
    }

    public Manufacturer getManufacturer() {
        return this.controller.getManufacturer();
    }

    public String getDeviceName() {
        return "Servo";
    }

    public String getConnectionInfo() {
        return this.controller.getConnectionInfo() + "; port " + this.portNumber;
    }

    public int getVersion() {
        return 1;
    }

    public synchronized void resetDeviceConfigurationForOpMode() {
        this.limitPositionMin = 0.0D;
        this.limitPositionMax = 1.0D;
        this.direction = Direction.FORWARD;
    }

    public void close() {
    }

    public ServoController getController() {
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

    public synchronized void setPosition(double position) {
        position = Range.clip(position, 0.0D, 1.0D);
        if(this.direction == Direction.REVERSE) {
            position = this.reverse(position);
        }

        double scaled = Range.scale(position, 0.0D, 1.0D, this.limitPositionMin, this.limitPositionMax);
        this.internalSetPosition(scaled);
    }

    protected void internalSetPosition(double position) {
        this.controller.setServoPosition(this.portNumber, position);
    }

    public synchronized double getPosition() {
        double position = this.controller.getServoPosition(this.portNumber);
        if(this.direction == Direction.REVERSE) {
            position = this.reverse(position);
        }

        double scaled = Range.scale(position, this.limitPositionMin, this.limitPositionMax, 0.0D, 1.0D);
        return Range.clip(scaled, 0.0D, 1.0D);
    }

    public synchronized void scaleRange(double min, double max) {
        min = Range.clip(min, 0.0D, 1.0D);
        max = Range.clip(max, 0.0D, 1.0D);
        if(min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        } else {
            this.limitPositionMin = min;
            this.limitPositionMax = max;
        }
    }

    private double reverse(double position) {
        return 1.0D - position + 0.0D;
    }
}

