package org.firstinspires.ftc.teamcode

import android.util.Size
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.SortOrder
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
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
                    -0.8,
                    0.7,
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
                    -0.8,
                    0.7,
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
                    -0.8,
                    0.7,
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
        val redBlobs = colorLocatorRed.blobs
        val blueBlobs = colorLocatorBlue.blobs

        val allBlobs = colorLocatorYellow.blobs + colorLocatorRed.blobs + colorLocatorBlue.blobs

        ColorBlobLocatorProcessor.Util.filterByArea(
            1000.0,
            8000.0,
            allBlobs
        ) // filter out very small or large blobs.

        ColorBlobLocatorProcessor.Util.sortByArea(
            SortOrder.DESCENDING,
            allBlobs
        )

        opmode.telemetry.addLine(" Area Density Aspect  Center")

        // Display the size (area) and center location for each Blob.
        for (b in allBlobs) {
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

        Swivel.restingState = 0.85 //remove, will break stuff if not

        if (alignSwivelButtonCurrentlyPressed && !alignSwivelButtonPreviouslyPressed) {
            if (allBlobs.isNotEmpty()) {
                Swivel.restingState = 7.0/1800.0*(allBlobs[0].boxFit.angle%180.0)+0.15
            }
        }



        alignSwivelButtonPreviouslyPressed = alignSwivelButtonCurrentlyPressed


        if (allBlobs.isNotEmpty()) {
            opmode.telemetry.addData(allBlobs[0].boxFit.angle.toString(), "all angles raw")
            opmode.telemetry.addData((7.0/1800.0*(allBlobs[0].boxFit.angle%180.0)+0.15).toString(), "all angles")
        } else {
            opmode.telemetry.addData("none", "all angles")
        }

        opmode.telemetry.update()

    }

    fun stopVision() {
        portal.close()
    }
}