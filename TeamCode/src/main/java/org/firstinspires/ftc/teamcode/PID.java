package org.firstinspires.ftc.teamcode;

public class PID {
    double p;
    double i;
    double prevint = 0;
    double lval = 0;
    double d;
    public PID(double P,double I,double D) {
        p = P;
        i = I;
        d = D;
    }

    public double step(double value,double desvalue) {
        double outp = value*this.p;
        this.prevint = (this.prevint+(desvalue-value))/2;
        double outi = this.prevint*this.i;
        double outd = (this.lval-value)*this.d;
        this.lval = value;
        return outd+outp+outi;
    }
}
