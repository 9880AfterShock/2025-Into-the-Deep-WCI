package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@Autonomous(name="Scuffed LM1 Auto")
public class ScuffedAuto extends LinearOpMode {
    private DcMotor frontLeftDrive   = null;
    private DcMotor frontRightDrive  = null;
    private DcMotor backLeftDrive   = null;
    private DcMotor backRightDrive  = null;
    Servo swivel;
    private DcMotor specimenLift = null;
    static final double FORWARD_SPEED = 0.6;
    static final double STOP_SPEED = 0.0;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        frontLeftDrive  = hardwareMap.get(DcMotor.class, "leftFront");
        frontRightDrive = hardwareMap.get(DcMotor.class, "rightFront");
        backLeftDrive  = hardwareMap.get(DcMotor.class, "leftRear");
        backRightDrive = hardwareMap.get(DcMotor.class, "rightRear");
        swivel = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "Specimen Swivel");
        specimenLift = hardwareMap.get(DcMotor.class, "specimenLift");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();

        runtime.reset();

        swivel.setPosition(1.0);

        frontLeftDrive.setPower(FORWARD_SPEED);
        frontRightDrive.setPower(-FORWARD_SPEED);
        backLeftDrive.setPower(-FORWARD_SPEED);
        backRightDrive.setPower(FORWARD_SPEED);

        while (opModeIsActive() && (runtime.seconds() < 0.6)) {
            sleep(10);
        }

        frontLeftDrive.setPower(STOP_SPEED);
        frontRightDrive.setPower(STOP_SPEED);
        backLeftDrive.setPower(STOP_SPEED);
        backRightDrive.setPower(STOP_SPEED);

        while (opModeIsActive()) {
            sleep(1);
        }
    }
}
