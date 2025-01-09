package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.subsystems.MainLift

object Raiser { //Prefix for commands
    private lateinit var motor: DcMotorEx //Init Motor Var

    @JvmField //idke why i neeed this for java but whatever
    var targPos = 0 // in encoder ticks
    @JvmField
    var upPos = 0 // in encoder ticks
    var downPos = -1135
    var hangPos = -500

    /*private*/ var raiserDownButtonCurrentlyPressed = false
    private var downButtonPreviouslyPressed = false
    /*private*/ var raiserUpButtonCurrentlyPressed = false
    private var upButtonPreviouslyPressed = false
    /*private*/ var raiserHangButtonCurrentlyPressed = false
    private var raiserResetPreviouslyPressed = false
    private var raiserResetButtonCurrentlyPressed = false
    private var raiserManualUpButtonCurrentlyPressed = false
    private var raiserManualUpButtonPreviouslyPressed = false
    private var hangButtonPreviouslyPressed = false
    private var manual = false
    lateinit var opmode: OpMode //opmode var innit
    private var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    private var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION //set motor mode
    fun initRaiser(opmode: OpMode){ //init motors
        motor = opmode.hardwareMap.get(DcMotorEx::class.java, "raiser") //config name
        targPos = 0
        motor.targetPosition = targPos
        motor.mode = encoderMode //reset encoder
        motor.mode = motorMode //enable motor mode
        this.opmode = opmode
        manual = false
    }
    fun initRaiserAfterAuto(opmode: OpMode){ //init motors
        motor = opmode.hardwareMap.get(DcMotorEx::class.java, "raiser") //config name
        motor.mode = motorMode //enable motor mode
        this.opmode = opmode
    }
    fun updateRaiser() {
        //raiserAutoUpPressed =
        raiserDownButtonCurrentlyPressed = opmode.gamepad2.b //can change controls
        raiserUpButtonCurrentlyPressed = opmode.gamepad2.y //can change controls
        raiserHangButtonCurrentlyPressed = opmode.gamepad2.x //can change controls
        raiserResetButtonCurrentlyPressed = opmode.gamepad2.right_stick_button //can change controls
        raiserManualUpButtonCurrentlyPressed = opmode.gamepad2.dpad_right //can change controls

        if (!((raiserDownButtonCurrentlyPressed && raiserUpButtonCurrentlyPressed) || (raiserDownButtonCurrentlyPressed && raiserHangButtonCurrentlyPressed) || (raiserUpButtonCurrentlyPressed && raiserHangButtonCurrentlyPressed))) {
            if ((raiserDownButtonCurrentlyPressed && !downButtonPreviouslyPressed) && MainLift.lift.currentPosition/ MainLift.encoderTicks <= MainLift.maxLowPos) { //make so it cannot be lowered beyond the limit of the size constraints
                targPos = downPos
            }
            if (raiserUpButtonCurrentlyPressed && !upButtonPreviouslyPressed) {
                targPos = upPos
            }
            if (raiserHangButtonCurrentlyPressed && !hangButtonPreviouslyPressed) {
                targPos = hangPos
            }
        }

        if (!raiserResetPreviouslyPressed && raiserResetButtonCurrentlyPressed) {
            raiserReset()
        }

        checkManualRaiserUp()

        downButtonPreviouslyPressed = raiserDownButtonCurrentlyPressed
        upButtonPreviouslyPressed = raiserUpButtonCurrentlyPressed
        hangButtonPreviouslyPressed = raiserHangButtonCurrentlyPressed
        raiserResetPreviouslyPressed = raiserResetButtonCurrentlyPressed
        raiserManualUpButtonPreviouslyPressed = raiserManualUpButtonCurrentlyPressed

        if (motor.currentPosition <= downPos + 30 && targPos == downPos) { //30 is margin of error
            motor.power = 0.0
        } else {
            motor.power = 0.7
        }
        motor.targetPosition = (targPos)
        opmode.telemetry.addData("Main Lift current", motor.getCurrent(CurrentUnit.AMPS))
        opmode.telemetry.addData("Raiser Position", targPos) //change to enum
    }

    private fun raiserReset(){
        motor.mode = encoderMode
        motor.mode = motorMode
        motor.power = 0.7
    }

    private fun checkManualRaiserUp(){
        if (raiserManualUpButtonCurrentlyPressed && !raiserManualUpButtonPreviouslyPressed){
            targPos = -downPos
            manual = true
        }
        if (manual && !raiserManualUpButtonCurrentlyPressed) {
            targPos = motor.currentPosition - 20 //offset
            manual = false
        }
    }

    class autoRaiserUp: Action {
        override fun run(p: TelemetryPacket): Boolean {
            motor.targetPosition = upPos
            motor.power = 0.7
            return kotlin.math.abs(motor.currentPosition - motor.targetPosition) > 50 //50 is offset
        }
    }
    class autoRaiserDown: Action {
        override fun run(p: TelemetryPacket): Boolean {
            motor.targetPosition = downPos
            motor.power = 0.7
            return false
        }
    }
    class autoRaiserReset: Action {
        override fun run(p: TelemetryPacket): Boolean {
            motor.targetPosition = upPos
            motor.power = 0.2
//            while(motor.currentPosition <= upPos - 30){ //30 is margin of error
//                sleep(30000)
//            }
            return false
        }
    }
}