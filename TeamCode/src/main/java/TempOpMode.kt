package org.firstinspires.ftc.teamcode.primary

//import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Claw
import org.firstinspires.ftc.teamcode.MainLift
import org.firstinspires.ftc.teamcode.MecanumDriveTrain
import org.firstinspires.ftc.teamcode.Raiser
import org.firstinspires.ftc.teamcode.SpecimenClaw
import org.firstinspires.ftc.teamcode.SpecimenLift
import org.firstinspires.ftc.teamcode.SpecimenSwivel
import org.firstinspires.ftc.teamcode.Wrist


//import org.firstinspires.ftc.teamcode.SampleType
//import org.firstinspires.ftc.teamcode.SampleTypeSensor

@TeleOp(name = "testing the wrist thin") //change string for display name
//Toggle Disabled to make appear in list or not.
//@Disabled
class TempOpMode : LinearOpMode() {
    private val runtime = ElapsedTime()
    private var test = 0
    private lateinit var wrist: DcMotor
    //Make Motor Vars

    @RequiresApi(Build.VERSION_CODES.O)
    override fun runOpMode() {
        //Add init msg
        telemetry.addData("Status", "Initialized")
        telemetry.update()

        //Call Init Functions (make sure to add "this")
        wrist = hardwareMap.get(DcMotor::class.java, "wrist") //config name
        //Wait for start

        var encoderMode: DcMotor.RunMode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        var motorMode: DcMotor.RunMode = DcMotor.RunMode.RUN_TO_POSITION


        waitForStart()
        runtime.reset()

        wrist.mode = encoderMode
        wrist.mode = motorMode
        //Running Loop
        while (opModeIsActive()) {
            //Tick Commands Here

            wrist.targetPosition = gamepad1.left_stick_y.toInt()*20

            wrist.power = 0.5

            // Show the elapsed time (and other telemetry) on driver station
            telemetry.addData("Status", "Run Time: $runtime")
            //telemetry.addData("TargetedSample:", colorstr)
            telemetry.update()
        }
    }
}