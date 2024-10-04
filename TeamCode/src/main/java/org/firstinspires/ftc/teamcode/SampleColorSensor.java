package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

public class SampleColorSensor {
    private static NormalizedColorSensor colorSensor;
    private static NormalizedRGBA color;
    public static void initColorSensor(OpMode opmode, float gain) {
        colorSensor = opmode.hardwareMap.get(NormalizedColorSensor.class, "SampleColorSensor");
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }
        colorSensor.setGain(gain);
    }
    public static void updateColorSensor() {
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }
        color = colorSensor.getNormalizedColors();
    }
    public static NormalizedRGBA getColor() {
        return color;
    }
    public static void setGain(float gain) {
        colorSensor.setGain(gain);
    }
    public static float getGain() {
        return colorSensor.getGain();
    }
}
