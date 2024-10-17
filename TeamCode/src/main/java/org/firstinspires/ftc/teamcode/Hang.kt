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
        hangButtonCurrentlyPressed = opmode.gamepad2.b //can change controls
        if (hangButtonCurrentlyPressed && !hangButtonPreviouslyPressed && !hanging) {
            MainLift.pos = 0.0
            MainLift.currentSpeed = 0.0
            Raiser.targPos = Raiser.downPos
            hanging = true
        }
        if (hanging) {
            opmode.telemetry.addData("HANGING!!!", 1)
            if (MainLift.lift.currentPosition <= 0.5*MainLift.encoderTicks) {
                Raiser.targPos = Raiser.upPos
            }
        }

        hangButtonPreviouslyPressed = hangButtonCurrentlyPressed
        unHangButtonPreviouslyPressed = unHangButtonCurrentlyPressed
    }
}