package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo



object Beard {
    lateinit var beard: Servo
    @JvmField
    var outPos = 1.0 //the positions
    @JvmField
    var inPos = 0.0 //the positions
    private var state = "In"
    private var beardButtonCurrentlyPressed = false
    private var beardButtonPreviouslyPressed = false

    lateinit var opmode:OpMode
    fun initBeard(opmode: OpMode){
        beard = opmode.hardwareMap.get(Servo::class.java, "Beard")
        this.opmode = opmode
        state = "In"
    }
    fun moveOut() {
        beard.position = outPos
        state = "Out"
    }
    fun moveIn(){
        beard.position = inPos
        state = "In"
    }

    private fun swap(){
        if (state == "Out") {
            moveIn()
        } else {
            moveOut()
        }
    }
    fun updateBeard() {
        opmode.telemetry.addData("Beard Position", state)
        beardButtonCurrentlyPressed = (opmode.gamepad2.right_trigger.toDouble() > 0.5) //change this to change the button

        if (beardButtonCurrentlyPressed && !beardButtonPreviouslyPressed) {
            swap()
        }
        beardButtonPreviouslyPressed = beardButtonCurrentlyPressed
    }

}