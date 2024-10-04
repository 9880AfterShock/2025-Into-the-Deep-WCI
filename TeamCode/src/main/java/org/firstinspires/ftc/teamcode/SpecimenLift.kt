package org.firstinspires.ftc.teamcode


import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor


object SpecimenLift { //Prefix for commands
    lateinit var lift: DcMotor //Init Motor Var
    var pos = 0.0 //starting Position
    var currentSpeed = 0.0 //Starting speed, WHY ARE YOU MAKING A FALLING LIFT???
    @JvmField
    var speed = 0.03 //update speed
    val encoderTicks = -537.7 //calculate your own ratio //negative to invert values
    @JvmField
    var minPos = 0.0 //folded all the way in
    @JvmField
    var maxPos = 3.5 //all the way out at 45° angle
    lateinit var opmode: OpMode //opmode var innit
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
        if (opmode.gamepad2.right_bumper && !opmode.gamepad2.left_bumper) {
            currentSpeed = speed
        }
        else
            if (opmode.gamepad2.left_bumper && !opmode.gamepad2.right_bumper) {
                currentSpeed = -speed
            } else {
                currentSpeed = 0.0
            }

        pos += currentSpeed

        if (pos>maxPos) {
            pos = maxPos
        }
        if (pos<minPos) {
            pos = minPos
        }

        lift.power = 1.0 //turn motor on
        lift.targetPosition = (pos*encoderTicks).toInt()
        opmode.telemetry.addData("Specimen Lift target position", pos) //Set telemetry
    }

}