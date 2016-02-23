/* Copyright (c) 2014 Qualcomm Technologies Inc

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
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.




 */

package org.fawkes.fawkesrc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Fawkes extends OpMode {
    DcMotor frontleft, frontright, backleft, backright, collector, scorer, pulluno, pulldos;
    Servo climber, tape;
    public float pwr, angle, collect, score, pull;
    public static double deadzone = 0.2;

    @Override
    public void init() {
        //MOVE MOTORS,
        frontleft = hardwareMap.dcMotor.get("front_left");
        frontright = hardwareMap.dcMotor.get("front_right");
        backleft = hardwareMap.dcMotor.get("back_left");
        backright = hardwareMap.dcMotor.get("back_right");

        //COLLECTOR
        collector = hardwareMap.dcMotor.get("collector");

        //SLIDES
        //scorer = hardwareMap.dcMotor.get("scorer");

        //Tape Measure
        pulluno = hardwareMap.dcMotor.get("arm");
        pulldos = hardwareMap.dcMotor.get("arm dos");
        tape= hardwareMap.servo.get("tape");
        //SERVOS
//        score_tilt = hardwareMap.servo.get("score_tilt");
        climber = hardwareMap.servo.get("climber");
        climber.setPosition(1.00);
        tape.setPosition(0.5);

        //reverse drive
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override //see was$$'s quote above
    public void loop() {
        getJoyVals();
        frontright.setPower(Range.clip(pwr + angle, -0.78, 0.78));
        backleft.setPower(Range.clip(pwr - angle, -0.78, 0.78));
        frontleft.setPower(Range.clip(pwr - angle, -0.78, 0.78));
        backright.setPower(Range.clip(pwr + angle, -0.78, 0.78));
        collector.setPower(collect);
        //scorer.setPower(score);
        pulluno.setPower(pull);
        pulldos.setPower(-pull);
    }

    public void getJoyVals() {
        //Movement
        pwr = gamepad1.left_stick_y;
        angle = gamepad1.right_stick_x;
        //deadzones
        if (Math.abs(pwr) < deadzone) pwr = 0;
        if (Math.abs(angle) < (deadzone / 2.0)) angle = 0;

        //Collector
        if (gamepad1.left_trigger > 0.3) collect = 0.50f;
        else if (gamepad1.left_bumper) collect = -0.50f;
        else collect = 0f;

        //scorer slides
        score = Range.clip(gamepad2.left_stick_y,-1,1);
        if(Math.abs(score)<deadzone) score=0;

        //tape measure
        pull = Range.clip(gamepad2.right_stick_y,-1,1);
        if(Math.abs(pull)<deadzone) pull=0;

        if (gamepad1.left_stick_button) climber.setPosition(0);
        if (gamepad1.right_stick_button) climber.setPosition(1.0);
        if(gamepad2.left_stick_button) tape.setPosition(0.5);
        if(gamepad2.right_stick_button) tape.setPosition(1.0);


/*
        if(gamepad2.x){
            score_tilt.setPosition(Range.clip(score_tilt.getPosition()+0.1,0,1));
        }
        else if(gamepad2.b){
            score_tilt.setPosition(Range.clip(score_tilt.getPosition() - 0.3, 0, 1));
        }
*/


    }

    @Override
    public void stop() {
        //nothing here? probably gotta call garbage collection at some point

    }
}