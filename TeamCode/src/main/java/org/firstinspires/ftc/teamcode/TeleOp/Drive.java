/*
Copyright 2023 FIRST Tech Challenge Team 9505

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class Drive extends OpMode {
    /* Declare OpMode members. */

    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor grabExtend;

    DcMotor grabPivot;
    DcMotor pullPivot;
    DcMotor pullExtend;

    Servo wrist;
    Servo spinny;

    int speedIndex;
    float[] speed;

    // Predefined positions of the motors:
    int pullPivotRest;
    int pullPivotPull;
    int pullPivotClimb;
    int pullExtendIn;
    int pullExtendOut;
    int grabPivotRest;
    int grabPivotGrab;
    int grabPivotScore;
    int grabExtendIn;
    int grabExtendOut;
    int wristRest;
    int wristGrab;
    int wristScore;

    // Controller 1 Variables:
    boolean rightBumperLastTime;
    boolean leftBumperLastTime;
    boolean aLastTime;
    boolean yLastTime;
    boolean xLastTime;
    boolean bLastTime;
    boolean dpadUpLastTime;
    boolean dpadDownLastTime;
    boolean dpadLeftLastTime;
    boolean dpadRightLastTime;
    boolean rightStickButtonLastTime;
    boolean leftStickButtonLastTime;

    // Controller 2 Variables:
    boolean rightBumperLastTime2;
    boolean leftBumperLastTime2;
    boolean aLastTime2;
    boolean yLastTime2;
    boolean xLastTime2;
    boolean bLastTime2;
    boolean dpadUpLastTime2;
    boolean dpadDownLastTime2;
    boolean dpadLeftLastTime2;
    boolean dpadRightLastTime2;
    boolean rightStickButtonLastTime2;
    boolean leftStickButtonLastTime2;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        speed = new float[]{.25f, .5f, .75f, 1.0f};
        speedIndex = 3;

        // Initialize hardware values
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");                  // Control Hub 3
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");                // Control Hub 2
        backRight = hardwareMap.get(DcMotor.class, "backRight");                // Control Hub 1
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");              // Control Hub 0

        grabExtend = hardwareMap.get(DcMotor.class, "grabExtend");              // Extension Hub 0
        grabPivot = hardwareMap.get(DcMotor.class, "grabPivot");                // Extension Hub 1
        pullPivot = hardwareMap.get(DcMotor.class, "pullPivot");                // Extension Hub 2
        pullExtend = hardwareMap.get(DcMotor.class, "pullExtend");              // Extension Hub 3

        wrist = hardwareMap.get(Servo.class, "wrist");                          // Extension Hub 0
        spinny = hardwareMap.get(Servo.class, "spinny");                        // Extension Hub 1

        wrist.setPosition(0);
        spinny.setPosition(0);

        // Sets these motors to brake upon zero power:
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Sets these motors to run in correct direction:
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Predefined motor positions:
        pullPivotRest = 0;
        pullPivotPull = 0;
        pullPivotClimb = 0;
        pullExtendIn = 0;
        pullExtendOut = 0;
        grabPivotRest = 0;
        grabPivotGrab = 0;
        grabPivotScore = 0;
        grabExtendIn = 0;
        grabExtendOut = 0;
        wristRest = 0;
        wristGrab = 0;
        wristScore = 0;
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */

    @Override

    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    @Override
    public void loop() {
        // Displays info on Driver Hub:
        telemetry.addData("Speed", speed[speedIndex] * 100 + "%"); // Tells us the wheel motors' current speed

        if (gamepad1.right_bumper && !rightBumperLastTime) {
            if (speedIndex != speed.length - 1) {
                speedIndex++;
            }
        }

        if (gamepad1.left_bumper && !leftBumperLastTime) {
            if (speedIndex != 0) {
                speedIndex--;
            }
        }

        // Robot strafes if driver1 holds left/right trigger. Drives normally with joysticks if triggers are not pressed
        frontLeft.setPower((gamepad1.left_stick_y + (gamepad1.left_trigger - gamepad1.right_trigger)) * speed[speedIndex]);
        backLeft.setPower((gamepad1.left_stick_y + (gamepad1.right_trigger - gamepad1.left_trigger)) * speed[speedIndex]);
        frontRight.setPower((gamepad1.right_stick_y + (gamepad1.right_trigger - gamepad1.left_trigger)) * speed[speedIndex]);
        backRight.setPower((gamepad1.right_stick_y + (gamepad1.left_trigger - gamepad1.right_trigger)) * speed[speedIndex]);

        pullPivot.setPower(gamepad2.left_stick_y);
        pullExtend.setPower(gamepad2.right_stick_x);

        grabPivot.setPower(gamepad2.right_stick_y);
        grabExtend.setPower(gamepad2.right_stick_x);

        if (gamepad2.dpad_up && !dpadUpLastTime2) {
            if (spinny.getPosition() == 0) {
                spinny.setPosition(1.0);
            }
            else if (spinny.getPosition() == 1.0) {
                spinny.setPosition(0);
            }
        }

        if (gamepad2.dpad_down && !dpadDownLastTime2) {
            if (wrist.getPosition() == 0) {
                wrist.setPosition(.2);
            }
            else if (wrist.getPosition() == .2) {
                wrist.setPosition(0);
            }
        }

        if (gamepad2.a && !aLastTime2) {
            pullPivot.setTargetPosition(pullPivotRest);
            grabPivot.setTargetPosition(grabPivotRest);
            wrist.setPosition(wristRest);
        }

        aLastTime = gamepad1.a;
        bLastTime = gamepad1.b;
        xLastTime = gamepad1.x;
        yLastTime = gamepad1.y;
        dpadUpLastTime = gamepad1.dpad_up;
        dpadDownLastTime = gamepad1.dpad_down;
        dpadLeftLastTime = gamepad1.dpad_left;
        dpadRightLastTime = gamepad1.dpad_right;
        leftBumperLastTime = gamepad1.left_bumper;
        rightBumperLastTime = gamepad1.right_bumper;
        leftStickButtonLastTime = gamepad1.left_stick_button;
        rightStickButtonLastTime = gamepad1.right_stick_button;

        aLastTime2 = gamepad2.a;
        bLastTime2 = gamepad2.b;
        xLastTime2 = gamepad2.x;
        yLastTime2 = gamepad2.y;
        dpadUpLastTime2 = gamepad2.dpad_up;
        dpadDownLastTime2 = gamepad2.dpad_down;
        dpadLeftLastTime2 = gamepad2.dpad_left;
        dpadRightLastTime2 = gamepad2.dpad_right;
        leftBumperLastTime2 = gamepad2.left_bumper;
        rightBumperLastTime2 = gamepad2.right_bumper;
        leftStickButtonLastTime2 = gamepad2.left_stick_button;
        rightStickButtonLastTime2 = gamepad2.right_stick_button;
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
