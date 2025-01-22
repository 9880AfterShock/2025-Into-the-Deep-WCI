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
            myBot.drive.actionBuilder(startPoseBlue)
                .setTangent(Math.PI/-2)
                .splineToSplineHeading(clipPoseBlueAsstronomical, Math.toRadians(-90.0))
                //
                .setTangent(Math.toRadians(175.0))// bad, maybe 100
                .splineToSplineHeading(pushPrepPoseRightBigFastHalfwayAsstronomical, Math.toRadians(-155.0))
                .splineToSplineHeading(pushPrepPoseRightBigFastAsstronomical,Math.toRadians(-70.0))//-51
                .splineToSplineHeading(pushPrepPoseRightSlideBigFastAsstronomical, Math.toRadians(90.0))
                .setTangent(Math.toRadians(90.0))
                .splineToLinearHeading(pushPoseRightBigFastAsstronomical,Math.toRadians(-90.0), velConstraintOverride = TranslationalVelConstraint(35.0))//27
                .splineToLinearHeading(pushPrepPoseMidBigFastAsstronomical,Math.toRadians(110.0), velConstraintOverride = TranslationalVelConstraint(25.0))//25
                .splineToLinearHeading(pushPoseMidBigFastAsstronomical,Math.toRadians(90.0)) // got these
                //.splineToSplineHeading(specStartPickupPoseSecondBigAsstronomical,Math.toRadians(90.0))
                .splineToSplineHeading(specStartPickupPoseBigAsstronomical,Math.toRadians(90.0), velConstraintOverride = TranslationalVelConstraint(20.0)) // 18
                //
                //.lineToX(-42.2, velConstraintOverride = TranslationalVelConstraint(24.0))//18
                //
                .setTangent(Math.toRadians(-35.0))
                .splineToLinearHeading(clipPoseBlueTheSecondAsstronomical,Math.toRadians(-80.0),/* velConstraintOverride = TranslationalVelConstraint(25.0)*/)// issue?
                //
                .setTangent(Math.toRadians(110.0))
                .splineToSplineHeading(specStartPickupPoseSecondBigAsstronomical, Math.toRadians(110.0))
                //
                //.lineToX(-43.2, velConstraintOverride = TranslationalVelConstraint(28.0))//20
                //
                .setTangent(Math.toRadians(-35.0))
                //.waitSeconds(0.275)
                .splineToLinearHeading(clipPoseBlueTheThirdAsstronomical,Math.toRadians(-80.0))// issue?
                //
                .setTangent(Math.toRadians(110.0))
                .splineToSplineHeading(specStartPickupPoseLastBigAsstronomical, Math.toRadians(110.0))
                //
                .setTangent(Math.toRadians(-35.0))
                .splineToLinearHeading(clipPoseBlueTheFourthAsstronomical,Math.toRadians(-80.0))// issue?
                //
                .setTangent(Math.toRadians(90.0))// change meEEeeeEE!!!!!!!
                .splineToSplineHeading(parkPoseBlueBig, Math.toRadians(135.0))
                //
                .build()
        )

        meepMeep.setBackground(Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
            .setDarkMode(true)
            .setBackgroundAlpha(0.95f)
            .addEntity(myBot)
            .start()
    }
}
