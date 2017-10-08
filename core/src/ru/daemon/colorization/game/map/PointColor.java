package ru.daemon.colorization.game.map;

import com.badlogic.gdx.graphics.Color;

public class PointColor {
    public static final int COLORS_NUMBER = 3;
    private static final int BITS_TO_ENCODE = 32 - Integer.numberOfLeadingZeros(COLORS_NUMBER - 1);//log2(x)
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
        id <<= BITS_TO_ENCODE;
        id |= green;
        id <<= BITS_TO_ENCODE;
        id |= blue;

        return id;
    }

    private static float toFloat(int color) {
        return 0.2f + 0.8f * color / (float) COLORS_NUMBER;
    }
    private static void validate(int color) {
        if (color < 0){
            throw new IllegalArgumentException("Color should not be negative. Given = " + color);
        }
        if (color >= COLORS_NUMBER){
            throw new IllegalArgumentException("Color should be less than " + COLORS_NUMBER + ". Given = " + color);
        }
    }
}
