#Basic Autonomous

##Goal
This is a kind of failsafe that exists at the most basic level.
It is expected to work at all times.
Simple, effective movements, such as knocking the ball off.

##Pseudocode
```
initialize gyroscope
calculate gyroscope drift
render gyroscope heading 
move forward a small bit
turn 45 degrees to the left (calculate gyro value accordingly)
move forward at maximum speed
wait 3 seconds to knock off the capball
back up, park
```