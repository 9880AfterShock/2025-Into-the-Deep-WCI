package org.firstinspires.ftc.teamcode
//imports
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d


// Poses
    // blue side
    var startPoseBlue = Pose2d(-14.0, 61.0, 0.0)
    var clipPoseBlue = Pose2d(0.0, 30.0, Math.toRadians(45.0))
    var parkPoseBlue = Pose2d(-60.0, 60.0, Math.PI * 1.5)
    // red side
    var startPoseRed = Pose2d(14.0, -61.0, Math.toRadians(180.0))
    var clipPoseRed = Pose2d(7.0, -32.5, Math.toRadians(180.0))
    //var backVecRed = Vector2d(0.0, -30.0)
    var backPoseRed = Pose2d(0.0, -40.0, Math.toRadians(180.0))
    var parkPoseRed = Pose2d(60.0, -60.0, Math.toRadians(90.0))
// Paths