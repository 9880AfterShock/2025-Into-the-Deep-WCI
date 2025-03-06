package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2


object Swivel {
    lateinit var swivel: Servo
    private var orientation = 0.5
    var restingState = 0.5

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
        if ((opmode.gamepad2.right_stick_y.toDouble() == 0.0 && opmode.gamepad2.right_stick_x.toDouble() == 0.0) || Wrist.currentPos == 2) {
            orientation = restingState
        } else {
            orientation = atan2(abs(opmode.gamepad2.right_stick_y), -opmode.gamepad2.right_stick_x).toDouble()
            orientation = abs(orientation / PI * (0.85 - 0.15) + 0.15) //boundaries are 0.85 and 0.15

            if (Wrist.currentPos == 0 && Raiser.targPos != 0) { //might need to move to make sure it spins when the wrist goes up
                restingState = 0.85 //for while its down
            } else{
                restingState = 0.5
            }

        }
        moveTo(orientation)

        opmode.telemetry.addData("Claw Swivel Position", orientation)
    }
    class autoSwivelRotate(var autoTargPos: Int): Action {
        override fun run(p: TelemetryPacket): Boolean {
            swivel.position = 7.0/1800.0*autoTargPos+0.15
            return false
        }
    }

}