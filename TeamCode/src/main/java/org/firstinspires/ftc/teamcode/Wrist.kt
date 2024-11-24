package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.subsystems.MainLift.pos

object Wrist {
    private lateinit var wrist: DcMotor
    val encoderTicks = 752.8 //calculate your own ratio
    @JvmField
    var positions = arrayOf (0, 90, 180) //positions, most forward to most backward
    @JvmField
    var initPos = 220 //innit pos prob 200-220 or so
    var currentPos = -1 //innit pos placeholder
    private var state = "Init"
    /*private*/ var backwardWristButtonCurrentlyPressed = false
    /*private*/ var backwardWristButtonPreviouslyPressed = false
    /*private*/ var forwardWristButtonCurrentlyPressed = false
    /*private*/ var forwardWristButtonPreviouslyPressed = false

    lateinit var opmode:OpMode
    var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION
    fun initWrist(opmode: OpMode){
        currentPos = -1 //reset pos to innit, change for teleop after auto
        wrist = opmode.hardwareMap.get(DcMotor::class.java, "wrist") //config name
        wrist.targetPosition = (pos * encoderTicks).toInt()
        wrist.mode = encoderMode //reset encoder
        wrist.mode = motorMode //enable motor mode
        this.opmode = opmode
    }
    fun initWristAfterAuto(opmode: OpMode){
        wrist = opmode.hardwareMap.get(DcMotor::class.java, "wrist") //config name
        wrist.mode = motorMode //enable motor mode
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
        //right now
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
            wrist.targetPosition = ((-encoderTicks*(-initPos+initPos))/360).toInt()
        } else {
            wrist.targetPosition = ((-encoderTicks*(-targetPosition+initPos))/360).toInt()
        }
        state = targetPosition.toString()
        wrist.power = 0.25
    }
    class autoWristGoToPos(var autoTargPos: Int): Action {
        override fun run(p: TelemetryPacket): Boolean {
            updatePosition(autoTargPos)
            return false
        }
    }
}