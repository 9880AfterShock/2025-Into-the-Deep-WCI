package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous(name="Skrimage Auto")
public class TempAuto extends LinearOpMode {
    private DcMotor leftDrive   = null;
    private DcMotor rightDrive  = null;
    static final double FORWARD_SPEED = 0.6;

    @Override
    public void runOpMode() {

        frontLeftDrive  = hardwareMap.get(DcMotor.class, "leftFront");
        frontRightDrive = hardwareMap.get(DcMotor.class, "rightFront");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "rightRear");
        backRightDrive = hardwareMap.get(DcMotor.class, "rightRear");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        // JUST STOP THE ROBOT CONTROLLER WHEN ITS DONE
      
        leftDrive.setPower(FORWARD_SPEED);
        rightDrive.setPower(FORWARD_SPEED);
    }
}
