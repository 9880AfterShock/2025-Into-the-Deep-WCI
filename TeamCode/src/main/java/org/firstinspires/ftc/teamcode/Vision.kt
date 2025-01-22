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

    lateinit var colorLocatorRed: ColorBlobLocatorProcessor
    lateinit var colorLocatorBlue: ColorBlobLocatorProcessor
    lateinit var colorLocatorYellow: ColorBlobLocatorProcessor
    lateinit var opmode: OpMode //opmode var innit

    private var alignSwivelButtonCurrentlyPressed = false
    private var alignSwivelButtonPreviouslyPressed = false

    lateinit var portal: VisionPortal

    var angle = 180.0 //sample for now

    fun initVision(opmode: OpMode){ //init motors
        colorLocatorYellow = ColorBlobLocatorProcessor.Builder()
            .setTargetColorRange(ColorRange.YELLOW) // use a predefined color match
            .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY) // exclude blobs inside blobs
            .setRoi(
                ImageRegion.asUnityCenterCoordinates(
                    -0.9,
                    0.9,
                    0.9,
                    -0.9
                )
            ) // search central main area of camera view
            .setDrawContours(true) // Show contours on the Stream Preview
            .setBlurSize(5) // Smooth the transitions between different colors in image
            .build()

        colorLocatorBlue = ColorBlobLocatorProcessor.Builder()
            .setTargetColorRange(ColorRange.BLUE) // use a predefined color match
            .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY) // exclude blobs inside blobs
            .setRoi(
                ImageRegion.asUnityCenterCoordinates(
                    -0.9,
                    0.9,
                    0.9,
                    -0.9
                )
            ) // search central main area of camera view
            .setDrawContours(true) // Show contours on the Stream Preview
            .setBlurSize(5) // Smooth the transitions between different colors in image
            .build()

        colorLocatorRed = ColorBlobLocatorProcessor.Builder()
            .setTargetColorRange(ColorRange.RED) // use a predefined color match
            .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY) // exclude blobs inside blobs
            .setRoi(
                ImageRegion.asUnityCenterCoordinates(
                    -0.9,
                    0.9,
                    0.9,
                    -0.9
                )
            ) // search central main area of camera view
            .setDrawContours(true) // Show contours on the Stream Preview
            .setBlurSize(5) // Smooth the transitions between different colors in image
            .build()

        portal = VisionPortal.Builder()
            .addProcessor(colorLocatorYellow)
            .addProcessor(colorLocatorBlue)
            .addProcessor(colorLocatorRed)
            .setCameraResolution(Size(960, 720))
            .setCamera(opmode.hardwareMap.get(WebcamName::class.java, "Webcam"))
            .build()

        this.opmode = opmode
    }

    fun updateVision(){

        alignSwivelButtonCurrentlyPressed = opmode.gamepad2.left_stick_button //can change controls

        opmode.telemetry.addData("preview on/off", "... Camera Stream\n")

        // Read the current list
        val yellowBlobs = colorLocatorYellow.blobs
        val redBlobs = colorLocatorYellow.blobs
        val blueBlobs = colorLocatorYellow.blobs

        ColorBlobLocatorProcessor.Util.filterByArea(
            50.0,
            15000.0,
            yellowBlobs
        ) // filter out very small or large blobs.
        ColorBlobLocatorProcessor.Util.filterByArea(
            50.0,
            15000.0,
            redBlobs
        ) // filter out very small or large blobs.
        ColorBlobLocatorProcessor.Util.filterByArea(
            50.0,
            15000.0,
            blueBlobs
        ) // filter out very small or large blobs.

        opmode.telemetry.addLine(" Area Density Aspect  Center")

        // Display the size (area) and center location for each Blob.
        for (b in yellowBlobs) {
            val boxFit = b.boxFit
            opmode.telemetry.addLine(
                String.format(
                    "%5d  %4.2f   %5.2f  (%3d,%3d)",
                    b.contourArea,
                    b.density,
                    b.aspectRatio,
                    boxFit.center.x.toInt(),
                    boxFit.center.y.toInt(),
                )
            )
        }

        for (b in redBlobs) {
            val boxFit = b.boxFit
            opmode.telemetry.addData(boxFit.angle.toString(), "Red Rotation")
        }
        for (b in blueBlobs) {
            val boxFit = b.boxFit
            opmode.telemetry.addData(boxFit.angle.toString(), "Blue Rotation")
        }

        alignSwivelButtonPreviouslyPressed = alignSwivelButtonCurrentlyPressed

        opmode.telemetry.update()

    }

    fun stopVision() {
        portal.close()
    }
}