package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.subsystems.MainLift


@Config
@Autonomous(name = "9880 2024 State Bucket", group = "Autonomous")
class AutonomousOpModeStateBucket : LinearOpMode() {

// lift class

    override fun runOpMode() {
        Raiser.initRaiser(this) //does run
        Wrist.initWrist(this)
        Claw.initClaw(this)
        MainLift.initLift(this)
        SpecimenSwivel.initSwivel(this)

        val drive = MecanumDrive(hardwareMap, startPoseBlueBucket)
        var toHub = drive.actionBuilder(startPoseBlueBucket)
            .splineToLinearHeading(bucketPoseBlue,0.0)
        var bucketOne = drive.actionBuilder(samplePoseOne)
            .splineToLinearHeading(bucketPoseBlue,0.0)
        var bucketTwo = drive.actionBuilder(samplePoseTwo)
            .splineToLinearHeading(bucketPoseBlue,0.0)
        var bucketThree = drive.actionBuilder(samplePoseThree)
            .splineToLinearHeading(bucketPoseBlue,0.0)
        var one = drive.actionBuilder(bucketPoseBlue)
            .splineToLinearHeading(samplePoseOne,0.0)
        var two = drive.actionBuilder(bucketPoseBlue)
            .splineToLinearHeading(samplePoseTwo,0.0)
        var three = drive.actionBuilder(bucketPoseBlue)
            .splineToLinearHeading(samplePoseThree,0.0)
        var park = drive.actionBuilder(bucketPoseBlue)
            .setTangent(-90.0)
            .splineToLinearHeading(bucketParkPoseBlue,90.0)
        var waitOne = drive.actionBuilder(bucketPoseBlue)
            .waitSeconds(1.0)

        while (!isStopRequested && !opModeIsActive()) {
            // Do nothing
        }

        waitForStart()

        if (isStopRequested) return
        runBlocking(
            SequentialAction(
                ParallelAction(
                    toHub.build(),
                    Raiser.autoRaiserUp(),
                    SpecimenSwivel.autoSpecSwivOut(),
                    MainLift.autoLiftMax(),
                ),
                one.build(),
                ParallelAction (
                    MainLift.autoLiftMin(),
                    Raiser.autoRaiserDown(),
                    MainLift.autoLiftPickup(3),
                ),
                ParallelAction(
                    bucketOne.build(),
                    Raiser.autoRaiserUp(),
                    SpecimenSwivel.autoSpecSwivOut(),
                    MainLift.autoLiftMax(),
                ),
//
//                MainLift.autoLiftMaxLow(),
//                waitOne.build(),
//                two.build(),
//                waitOne.build(),
//                ParallelAction(
//                    bucketTwo.build(),
//                    Raiser.autoRaiserUp(),
//                    SpecimenSwivel.autoSpecSwivOut(),
//                    MainLift.autoLiftMax(),
//                ),
//
//                MainLift.autoLiftMaxLow(),
//                waitOne.build(),
//                three.build(),
//                waitOne.build(),
//                ParallelAction(
//                    bucketThree.build(),
//                    Raiser.autoRaiserUp(),
//                    SpecimenSwivel.autoSpecSwivOut(),
//                    MainLift.autoLiftMax(),
//                ),
//
//                MainLift.autoLiftMaxLow(),
//                waitOne.build(),
                park.build()
            )
        )
    }
}