package ru.daemon.colorization.game.logic;

import com.badlogic.gdx.graphics.Color;

public class ColorHolder {
    private final PointHolder red;
    private final PointHolder green;
    private final PointHolder blue;

    public ColorHolder(int steps) {
        this.red = new PointHolder(0, steps - 1);
        this.green = new PointHolder(0, steps - 1);
        this.blue = new PointHolder(0, steps - 1);
    }

    public ColorHolder(int steps, int red, int green, int blue) {
        this.red = new PointHolder(0, steps - 1, red);
        this.green = new PointHolder(0, steps - 1, green);
        this.blue = new PointHolder(0, steps - 1 , blue);
    }

    public PointHolder getRed() {
        return red;
    }

    public PointHolder getGreen() {
        return green;
    }

    public PointHolder getBlue() {
        return blue;
    }

    public PointHolder get(Component component) {
        switch (component) {
            case RED:
                return getRed();
            case GREEN:
                return getGreen();
            case BLUE:
                return getBlue();
            default:
                return null;
        }
    }

    public enum Component {
        RED(Color.RED),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE);

        private Color color;

        Component(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }
}
