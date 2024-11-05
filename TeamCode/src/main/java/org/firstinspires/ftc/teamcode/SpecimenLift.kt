package org.firstinspires.ftc.teamcode


import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.subsystems.MainLift
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
        opmode.telemetry.addData("lift innited", 1.2)
        opmode.telemetry.update()
    }
    fun updateLift(){

        //can change controls
        upButtonCurrentlyPressed = opmode.gamepad2.right_bumper
        downButtonCurrentlyPressed = opmode.gamepad2.left_bumper
        upManualButton = false //not needed rn
        downManualButton = opmode.gamepad2.dpad_left
        zeroButtonCurrentlyPressed = opmode.gamepad2.dpad_right

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

    class autoSpecimenLiftUp: Action{
        override fun run(p: TelemetryPacket): Boolean {
            lift.targetPosition = (maxPos*encoderTicks).toInt()
            lift.power = 1.0
//            p.addLine("Lift targ pos "+lift.targetPosition)
//            if (abs(lift.targetPosition - lift.currentPosition) < 50) {
//                return false
//            }
            return true
        }
    }
    class autoSpecimenLiftDown: Action{
        override fun run(p: TelemetryPacket): Boolean {
            lift.targetPosition = (minPos*encoderTicks).toInt()
            lift.power = 1.0
            if (abs(lift.targetPosition - lift.currentPosition) < 50) {
                lift.power = 0.0
                return false
            }
            return true
        }

    }
    //
    class autoSpecLiftUp: Action {
        override fun run(p: TelemetryPacket): Boolean {
            LiftRun.currTargetInTicks = SpecimenLift.maxPos.toInt() * MainLift.encoderTicks.toInt()

            return false
        }
    }
    class autoSpecLiftScore: Action {
        override fun run(p: TelemetryPacket): Boolean {
            LiftRun.currTargetInTicks = SpecimenLift.minPos.toInt() / encoderTicks.toInt()
            if ((maxDrop > lift.currentPosition/encoderTicks) && (lift.currentPosition/encoderTicks > minDrop) && (lift.targetPosition/encoderTicks == minPos)) {
                SpecimenClaw.open()
            }

            return false
        }
    }

    class autoSpecLiftDown: Action {
        override fun run(p: TelemetryPacket): Boolean {
            LiftRun.currTargetInTicks = SpecimenLift.minPos.toInt() * MainLift.encoderTicks.toInt()
            SpecimenLift.opmode.telemetry.addData("spec lift down", 1)
            SpecimenLift.opmode.telemetry.update()
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
                SpecimenLift.lift.targetPosition = MainLift.minPos.toInt() * MainLift.encoderTicks.toInt()
                SpecimenLift.lift.power = 1.0
                SpecimenLift.lift.mode = DcMotor.RunMode.RUN_TO_POSITION
                opmode.telemetry.addData("lift inited", 1)
                opmode.telemetry.update()
            }

            lift.targetPosition = currTargetInTicks
            lift.power = 1.0
            lift.mode = DcMotor.RunMode.RUN_TO_POSITION

            return false// originally true, maybe set false?
        }

    }

}