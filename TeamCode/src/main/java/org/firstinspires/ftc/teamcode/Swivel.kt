package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.subsystems.MainLift
import kotlin.math.atan2
import kotlin.math.cos


object Swivel {
    lateinit var swivel: Servo
    private var orientation = 0.5

    lateinit var opmode:OpMode

    fun initSwivel(opmode: OpMode){
        swivel = opmode.hardwareMap.get(Servo::class.java, "Swivel")
        this.opmode = opmode
    }

    fun moveTo(orientation: Double) {

    }

    fun updateSwivel() {
        orientation = atan2(opmode.gamepad2.right_stick_y, opmode.gamepad2.right_stick_x).toDouble()
        opmode.telemetry.addData("Claw Swivel Position", orientation)
    }

}