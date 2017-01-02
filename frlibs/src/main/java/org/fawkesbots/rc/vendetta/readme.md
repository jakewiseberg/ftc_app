#Vendetta

##What it does
- provides better motors and servos with more safe implementations
- provides structure to hardware definitions
- provides simple sensor interfacing and battery checks

##How to use Sentinel
Add a @DefSentinel and extend the Sentinel class.
``` java
@DefSentinel(
    drive="Type of drive",
    description="Description of drive"
) //if it is more than just a chassis drive set specialized=true above
public class HardwareNAME extends Sentinel {
```
Write the constructor, pass on a HardwareMap hwMap
``` java
public HardwareNAME(HardwareMap hwMap) { super(hwMap); }
```
Add any necessary motors as global instance variables and write the hardwareSetup.
``` java
public FawkesMotor motor1, motor2;
public boolean hardwareSetup() {
    motor1 = retrieveMotor("motor 1").unencode().power(0);
    motor2 = retrieveMotor("motor 2").unencode().power(0);
    return true;
}
```
Add a drive system.
``` java
public boolean tank(float left, float right) {
        fl.power(left); bl.power(left);
        fr.power(right); br.power(right);
        return true;
}
```

You can now extend this class in the future.
``` java
@DefSentinel (
    drive="Tank",
    specialized = true,
    description="Tank drive with added claw servos"
)
public class HardwareClawbot extends HardwareTank {
```

##How to use Autonomous
AUTONOMOUS CLASS IS NOT FINISHED--DO NOT RUN OR YOU WILL ENCOUNTER PROBLEMS!
You can use the Autonomous class similarly to the OpMode.
Instead of defining loop(), define autonomous() which will only run once.

#Sensors
##Gyro
Gyro is still buggy. Basically, create a Gyro object, and call .init() on it.
Then, check the orientation[0] value. It is recommended to calculate drift and convert to degrees.
The gyro is still buggy. GyroTest.java under tests contains a working example.
In this example, -30 degrees is left. 270 degrees is right. -70 is 180 degrees. 0 is home.
I do not know why this is. Make sure to steady the phone when using this.
Reboot if your values are jumpy.2