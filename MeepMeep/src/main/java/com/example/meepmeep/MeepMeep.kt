package com.example.meepmeep
// 1.13.1 ktx version

import com.acmerobotics.roadrunner.Pose2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.MeepMeep.Background
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder

object MeepMeep {
    @JvmStatic
    fun main(args: Array<String>) {
        val meepMeep = MeepMeep(700)
        val myBot =
            DefaultBotBuilder(meepMeep) // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60.0, 60.0, Math.toRadians(180.0), Math.toRadians(180.0), 17.0)
                .build()

        myBot.runAction(
            myBot.drive.actionBuilder(Pose2d(20.0, 61.0, 0.0))
                //run claw flip
                // run arm up
                .splineToSplineHeading(Pose2d(48.0, 48.0, Math.toRadians(45.0)), 0.125)
                .waitSeconds(0.5)
                // run arm extend
                .waitSeconds(1.5)
                // run claw release
                .waitSeconds(1.0)
                // run claw grab
                .waitSeconds(0.5)
                // run arm unextend
                .waitSeconds(2.0)
                // leave arm up
                .splineToSplineHeading(Pose2d(34.0, 13.0, Math.PI * 1.5), Math.PI)
                .build()
        )

        meepMeep.setBackground(Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
            .setDarkMode(true)
            .setBackgroundAlpha(0.95f)
            .addEntity(myBot)
            .start()
    }
}
