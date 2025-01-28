package org.firstinspires.ftc.teamcode

//import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.NormalizedRGBA
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.subsystems.MainLift


@TeleOp(name = "vison testing") //change string for display name
//Toggle Disabled to make appear in list or not.
//@Disabled
class VisionOpmode : LinearOpMode() {
    private val runtime = ElapsedTime()
    //Make Motor Vars

    override fun runOpMode() {
        //Add init msg
        telemetry.addData("Status", "Initialized")
        telemetry.update()

        Vision.initVision(this)
        Swivel.initSwivel(this)
        // init commands here

        //Wait for st
        //Running Loop
        while (opModeIsActive() || opModeInInit()) {
            //Tick Commands Here
            Vision.updateVision()
            Swivel.updateSwivel()
        }
        Vision.stopVision()

    }
}