package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Basket {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(720);
        Pose2d initialPose = new Pose2d(33, 61.7, Math.PI * 1.5);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(55, 55, Math.PI * 1.25), Math.PI * 0.25)
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(37, 30, Math.PI * 0), Math.PI * 1.25)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(37, 24, Math.PI * 0), Math.PI * 1.5)
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(55, 55, Math.PI * 1.25), Math.PI * 0.25)
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(45, 24, Math.PI * 0), Math.PI * 1.5)
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(55, 55, Math.PI * 1.25), Math.PI * 0.25)
                .setTangent(Math.PI * 1.25)
                .splineToLinearHeading(new Pose2d(55, 24, Math.PI * 0), Math.PI * 1.5)
                .setTangent(Math.PI * 0.25)
                .splineToLinearHeading(new Pose2d(55, 55, Math.PI * 1.25), Math.PI * 0.25)
                .setTangent(Math.PI * 1.5)
                .splineToLinearHeading(new Pose2d(26, 12, Math.PI * 1), Math.PI * 1)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}