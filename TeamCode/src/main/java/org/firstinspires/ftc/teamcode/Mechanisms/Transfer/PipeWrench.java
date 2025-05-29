package Mechanisms.Transfer;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.calib3d.StereoMatcher;

public class PipeWrench {
    public static Servo transfer;
    private static Servo transferClaw;
    public static double openPos = 0.7; //for transfer claw
    public static double closePos = 0.925; //for transfer claw
    public static double outPos = 0.05; //for transfer
    public static double inPos = 0.15; //for transfer
    public static double backPos = 0.5; //for transfer
    private static String clawState = "Closed";
    private static String state = "In";
    private static boolean transferButtonCurrentlyPressed = false;
    private static boolean transferButtonPreviouslyPressed = false;
    private static boolean transferClawButtonCurrentlyPressed = false;
    private static boolean transferClawButtonPreviouslyPressed = false;

    private static OpMode opmode;

    public static void initTransfer(OpMode opmode) {
        transfer = opmode.hardwareMap.get(Servo.class, "transfer"); // config name
        transferClaw = opmode.hardwareMap.get(Servo.class, "transferClaw"); // config name
        PipeWrench.opmode = opmode;
        clawState = "Closed";
        state = "In";
    }

    public static void open() {
        transferClaw.setPosition(openPos);
        clawState = "Open";
    }

    public static void close() {
        transferClaw.setPosition(closePos);
        clawState = "Close";
    }

    public static void in() {
        transfer.setPosition(inPos);
        state = "In";
    }

    public static void out() {
        transfer.setPosition(outPos);
        state = "Out";
    }

    public static void back() {
        transfer.setPosition(backPos);
        state = "Back";
    }

    public static void transferIncrement() {
        if (state == "In") {
            back();
        } else {
            if (state == "Back") {
                out();
            } else {
                in();
            }
        }
    }

    public static void clawSwap() {
        if (clawState.equals("Open")) {
            close();
        } else {
            open();
        }
    }

    public static void updateTransfer() {
        //disabled, now controled in basket lift
        //transferButtonCurrentlyPressed = opmode.gamepad1.y; // change this to change the button
        transferClawButtonCurrentlyPressed = opmode.gamepad1.x; // change this to change the button

        if (transferClawButtonCurrentlyPressed && !transferClawButtonPreviouslyPressed) {
            clawSwap();
        }

//        if (transferButtonCurrentlyPressed && !transferButtonPreviouslyPressed) {
//            transferIncrement();
//        }

        transferButtonPreviouslyPressed = transferButtonCurrentlyPressed;
        transferClawButtonPreviouslyPressed = transferClawButtonCurrentlyPressed;

        opmode.telemetry.addData("Transfer State", state);
        opmode.telemetry.addData("Transfer Claw State", clawState);
    }
}