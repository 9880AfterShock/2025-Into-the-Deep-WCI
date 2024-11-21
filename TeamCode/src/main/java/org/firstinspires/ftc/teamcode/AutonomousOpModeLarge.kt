package org.firstinspires.ftc.teamcode

// RR-specific imports
// Non-RR imports
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.TranslationalVelConstraint
import com.acmerobotics.roadrunner.ftc.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode


@Config
@Autonomous(name = "9880 2024 ___Big___ Specimen", group = "Autonomous")
class AutonomousOpModeLarge : LinearOpMode() {

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
            .waitSeconds(0.2)
            .setTangent(Math.PI/-2)
            .splineToSplineHeading(clipPoseBlue, Math.toRadians(-90.0))
            //.waitSeconds(0.2)
        var clipToPushGrabBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .setTangent(Math.toRadians(160.0))
            .splineToSplineHeading(pushPrepPoseBig,Math.toRadians(260.0))
            .strafeToLinearHeading(pushPrepPoseRightBig,Math.toRadians(90.0))
            .strafeToLinearHeading(pushPoseRightBig, Math.toRadians(90.0))
            .strafeToLinearHeading(pushPrepPoseRightBig,Math.toRadians(90.0))
            .strafeToLinearHeading(pushPrepPoseMidBig,Math.toRadians(90.0))
            .strafeToLinearHeading(pushPoseMidBig, Math.toRadians(90.0)) // got these
            .setTangent(Math.toRadians(45.0))
            .splineToSplineHeading(specStartPickupPoseBig,Math.toRadians(90.0)) // make it move sideways into the clip to grab it better, at least 2 inches.
            //.waitSeconds(0.5)
        var grabToGrabSlide: TrajectoryActionBuilder = drive.actionBuilder(specStartPickupPoseBig)
            .lineToX(-39.2)
        var grabToClipBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseBig)
            .setTangent(Math.toRadians(-35.0))
            .splineToLinearHeading(clipPoseBlue,Math.toRadians(-80.0))// issue?
        var grabToClipTheSecondBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseBig)
            .setTangent(Math.toRadians(-35.0))
            .waitSeconds(0.275)
            .splineToLinearHeading(clipPoseBlueTheSecond,Math.toRadians(-80.0))// issue?
        var grabToClipTheThirdBig: TrajectoryActionBuilder = drive.actionBuilder(specEndPickupPoseBig)
            .setTangent(Math.toRadians(-35.0))
            .waitSeconds(0.275)
            .splineToLinearHeading(clipPoseBlueTheThird,Math.toRadians(-80.0))// issue?
        var clipToGrabBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .setTangent(Math.toRadians(110.0))
            .splineToSplineHeading(specStartPickupPoseBig, Math.toRadians(110.0))
        var clipToGrabTheSecondBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheSecond)
            .setTangent(Math.toRadians(110.0))
            .splineToSplineHeading(specStartPickupPoseBig, Math.toRadians(110.0))
            //.waitSeconds(0.5)
        var clipToGrabTheThirdBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheThird)
            .setTangent(Math.toRadians(110.0))
            .splineToSplineHeading(specStartPickupPoseBig, Math.toRadians(110.0))
        var waitSecondsFive: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .waitSeconds(0.40)
        var waitSecondsTwo: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .waitSeconds(30.0)
//        var backToBlue: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
//            .setTangent(Math.PI/2)
//            .splineToSplineHeading(backPoseBlue, Math.PI/2)
//            //.strafeToConstantHeading(Vector2d(0.0, -30.0)/*backVecBlue*/)
//            .waitSeconds(0.2)
        var clipToParkBig: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlueTheThird)
            .setTangent(Math.toRadians(135.0))// change meEEeeeEE!!!!!!!
            .splineToSplineHeading(parkPoseBlueBig, Math.toRadians(135.0))


        // help!!! get rowan, or look at what they do in github, alt use the prev years github, may be the same?
        /*var tab2: TrajectoryActionBuilder = drive.actionBuilder(initialPose)
            .lineToY(37.0)
            .setTangent(Math.toRadians(0.0))
            .lineToX(18.0)
            .waitSeconds(3.0)
            .setTangent(Math.toRadians(0.0))
            .lineToXSplineHeading(46.0, Math.toRadians(180.0))
            .waitSeconds(3.0)

        var tab3: TrajectoryActionBuilder = drive.actionBuilder(initialPose)
            .lineToYSplineHeading(33.0, Math.toRadians(180.0))
            .waitSeconds(2.0)
            .strafeTo(Vector2d(46.0, 30.0))
            .waitSeconds(3.0)*/

        /*var trajectoryActionCloseOut: Action = tab1.fresh()
            .strafeTo(Vector2d(48.0, 12.0))
            .build()*/

        while (!isStopRequested && !opModeIsActive()) {
            // Do nothing
        }

        waitForStart()

        if (isStopRequested) return

        // val startPosition = 1

//        ParallelAction(
//            SequentialAction(
//                SpecimenSwivel.autoSpecSwivOut(),
//                SpecimenClaw.autoSpecClawClose(),
//                //waitSecondsFive.build(),
//                SpecimenLift.autoSpecimenLiftUp(/*3500*/), // issue spot
//            ),
//            grabToClipTheSecondBig.build(),
//        ),
//        ParallelAction(
//            SequentialAction(
//                waitSecondsFive.build(),
//                clipToGrabTheSecondBig.build(),
//            ),
        runBlocking(
            SequentialAction(
                ParallelAction(
                    SequentialAction(
                        SpecimenSwivel.autoSpecSwivOut(),
                        SpecimenClaw.autoSpecClawClose(),
                        //waitSecondsFive.build(),
                        SpecimenLift.autoSpecimenLiftUp(/*3500*/),
                    ),
                    startToClipBig.build(),
                    Raiser.autoRaiserReset(),
                    ),
                ParallelAction(
                    SequentialAction(
                        waitSecondsFive.build(),
                        clipToPushGrabBig.build(),
                    ),
                    SpecimenLift.autoSpecimenLiftDown(800),
                ),
                grabToGrabSlide.build(),
                SpecimenClaw.autoSpecClawClose(),
                ParallelAction(
                    SequentialAction(
                        SpecimenSwivel.autoSpecSwivOut(),
                        SpecimenClaw.autoSpecClawClose(),
                        //waitSecondsFive.build(),
                        SpecimenLift.autoSpecimenLiftUp(/*3500*/), // issue spot
                    ),
                    grabToClipTheSecondBig.build(),
                ),
                ParallelAction(
                    SequentialAction(
                        waitSecondsFive.build(),
                        clipToGrabTheSecondBig.build(),
                    ),
                    SpecimenLift.autoSpecimenLiftDown(800),
                ),

                grabToGrabSlide.build(),
                SpecimenClaw.autoSpecClawClose(),
                ParallelAction(
                    SequentialAction(
                        SpecimenSwivel.autoSpecSwivOut(),
                        SpecimenClaw.autoSpecClawClose(),
                        //waitSecondsFive.build(),
                        SpecimenLift.autoSpecimenLiftUp(/*3500*/), // issue spot
                    ),
                    grabToClipTheThirdBig.build(),
                ),
                ParallelAction(
                    SequentialAction(
                        SpecimenLift.autoSpecimenLiftDown(800),
                        waitSecondsFive.build(),
                        clipToParkBig.build(),
                        ),
                    ),

                 // low voltage and ruins everything ::(
                waitSecondsTwo.build(), // put in sequential within parallel with raiser reset?
                //trajectoryActionChosen,
                //trajectoryActionCloseOut
            )
        )
    }
}