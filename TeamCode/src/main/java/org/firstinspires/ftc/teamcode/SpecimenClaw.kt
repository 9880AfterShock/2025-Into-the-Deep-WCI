package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.Servo
import java.lang.Thread.sleep


object SpecimenClaw {
    private lateinit var claw: Servo
    @JvmField
    var openPos = 0.7 //the positions
    @JvmField
    var closePos = 1.0 //the positions
    var state = "Closed"
    private var clawButtonCurrentlyPressed = false
    private var clawButtonPreviouslyPressed = false

    lateinit var opmode:OpMode
    fun initClaw(opmode: OpMode){
        claw = opmode.hardwareMap.get(Servo::class.java, "Specimen Claw") //config name
        this.opmode = opmode
        state = "Closed"
    }
    fun open() {
        claw.position = openPos
        state = "Open"
    }
     fun close(){
        claw.position = closePos
        state = "Closed"
    }
    private fun swap(){
        if (state == "Open") {
            close()
        } else {
            open()
        }
    }
    fun updateClaw() {
        opmode.telemetry.addData("Specimen Claw State", state)
        // Check the status of the claw button on the gamepad
        clawButtonCurrentlyPressed = opmode.gamepad1.b //change this to change the button

        // If the button state is different than what it was, then act
        if (clawButtonCurrentlyPressed != clawButtonPreviouslyPressed) {
            // If the button is (now) down
            if (clawButtonCurrentlyPressed) {
                swap()
            }
        }
        clawButtonPreviouslyPressed = clawButtonCurrentlyPressed

    }
    class autoSpecClawSwap: Action {
        override fun run(p: TelemetryPacket): Boolean {
            //LiftRun.currTargetInTicks = maxPos.toInt() * encoderTicks.toInt()
            swap()
//            SpecimenClaw.opmode.telemetry.addData("claw swap", 1)
//            SpecimenClaw.opmode.telemetry.update()
            return true
        }
    }
    class autoSpecClawOpen: Action {
        override fun run(p: TelemetryPacket): Boolean {
            //LiftRun.currTargetInTicks = maxPos.toInt() * encoderTicks.toInt()
            open()
//            SpecimenClaw.opmode.telemetry.addData("claw swap", 1)
//            SpecimenClaw.opmode.telemetry.update()
            return true
        }
    }
    class autoSpecClawClose: Action {
        override fun run(p: TelemetryPacket): Boolean {
            close()
            p.put("claw done", 1.0)
            return false
        }
    }
    class autoDelaySpecClawClose: Action {
        override fun run(p: TelemetryPacket): Boolean {
            sleep(350)//350
            close()
            p.put("claw done", 1.0)
            return false
        }
    }
    class autoDelaySpecClawCloseSecond: Action {
        override fun run(p: TelemetryPacket): Boolean {
            sleep(550)//350
            close()
            p.put("claw done", 1.0)
            return false
        }
    }
}