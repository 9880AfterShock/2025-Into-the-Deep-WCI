package org.firstinspires.ftc.teamcode

//import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.NormalizedRGBA
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.subsystems.MainLift


@TeleOp(name = "9880 TeleOpMode Into-the-Deep") //change string for display name
//Toggle Disabled to make appear in list or not.
//@Disabled
class TeleOpMode : LinearOpMode() {
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
        MainLift.initLift(this)
        Raiser.initRaiser(this)
        Wrist.initWrist(this)
        SampleColorSensor.initColorSensor(this, 3.0F, "SampleColorSensor")
        SpecimenLift.initLift(this)
        SpecimenClaw.initClaw(this)
        SpecimenSwivel.initSwivel(this) //added because changes
        SampleTypeSensor.initTypeSensor(0.1F,0.6F,0.3F,0.1F)
        //Hang.initHang(this)
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
            //SpecimenSwivel.updateSwivel() //not needed rn
            //Hang.checkHang()
          val color: SampleType = SampleTypeSensor.getSampleType()
            val colorval: NormalizedRGBA = SampleColorSensor.getColor()
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
            telemetry.addData("Hue:", TeamColor.fromRGB(colorval.red, colorval.green, colorval.blue).toHSV().hue)
            telemetry.addData("Status", "Run Time: $runtime")
            telemetry.addData("TargetedSample:", colorstr)
            telemetry.update()
        }
    }
}