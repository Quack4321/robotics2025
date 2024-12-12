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

    DcMotor rightFront;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor leftBack;
    DcMotor grabExtend;

    DcMotor grabPivot;
    DcMotor pullPivot;
    DcMotor pullExtend;

    Servo wrist;
    Servo spinny;

    int speedIndex;
    float[] speed;

    // Predefined positions of the motors:
    int pullPivotStart;
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
    long preTime;

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
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");                  // Control Hub 3
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");                // Control Hub 2
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");                // Control Hub 1
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");              // Control Hub 0

        grabExtend = hardwareMap.get(DcMotor.class, "grabExtend");              // Extension Hub 0
        grabPivot = hardwareMap.get(DcMotor.class, "grabPivot");                // Extension Hub 1
        pullPivot = hardwareMap.get(DcMotor.class, "pullPivot");                // Extension Hub 2
        pullExtend = hardwareMap.get(DcMotor.class, "pullExtend");              // Extension Hub 3

        wrist = hardwareMap.get(Servo.class, "wrist");                          // Extension Hub 0
        spinny = hardwareMap.get(Servo.class, "spinny");                        // Extension Hub 1

        wrist.setPosition(0);
        spinny.setPosition(0);

        // Set motors to brake upon zero power:
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pullExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        pullPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        grabPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Sets these motors to run in correct direction:
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);

        // Predefined motor positions:
        pullPivotStart = 6300;
        pullPivotRest = 5075;
        pullPivotPull = 0;
        pullPivotClimb = 0;
        pullExtendIn = 0;
        pullExtendOut = 0;
        grabPivotRest = 1700;
        grabPivotGrab = 0;
        grabPivotScore = 5075;
        grabExtendIn = 0;
        grabExtendOut = 2100;
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
        preTime = System.currentTimeMillis();
        switchToAuto();
        if(!gamepad2.right_stick_button) {

        }
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */

    @Override
    public void loop() {
        // Displays info on Driver Hub:
        telemetry.addData("Speed", speed[speedIndex] * 100 + "%"); // Tells us the wheel motors' current speed
        telemetry.addData("grabExtend Target Position: ", grabExtend.getTargetPosition());
        telemetry.addData("pullExtend Target Position: ", pullExtend.getTargetPosition());
        telemetry.addData("grabPivot Target Position: ", grabPivot.getTargetPosition());
        telemetry.addData("pullPivot Target Position: ", pullPivot.getTargetPosition());

        telemetry.addData("grabExtend Current Position: ", grabExtend.getCurrentPosition());
        telemetry.addData("pullExtend Current Position: ", pullExtend.getCurrentPosition());
        telemetry.addData("grabPivot Current Position: ", grabPivot.getCurrentPosition());
        telemetry.addData("pullPivot Current Position: ", pullPivot.getCurrentPosition());

        // CONTROLLER 1

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
        leftFront.setPower((gamepad1.left_stick_y + (gamepad1.left_trigger - gamepad1.right_trigger)) * speed[speedIndex]);
        leftBack.setPower((gamepad1.left_stick_y + (gamepad1.right_trigger - gamepad1.left_trigger)) * speed[speedIndex]);
        rightFront.setPower((gamepad1.right_stick_y + (gamepad1.right_trigger - gamepad1.left_trigger)) * speed[speedIndex]);
        rightBack.setPower((gamepad1.right_stick_y + (gamepad1.left_trigger - gamepad1.right_trigger)) * speed[speedIndex]);


        // CONTROLLER 2

        // WHEEL SPIN
        if (gamepad2.dpad_up && !dpadUpLastTime2) {
            spinny.setPosition(1.0);
        }

        if (gamepad2.dpad_down && !dpadDownLastTime2) {
            spinny.setPosition(0);
        }

        // WRIST POSITIONS
        if (gamepad2.dpad_left && !dpadLeftLastTime2) {
            wrist.setPosition(0);
        }

        if (gamepad2.dpad_right && !dpadRightLastTime2) {
            wrist.setPosition(0.2);
        }

        // A - RESTING POSITIONS
        if (gamepad2.a && !aLastTime2) {
            rest();
        }

        if (gamepad2.x && !xLastTime2) {
            switchToAuto();
            grabPivot.setTargetPosition(4100);
        }

        if (gamepad2.b && !bLastTime2) {
            switchToAuto();
            grabExtend.setTargetPosition(grabExtendOut);
            grabPivot.setTargetPosition(grabPivotScore);
        }

        // Y - HANGING SEQUENCE
        if (gamepad2.y && !yLastTime2 /*&& System.currentTimeMillis() - preTime > 90000*/) {
            hang();
        }


        if (gamepad2.left_bumper) {
            switchToAuto();
            pullPivot.setTargetPosition(pullPivot.getCurrentPosition() - 50);
        }

        if (gamepad2.right_bumper) {
            switchToAuto();
            pullPivot.setTargetPosition(pullPivot.getCurrentPosition() + 50);
        }

        if (gamepad2.left_stick_button && !leftStickButtonLastTime2) {
            resetPositions();
        }

        if (gamepad2.right_trigger > .5) {
            switchToDriver();
        }

        if (gamepad2.left_trigger > .5) {
            switchToAuto();
        }
        if (pullExtend.getMode() == DcMotor.RunMode.RUN_USING_ENCODER) {
            pullExtend.setPower(gamepad2.left_stick_y);
            pullPivot.setPower(gamepad2.left_stick_x);
            grabExtend.setPower(gamepad2.right_stick_y);
            grabPivot.setPower(gamepad2.right_stick_x);
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

    public void switchToAuto() {
        pullPivot.setTargetPosition(pullPivot.getCurrentPosition());
        grabPivot.setTargetPosition(grabPivot.getCurrentPosition());
        pullExtend.setTargetPosition(pullExtend.getCurrentPosition());
        grabExtend.setTargetPosition(grabExtend.getCurrentPosition());

        pullPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grabPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grabExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        pullExtend.setPower(1.0);
        grabExtend.setPower(1.0);
        pullPivot.setPower(1.0);
        grabPivot.setPower(1.0);
    }

    public void switchToDriver() {
        pullPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        grabPivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pullExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        grabExtend.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void resetPositions() {
        grabExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        grabPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        grabExtend.setTargetPosition(grabExtend.getCurrentPosition());
        pullExtend.setTargetPosition(grabExtend.getCurrentPosition());
        grabPivot.setTargetPosition(grabExtend.getCurrentPosition());
        pullPivot.setTargetPosition(grabExtend.getCurrentPosition());

        grabExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        grabPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pullPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void hang() {
        switchToAuto();
        pullExtend.setTargetPosition(12000);
        waitForMotors();
        pullPivot.setTargetPosition(6000);
//        waitForMotors();
//        pullExtend.setTargetPosition(0);
//        pullPivot.setPower(.1);
//        pullPivot.setTargetPosition(2700);
//        waitForMotors();
//        pullExtend.setTargetPosition(4100);
    }

    public void rest() {
        switchToAuto();
        pullPivot.setTargetPosition(pullPivotRest);
        pullExtend.setTargetPosition(0);
        grabPivot.setTargetPosition(grabPivotRest);
        wrist.setPosition(wristRest);
    }

    public void wait(double time) {
        double initTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - initTime < time*1000);
    }

    public void waitForMotors() {
        while (pullPivot.getCurrentPosition() < pullPivot.getTargetPosition() - 10 ||
                pullPivot.getCurrentPosition() > pullPivot.getTargetPosition() + 10 ||
                pullExtend.getCurrentPosition() < pullExtend.getTargetPosition() - 10 ||
                pullExtend.getCurrentPosition() > pullExtend.getTargetPosition() + 10);
    }
}
