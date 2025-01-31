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
            .splineToLinearHeading(bucketPoseZero,0.0)
        var bucketOne = drive.actionBuilder(samplePoseOne)
            .splineToLinearHeading(bucketPoseOne,0.0)
        var bucketTwo = drive.actionBuilder(samplePoseTwo)
            .splineToLinearHeading(bucketPoseTwo,0.0)
        var bucketThree = drive.actionBuilder(samplePoseThree)
            .splineToLinearHeading(bucketPoseThree,0.0)
        var one = drive.actionBuilder(bucketPoseZero)
            .splineToLinearHeading(samplePoseOne,0.0)
        var two = drive.actionBuilder(bucketPoseOne)
            .splineToLinearHeading(samplePoseTwo,0.0)
        var three = drive.actionBuilder(bucketPoseTwo)
            .splineToLinearHeading(samplePoseThree,0.0)
        var park = drive.actionBuilder(bucketPoseThree)
            .setTangent(-90.0)
            .splineToLinearHeading(bucketParkPoseBlue,90.0)
        var waitPointFour = drive.actionBuilder(bucketPoseBlue)
            .waitSeconds(0.4)

        while (!isStopRequested && !opModeIsActive()) {
            // Do nothing
        }

        waitForStart()

        if (isStopRequested) return
        runBlocking(
            SequentialAction(

                //Score Preload
                ParallelAction(
                    Claw.autoClawClose(0),
                    Swivel.autoSwivelRotate(90),
                    toHub.build(), //after this move arm up and drop in bucket
                    Raiser.autoRaiserUp(),
                    SpecimenSwivel.autoSpecSwivOut(),
                    MainLift.autoLiftMax(),
                    SequentialAction(
                        Wrist.autoWristGoToPos(Wrist.positions[1]),
                        Swivel.autoSwivelRotate(180),
                    ),
                ),

                Claw.autoClawOpen(400),

                //Pickup Spike 1
                MainLift.autoLiftMin(),
                Swivel.autoSwivelRotate(180),
                Raiser.autoRaiserDown(),
                one.build(),
                SequentialAction (
                    MainLift.autoLiftPickup(1),
                    Wrist.autoWristGoToPos(Wrist.positions[0]),
                    waitPointFour.build(),
                    Claw.autoClawClose(400),
                ),

                MainLift.autoLiftMin(),

                //Score spike 1
                ParallelAction(
                    Swivel.autoSwivelRotate(90),
                    SequentialAction(
                        bucketOne.build(),
                        Raiser.autoRaiserUp(),
                        MainLift.autoLiftMax(),
                    ),
                    SequentialAction(
                        Wrist.autoWristGoToPos(Wrist.positions[1]),
                        Swivel.autoSwivelRotate(180),
                    ),
                ),

                Claw.autoClawOpen(400),



                //Pickup Spike 2
                MainLift.autoLiftMin(),
                Swivel.autoSwivelRotate(22),
                Raiser.autoRaiserDown(),
                two.build(),
                SequentialAction (
                    MainLift.autoLiftPickup(2),
                    Wrist.autoWristGoToPos(Wrist.positions[0]),
                    waitPointFour.build(),
                    Claw.autoClawClose(400),
                ),

                //Score spike 2
                ParallelAction(
                    Swivel.autoSwivelRotate(90),
                    SequentialAction(
                        bucketTwo.build(),
                        Raiser.autoRaiserUp(),
                        MainLift.autoLiftMax(),
                    ),
                    SequentialAction(
                        Wrist.autoWristGoToPos(Wrist.positions[1]),
                        Swivel.autoSwivelRotate(180),
                    ),
                ),

                Claw.autoClawOpen(400),




                //Pickup Spike 3
                MainLift.autoLiftMin(),
                Swivel.autoSwivelRotate(37),
                Raiser.autoRaiserDown(),
                three.build(),
                SequentialAction (
                    MainLift.autoLiftPickup(3),
                    Wrist.autoWristGoToPos(Wrist.positions[0]),
                    waitPointFour.build(),
                    Claw.autoClawClose(400),
                ),

                //Score spike 2
                ParallelAction(
                    Swivel.autoSwivelRotate(90),
                    SequentialAction(
                        bucketThree.build(),
                        Raiser.autoRaiserUp(),
                        MainLift.autoLiftMax(),
                    ),
                    SequentialAction(
                        Wrist.autoWristGoToPos(Wrist.positions[1]),
                        Swivel.autoSwivelRotate(180),
                    ),
                ),

                Claw.autoClawOpen(400),





                //reset for teleop
                MainLift.autoLiftMin(),
                Swivel.autoSwivelRotate(90),
                Wrist.autoWristGoToPos(-1),
                Raiser.autoRaiserUp(),

//
//
//                two.build(), //after this pick up sample
//
//                bucketTwo.build(), //after this move arm up and drop in bucket
//
//                three.build(), //after this pick up sample

//                bucketThree.build(), //after this move arm up and drop in bucket

                //Park
                //park.build()
            )
        )
    }
}