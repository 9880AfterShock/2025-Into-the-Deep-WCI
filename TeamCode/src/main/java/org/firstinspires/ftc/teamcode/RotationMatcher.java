package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class RotationMatcher {
    private final DcMotor leftRear; //init motor vars
    private final DcMotor leftFront;
    private final DcMotor rightRear;
    private final DcMotor rightFront;
    private final PID pid;
    public RotationMatcher(double P, double I, double D, OpMode opMode) {
        pid = new PID(P,I,D);
        leftRear = opMode.hardwareMap.get(DcMotor.class, "leftRear"); //motor config names
        leftFront = opMode.hardwareMap.get(DcMotor.class,"leftFront");
        rightRear = opMode.hardwareMap.get(DcMotor.class, "rightRear");
        rightFront = opMode.hardwareMap.get(DcMotor.class, "rightFront");
        this.leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void step(double value,double desvalue) {
        double neardesval = desvalue;
        if (Math.abs(neardesval-value) > Math.abs((desvalue+Math.toRadians(360))-value)) {
            neardesval = desvalue+Math.toRadians(360);
        } else if (Math.abs(neardesval-value) > Math.abs((desvalue-Math.toRadians(360))-value)) {
            neardesval = desvalue-Math.toRadians(360);
        }
        double speed = pid.step(value,neardesval);
        this.leftFront.setPower(speed);
        this.rightFront.setPower(speed);
        this.leftRear.setPower(speed);
        this.rightRear.setPower(speed);
    }
}
