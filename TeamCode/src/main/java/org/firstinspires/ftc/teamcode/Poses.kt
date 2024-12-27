package org.firstinspires.ftc.teamcode
//imports
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d


// Poses
// blue side
var startPoseBlueBucket = Pose2d(32.8, 61.5, 0.0)
var neutralPoseBlueFirst = Pose2d(34.5, 24.0,0.0)
var neutralPoseBlueSecond = Pose2d(43.0, 24.0,0.0)
var bucketPoseBlue = Pose2d(44.6,40.5, Math.toRadians(45.0))
var bucketParkPoseBlue = Pose2d(26.0, 0.0, Math.toRadians(180.0))
var startPoseBlue = Pose2d(-14.0, 61.0, 0.0) // use for big
var clipPoseBlue = Pose2d(-7.0, 32.50 , 0.0) // use for big
var backPoseBlue = Pose2d(0.0, 40.0, 0.0)
var parkPoseBlue = Pose2d(-57.0, 57.0, Math.toRadians(-90.0))

// red side
var startPoseRed = Pose2d(14.0, -61.0, Math.toRadians(180.0))
var clipPoseRed = Pose2d(7.0, -33.25, Math.toRadians(180.0))
var backPoseRed = Pose2d(0.0, -40.0, Math.toRadians(180.0))
var parkPoseRed = Pose2d(57.0, -57.0, Math.toRadians(90.0))
// big auto
var pushPrepPoseRightBigFast = Pose2d(-34.8,12.1,Math.toRadians(90.0)) //good // was -35
var pushPoseRightBigFast = Pose2d(-43.9, 46.0,Math.toRadians(90.0)) //good
var pushPrepPoseMidBigFast = Pose2d(-52.2,12.1,Math.toRadians(90.0)) // good
var pushPoseMidBigFast = Pose2d(-52.2, 46.0,Math.toRadians(90.0)) //good
var pushPrepPoseBig = Pose2d(-34.8,12.1, Math.PI/2)
var pushPrepPoseRightBig = Vector2d(-43.9,12.1) //good
var pushPoseRightBig = Vector2d(-43.9, 50.0) //good
var pushPrepPoseMidBig = Vector2d(-52.2,12.1) // good
var pushPoseMidBig = Vector2d(-52.2, 50.0) //good
var specStartPickupPoseBig = Pose2d(-35.2, 57.0, Math.PI) // good original x is -36.2
var specEndPickupPoseBig = Pose2d(-42.2,57.15, Math.PI) // test for moving
var specStartPickupPoseLastBig = Pose2d(-36.2, 58.5, Math.PI)
var specEndPickupPoseLastBig = Pose2d(-44.2, 58.75, Math.PI)//-42.2, 60.1, math.pi
var clipPoseBlueTheSecond = Pose2d(0.0, 30.50, 0.0)//0.0, 31.750, 0.0// 31.250 // 31.000
var clipPoseBlueTheThird = Pose2d(5.0, 31.000, 0.0)// 5.0, 32.250, 0.0
var parkPoseBlueBig = Pose2d(-50.0, 57.0, Math.toRadians(0.0)) // use for big
// Paths
