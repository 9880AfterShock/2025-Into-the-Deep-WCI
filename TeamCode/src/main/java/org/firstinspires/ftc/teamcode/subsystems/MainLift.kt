package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.Raiser
import java.lang.Thread.sleep

object MainLift { //Prefix for commands
    lateinit var lift: DcMotorEx //Init Motor Var
    @JvmField
    var pos = 0.0 //starting Position
    var currentSpeed = 0.0 //Starting speed, WHY ARE YOU MAKING A FALLING LIFT???
    @JvmField
    var speed = 0.05 //update speed
    val encoderTicks = 751.8 //calculate your own ratio///// old us 537.7
    @JvmField
    var minPos = 0.0 //folded all the way in
    @JvmField
    var pickUpPos = doubleArrayOf(0.7, 1.0) //poses for pickup during auto
    @JvmField
    var maxPos = 7.0 //all the way up
    @JvmField
    var maxLowPos = 3.5 //maximum position when lowered
    @JvmField
    var maxHangPos = 3.5 //maximum position when in hanging mode //temp values, CHANGE!
    lateinit var opmode: OpMode //opmode var innit
    var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION //set motor mode
    fun initLift(opmode: OpMode){ //init motors
        pos = 0.0
        lift = opmode.hardwareMap.get(DcMotorEx::class.java, "mainLift") //config name
        lift.targetPosition = (pos*encoderTicks).toInt()
        lift.mode = encoderMode //reset encoder
        lift.mode = motorMode //enable motor mode
        this.opmode = opmode
    }
    fun initLiftAfterAuto(opmode: OpMode){ //init motors
        lift = opmode.hardwareMap.get(DcMotorEx::class.java, "mainLift") //config name
        lift.mode = motorMode //enable motor mode
        this.opmode = opmode
    }

    fun updateLift(){
//can change controls
        if (opmode.gamepad2.dpad_up && !opmode.gamepad2.dpad_down) {
            currentSpeed = speed
        }
        else
            if (opmode.gamepad2.dpad_down && !opmode.gamepad2.dpad_up) {
                currentSpeed = -speed
            } else {
                currentSpeed = 0.0
            }

        pos += currentSpeed


        if (opmode.gamepad2.left_trigger.toDouble() > 0.5) {
            pos = minPos
        } else {
            if (opmode.gamepad2.right_trigger.toDouble() > 0.5) {
                pos = maxPos
            }
        }


        if (pos>maxPos) {
            pos = maxPos
        }
        if (pos>maxLowPos && Raiser.targPos == Raiser.downPos) {
            pos = maxLowPos
        }
        if (pos>maxHangPos && Raiser.targPos == Raiser.hangPos) {
            pos = maxHangPos
        }
        if (pos<minPos) {
            pos = minPos
        }

        lift.power = 1.0 //turn motor on
        lift.targetPosition = (pos*encoderTicks).toInt()
        opmode.telemetry.addData("Main Lift current", lift.getCurrent(CurrentUnit.AMPS))
        opmode.telemetry.addData("Main Lift target position", pos) //Set telemetry
    }




    class autoLiftMax: Action {
        override fun run(p: TelemetryPacket): Boolean {
            lift.targetPosition = (encoderTicks*maxPos).toInt()
            lift.power = 1.0
            return kotlin.math.abs(lift.currentPosition - lift.targetPosition) > 50 //50 is offset
        }
    }
    class autoLiftMin: Action {
        override fun run(p: TelemetryPacket): Boolean {
            lift.targetPosition = (encoderTicks*minPos).toInt()
            lift.power = 1.0
            return kotlin.math.abs(lift.currentPosition - lift.targetPosition) > 50 //50 is offset
        }
    }
    class autoLiftPickup(var spikePos: Int): Action {
        override fun run(p: TelemetryPacket): Boolean {
            lift.targetPosition = (encoderTicks* pickUpPos[spikePos-1]).toInt()
            lift.power = 1.0
            return kotlin.math.abs(lift.currentPosition - lift.targetPosition) > 50 //50 is offset
        }
    }
    class autoLiftMaxLow: Action {
        override fun run(p: TelemetryPacket): Boolean {
            lift.targetPosition = (encoderTicks*maxLowPos).toInt()
            lift.power = 1.0
            return kotlin.math.abs(lift.currentPosition - lift.targetPosition) > 50 //50 is offset
        }
    }





    // all below are oscar's idk what they do lol
    class LiftUp: Action {
        override fun run(p: TelemetryPacket): Boolean {
            LiftRun.currTargetInTicks = maxPos.toInt() * encoderTicks.toInt()

            return false
        }
    }

    class LiftDown: Action {
        override fun run(p: TelemetryPacket): Boolean {
            LiftRun.currTargetInTicks = minPos.toInt() * encoderTicks.toInt()

            return false
        }
    }

    class LiftRun : Action {
        var initialized: Boolean = false

        companion object {
            var currTargetInTicks = 0;
        }

        override fun run(p: TelemetryPacket): Boolean {

            if (!initialized) {
                lift.targetPosition = minPos.toInt() * encoderTicks.toInt()
                lift.power = 1.0
                lift.mode = DcMotor.RunMode.RUN_TO_POSITION
            }

            lift.targetPosition = currTargetInTicks
            lift.power = 1.0
            lift.mode = DcMotor.RunMode.RUN_TO_POSITION


            return true
        }

    }

}