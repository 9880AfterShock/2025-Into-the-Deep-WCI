package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2


object Swivel {
    lateinit var swivel: Servo
    private var orientation = 0.5
    private var restingState = 0.5

    lateinit var opmode:OpMode

    fun initSwivel(opmode: OpMode){
        swivel = opmode.hardwareMap.get(Servo::class.java, "Swivel")
        orientation = 0.5
        restingState = 0.5
        this.opmode = opmode
    }

    private fun moveTo(orientation: Double) {
        swivel.position = orientation
    }

    fun updateSwivel() {
        if ((abs(opmode.gamepad2.right_stick_y.toDouble()) == 0.0 && opmode.gamepad2.right_stick_x.toDouble() == 0.0) || Wrist.currentPos == 2) {
            orientation = restingState
        } else {
            orientation = atan2(opmode.gamepad2.right_stick_y, -opmode.gamepad2.right_stick_x).toDouble()
            orientation = orientation*0.6/PI + 0.2
            restingState = 0.5
        }
        moveTo(orientation)

        opmode.telemetry.addData("Claw Swivel Position", orientation)
    }

}