package org.firstinspires.ftc.teamcode.primary

import android.graphics.Color
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
import org.firstinspires.ftc.teamcode.SampleType
import org.firstinspires.ftc.teamcode.SampleTypeSensor

@TeleOp(name = "9880 TeleOpMode Into-the-Deep") //change string for display name
//Toggle Disabled to make appear in list or not.
//@Disabled
class TeleOpMode : LinearOpMode() {
    private val runtime = ElapsedTime()
    private var test = 0
    //Make Motor Vars

    @RequiresApi(Build.VERSION_CODES.O)
    override fun runOpMode() {
        //Add init msg
        telemetry.addData("Status", "Initialized")
        telemetry.update()

        //Call Init Functions (make sure to add "this")
        MecanumDriveTrain.initDrive(this)
        Claw.initClaw(this)
        MainLift.initLift(this)
        Raiser.initRaiser(this)
        Wrist.initWrist(this)
        SampleColorSensor.initColorSensor(this, 2.0F)
        SpecimenLift.initLift(this)
        SpecimenClaw.initClaw(this)
        SpecimenSwivel.initSwivel(this) //added because changes
        SampleTypeSensor.initTypeSensor(Color.valueOf(1F,0F,0F),Color.valueOf(0F, 0F, 1F),Color.valueOf(1F,1F,0F), 0.1F)
        // init commands here

        //Wait for start
        waitForStart()
        SpecimenSwivel.moveOut()
        runtime.reset()

        //Running Loop
        while (opModeIsActive()) {
            //Tick Commands Here
            MecanumDriveTrain.updateDrive() //remove for testing
            Claw.updateClaw()
            MainLift.updateLift()
            Raiser.updateRaiser()
            Wrist.updateWrist()
            SampleColorSensor.updateColorSensor()
            SpecimenLift.updateLift()
            SpecimenClaw.updateClaw()
            //SpecimenSwivel.updateSwivel() //not needed rn
            val color: SampleType = SampleTypeSensor.getSampleType()
            var colorstr = ""
            if (color == SampleType.NONE) {
                colorstr = "Unknown"
            } else if (color == SampleType.FRIENDLY) {
                colorstr = "Alliance"
            } else if (color == SampleType.NEUTRAL) {
                colorstr = "Neutral"
            } else if (color == SampleType.OPPONENT) {
                colorstr = "Opposition"
            }

            // Show the elapsed time (and other telemetry) on driver station
            telemetry.addData("Status", "Run Time: $runtime")
            telemetry.addData("TargetedSample:", colorstr)
            telemetry.update()
        }
    }
}