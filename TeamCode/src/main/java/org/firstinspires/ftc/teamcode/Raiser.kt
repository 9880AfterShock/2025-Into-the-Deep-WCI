package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor

object Raiser { //Prefix for commands
    private lateinit var motor: DcMotor //Init Motor Var

    var targPos = 0 // in encoder ticks
    var upPos = 0 // in encoder ticks
    var downPos = -1135
    var hangPos = -500

    private var downButtonCurrentlyPressed = false
    private var downButtonPreviouslyPressed = false
    private var upButtonCurrentlyPressed = false
    private var upButtonPreviouslyPressed = false
    private var hangButtonCurrentlyPressed = false
    private var hangButtonPreviouslyPressed = false
    lateinit var opmode: OpMode //opmode var innit
    private var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    private var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION //set motor mode
    fun initRaiser(opmode: OpMode){ //init motors
        motor = opmode.hardwareMap.get(DcMotor::class.java, "raiser") //config name
        targPos = 0
        motor.targetPosition = targPos
        motor.mode = encoderMode //reset encoder
        motor.mode = motorMode //enable motor mode
        this.opmode = opmode
    }
    fun initRaiserAfterAuto(opmode: OpMode){ //init motors
        motor = opmode.hardwareMap.get(DcMotor::class.java, "raiser") //config name
        this.opmode = opmode
    }
    fun updateRaiser() {
        downButtonCurrentlyPressed = opmode.gamepad2.b //can change controls
        upButtonCurrentlyPressed = opmode.gamepad2.y //can change controls
        hangButtonCurrentlyPressed = opmode.gamepad2.x //can change controls

        if (!((downButtonCurrentlyPressed && upButtonCurrentlyPressed) || (downButtonCurrentlyPressed && hangButtonCurrentlyPressed) || (upButtonCurrentlyPressed && hangButtonCurrentlyPressed))) {
            if ((downButtonCurrentlyPressed && !downButtonPreviouslyPressed) && MainLift.lift.currentPosition/MainLift.encoderTicks <= MainLift.maxLowPos) { //make so it cannot be lowered beyond the limit of the size constraints
                targPos = downPos
            }
            if (upButtonCurrentlyPressed && !upButtonPreviouslyPressed) {
                targPos = upPos
            }
            if (hangButtonCurrentlyPressed && !hangButtonPreviouslyPressed) {
                targPos = hangPos
            }
        }

        downButtonPreviouslyPressed = downButtonCurrentlyPressed
        upButtonPreviouslyPressed = upButtonCurrentlyPressed
        hangButtonPreviouslyPressed = hangButtonCurrentlyPressed

        if (motor.currentPosition <= downPos + 30 && targPos == downPos) { //30 is margin of error
            motor.power = 0.0
        } else {
            motor.power = 0.7
        }
        motor.targetPosition = (targPos)
        opmode.telemetry.addData("Raiser Position", targPos) //change to enum
        opmode.telemetry.addData("TRUE RAISER POS", motor.currentPosition) //change to enum
    }
}