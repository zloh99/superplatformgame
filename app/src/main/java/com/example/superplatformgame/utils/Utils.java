package com.example.superplatformgame.utils;

public class Utils {
    /**
     * getDistanceBetweenPoints returns distance between 2d points p1 and p2
     * @param p1x
     * @param p2x
     * @param p1y
     * @param p2y
     * @return
     */
    public static double getDistanceBetweenPoints(double p1x, double p1y, double p2x, double p2y) {
        return Math.sqrt( //sqrt(c^2) = sqrt(a^2 + b^2) to get straight line from touch to center of outer circle
                Math.pow(p1x - p2x, 2) +
                        Math.pow(p1y - p2y, 2));
    }
}
