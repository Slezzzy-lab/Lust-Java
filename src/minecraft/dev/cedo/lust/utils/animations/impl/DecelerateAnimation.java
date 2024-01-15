package dev.cedo.lust.utils.animations.impl;


import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;

public class DecelerateAnimation extends Animation {

    public DecelerateAnimation(int ms, double endPoint) { super(ms, endPoint);}

    public DecelerateAnimation(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    protected double getEquation(double x1) {
        double x = x1 / duration;
        return 1 - ((x - 1) * (x - 1));
    }
}
