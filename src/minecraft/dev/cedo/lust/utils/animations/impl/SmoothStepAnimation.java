package dev.cedo.lust.utils.animations.impl;

import dev.cedo.lust.utils.animations.Animation;
import dev.cedo.lust.utils.animations.Direction;

public class SmoothStepAnimation extends Animation {

    public SmoothStepAnimation(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public SmoothStepAnimation(int ms, double endPoint, Direction direction) {
        super(ms, endPoint, direction);
    }

    protected double getEquation(double x1) {
        double x = x1 / (double) duration; //Used to force input to range from 0 - 1
        return -2 * Math.pow(x, 3) + (3 * Math.pow(x, 2));
    }

}
