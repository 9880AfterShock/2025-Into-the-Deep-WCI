package org.firstinspires.ftc.robotcontroller.external.samples



// RR-specific imports
import com.acmerobotics.roadrunner.Action

// Non-RR imports
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.MecanumDrive


@Config
@Autonomous(name = "BLUE_TEST_AUTO_PIXEL", group = "Autonomous")
class BlueSideTestAuto : LinearOpMode() {

// lift class

    override fun runOpMode() {
        // instantiate your MecanumDrive at a particular pose.
        val initialPose = Pose2d(11.8, 61.7, Math.toRadians(90.0))
        val drive = MecanumDrive(hardwareMap, initialPose)


        // actionBuilder builds from the drive steps passed to it
        var tab1: TrajectoryActionBuilder = drive.actionBuilder(initialPose)
            .lineToYSplineHeading(33.0, Math.toRadians(0.0))
            .waitSeconds(2.0)
            .setTangent(Math.toRadians(90.0))
            .lineToY(48.0)
            .setTangent(Math.toRadians(0.0))
            .lineToX(32.0)
            .strafeTo(Vector2d(44.5, 30.0))
            .turn(Math.toRadians(180.0))
            .lineToX(47.5)
            .waitSeconds(3.0)

        var tab2: TrajectoryActionBuilder = drive.actionBuilder(initialPose)
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
            .waitSeconds(3.0)

        var trajectoryActionCloseOut: Action = tab1.fresh()
            .strafeTo(Vector2d(48.0, 12.0))
            .build()


    }
}