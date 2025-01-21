package com.example.meepmeep
// 1.13.1 ktx version

import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.TranslationalVelConstraint
import com.acmerobotics.roadrunner.Vector2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.MeepMeep.Background
import com.noahbres.meepmeep.core.toRadians
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
            //myBot.drive.actionBuilder((Pose2d(32.8, 59.2, 0.0)))
            myBot.drive.actionBuilder(startPoseBlueBucket)
                .splineToLinearHeading(bucketPoseBlue,0.0)
                .waitSeconds(3.0) //drop in bucket
                .setTangent(Math.toRadians(-110.0))
                .splineToLinearHeading(Pose2d(35.0,26.0,0.0),Math.toRadians(-70.0))
                .waitSeconds(3.0) //pick up neutral
<<<<<<< HEAD
                .strafeToLinearHeading(Vector2d(35.0,46.0),0.0)
                .splineToLinearHeading(bucketPoseBlue,0.0)
=======
                .splineToLinearHeading(Pose2d(47.1,46.5,Math.toRadians(45.0)),0.0)
>>>>>>> 42b4e2f (MeepMeep Bucket Path)
                .waitSeconds(3.0) //drop in bucket 2nd
                .strafeToLinearHeading(Vector2d(35.0,0.0),Math.toRadians(135.0))
                .strafeToLinearHeading(Vector2d(26.0,0.0), Math.toRadians(180.0))
                .build()


//                .setTangent(Math.PI/-2)
//                .splineToLinearHeading(Pose2d(-5.0,34.8,Math.toRadians(0.0)),Math.PI/-2)
//                .waitSeconds(2.0)
//                .setTangent(Math.toRadians(160.0))
//                .splineToSplineHeading(pushPrepPoseRightBigFast,Math.toRadians(-90.0))
//                .setTangent(Math.toRadians(180.0))
//                .splineToLinearHeading(pushPoseRightBigFast,Math.toRadians(-90.0), velConstraintOverride = TranslationalVelConstraint(40.0))
//                .splineToLinearHeading(pushPrepPoseMidBigFast,Math.toRadians(110.0))
//                .setTangent(Math.toRadians(90.0))
//                .splineToLinearHeading(pushPoseMidBigFast,Math.toRadians(90.0)) // got these
//                .splineToSplineHeading(specStartPickupPoseLastBig,Math.toRadians(90.0))
//                .waitSeconds(2.0)
//                .setTangent(Math.toRadians(-35.0))
//                .splineToLinearHeading(Pose2d(0.0,34.8,Math.toRadians(0.0)),Math.toRadians(-80.0))
//                .waitSeconds(2.0)
//                .setTangent(Math.toRadians(110.0))
//                .splineToSplineHeading(Pose2d(-36.2,60.2, Math.PI), Math.toRadians(110.0))
//                .setTangent(Math.toRadians(-35.0))
//                .splineToLinearHeading(Pose2d(0.0,34.8,Math.toRadians(0.0)),Math.toRadians(-80.0))
//                .setTangent(Math.toRadians(110.0))
//                .splineToSplineHeading(Pose2d(-57.0, 57.0, Math.toRadians(-90.0)), Math.toRadians(110.0))
//                .build()
        )

        meepMeep.setBackground(Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
            .setDarkMode(true)
            .setBackgroundAlpha(0.95f)
            .addEntity(myBot)
            .start()
    }
}
