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
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains a minimal example of a Linear "OpMode". An OpMode is a 'program' that runs
 * in either the autonomous or the TeleOp period of an FTC match. The names of OpModes appear on
 * the menu of the FTC Driver Station. When an selection is made from the menu, the corresponding
 * OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@Autonomous

public class AutonomousTest extends LinearOpMode {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setTargetPosition(frontRight.getCurrentPosition());
        frontLeft.setTargetPosition(frontRight.getCurrentPosition());
        backRight.setTargetPosition(backRight.getCurrentPosition());
        backLeft.setTargetPosition(backRight.getCurrentPosition());

        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setPower(0.35);
        frontRight.setPower(0.35);
        backRight.setPower(0.35);
        backLeft.setPower(0.35);
        // Wait for the game to start (driver presses PLAY)



        waitForStart();
        driveTicks (1095, 1095, 1095, 1095); //Forward 2ft

        driveTicks (369, 1821, 369, 1821); //Num+(537.6*1.35) = 90 degree right

        driveTicks (3654, 5106, 3654, 5106); //Forward 3Ft

        driveTicks (2559, 4011, 2559, 4011); //Back 2ft

        driveTicks (1833, 4737, 1833, 4737); //Turn 90 degree right

        driveTicks (2928, 5832, 2928, 5832); // Forward 2Ft

        driveTicks (3654, 5106, 3654, 5106); //Num-(537.6*1.35) = 90 degree left

        driveTicks (5297, 6749, 5297, 6749); //Forward 3Ft

        driveTicks (6023, 6023, 6023, 6023); //Turn 90 degree left

        driveTicks (8213, 8213, 8213, 8213); //Forward 4Ft

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("frontRight Ticks", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft Ticks", frontLeft.getCurrentPosition());
            telemetry.addData("backRight Ticks", backRight.getCurrentPosition());
            telemetry.addData("backLeft Ticks", backLeft.getCurrentPosition());
            telemetry.update();


        }
    }
    public void wait(double x) {
        long preTime=System.currentTimeMillis();
        while(System.currentTimeMillis()-preTime < x*1000) {
            if (isStopRequested()) {
                break;
            }
        }
    }

    public void driveTicks(int frontRightTicks, int frontLeftTicks, int backRightTicks, int backLeftTicks) {
        frontRight.setTargetPosition(frontRightTicks);
        frontLeft.setTargetPosition(frontLeftTicks);
        backRight.setTargetPosition(backRightTicks);
        backLeft.setTargetPosition(backLeftTicks);

        while(frontRight.getCurrentPosition() <= frontRightTicks - 3 || frontRight.getCurrentPosition() >= frontRightTicks + 3);
        while(backRight.getCurrentPosition() <= backRightTicks - 3 || backRight.getCurrentPosition() >= backRightTicks + 3);
        while(frontLeft.getCurrentPosition() <= frontLeftTicks - 3 || frontLeft.getCurrentPosition() >= frontLeftTicks + 3);
        while(backLeft.getCurrentPosition() <= backLeftTicks - 3 || backLeft.getCurrentPosition() >= backLeftTicks + 3);

        // frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODERS);
        // frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODERS);
        // backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODERS);
        // backLeft.setMode(DcMotors.RunMode.STOP_AND_RESET_ENCODERS);
    }
}
