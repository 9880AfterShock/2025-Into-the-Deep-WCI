package org.firstinspires.ftc.teamcode

// RR-specific imports
// Non-RR imports
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TranslationalVelConstraint
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

// score further right for all 3
// add 4th spec.
// slide more to the side for human player
@Config
@Autonomous(name = "9880 2024 Semifinals ___Big___ Specimen", group = "Autonomous")
class AutonomousOpModeSemifinals : LinearOpMode() {

// lift class

    override fun runOpMode() {
        SpecimenSwivel.initSwivel(this) // swivel is inited at the start, this works
        SpecimenClaw.initClaw(this) //
        SpecimenLift.initLift(this)
        Raiser.initRaiser(this)

        // instantiate your MecanumDrive at a particular pose.
        //val initialPose = Pose2d(11.8, 61.7, Math.toRadians(90.0))
        val drive = MecanumDrive(hardwareMap, startPoseBlue)



        // actionBuilder builds from the drive steps passed to it
        var startToClipBig: TrajectoryActionBuilder = drive.actionBuilder(startPoseBlue)
            //.waitSeconds(0.2)
            .setTangent(Math.PI/-2)
            .splineToSplineHeading(clipPoseBlue, Math.toRadians(-90.0))
            //.waitSeconds(0.2)
        var clipToPushGrabBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .setTangent(Math.toRadians(140.0))// bad, maybe 100
            .splineToSplineHeading(pushPrepPoseRightBigFast,Math.toRadians(310.0))//-51
            .setTangent(Math.toRadians(180.0))
            .splineToLinearHeading(pushPoseRightBigFast,Math.toRadians(-90.0), velConstraintOverride = TranslationalVelConstraint(27.0))//30 was caden // low is slow
            .splineToLinearHeading(pushPrepPoseMidBigFast,Math.toRadians(-160.0), velConstraintOverride = TranslationalVelConstraint(25.0))//30 was caden // low is slow)
            .setTangent(Math.toRadians(90.0))
            .splineToLinearHeading(pushPoseMidBigFast,Math.toRadians(90.0)) // got these
            .splineToSplineHeading(specStartPickupPoseSecondBig,Math.toRadians(90.0))
            .splineToSplineHeading(specStartPickupPoseBig,Math.toRadians(90.0), velConstraintOverride = TranslationalVelConstraint(18.0)) // make it move sideways into the clip to grab it better, at least 2 inches.
            //.waitSeconds(0.5)
        var grabToGrabSlide: TrajectoryActionBuilder = drive.actionBuilder(specStartPickupPoseBig)
            .lineToX(-42.2, velConstraintOverride = TranslationalVelConstraint(18.0))
        var grabToGrabSlideSecond: TrajectoryActionBuilder = drive.actionBuilder(specStartPickupPoseSecondBig)
            .lineToX(-42.2, velConstraintOverride = TranslationalVelConstraint(18.0))//41.2
        var grabToGrabSlideLast: TrajectoryActionBuilder = drive.actionBuilder(specStartPickupPoseLastBig)
            .lineToX(-42.2, velConstraintOverride = TranslationalVelConstraint(18.0))//41.2
        var grabToClipBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseBig)
            .setTangent(Math.toRadians(-35.0))
            .splineToLinearHeading(clipPoseBlue,Math.toRadians(-80.0))// issue?
        var grabToClipTheSecondBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseBig)
            .setTangent(Math.toRadians(-35.0))
            //.waitSeconds(0.275)
            .splineToLinearHeading(clipPoseBlueTheSecond,Math.toRadians(-80.0), velConstraintOverride = TranslationalVelConstraint(25.0))// issue?
        var grabToClipTheThirdBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseLastBig)
            .setTangent(Math.toRadians(-35.0))
            //.waitSeconds(0.275)
            .splineToLinearHeading(clipPoseBlueTheThird,Math.toRadians(-80.0))// issue?
        var grabToClipTheFourthBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseSecondBig)
            .setTangent(Math.toRadians(-35.0))
            //.waitSeconds(0.275)
            .splineToLinearHeading(clipPoseBlueTheFourth,Math.toRadians(-80.0))// issue?
        var clipToGrabBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .setTangent(Math.toRadians(110.0))
            .splineToSplineHeading(specStartPickupPoseBig, Math.toRadians(110.0))
        var clipToGrabTheSecondBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheSecond)
            .setTangent(Math.toRadians(110.0))
            .splineToSplineHeading(specStartPickupPoseSecondBig, Math.toRadians(110.0))
            //.waitSeconds(0.5)
        var clipToGrabTheThirdBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheThird)
            .setTangent(Math.toRadians(110.0))
            .splineToSplineHeading(specStartPickupPoseLastBig, Math.toRadians(110.0))
        var waitSecondsFive: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .waitSeconds(0.90)
        var waitSecondsTwo: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .waitSeconds(30.0)
        var clipToParkBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheFourth)
            .setTangent(Math.toRadians(90.0))// change meEEeeeEE!!!!!!!
            .splineToSplineHeading(parkPoseBlueBig, Math.toRadians(135.0))
        var clipToBackBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheFourth)
            .strafeToLinearHeading(backPoseBlueBig,Math.toRadians(0.0))
        var scoreFinalBackBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheThird)
            .strafeToConstantHeading(Vector2d(5.0, 45.0))//was 40




        while (!isStopRequested && !opModeIsActive()) {
            // Do nothing
        }

        waitForStart()

        if (isStopRequested) return

        runBlocking(
            SequentialAction(
                Raiser.autoRaiserReset(),
                ParallelAction(
                    SequentialAction(
                        SpecimenSwivel.autoSpecSwivOutStart(),
                        SpecimenClaw.autoSpecClawClose(),
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
                        SpecimenClaw.autoSpecClawClose(),
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
                    SpecimenClaw.autoDelaySpecClawClose(),
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
                    clipToBackBig.build(),
                ),
                    //),

                 // low voltage and ruins everything ::(
                waitSecondsTwo.build(),
            )
        )
    }
}