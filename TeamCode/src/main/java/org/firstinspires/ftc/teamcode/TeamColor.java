package org.firstinspires.ftc.teamcode;

public class TeamColor {
    public float red;
    public float green;
    public float blue;
    public float hue;
    public float sat;
    public float val;
    public TeamColor(float r,float g,float b,float h,float s,float v) {
        red = r;
        green = g;
        blue = b;
        hue = h;
        sat = s;
        val = v;
    }
    public static TeamColor fromRGB(float r, float g, float b) {
        return new TeamColor(r,g,b,-1F,-1F,-1);
    }
    public static TeamColor fromHSV(float h, float s, float v) {
        return new TeamColor(-1F,-1F,-1,h,s,v);
    }
    public static TeamColor RGBToHSV(TeamColor tc) {
        float h, s, v;


        // Normalize RGB values to the range [0, 1]
        float rNorm = tc.red;
        float gNorm = tc.green;
        float bNorm = tc.blue;

        float cMax = Math.max(rNorm, Math.max(gNorm, bNorm));
        float cMin = Math.min(rNorm, Math.min(gNorm, bNorm));
        float delta = cMax - cMin;

        // Calculate Hue
        if (delta == 0) {
            h = 0;
        } else if (cMax == rNorm) {
            h = 60 * ((gNorm - bNorm) / delta % 6);
        } else if (cMax == gNorm) {
            h = 60 * (((bNorm - rNorm) / delta) + 2);
        } else {
            h = 60 * (((rNorm - gNorm) / delta) + 4);
        }

        // Calculate Saturation
        if (cMax == 0) {
            s = 0;
        } else {
            s = delta / cMax;
        }

        // Value is simply the maximum component
        v = cMax;

        return fromHSV(h/360,s,v);
    }
    public static TeamColor HSVToRGB(TeamColor color) {
        TeamColor rgb = new TeamColor(0F,0F,0F,0F,0F,0F);
        double hh = color.hue / 60;
        int i = ((int) hh) % 6;

        double f = hh - i;
        double p = color.val * (1 - color.sat);
        double q = color.val * (1 - f * color.sat);
        double t = color.val * (1 - (1 - f) * color.sat);

        switch (i) {
            case 0:
                rgb.red = color.val; rgb.green = (float) t; rgb.blue = (float) q; break;
            case 1:
                rgb.red = (float) q; rgb.green = color.val; rgb.blue = (float) p; break;
            case 2:
                rgb.red = (float) p; rgb.green = color.val; rgb.blue = (float) t; break;
            case 3:
                rgb.red = (float) p; rgb.green = (float) q; rgb.blue = color.val; break;
            case 4:
                rgb.red = (float) t; rgb.green = (float) p; rgb.blue = color.val; break;
            case 5:
                rgb.red = color.val; rgb.green = (float) p; rgb.blue = (float) q; break;
            default:
        }

        return rgb;
    }
}
