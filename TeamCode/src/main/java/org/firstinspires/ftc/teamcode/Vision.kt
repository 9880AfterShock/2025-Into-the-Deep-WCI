package org.firstinspires.ftc.teamcode

/*Copyright (c) 2014-2022 FIRST.  All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

import android.util.Log
import android.util.Size
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.SortOrder
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor
import org.firstinspires.ftc.vision.opencv.ColorRange
import org.firstinspires.ftc.vision.opencv.ImageRegion
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.RotatedRect
import java.util.concurrent.TimeUnit
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.pow
import kotlin.collections.plus


object Vision { //Prefix for commands

    lateinit var colorLocatorRed: ColorBlobLocatorProcessor
    lateinit var colorLocatorBlue: ColorBlobLocatorProcessor
    lateinit var colorLocatorYellow: ColorBlobLocatorProcessor
    lateinit var opmode: OpMode //opmode var innit

    private var alignSwivelButtonCurrentlyPressed = false
    private var alignSwivelButtonPreviouslyPressed = false

    lateinit var portal: VisionPortal
    lateinit var pointsOverTime: IntArray

    var angle = 180.0 //sample for now
    val exposureMillis = 5
    var testTelemetry = 0.0
    var degreeAngle = 0.0

    fun initVision(opmode: OpMode){ //init motors

        colorLocatorYellow = ColorBlobLocatorProcessor.Builder()
            .setTargetColorRange(ColorRange.YELLOW) // use a predefined color match
            .setContourMode(ColorBlobLocatorProcessor.ContourMode.EXTERNAL_ONLY) // exclude blobs inside blobs
            .setRoi(
                ImageRegion.asUnityCenterCoordinates(
                    -0.7,
                    0.6,
                    0.8,
                    -0.8
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
                    -0.7,
                    0.6,
                    0.8,
                    -0.8
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
                    -0.7,
                    0.6,
                    0.8,
                    -0.8
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

        portal.setProcessorEnabled(colorLocatorYellow, true)
        portal.setProcessorEnabled(colorLocatorRed, true)
        portal.setProcessorEnabled(colorLocatorBlue, true)

        pointsOverTime = intArrayOf()

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

//        val allBlobs = if (opmode.gamepad1.left_stick_x.toDouble() > 0.0) { //filter by driver input
//            colorLocatorYellow.blobs
//        } else {
//            colorLocatorRed.blobs + colorLocatorBlue.blobs
//        }

        ColorBlobLocatorProcessor.Util.filterByArea(
            100.0,
            15000.0,
            allBlobs
        ) // filter out very small or large blobs.

        ColorBlobLocatorProcessor.Util.sortByArea(
            SortOrder.DESCENDING,
            allBlobs
        )

        opmode.telemetry.addLine(" Area Density Aspect  Center")

        // Display the size (area) and center location for each Blob.
//        for (b in allBlobs) {
//            val boxFit = b.boxFit
//            opmode.telemetry.addLine(
//                String.format(
//                    "%5d  %4.2f   %5.2f  (%3d,%3d)",
//                    b.contourArea,
//                    b.density,
//                    b.aspectRatio,
//                    boxFit.center.x.toInt(),
//                    boxFit.center.y.toInt(),
//                )
//            )
//        }

        if (allBlobs.isNotEmpty()) {
            val boxFit: RotatedRect = allBlobs[0].boxFit

            val leftMost = getMostLeftPoint(allBlobs[0].contourPoints)
            val rightMost = getMostRightPoint(allBlobs[0].contourPoints)

            if (leftMost != null) {
                if (rightMost != null) {
                    degreeAngle = if (leftMost.y < rightMost.y) { //angled this way //
                        boxFit.angle + 90
                    } else { //angled this way \\
                        boxFit.angle

                    }
                }
            }
            opmode.telemetry.addData("real angle ======",7.0/1800.0*(degreeAngle%180)+0.15)
        }


        if (alignSwivelButtonCurrentlyPressed && !alignSwivelButtonPreviouslyPressed) {
            pointsOverTime = intArrayOf()
            if (allBlobs.isNotEmpty()) {
                Swivel.restingState = 7.0/1800.0*(degreeAngle%180)+0.15
                //Swivel.restingState = 7.0/1800.0*(allBlobs[0].boxFit.angle%180.0)+0.15 //box of best fit
//                Swivel.restingState = 7.0/1800.0*((atan(calculateSlope(allBlobs[0].contourPoints))*180/PI)%180.0)+0.15 //line of best fit of all points

            }
        }

        //yoinked from example code, might not work
        if (allBlobs.isNotEmpty()) {
            val boxFit: RotatedRect = allBlobs[0].boxFit
            val myHorizontalBoxFit: Rect = boxFit.boundingRect()
            val angleOfSample = myHorizontalBoxFit.size()
            opmode.telemetry.addData("angles rotated size", angleOfSample)

            opmode.telemetry.addData("top left X", myHorizontalBoxFit.x);
            opmode.telemetry.addData("top left Y", myHorizontalBoxFit.y);
            opmode.telemetry.addData("width", myHorizontalBoxFit.width);
            opmode.telemetry.addData("height", myHorizontalBoxFit.height);
            //pointsOverTime (angleOfSample)





        }
//        if (alignSwivelButtonPreviouslyPressed && !alignSwivelButtonCurrentlyPressed) {
//            if (pointsOverTime.isNotEmpty()) {
//                //Swivel.restingState = 7.0/1800.0*((atan(anglesOverTime.average())*180/PI)%180.0)+0.15
//                //testTelemetry = 7.0/1800.0*((atan(calculateSlope(pointsOverTime))*180/PI)%180.0)+0.15
//                testTelemetry = linearRegression(pointsOverTime)
//            }
//        }


        alignSwivelButtonPreviouslyPressed = alignSwivelButtonCurrentlyPressed


        opmode.telemetry.addData("avg over time", testTelemetry)
        if (allBlobs.isNotEmpty()) {
            opmode.telemetry.addData("angles raw", allBlobs[0].boxFit.angle) //need to adjust
            opmode.telemetry.addData("angles",7.0/1800.0*(allBlobs[0].boxFit.angle%180)+0.15)
            opmode.telemetry.addData("fit line", calculateSlope(allBlobs[0].contourPoints))
            opmode.telemetry.addData("fit line calced", atan(calculateSlope(allBlobs[0].contourPoints))*180/PI)
            opmode.telemetry.update()
        } else {
            //opmode.telemetry.addData("none", "all angles")
        }

//        opmode.telemetry.update()

    }

    fun stopVision() {
        portal.close()
    }











    //Thanks escape velocity :D
    fun setExposure(exposure: Int): Boolean {
        if (portal.cameraState != VisionPortal.CameraState.STREAMING) {
            return false
        }

        val control = portal.getCameraControl(ExposureControl::class.java)
        control.mode = ExposureControl.Mode.Manual
        return control.setExposure(exposure.toLong(), TimeUnit.MILLISECONDS)
    }

    fun setExposure(): Boolean {
        return setExposure(exposureMillis)
    }

    fun waitForSetExposure(timeoutMs: Long, maxAttempts: Int, exposure: Int): Boolean {
        val startMs = System.currentTimeMillis()
        var attempts = 0
        var msAfterStart: Long

        do {
            msAfterStart = System.currentTimeMillis() - startMs
            Log.i("camera", "Attempting to set camera exposure, attempt ${attempts + 1}, $msAfterStart ms after start")
            if (setExposure(exposure)) {
                Log.i("camera", "Set exposure succeeded")
                return true
            }
            attempts++
        } while (msAfterStart < timeoutMs && attempts < maxAttempts)

        Log.e("camera", "Set exposure failed")
        return false
    }
    fun waitForSetExposure(timeoutMs: Long, maxAttempts: Int): Boolean {
        return waitForSetExposure(timeoutMs, maxAttempts, exposureMillis)
    }



    fun calculateSlope(points: Array<Point>): Double {

        //Calculate the means of x and y
        val meanX = points.sumOf { it.x } / points.size
        val meanY = points.sumOf { it.y } / points.size

        //Calculate the numerator and denominator for the slope formula
        val numerator = points.sumOf { (it.x - meanX) * (it.y - meanY) }
        val denominator = points.sumOf { (it.x - meanX).pow(2) }

        //Return the slope
        return numerator / denominator
    }

    fun linearRegression(points: Array<Point>): Double {
        val n = points.size
        val sumX = points.sumOf { it.x }
        val sumY = points.sumOf { it.y }
        val sumXY = points.sumOf { it.x * it.y }
        val sumXSquare = points.sumOf { it.x * it.x }

        // Calculate the slope (m)
        val m = (n * sumXY - sumX * sumY) / (n * sumXSquare - sumX * sumX)

        return m
    }

    private fun getHighestPoint(points: Array<out Point?>): Point? {
        return points.filterNotNull() // Filter out the null points
            .maxByOrNull { it.y } // Find the point with the highest y value
    }
    private fun getMostLeftPoint(points: Array<out Point?>): Point? {
        return points.filterNotNull() // Filter out the null points
            .minByOrNull { it.x } // Find the point with the highest y value
    }
    private fun getMostRightPoint(points: Array<out Point?>): Point? {
        return points.filterNotNull() // Filter out the null points
            .maxByOrNull { it.x } // Find the point with the highest y value
    }
}