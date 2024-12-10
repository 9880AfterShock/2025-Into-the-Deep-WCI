package org.firstinspires.ftc.teamcode;

import static java.lang.Float.min;
import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class SampleTypeSensor {
    static float TeamHue;
    static float NeutralHue;
    static float EnemyHue;
    static float Tolerance;
    public static float colorDist(NormalizedRGBA a, TeamColor b) {
        TeamColor hsv2 = TeamColor.fromRGB(a.red,a.green,a.blue).toHSV();
        return min(abs(b.hue - hsv2.hue),abs(b.hue - (hsv2.hue+1F)));
    }
    public static void initTypeSensor(float teamHue, float enemyHue, float neutralHue, float tolerance) {
        TeamHue = teamHue;
        EnemyHue = enemyHue;
        NeutralHue = neutralHue;
        Tolerance = tolerance;
    }
    public static SampleType getSampleType() {
        NormalizedRGBA color = SampleColorSensor.getColor();
        float Neutral = colorDist(color, org.firstinspires.ftc.teamcode.TeamColor.fromHSV(NeutralHue, 0f, 0f));
        float Team = colorDist(color, org.firstinspires.ftc.teamcode.TeamColor.fromHSV(TeamHue, 0f, 0f));
        float Enemy = colorDist(color, org.firstinspires.ftc.teamcode.TeamColor.fromHSV(EnemyHue, 0f, 0f));
        if (min(Neutral,min(Team,Enemy)) <= Tolerance) {
            if (Neutral < Enemy && Neutral < Team) {
                return SampleType.NEUTRAL;
            } else if (Team < Neutral && Team < Enemy) {
                return SampleType.FRIENDLY;
            } else if (Enemy < Neutral && Enemy < Team) {
                return SampleType.OPPONENT;
            }
        }
        return SampleType.NONE;
    }
}
