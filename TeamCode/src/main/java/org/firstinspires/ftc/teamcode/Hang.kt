package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode

object Hang {
    var hanging = false
    lateinit var opmode: OpMode //opmode var innit
    private var hangButtonCurrentlyPressed = false
    private var hangButtonPreviouslyPressed = false
    private var unHangButtonCurrentlyPressed = false
    private var unHangButtonPreviouslyPressed = false
    fun initHang(opmode: OpMode){ //init motors
        this.opmode = opmode
        hanging = false
    }
    fun checkHang(){
        hangButtonCurrentlyPressed = (opmode.gamepad2.right_trigger.toDouble() > 0.1) //can change controls
        unHangButtonCurrentlyPressed = (opmode.gamepad2.left_trigger.toDouble() > 0.1) //can change controls
        if (hangButtonCurrentlyPressed && !hangButtonPreviouslyPressed && !hanging) { //not working???
            MainLift.pos = 0.0
            MainLift.currentSpeed = 0.0
            Raiser.targPos = Raiser.downPos
            hanging = true
        }
        if (hanging) {
            if (MainLift.lift.currentPosition <= 1.0*MainLift.encoderTicks) {
                Raiser.targPos = Raiser.upPos
            }
        }

        hangButtonPreviouslyPressed = hangButtonCurrentlyPressed
        unHangButtonPreviouslyPressed = unHangButtonCurrentlyPressed

        opmode.telemetry.addData("Hanging", hanging.toString())
    }
}