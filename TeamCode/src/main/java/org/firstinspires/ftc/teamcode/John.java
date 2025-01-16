package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous()
@Disabled()
public class John extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(9.0,-63.0,Math.toRadians(90)));
        Action trajectoryAction1;
        trajectoryAction1  = drive.actionBuilder(drive.pose)
                .lineToY(0)
                //.turn(Math.toRadians(0))
                //.splineTo(new Vector2d(24, 12), Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(30, 24))
                .waitSeconds(2)
                .lineToYConstantHeading(48)
                .build();

        waitForStart();
        if (isStopRequested()) return;
        Action trajectoryActionChosen;
        trajectoryActionChosen= trajectoryAction1;
        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen
                        // .trajectoryActionCloseOut
                )
        );

    }
}