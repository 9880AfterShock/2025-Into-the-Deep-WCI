package org.firstinspires.ftc.teamcode

// RR-specific imports
// Non-RR imports
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.Vector2d
import com.acmerobotics.roadrunner.ftc.*
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Raiser


@Config
@Autonomous(name = "9880 2024 Blue Specimen", group = "Autonomous")
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
        var startToClipBlue: TrajectoryActionBuilder = drive.actionBuilder(startPoseBlue)
            .waitSeconds(0.2)
            .setTangent(Math.PI/-2)
            .splineToSplineHeading(clipPoseBlue, Math.toRadians(-90.0))
            .waitSeconds(0.2)
        var waitSecondsFive: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .waitSeconds(5.0)
        var waitSecondsTwo: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .waitSeconds(30.0)
        var backToBlue: TrajectoryActionBuilder = drive.actionBuilder(clipPoseBlue)
            .setTangent(Math.PI/2)
            .splineToSplineHeading(backPoseBlue, Math.PI/2)
            //.strafeToConstantHeading(Vector2d(0.0, -30.0)/*backVecBlue*/)
            .waitSeconds(0.2)
        var clipToParkBlue: TrajectoryActionBuilder = drive.actionBuilder(backPoseBlue)
            .setTangent(Math.PI/2)// change meEEeeeEE!!!!!!!
            .splineToSplineHeading(parkPoseBlue, Math.toRadians(130.0))


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
                    SequentialAction(
                        SpecimenSwivel.autoSpecSwivOut(),
                        SpecimenClaw.autoSpecClawClose(),
                        SpecimenLift.autoSpecimenLiftUp(/*3500*/),
                    ),
                ),
                ParallelAction(
                    //SpecimenLift.autoSpecimenLiftUp(/*3500*/),
                    //backToBlue.build(),
                    backToBlue.build(),
                    SpecimenLift.autoSpecimenLiftDown(2000),
                ),
                clipToParkBlue.build(),
                Raiser.autoRaiserReset(),
                waitSecondsTwo.build(),
                //trajectoryActionChosen,
                //trajectoryActionCloseOut
            )
        )
    }
}