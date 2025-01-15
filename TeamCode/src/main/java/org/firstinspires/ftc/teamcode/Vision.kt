package org.firstinspires.ftc.teamcode

import android.util.Size
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor
import org.firstinspires.ftc.vision.opencv.ColorRange
import org.firstinspires.ftc.vision.opencv.ImageRegion

object Vision { //Prefix for commands
    lateinit var lift: DcMotorEx //Init Motor Var
    @JvmField
    var pos = 0.0 //starting Position
    var currentSpeed = 0.0 //Starting speed, WHY ARE YOU MAKING A FALLING LIFT???
    @JvmField
    var speed = 0.05 //update speed
    val encoderTicks = 751.8 //calculate your own ratio///// old us 537.7
    @JvmField
    var minPos = 0.0 //folded all the way in

    lateinit var colorLocator: ColorBlobLocatorProcessor
    lateinit var opmode: OpMode //opmode var innit
    var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION //set motor mode
    fun initVision(opmode: OpMode){ //init motors
        colorLocator = ColorBlobLocatorProcessor.Builder()
            .setTargetColorRange(ColorRange.YELLOW) // use a predefined color match
            .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY) // exclude blobs inside blobs
            .setRoi(
                ImageRegion.asUnityCenterCoordinates(
                    -0.5,
                    0.5,
                    0.5,
                    -0.5
                )
            ) // search central 1/4 of camera view
            .setDrawContours(true) // Show contours on the Stream Preview
            .setBlurSize(5) // Smooth the transitions between different colors in image
            .build()

        val portal = VisionPortal.Builder()
            .addProcessor(colorLocator)
            .setCameraResolution(Size(960, 720))
            .setCamera(opmode.hardwareMap.get(WebcamName::class.java, "Webcam"))
            .build()

        this.opmode = opmode
    }

    fun updateVision(){

        opmode.telemetry.addData("preview on/off", "... Camera Stream\n")

        // Read the current list
        val blobs = colorLocator.blobs

        ColorBlobLocatorProcessor.Util.filterByArea(
            50.0,
            20000.0,
            blobs
        ) // filter out very small or large blobs.

        opmode.telemetry.addLine(" Area Density Aspect  Center")

        // Display the size (area) and center location for each Blob.
        for (b in blobs) {
            val boxFit = b.boxFit
            opmode.telemetry.addLine(
                String.format(
                    "%5d  %4.2f   %5.2f  (%3d,%3d)",
                    b.contourArea,
                    b.density,
                    b.aspectRatio,
                    boxFit.center.x.toInt(),
                    boxFit.center.y.toInt()
                )
            )
        }

        opmode.telemetry.update()

    }

}