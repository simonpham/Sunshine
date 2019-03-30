package com.github.simonpham.sunshine.model;

/**
 * Created by Simon Pham on 3/3/19.
 * Email: simonpham.dn@gmail.com
 */
public class Wind {

    private double speed;
    private double deg;

    public Wind(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }
}
