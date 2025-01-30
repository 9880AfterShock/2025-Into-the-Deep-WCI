package org.firstinspires.ftc.teamcode

import android.service.carrier.CarrierMessagingService.SendMultipartSmsResult
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
        Swivel.initSwivel(this)

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

                //Score Preload
                ParallelAction(
                    Claw.autoClawClose(),
                    Swivel.autoSwivelRotate(0.5),
                    toHub.build(), //after this move arm up and drop in bucket
                    Raiser.autoRaiserUp(),
                    SpecimenSwivel.autoSpecSwivOut(),
                    MainLift.autoLiftMax(),
                ),

                SequentialAction(
                    Wrist.autoWristGoToPos(Wrist.positions[1]),
                    Swivel.autoSwivelRotate(0.15),
                    Claw.autoClawOpen(400),
                ),

                //Pickup Spike 1
                ParallelAction(
                    one.build(),
                    SequentialAction (
                        MainLift.autoLiftPickup(3),
                        Raiser.autoRaiserDown(),
                        ParallelAction(
                            Wrist.autoWristGoToPos(Wrist.positions[0]),
                            Swivel.autoSwivelRotate(0.4)
                        ),
                        Claw.autoClawClose()
                    ),
                ),

                //Score Spike 1
//                ParallelAction(
//                    bucketOne.build(), //after this move arm up and drop in bucket
//                    SequentialAction(
//                        Raiser.autoRaiserUp(),
//                        MainLift.autoLiftMax(),
//                    ),
//                ),
//
//
//                two.build(), //after this pick up sample
//
//                bucketTwo.build(), //after this move arm up and drop in bucket
//
//                three.build(), //after this pick up sample

//                bucketThree.build(), //after this move arm up and drop in bucket

                //Park
                park.build()
            )
        )
    }
}