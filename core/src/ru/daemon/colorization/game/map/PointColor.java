package ru.daemon.colorization.game.map;

import com.badlogic.gdx.graphics.Color;

public class PointColor {
    public static final int MAX = 16;
    private static final int MAX_BITS = 31 - Integer.numberOfLeadingZeros(MAX);
    private int red, green, blue;

    public PointColor() {
    }

    public PointColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        validate(red);
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        validate(green);
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        validate(blue);
        this.blue = blue;
    }

    public Color getTrueColor() {
        return new Color(toFloat(red), toFloat(green), toFloat(blue), 1f);
    }

    public int getId() {
        int id = 0;

        id |= red;
        id <<= MAX_BITS;
        id |= green;
        id <<= MAX_BITS;
        id |= blue;

        return id;
    }

    private static float toFloat(int color) {
        return 0.2f + 0.6f * color / (float) MAX;
    }
    private static void validate(int color) {
        if (color < 0){
            throw new IllegalArgumentException("Color should not be negative. Given = " + color);
        }
        if (color >= MAX){
            throw new IllegalArgumentException("Color should be less than " + MAX + ". Given = " + color);
        }
    }
}
