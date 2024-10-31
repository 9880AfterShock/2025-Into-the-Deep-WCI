package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DistanceMatcher {
    private DcMotor leftRear; //init motor vars
    private DcMotor leftFront;
    private DcMotor rightRear;
    private DcMotor rightFront;
    private PID pid;
    public DistanceMatcher(double P, double I, double D, OpMode opMode) {
        pid = new PID(P,I,D);
        leftRear = opMode.hardwareMap.get(DcMotor.class, "leftRear"); //motor config names
        leftFront = opMode.hardwareMap.get(DcMotor.class,"leftFront");
        rightRear = opMode.hardwareMap.get(DcMotor.class, "rightRear");
        rightFront = opMode.hardwareMap.get(DcMotor.class, "rightFront");
        this.leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftRear.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    public void step(double value,double desvalue) {
        double speed = pid.step(value,desvalue);
        this.leftFront.setPower(speed);
        this.rightFront.setPower(speed);
        this.leftRear.setPower(speed);
        this.rightRear.setPower(speed);
    }
}
