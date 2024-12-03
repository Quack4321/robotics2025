/*
Copyright 2024 FIRST Tech Challenge Team 9505

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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.acmerobotics.dashboard.FtcDashboard;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This file contains a minimal example of an iterative (Non-Linear) "OpMode". An OpMode is a
 * 'program' that runs in either the autonomous or the TeleOp period of an FTC match. The names
 * of OpModes appear on the menu of the FTC Driver Station. When an selection is made from the
 * menu, the corresponding OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@TeleOp

public class Drive extends OpMode {
    /* Declare OpMode members. */

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor arm;
    Servo wrist;

    boolean leftBumperLastTime;
    boolean rightBumperLastTime;
    boolean dpadRightLastTime;
    boolean dpadLeftLastTime;

    float[] speeds;
    int level;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        arm = hardwareMap.get(DcMotor.class, "arm");
        wrist = hardwareMap.get(Servo.class, "wrist");

        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);

        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        speeds = new float[] {0.25f, 0.5f, 0.75f, 1.0f};
        level = 1;

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
        frontLeft.setPower(speeds[level]*(gamepad1.left_stick_y + (-gamepad1.right_trigger + gamepad1.left_trigger)));
        backLeft.setPower((gamepad1.left_stick_y + (-gamepad1.left_trigger + gamepad1.right_trigger))*speeds[level]);
        frontRight.setPower((gamepad1.right_stick_y + (-gamepad1.left_trigger + gamepad1.right_trigger))*speeds[level]);
        backRight.setPower(speeds[level]*(gamepad1.right_stick_y + (-gamepad1.right_trigger + gamepad1.left_trigger)));
        /*frontLeft.setPower(speeds[level]*((gamepad1.left_stick_y - gamepad1.right_stick_x) + (-gamepad1.right_trigger + gamepad1.left_trigger)));
        backLeft.setPower(((gamepad1.left_stick_y - gamepad1.right_stick_x) + (-gamepad1.left_trigger + gamepad1.right_trigger))*speeds[level]);
        frontRight.setPower(((gamepad1.left_stick_y + gamepad1.right_stick_x) + (-gamepad1.left_trigger + gamepad1.right_trigger))*speeds[level]);
        backRight.setPower(speeds[level]*((gamepad1.left_stick_y + gamepad1.right_stick_x) + (-gamepad1.right_trigger + gamepad1.left_trigger)));
        */

        telemetry.addData("FrontRight", frontRight.getPower());
        telemetry.addData("FrontLeft", frontLeft.getPower());
        telemetry.addData("BackRight", backRight.getPower());
        telemetry.addData("BackLeft", backLeft.getPower());

        if (gamepad1.dpad_right){
            arm.setPower(0.5);
        } else {
            arm.setPower(0.0);
        }
        if (gamepad1.dpad_left){
            arm.setPower(-0.5);
        } else {
            arm.setPower(0.0);
        }

        if(gamepad1.a) wrist.setPosition(0);
        if(gamepad1.b) wrist.setPosition(1.0);

        if(gamepad1.left_bumper && !leftBumperLastTime)
        {
            if(level !=0)
            {
                level--;
            }
        }

        if(gamepad1.right_bumper && !rightBumperLastTime)
        {
            if(level !=3)
            {
                level++;
            }
        }

        leftBumperLastTime = gamepad1.left_bumper;
        rightBumperLastTime = gamepad1.right_bumper;
        dpadRightLastTime = gamepad1.dpad_right;
        dpadLeftLastTime = gamepad1.dpad_left;

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
