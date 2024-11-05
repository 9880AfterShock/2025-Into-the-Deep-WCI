package com.example.meepmeep
// 1.13.1 ktx version

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.MeepMeep.Background
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder

object MeepMeep {
    @JvmStatic
    fun main(args: Array<String>) {
        val meepMeep = MeepMeep(700)
        val myBot =
            DefaultBotBuilder(meepMeep) // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60.0, 60.0, Math.toRadians(180.0), Math.toRadians(0.0), 14.5)
                .build()
        myBot.runAction(
            myBot.drive.actionBuilder((Pose2d(0.4, 59.2, 0.0)))
                .splineToLinearHeading(Pose2d(-5.0,34.8,Math.toRadians(180.0)),0.0)
                .waitSeconds(2.0)
                .strafeToLinearHeading(Vector2d(-34.8,40.4),Math.toRadians(90.0))
                .strafeTo(Vector2d(-34.8,12.1))
                .strafeToLinearHeading(Vector2d(-43.9,12.1),Math.toRadians(90.0))
                .strafeToLinearHeading(Vector2d(-43.9, 50.0), Math.toRadians(90.0))
                .strafeToLinearHeading(Vector2d(-43.9,12.1),Math.toRadians(90.0))
                .strafeToLinearHeading(Vector2d(-52.2,12.1),Math.toRadians(90.0))
                .strafeToLinearHeading(Vector2d(-52.2, 50.0), Math.toRadians(90.0))
                .strafeToLinearHeading(Vector2d(-52.2,60.2),0.0)
                .waitSeconds(2.0)
                .strafeToLinearHeading(Vector2d(-33.9,42.1),Math.toRadians(180.0))
                .splineToLinearHeading(Pose2d(0.0,34.8,Math.toRadians(180.0)),5.0)
                .waitSeconds(2.0)
                .strafeToLinearHeading(Vector2d(-60.0,60.0,),Math.toRadians(180.0))
                .build()
        )

        meepMeep.setBackground(Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
            .setDarkMode(true)
            .setBackgroundAlpha(0.95f)
            .addEntity(myBot)
            .start()
    }
}
