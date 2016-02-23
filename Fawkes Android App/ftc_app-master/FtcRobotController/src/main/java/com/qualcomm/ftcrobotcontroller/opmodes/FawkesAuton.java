/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * A simple example of a linear op mode that will approach an IR beacon
 */
public class FawkesAuton extends LinearOpMode{

  DcMotor frontright, frontleft, backright, backleft;
  Servo climber;
  final static double WHEEL_RADIUS = 8;
  final static double GEAR_RATIO = 1.0/3.0;

  public static double encode(double dist) {
    return 1440 * (dist / (Math.PI * WHEEL_RADIUS * 2 * GEAR_RATIO));
  }

  public void pwr(double fr, double fl, double br, double bl) {
    fr = Range.clip(fr, -0.78, 0.78);
    fl = Range.clip(fl, -.78, 0.78);
    br = Range.clip(br, -.78, .78);
    bl = Range.clip(bl, -.78, .78);
    frontright.setPower(fr);
    frontleft.setPower(fl);
    backleft.setPower(bl);
    backright.setPower(br);
  }

  public void pwr(double l, double r) {
    pwr(r, l, r, l);
  }

  @Override
  public void runOpMode() throws InterruptedException {
    frontleft = hardwareMap.dcMotor.get("front_left");
    frontright = hardwareMap.dcMotor.get("front_right");
    backleft = hardwareMap.dcMotor.get("back_left");
    backright = hardwareMap.dcMotor.get("back_right");
    climber = hardwareMap.servo.get("climber");
    sleep(4000);
    waitForStart();
    pwr(0.78, -.78);
    sleep(3000);
    pwr(0, 0);
    pwr(0.3, 0.3);
    sleep(600);
    pwr(.78, -.78);
    sleep(1250);
    pwr(0, 0);
    pwr(-.78, .78);
    sleep(1250);
    pwr(-.3, -.3);
    sleep(1400);
    pwr(.78, -.78);
    sleep(700);
    pwr(0, 0);
    climber.setPosition(0.7);
    sleep(1200);
    climber.setPosition(0);

    pwr(-.78,.78);
    sleep(700);
    pwr(0.3,0.3);
    sleep(1400);
    pwr(.78,-.78);
    sleep(1250);
    pwr(0,0);

    while(true)
      sleep(1000000);
  } // loop

}