package org.firstinspires.ftc.teamcode.primary

import android.os.Build
import androidx.annotation.RequiresApi
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Claw
import org.firstinspires.ftc.teamcode.MainLift
import org.firstinspires.ftc.teamcode.MecanumDriveTrain
import org.firstinspires.ftc.teamcode.Raiser
import org.firstinspires.ftc.teamcode.SpecimenClaw
import org.firstinspires.ftc.teamcode.SpecimenLift
import org.firstinspires.ftc.teamcode.SpecimenSwivel
import org.firstinspires.ftc.teamcode.Wrist
import org.firstinspires.ftc.teamcode.SampleColorSensor

@TeleOp(name = "9880 TeleOpMode Into-the-Deep After Autonomous") //change string for display name
//Toggle Disabled to make appear in list or not.
//@Disabled
class TeleOpModeAfterAuto : LinearOpMode() {
    private val runtime = ElapsedTime()
    //Make Motor Vars

    @RequiresApi(Build.VERSION_CODES.O)
    override fun runOpMode() {
        //Add init msg
        telemetry.addData("Status", "Initialized")
        telemetry.update()

        //Call Init Functions (make sure to add "this")
        MecanumDriveTrain.initDrive(this)
        Claw.initClaw(this)
        MainLift.initLiftAfterAuto(this)
        Raiser.initRaiserAfterAuto(this)
        Wrist.initWristAfterAuto(this)
        SampleColorSensor.initColorSensor(this, 2.0F)
        SpecimenLift.initLiftAfterAuto(this)
        SpecimenClaw.initClaw(this)
        SpecimenSwivel.initSwivel(this) //added because changes
        // init commands here

        //Wait for start
        waitForStart()
        SpecimenSwivel.moveOut()
        runtime.reset()

        //Running Loop
        while (opModeIsActive()) {
            //Tick Commands Here
            MecanumDriveTrain.updateDrive()
            Claw.updateClaw()
            MainLift.updateLift()
            Raiser.updateRaiser()
            Wrist.updateWrist()
            SampleColorSensor.updateColorSensor()
            SpecimenLift.updateLift()
            SpecimenClaw.updateClaw()

            // Show the elapsed time (and other telemetry) on driver station
            telemetry.addData("Status", "Run Time: $runtime")
            telemetry.update()
        }
    }
}