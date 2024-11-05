package org.firstinspires.ftc.teamcode

// RR-specific imports
// Non-RR imports
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.ftc.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.SpecimenClaw.opmode
import org.firstinspires.ftc.teamcode.SpecimenSwivel.initSwivel
import org.firstinspires.ftc.teamcode.subsystems.MainLift


@Config
@Autonomous(name = "9880 2024 Blue Specimen", group = "Autonomous")
class AutonomousOpModeBlue : LinearOpMode() {

// lift class

    override fun runOpMode() {
        initSwivel(this) // swivel is inited at the start, this works
        SpecimenClaw.initClaw(this)
        SpecimenClaw.close()// make sure
        SpecimenLift.initLift(this) //?????????????!
        MainLift.initLift(this)
        // instantiate your MecanumDrive at a particular pose.
        //val initialPose = Pose2d(11.8, 61.7, Math.toRadians(90.0))
        val drive = MecanumDrive(hardwareMap, startPoseBlue)



        // actionBuilder builds from the drive steps passed to it
        var startToClipBlue: TrajectoryActionBuilder = drive.actionBuilder(startPoseBlue)
        .splineToSplineHeading(clipPoseBlue, Math.PI/-2)
        opmode.telemetry.addData("start to clip blue", 1)
        opmode.telemetry.update()
        var waitSecondsFive: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
        .waitSeconds(5.0)
        opmode.telemetry.addData("wait 5 seconds", 1)
        opmode.telemetry.update()
        var waitSecondsTwo: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .waitSeconds(2.0)
        opmode.telemetry.addData("wait 2 seconds", 1)
        opmode.telemetry.update()
        var clipToParkBlue: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .splineToSplineHeading(parkPoseBlue, Math.PI)
        opmode.telemetry.addData("clip to park", 1)
        opmode.telemetry.update()


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


        runBlocking(
            SequentialAction(
                ParallelAction(
                    startToClipBlue.build(),
                    // get things to move
                    SpecimenSwivel.autoSpecSwivOut(),
                    SpecimenClaw.autoSpecClawClose()
                ),
                SequentialAction(
                    //SpecimenLift.LiftRun(),// now innited, never moves
                    //SpecimenLift.autoSpecLiftUp(),
                    SpecimenLift.autoSpecimenLiftUp(1),
                    waitSecondsFive.build(),
                    //SpecimenClaw.autoSpecClawSwap()

                    SpecimenLift.autoSpecimenLiftDown(1),
                    //SpecimenClaw.autoSpecClawSwap(),
                    waitSecondsTwo.build()
                ),
                clipToParkBlue.build()

                //trajectoryActionChosen,
                //trajectoryActionCloseOut
            )
        )
    }
}