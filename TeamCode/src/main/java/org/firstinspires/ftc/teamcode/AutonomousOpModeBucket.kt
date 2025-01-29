package org.firstinspires.ftc.teamcode

// RR-specific imports
// Non-RR imports
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.subsystems.MainLift


@Config
@Autonomous(name = "9880 2024 Blue Bucket", group = "Autonomous")
class AutonomousOpModeBucket : LinearOpMode() {

// lift class

    override fun runOpMode() {
        Raiser.initRaiser(this) //does run
        Wrist.initWrist(this)
        Claw.initClaw(this)
        MainLift.initLift(this)
        SpecimenSwivel.initSwivel(this)

        val drive = MecanumDrive(hardwareMap, startPoseBlueBucket)
        var preloadBucket = drive.actionBuilder(startPoseBlueBucket)
            .splineToLinearHeading(bucketPoseBlue, 0.0)
        var pickUpNeutralFirst = drive.actionBuilder(bucketPoseBlue)
            .strafeToLinearHeading(Vector2d(35.0, 46.5), 0.0)
            .strafeToLinearHeading(neutralPoseBlueFirst.position, neutralPoseBlueFirst.heading)
        var firstSpikeBucket = drive.actionBuilder(neutralPoseBlueFirst)
            .strafeToLinearHeading(Vector2d(35.0, 46.0), 0.0)
            .splineToLinearHeading(bucketPoseBlue, 0.0)
        var secondSpikeBucket = drive.actionBuilder(neutralPoseBlueSecond)
            .strafeToLinearHeading(Vector2d(35.0, 46.0), 0.0)
            .splineToLinearHeading(bucketPoseBlue, 0.0)
        var pickUpNeutralSecond = drive.actionBuilder(bucketPoseBlue)
            .strafeToLinearHeading(Vector2d(35.0, 46.5), 0.0)
            .strafeToLinearHeading(neutralPoseBlueSecond.position, neutralPoseBlueSecond.heading)
        var park = drive.actionBuilder(bucketPoseBlue)
            .strafeToLinearHeading(Vector2d(35.0, 0.0), Math.toRadians(135.0))
            .strafeToLinearHeading(bucketParkPoseBlue.position, bucketParkPoseBlue.heading)
        var waitSecondsHalf: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue) //fix if clipPoseBLue doesnt work
            .waitSeconds(0.5)
        var waitSecondsOne: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue) //fix if clipPoseBLue doesnt work
            .waitSeconds(1.0)
        var waitSecondsZeroPointThree: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue) //fix if clipPoseBLue doesnt work
            .waitSeconds(0.3)

        while (!isStopRequested && !opModeIsActive()) {
            // Do nothing
        }

        waitForStart()

        if (isStopRequested) return
        runBlocking(
            SequentialAction(

                //Go to bucket
                ParallelAction(
                    Claw.autoClawClose(),
                    preloadBucket.build(),
                    Raiser.autoRaiserUp(),
                    SpecimenSwivel.autoSpecSwivOut(), //need to see if works
                    MainLift.autoLiftMax(), //ditto
                ),


                //Drop sample
                Wrist.autoWristGoToPos(Wrist.positions[1]),
                waitSecondsZeroPointThree.build(),
                Claw.autoClawOpen(500),
                Wrist.autoWristGoToPos(Wrist.positions[2]),
                Claw.autoClawClose(),
                Wrist.autoWristGoToPos(Wrist.initPos),


                //Go to pickup 1
                MainLift.autoLiftMaxLow(),
                ParallelAction(
                    pickUpNeutralFirst.build(),
                    SequentialAction(
                        MainLift.autoLiftPickup(1),
                        Raiser.autoRaiserDown(),
                    ),
                    Wrist.autoWristGoToPos(Wrist.positions[1]),
                ),

                //Pickup Spike 1
                Claw.autoClawOpen(300),
                ParallelAction(
                    Wrist.autoWristGoToPos(Wrist.positions[0]),
                    Claw.autoClawClose(),
                ),
                waitSecondsHalf.build(),
                Wrist.autoWristGoToPos(Wrist.positions[1]),


                //Back to bucket
                ParallelAction(
                    Wrist.autoWristGoToPos(Wrist.initPos),
                    SequentialAction(
                        Raiser.autoRaiserUp(),
                        MainLift.autoLiftMax(),
                    ),
                    firstSpikeBucket.build(),
                ),


                //Drop sample again
                Wrist.autoWristGoToPos(Wrist.positions[1]),
                waitSecondsZeroPointThree.build(),
                Claw.autoClawOpen(500),
                Wrist.autoWristGoToPos(Wrist.positions[2]),
                Claw.autoClawClose(),
                Wrist.autoWristGoToPos(Wrist.initPos),


                //Go to pickup 2
                MainLift.autoLiftMaxLow(),
                ParallelAction(
                    pickUpNeutralSecond.build(),
                    SequentialAction(
                        MainLift.autoLiftPickup(2),
                        Raiser.autoRaiserDown(),
                    ),
                    Wrist.autoWristGoToPos(Wrist.positions[1]),
                ),


                //Pickup Spike 2
                Claw.autoClawOpen(300),
                ParallelAction(
                    Wrist.autoWristGoToPos(Wrist.positions[0]),
                    Claw.autoClawClose(),
                ),
                waitSecondsOne.build(),
                Wrist.autoWristGoToPos(Wrist.positions[1]),


                //Back to bucket
                ParallelAction(
                    Wrist.autoWristGoToPos(Wrist.initPos),
                    SequentialAction(
                        Raiser.autoRaiserUp(),
                        MainLift.autoLiftMax(),
                    ),
                    secondSpikeBucket.build(),
                ),


                //Drop sample for the last time
                Wrist.autoWristGoToPos(Wrist.positions[1]),
                waitSecondsZeroPointThree.build(),
                Claw.autoClawOpen(500),
                Wrist.autoWristGoToPos(Wrist.positions[2]),
                Claw.autoClawClose(),
                Wrist.autoWristGoToPos(Wrist.initPos),




                //Park pos
                MainLift.autoLiftMin(),
                park.build(),
                Wrist.autoWristGoToPos(Wrist.initPos)
            )
        )
    }
}