package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TranslationalVelConstraint
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Config
@Autonomous(name = "9880 2024 State ___ASTRONOMIC___ Specimen", group = "Autonomous")
class AutonomousOpModeState : LinearOpMode() {

// lift class

    override fun runOpMode() {
        SpecimenSwivel.initSwivel(this) // swivel is inited at the start, this works
        SpecimenClaw.initClaw(this) //
        SpecimenLift.initLift(this)
        Raiser.initRaiser(this)

        //
        //
        //
        val drive = MecanumDrive(hardwareMap, startPoseBlue)
        var startToClipBig: TrajectoryActionBuilder = drive.actionBuilder(startPoseBlue)
            .setTangent(Math.PI/-2)
            .splineToSplineHeading(clipPoseBlueAsstronomical, Math.toRadians(-90.0))
        var clipToPushGrabBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueAsstronomical)
            .setTangent(Math.toRadians(140.0))// bad, maybe 100
            .splineToSplineHeading(pushPrepPoseRightBigFastAsstronomical,Math.toRadians(310.0))//-51
            .setTangent(Math.toRadians(180.0))
            .splineToLinearHeading(pushPoseRightBigFastAsstronomical,Math.toRadians(-90.0), velConstraintOverride = TranslationalVelConstraint(27.0))//30 was caden // low is slow
            .splineToLinearHeading(pushPrepPoseMidBigFastAsstronomical,Math.toRadians(-160.0), velConstraintOverride = TranslationalVelConstraint(25.0))//30 was caden // low is slow) // try -135
            .setTangent(Math.toRadians(90.0))
            .splineToLinearHeading(pushPoseMidBigFastAsstronomical,Math.toRadians(90.0)) // got these
            .splineToSplineHeading(specStartPickupPoseSecondBigAsstronomical,Math.toRadians(90.0))
            .splineToSplineHeading(specStartPickupPoseBigAsstronomical,Math.toRadians(90.0), velConstraintOverride = TranslationalVelConstraint(18.0)) // make it move sideways into the clip to grab it better, at least 2 inches.
        var grabToGrabSlide: TrajectoryActionBuilder = drive.actionBuilder(specStartPickupPoseBigAsstronomical)
            .lineToX(-42.2, velConstraintOverride = TranslationalVelConstraint(18.0))
        var grabToGrabSlideSecond: TrajectoryActionBuilder = drive.actionBuilder(specStartPickupPoseSecondBigAsstronomical)
            .lineToX(-43.2, velConstraintOverride = TranslationalVelConstraint(18.0))//42.2
        var grabToGrabSlideLast: TrajectoryActionBuilder = drive.actionBuilder(specStartPickupPoseLastBigAsstronomical)
            .lineToX(-42.2, velConstraintOverride = TranslationalVelConstraint(18.0))//41.2
        var grabToClipTheSecondBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseBigAsstronomical)
            .setTangent(Math.toRadians(-35.0))
            .splineToLinearHeading(clipPoseBlueTheSecondAsstronomical,Math.toRadians(-80.0), velConstraintOverride = TranslationalVelConstraint(25.0))// issue?
        var grabToClipTheThirdBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseLastBigAsstronomical)
            .setTangent(Math.toRadians(-35.0))
            .waitSeconds(0.275)
            .splineToLinearHeading(clipPoseBlueTheThirdAsstronomical,Math.toRadians(-80.0))// issue?
        var grabToClipTheFourthBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseSecondBigAsstronomical)
            .setTangent(Math.toRadians(-35.0))
            .splineToLinearHeading(clipPoseBlueTheFourthAsstronomical,Math.toRadians(-80.0))// issue?
        var clipToGrabTheSecondBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheSecondAsstronomical)
            .setTangent(Math.toRadians(110.0))
            .splineToSplineHeading(specStartPickupPoseSecondBigAsstronomical, Math.toRadians(110.0))
        var clipToGrabTheThirdBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheThirdAsstronomical)
            .setTangent(Math.toRadians(110.0))
            .splineToSplineHeading(specStartPickupPoseLastBigAsstronomical, Math.toRadians(110.0))
        var waitSecondsTwo: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueAsstronomical)
            .waitSeconds(30.0)
        var clipToParkBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheFourthAsstronomical)// state goals! :) Nerd Emoji
            .setTangent(Math.toRadians(90.0))// change meEEeeeEE!!!!!!!
            .splineToSplineHeading(parkPoseBlueBig, Math.toRadians(135.0))
        var clipToBackBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheFifthAsstronomical)
            .strafeToLinearHeading(backPoseBlueBig,Math.toRadians(0.0))
        //
        //
        //
        while (!isStopRequested && !opModeIsActive()) {
            // Do nothing
        }

        waitForStart()

        if (isStopRequested) return
        //
        //
        //
        runBlocking(
            SequentialAction(
                ParallelAction(
                    Raiser.autoRaiserReset(),
                    SequentialAction(
                        ParallelAction(
                            SpecimenSwivel.autoSpecSwivOutStart(),
                            SpecimenClaw.autoSpecClawClose(),
                        ),
                        SpecimenLift.autoSpecimenLiftUp(/*3500*/),
                    ),
                    startToClipBig.build(),
                ),
                SequentialAction(
                    SpecimenLift.autoSpecimenLiftDown(100),
                    clipToPushGrabBig.build(),
                ),
                ParallelAction(
                    grabToGrabSlide.build(),
                    SpecimenClaw.autoDelaySpecClawClose(),
                ),
                ParallelAction(
                    SequentialAction(
                        SpecimenSwivel.autoSpecSwivOut(),
                        SpecimenClaw.autoDelaySpecClawClose(),
                        SpecimenLift.autoSpecimenLiftUp(), // issue spot
                    ),
                    grabToClipTheSecondBig.build(),
                ),
                SequentialAction(
                    SpecimenLift.autoSpecimenLiftDown(80),//100 // 90
                    clipToGrabTheSecondBig.build(),
                ),
                ParallelAction(
                    grabToGrabSlideSecond.build(),
                    SpecimenSwivel.autoSpecSwivOut(),
                    SpecimenClaw.autoDelaySpecClawCloseSecond(),
                ),
                ParallelAction(
                    SequentialAction(
                        SpecimenSwivel.autoSpecSwivOut(),
                        SpecimenClaw.autoSpecClawClose(),
                        SpecimenLift.autoSpecimenLiftUp(/*3500*/), // issue spot
                    ),
                    grabToClipTheThirdBig.build(),
                ),
                SequentialAction(
                    SpecimenLift.autoSpecimenLiftDown(80),//100 // 90
                    clipToGrabTheThirdBig.build()

                ),
                ParallelAction(
                    grabToGrabSlideLast.build(),
                    SpecimenClaw.autoDelaySpecClawClose(),
                ),
                ParallelAction(
                    SequentialAction(
                        SpecimenSwivel.autoSpecSwivOut(),
                        SpecimenClaw.autoSpecClawClose(),
                        SpecimenLift.autoSpecimenLiftUp(/*3500*/), // issue spot
                    ),
                    grabToClipTheFourthBig.build(),
                ),
                SequentialAction(
                    SpecimenLift.autoSpecimenLiftDown(80),//100 // 90
                    clipToParkBig.build(),
                ),
                //),

                // low voltage and ruins everything ::(
                waitSecondsTwo.build(),
            )
        )
    }
}

































































































// swearword