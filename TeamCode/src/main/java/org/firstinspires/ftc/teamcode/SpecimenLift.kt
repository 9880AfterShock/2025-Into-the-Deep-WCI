package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor


object SpecimenLift { //Prefix for commands
    lateinit var lift: DcMotor //Init Motor Var
    var pos = 0.0 //starting Position
    val encoderTicks = -537.7 //calculate your own ratio //negative to invert values
    @JvmField
    var minPos = 0.0 //all the way down
    @JvmField
    var maxPos = 3.5 //GOOD and working
    var minDrop = 2.4 //lower drop pos in rotations
    var maxDrop = 2.5 // higher drop pos in rotations
    lateinit var opmode: OpMode //opmode var innit
    private var downButtonCurrentlyPressed = false
    private var downButtonPreviouslyPressed = false
    private var upButtonCurrentlyPressed = false
    private var upButtonPreviouslyPressed = false
    var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION //set motor mode
    fun initLift(opmode: OpMode){ //init motors
        pos = 0.0
        lift = opmode.hardwareMap.get(DcMotor::class.java, "specimenLift") //config name
        lift.targetPosition = (pos*encoderTicks).toInt()
        lift.mode = encoderMode //reset encoder
        lift.mode = motorMode //enable motor mode
        this.opmode = opmode
    }
    fun updateLift(){

        //can change controls
        downButtonCurrentlyPressed = opmode.gamepad2.left_bumper
        upButtonCurrentlyPressed = opmode.gamepad2.right_bumper

        if (!(downButtonCurrentlyPressed && upButtonCurrentlyPressed)) {
            if ((downButtonCurrentlyPressed && !downButtonPreviouslyPressed)) {
                pos = minPos
            }
            if (upButtonCurrentlyPressed && !upButtonPreviouslyPressed) {
                pos = maxPos
            }
        }

        checkDrop()

        downButtonPreviouslyPressed = downButtonCurrentlyPressed
        upButtonPreviouslyPressed = upButtonCurrentlyPressed


        //pos += currentSpeed
        lift.power = 0.7 //turn motor on
        lift.targetPosition = (pos*encoderTicks).toInt()
        opmode.telemetry.addData("Specimen Lift target position", pos) //Set telemetry
    }

    private fun checkDrop() {
        if ((maxDrop > lift.currentPosition/encoderTicks) && (lift.currentPosition/encoderTicks > minDrop) && (lift.targetPosition/encoderTicks == minPos)){
            SpecimenClaw.open()
        }
    }

}