package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
//import kotlin.concurrent.thread not needed lol


object Wrist {
    private lateinit var wrist: Servo
    @JvmField
    var positions = arrayOf (0, 90, 180) //positions, most fzorward to most backward
    @JvmField
    var initPos = 200 //innit pos prob 200-220 or so
    var currentPos = -1 //innit pos placeholder
    private var state = "Init"
    private var backwardWristButtonCurrentlyPressed = false
    private var backwardWristButtonPreviouslyPressed = false
    private var forwardWristButtonCurrentlyPressed = false
    private var forwardWristButtonPreviouslyPressed = false

    lateinit var opmode:OpMode

    fun initWrist(opmode: OpMode){
        currentPos = -1 //reset pos to innit, change for teleop after auto
        wrist = opmode.hardwareMap.get(Servo::class.java, "Wrist") //config name
        this.opmode = opmode
    }

    fun updateWrist() {
        // Check the status of the claw button on the game pad
        forwardWristButtonCurrentlyPressed = opmode.gamepad1.right_bumper //change these to change the button
        backwardWristButtonCurrentlyPressed = opmode.gamepad1.left_bumper

        //if (!(forwardWristButtonCurrentlyPressed && backwardWristButtonCurrentlyPressed)) { //safety mechanism
        if (forwardWristButtonCurrentlyPressed && !forwardWristButtonPreviouslyPressed) {
            changePosition("forward")
        }
        if (backwardWristButtonCurrentlyPressed && !backwardWristButtonPreviouslyPressed) {
            changePosition("backward")
        }
        //}

        forwardWristButtonPreviouslyPressed = forwardWristButtonCurrentlyPressed
        backwardWristButtonPreviouslyPressed = backwardWristButtonCurrentlyPressed

        opmode.telemetry.addData("Wrist State", state)
    }

    private fun changePosition(direction: String){
        if (currentPos == -1 && direction == "forward") {
            currentPos = positions.size-1 //if inited, go to last in array
            updatePosition(positions[currentPos])
        } else {
            if (currentPos != -1) {
                if (direction == "forward" && positions[currentPos] != positions[0]) {
                    currentPos -= 1
                    if (currentPos == 0) {
                        Claw.close()
                    }
                }
                if (direction == "backward" && currentPos != positions.size-1) {
                    currentPos += 1
                }
                updatePosition(positions[currentPos])
            }
        }
    }
    private fun updatePosition(targetPosition: Int){
        if (targetPosition == -1) {
            wrist.position = initPos.toDouble() / 270 //change both
        } else {
            wrist.position = targetPosition.toDouble() / 270 //change both
        }
        state = targetPosition.toString()
    }
}