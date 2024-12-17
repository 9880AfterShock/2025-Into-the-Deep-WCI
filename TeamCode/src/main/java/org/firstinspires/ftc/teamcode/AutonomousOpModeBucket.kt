package org.firstinspires.ftc.teamcode

// RR-specific imports
// Non-RR imports
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.Pose2d
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
        var firstBucket = drive.actionBuilder(startPoseBlueBucket)
            .splineToLinearHeading(bucketPoseBlue, 0.0)
        var pickUpNeutral = drive.actionBuilder(bucketPoseBlue)
            .strafeToLinearHeading(Vector2d(35.0, 46.5), 0.0)
            .strafeToLinearHeading(neutralPoseBlue.position, neutralPoseBlue.heading)
        var secondBucket = drive.actionBuilder(neutralPoseBlue)
            .strafeToLinearHeading(Vector2d(35.0, 46.0), 0.0)
            .splineToLinearHeading(bucketPoseBlue, 0.0)
        var park = drive.actionBuilder(bucketPoseBlue)
            .strafeToLinearHeading(Vector2d(35.0, 0.0), Math.toRadians(135.0))
            .strafeToLinearHeading(bucketParkPoseBlue.position, bucketParkPoseBlue.heading)
        var waitSecondsTwo: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue) //fix if clipPoseBLue doesnt work
            .waitSeconds(2.0)

        while (!isStopRequested && !opModeIsActive()) {
            // Do nothing
        }

        waitForStart()

        if (isStopRequested) return
        runBlocking(
            SequentialAction(
                ParallelAction(
                    firstBucket.build(),
                    Raiser.autoRaiserUp(),
                    SpecimenSwivel.autoSpecSwivOut(),
                    MainLift.autoLiftMax(), //this works, but the max without low does not work. but this has a wrong sign in it? try kotlin abs maybe??? worked, maybe? in wrist
                ),

                Wrist.autoWristGoToPos(Wrist.positions[1]),
                Claw.autoClawOpen(1500),
                Wrist.autoWristGoToPos(Wrist.positions[2]),
                Claw.autoClawClose(),

                MainLift.autoLiftMaxLow(),
                ParallelAction(
                    pickUpNeutral.build(),
                    SequentialAction(
                        MainLift.autoLiftPickup(1),
                        Raiser.autoRaiserDown(),
                    ),
                    Wrist.autoWristGoToPos(Wrist.positions[1]),
                ),
                Claw.autoClawOpen(0),
                ParallelAction(
                    Wrist.autoWristGoToPos(Wrist.positions[0]),
                    Claw.autoClawClose(),
                ),
                ParallelAction(
                    SequentialAction(
                        Raiser.autoRaiserUp(),
                        MainLift.autoLiftMax(),
                    ),
                    secondBucket.build(),
                ),

                Wrist.autoWristGoToPos(Wrist.positions[1]),
                Claw.autoClawOpen(0),
                Wrist.autoWristGoToPos(Wrist.positions[2]),

                MainLift.autoLiftMin(),
                park.build(),
                Wrist.autoWristGoToPos(Wrist.initPos)
            )
        )
    }
}