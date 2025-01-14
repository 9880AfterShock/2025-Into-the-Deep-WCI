package org.firstinspires.ftc.teamcode;
import static java.lang.Math.round;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name="Scuffed LM1 Auto")
public class ScuffedAuto extends LinearOpMode {
    private DcMotor frontLeftDrive   = null;
    private DcMotor frontRightDrive  = null;
    private DcMotor backLeftDrive   = null;
    private DcMotor backRightDrive  = null;
    Servo swivel;
    private DcMotor specimenLift = null;
    Servo specimenClaw;
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
        specimenClaw = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "Specimen Claw");
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        specimenLift.setTargetPosition(0);
        specimenLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();
        runtime.reset();
        swivel.setPosition(1.0);
        specimenClaw.setPosition(1.0);
        sleep(300);
        specimenLift.setTargetPosition((int)(3.5*-537.7));
        specimenLift.setPower(1.0);
        sleep(500);
        frontLeftDrive.setPower(FORWARD_SPEED);
        frontRightDrive.setPower(-FORWARD_SPEED);
        backLeftDrive.setPower(-FORWARD_SPEED);
        backRightDrive.setPower(FORWARD_SPEED);
        while (opModeIsActive() && (runtime.seconds() < 2.035)) {
            sleep(10);
        }
// tpp
        frontLeftDrive.setPower(STOP_SPEED);
        frontRightDrive.setPower(STOP_SPEED);
        backLeftDrive.setPower(STOP_SPEED);
        backRightDrive.setPower(STOP_SPEED);
        while (opModeIsActive() && specimenLift.getCurrentPosition() > (int)3.4*-537.7) {
            sleep(10);
        }
        specimenLift.setTargetPosition(0);
        while (opModeIsActive() && specimenLift.getCurrentPosition() < (int)(2.9*-537.7)) {
            sleep(10);
        }
        specimenClaw.setPosition(0.7);
        frontLeftDrive.setPower(-FORWARD_SPEED);
        frontRightDrive.setPower(FORWARD_SPEED);
        backLeftDrive.setPower(FORWARD_SPEED);
        backRightDrive.setPower(-FORWARD_SPEED);
        while (opModeIsActive() && (runtime.seconds() < 3.4)) {
            sleep(10);
        }
        frontLeftDrive.setPower(-FORWARD_SPEED);
        frontRightDrive.setPower(-FORWARD_SPEED);
        backLeftDrive.setPower(-FORWARD_SPEED);
        backRightDrive.setPower(-FORWARD_SPEED);
        while (opModeIsActive() && (runtime.seconds() < 4.6)) {
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