package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor

object Raiser { //Prefix for commands
    private lateinit var motor: DcMotor //Init Motor Var
    var targetDegrees = 0.0 //starting Position
    @JvmField
    val encoderTicks = 1425.1 //calculate your own ratio
    val gearRatio = 100/20
    val upPos = 55.0 //in degrees
    val hangPos = 45.0 //in degrees
    val downPos = 0.0 //in degrees
    var startOffset = 1150 //offset of the start pos from the level pos in encoder ticks
    private var downButtonCurrentlyPressed = false
    private var downButtonPreviouslyPressed = false
    private var upButtonCurrentlyPressed = false
    private var upButtonPreviouslyPressed = false
    private var hangButtonCurrentlyPressed = false
    private var hangButtonPreviouslyPressed = false
    lateinit var opmode: OpMode //opmode var innit
    var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
    var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION //set motor mode
    fun initRaiser(opmode: OpMode){ //init motors
        motor = opmode.hardwareMap.get(DcMotor::class.java, "raiser") //config name
        motor.targetPosition = (targetDegrees*encoderTicks*gearRatio/360 - startOffset).toInt()
        motor.mode = encoderMode //reset encoder
        motor.mode = motorMode //enable motor mode
        this.opmode = opmode
    }
    fun updateRaiser() {
        downButtonCurrentlyPressed = opmode.gamepad2.b //can change controls
        upButtonCurrentlyPressed = opmode.gamepad2.y //can change controls
        hangButtonCurrentlyPressed = opmode.gamepad2.x //can change controls

        // If the button state is different than what it was, then act
        if (!((downButtonCurrentlyPressed && upButtonCurrentlyPressed) || (downButtonCurrentlyPressed && hangButtonCurrentlyPressed) || (upButtonCurrentlyPressed && hangButtonCurrentlyPressed))) {
            if ((downButtonCurrentlyPressed && !downButtonPreviouslyPressed) && MainLift.lift.currentPosition/MainLift.encoderTicks <= MainLift.maxLowPos) { //make so it cannot be lowered beyond the limit of the size constraints
                targetDegrees = downPos
            }
            if ((hangButtonCurrentlyPressed && !hangButtonPreviouslyPressed) && MainLift.lift.currentPosition/MainLift.encoderTicks <= MainLift.maxHangPos) { //make so it cannot be lowered beyond the limit of the size constraints
                targetDegrees = hangPos
            }
            if (upButtonCurrentlyPressed && !upButtonPreviouslyPressed) {
                targetDegrees = upPos
                }
            }

        downButtonPreviouslyPressed = downButtonCurrentlyPressed
        upButtonPreviouslyPressed = upButtonCurrentlyPressed
        hangButtonPreviouslyPressed = hangButtonCurrentlyPressed

        if ((motor.currentPosition- startOffset)/encoderTicks/gearRatio*360<= 5.0 && targetDegrees == 0.0) { //5 is degrees margin of error
            motor.power = 0.0 //let motor rest
        } else {
            motor.power = 0.7 //turn motor on
        }

        motor.targetPosition = (targetDegrees*encoderTicks*gearRatio/360 - startOffset).toInt()
        opmode.telemetry.addData("Raiser target position", targetDegrees) //Set telemetry
        opmode.telemetry.addData("raiser power", motor.power)
    }
}