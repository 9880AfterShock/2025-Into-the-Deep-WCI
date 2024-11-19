package org.firstinspires.ftc.teamcode

// RR-specific imports
// Non-RR imports
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode


@Config
@Autonomous(name = "9880 2024 Blue Bucket", group = "Autonomous")
class AutonomousOpModeBucket : LinearOpMode() {

// lift class

    override fun runOpMode() {
        Raiser.initRaiser(this)

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
            .strafeToLinearHeading(Vector2d(35.0, 0.0), 0.0)
            .strafeToLinearHeading(bucketParkPoseBlue.position, bucketParkPoseBlue.heading)

        while (!isStopRequested && !opModeIsActive()) {
            // Do nothing
        }

        waitForStart()

        if (isStopRequested) return

        runBlocking(
            SequentialAction(
                firstBucket.build(),
                pickUpNeutral.build(),
                secondBucket.build(),
                park.build()
            )
        )
    }
}