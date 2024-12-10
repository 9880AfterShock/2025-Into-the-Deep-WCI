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
    public TeamColor toHSV() {
        if (this.red+this.green+this.blue != 0) {
            return this;
        }
        float h, s, v;


        // Normalize this values to the range [0, 1]
        float rNorm = this.red;
        float gNorm = this.green;
        float bNorm = this.blue;

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
        this.hue = h/360;
        this.sat = s;
        this.val = v;
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        return this;
    }
    public TeamColor toRGB() {
        if (this.hue+this.sat+this.val != 0) {
            return this;
        }
        double hh = this.hue / 60;
        int i = ((int) hh) % 6;

        double f = hh - i;
        double p = this.val * (1 - this.sat);
        double q = this.val * (1 - f * this.sat);
        double t = this.val * (1 - (1 - f) * this.sat);

        switch (i) {
            case 0:
                this.red = this.val; this.green = (float) t; this.blue = (float) q; break;
            case 1:
                this.red = (float) q; this.green = this.val; this.blue = (float) p; break;
            case 2:
                this.red = (float) p; this.green = this.val; this.blue = (float) t; break;
            case 3:
                this.red = (float) p; this.green = (float) q; this.blue = this.val; break;
            case 4:
                this.red = (float) t; this.green = (float) p; this.blue = this.val; break;
            case 5:
                this.red = this.val; this.green = (float) p; this.blue = (float) q; break;
            default:
        }
        this.hue = 0;
        this.sat = 0;
        this.val = 0;
        return this;
    }
}
