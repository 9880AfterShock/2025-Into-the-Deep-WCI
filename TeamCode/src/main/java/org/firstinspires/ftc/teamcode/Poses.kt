package org.firstinspires.ftc.teamcode
//imports
import com.acmerobotics.roadrunner.Pose2d
import com.acmerobotics.roadrunner.Vector2d


// Poses
// blue side
var startPoseBlueBucket = Pose2d(32.8, 61.5, 0.0)
var neutralPoseBlueFirst = Pose2d(34.9, 24.0,0.0)
var neutralPoseBlueSecond = Pose2d(43.2, 24.0,0.0)
var bucketPoseBlue = Pose2d(45.4,42.7, Math.toRadians(45.0))
var bucketParkPoseBlue = Pose2d(26.0, 0.0, Math.toRadians(180.0))
var startPoseBlue = Pose2d(-14.0, 61.0, 0.0) // use for big
//
var clipPoseBlue = Pose2d(-11.0, 32.0 , 0.0) // use for big
//
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
var pushPrepPoseMidBigFast = Pose2d(-53.2,12.1,Math.toRadians(90.0)) // good
var pushPoseMidBigFast = Pose2d(-52.2, 46.0,Math.toRadians(90.0)) //good
//
var pushPrepPoseRightBigFastAsstronomical = Pose2d(-35.8,12.1,Math.toRadians(90.0)) //good // was -35
var pushPoseRightBigFastAsstronomical = Pose2d(-43.9, 46.0,Math.toRadians(90.0)) //good
var pushPrepPoseMidBigFastAsstronomical = Pose2d(-51.2,12.1,Math.toRadians(90.0)) // good
var pushPoseMidBigFastAsstronomical = Pose2d(-50.0, 46.0,Math.toRadians(90.0)) //good
//
var pushPrepPoseBig = Pose2d(-34.8,12.1, Math.PI/2)
var pushPrepPoseRightBig = Vector2d(-43.9,12.1) //good
var pushPoseRightBig = Vector2d(-43.9, 50.0) //good
var pushPrepPoseMidBig = Vector2d(-52.2,12.1) // good
var pushPoseMidBig = Vector2d(-52.2, 50.0) //good
//
var specStartPickupPoseBig = Pose2d(-37.7, 57.15, Math.PI) // good original x is -36.2
var specEndPickupPoseBig = Pose2d(-44.2,56.5, Math.PI) // test for moving
var specStartPickupPoseSecondBig = Pose2d(-34.85, 58.305, Math.PI)//505
var specEndPickupPoseSecondBig = Pose2d(-44.7, 58.5, Math.PI)//-42.2, 60.1, math.pi
var specStartPickupPoseLastBig = Pose2d(-33.2, 59.25, Math.PI)// in need of poses
var specEndPickupPoseLastBig = Pose2d(-36.7, 58.25, Math.PI)// in need of poses
//
var specStartPickupPoseBigAsstronomical = Pose2d(-37.7, 57.15, Math.PI) // good original x is -36.2
var specEndPickupPoseBigAsstronomical = Pose2d(-39.2,56.5, Math.PI) // test for moving
var specStartPickupPoseSecondBigAsstronomical = Pose2d(-34.85, 58.155, Math.PI)//505
var specEndPickupPoseSecondBigAsstronomical = Pose2d(-40.7, 58.3, Math.PI)//-42.2, 60.1, math.pi
var specStartPickupPoseLastBigAsstronomical = Pose2d(-33.2, 59.25, Math.PI)// in need of poses
var specEndPickupPoseLastBigAsstronomical = Pose2d(-36.7, 58.25, Math.PI)// in need of poses
//
var clipPoseBlueTheSecond = Pose2d(-7.0, 29.00, 0.0)//0.0, 31.750, 0.0// 31.250 // 31.000
var clipPoseBlueTheThird = Pose2d(-2.0, 30.83, 0.0)// 29.6800
var clipPoseBlueTheFourth = Pose2d(3.0, 31.0, 0.0)// in need of Poses
//
var clipPoseBlueAsstronomical = Pose2d(-11.0, 32.0 , 0.0) // use for big
var clipPoseBlueTheSecondAsstronomical = Pose2d(-7.0, 29.00, 0.0)//0.0, 31.750, 0.0// 31.250 // 31.000
var clipPoseBlueTheThirdAsstronomical = Pose2d(-2.0, 30.83, 0.0)// 29.6800
var clipPoseBlueTheFourthAsstronomical = Pose2d(3.0, 31.0, 0.0)// in need of Poses
var clipPoseBlueTheFifthAsstronomical = Pose2d(3.0, 31.0, 0.0)// in need of Poses
//
var parkPoseBlueBig = Pose2d(-50.0, 57.0, Math.toRadians(0.0)) // use for big
var backPoseBlueBig = Vector2d(2.0, 40.0)// y = 45, 37 not enough. |38 works! we have extra time, so we are going a tad farther|
// Paths