package org.firstinspires.ftc.teamcode.Autonomous;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.TrajectoryBuilder;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.Vector;

@Config
@Autonomous(name = "SillyTesting", group = "Autonomous")
public class SillyTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(11.8, 61.7, Math.toRadians(90)));

        // actionBuilder builds from the drive steps passed to it,
        // and .build(); is needed to build the trajectory
        Action trajectoryAction1 = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(24,24), Math.PI)
                .build();
        Action trajectoryAction2 = drive.actionBuilder(new Pose2d(24,24,Math.PI/2))
                .strafeToLinearHeading(new Vector2d(0,48), 3*Math.PI/2)
                .build();
        Action trajectoryAction3 = drive.actionBuilder(new Pose2d(0,48,3*Math.PI/2))
                .strafeToLinearHeading(new Vector2d(-24,24), 0)
                .build();
        Action trajectoryAction4 = drive.actionBuilder(new Pose2d(-24,24,0))
                .strafeToLinearHeading(new Vector2d(0,0), Math.PI/2)
                .build();
        Action trajectoryAction5 = drive.actionBuilder(drive.pose)
                .lineToY(24)
                .turnTo(Math.PI)
                .build();
        Action trajectoryAction6 = drive.actionBuilder(new Pose2d(0,24,Math.PI))
                .lineToX(24)
                .build();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
//                        trajectoryAction1,
//                        trajectoryAction2,
//                        trajectoryAction3,
//                        trajectoryAction4
                        trajectoryAction5,
                        trajectoryAction6
                )
        );
    }
}
