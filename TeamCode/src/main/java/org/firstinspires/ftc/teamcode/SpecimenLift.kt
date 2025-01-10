package org.firstinspires.ftc.teamcode


import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.subsystems.MainLift
import java.lang.Thread.sleep
import kotlin.math.abs


object SpecimenLift { //Prefix for commands
    lateinit var lift: DcMotor //Init Motor Var
    var pos = 0.0 //starting Position
    var currentSpeed = 0.0 //Starting speed
    @JvmField
    var speed = 0.03 //update speed
    val encoderTicks = -537.7 //calculate your own ratio //negative to invert values
    @JvmField
    var minPos = 0.0 //folded all the way in
    @JvmField
    var maxPos = 3.5 //all the way out at 45° angle
    var minDrop = 2.7 //lower drop pos in rotations
    var maxDrop = 2.9 // higher drop pos in rotations
    lateinit var opmode: OpMode //opmode var innit
    var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION //set motor mode
    private var upButtonCurrentlyPressed = false
    private var upButtonPreviouslyPressed = false
    private var downButtonCurrentlyPressed = false
    private var downButtonPreviouslyPressed = false
    private var upManualButton = false
    private var downManualButton = false
    private var zeroButtonCurrentlyPressed = false
    private var zeroButtonPreviouslyPressed = false
    fun initLift(opmode: OpMode){ //init motors
        pos = 0.0
        lift = opmode.hardwareMap.get(DcMotor::class.java, "specimenLift") //config name
        lift.targetPosition = (pos*encoderTicks).toInt()
        lift.power = 1.0
        lift.mode = encoderMode //reset encoder
        lift.mode = motorMode //enable motor mode
        this.opmode = opmode
    }
    fun updateLift(){

        //can change controls
        upButtonCurrentlyPressed = opmode.gamepad2.right_bumper
        downButtonCurrentlyPressed = opmode.gamepad2.left_bumper
        upManualButton = false //not needed rn
        downManualButton = opmode.gamepad2.dpad_left
        zeroButtonCurrentlyPressed = opmode.gamepad2.left_stick_button

        if (downManualButton) { //manual
            currentSpeed = -speed
        }else {
            currentSpeed = 0.0
        }
        pos += currentSpeed

        if (!(downButtonCurrentlyPressed && upButtonCurrentlyPressed)) { //preset
            if ((downButtonCurrentlyPressed && !downButtonPreviouslyPressed)) {
                pos = minPos
            }
            if (upButtonCurrentlyPressed && !upButtonPreviouslyPressed && SpecimenClaw.state == "Closed") {
                pos = maxPos
            }
        }

        if ((zeroButtonCurrentlyPressed && !zeroButtonPreviouslyPressed)) { //stop and reset encoders
            lift.power = 0.0
            pos = 0.0
            lift.mode = encoderMode
            lift.mode = motorMode
        }

//        if (pos>maxPos) { //keep in limits
//            pos = maxPos
//        }
//        if (pos<minPos) {
//            pos = minPos
//        }

        checkDrop()

        upButtonPreviouslyPressed = upButtonCurrentlyPressed
        downButtonPreviouslyPressed = downButtonCurrentlyPressed
        zeroButtonPreviouslyPressed = zeroButtonCurrentlyPressed

        lift.power = 1.0 //turn motor on
        lift.targetPosition = (pos*encoderTicks).toInt()
        opmode.telemetry.addData("Specimen Lift target position", pos) //Set telemetry
    }
     fun checkDrop() {
        if ((maxDrop > lift.currentPosition/encoderTicks) && (lift.currentPosition/encoderTicks > minDrop) && (lift.targetPosition/encoderTicks == minPos)){
            SpecimenClaw.open()
        }
    }

    class autoSpecimenLiftUp(/*var waitTime: Long*/): Action{
        override fun run(p: TelemetryPacket): Boolean {
            lift.targetPosition = (maxPos*encoderTicks).toInt()
            lift.power = 1.0
            //sleep(waitTime)
            return false
        }
    }
    class autoSpecimenLiftDown(var waitTime: Long): Action{

        val timer: ElapsedTime = ElapsedTime()

        override fun run(p: TelemetryPacket): Boolean {
            lift.targetPosition = (minPos * encoderTicks).toInt()
            lift.power = 1.0
            while (true) {
                if ((maxDrop > lift.currentPosition / encoderTicks) && (lift.currentPosition / encoderTicks > minDrop) && (lift.targetPosition / encoderTicks == minPos)) {
                    SpecimenClaw.open()
                    sleep(waitTime)
                    return false
                }
            }
        }
    }
}