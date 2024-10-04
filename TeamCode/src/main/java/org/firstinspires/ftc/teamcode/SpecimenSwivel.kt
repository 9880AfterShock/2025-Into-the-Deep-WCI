package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo


object SpecimenSwivel {
    private lateinit var swivel: Servo
    @JvmField
    var outPos = 1.0 //the positions
    @JvmField
    var inPos = 0.7 //the positions
    var inited = false
    private var state = "In"
    private var swivelButtonCurrentlyPressed = false
    private var swivelButtonPreviouslyPressed = false

    lateinit var opmode:OpMode
    fun initSwivel(opmode: OpMode){
        swivel = opmode.hardwareMap.get(Servo::class.java, "Specimen Swivel") //config name
        this.opmode = opmode
        state = "In"
        inited = false
    }
    fun moveOut() {
        swivel.position = outPos
        state = "Out"
    }
    private fun moveIn(){
        swivel.position = inPos //swivel doesnt move
        state = "In" //this runs
    }
    private fun swap(){
        if (state == "Out") {
            moveIn()
        } else {
            moveOut()
        }
    }
    fun updateSwivel() {
        opmode.telemetry.addData("Swivel Position", state)
        // Check the status of the claw button on the gamepad
        swivelButtonCurrentlyPressed = opmode.gamepad1.y //change this to change the button //disabled for safety

        // If the button state is different than what it was, then act
        if (swivelButtonCurrentlyPressed != swivelButtonPreviouslyPressed) {
            // If the button is (now) down
            if (swivelButtonCurrentlyPressed) {
                swap()
            }
        }
        swivelButtonPreviouslyPressed = swivelButtonCurrentlyPressed
    }
}