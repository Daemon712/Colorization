package ru.daemon.colorization.game.logic;

public class ColorHolder {
    public final PointHolder red;
    public final PointHolder green;
    public final PointHolder blue;

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

    public PointHolder get(Component component) {
        switch (component) {
            case RED:
                return red;
            case GREEN:
                return green;
            case BLUE:
                return blue;
            default:
                throw new UnsupportedOperationException("unknown component " + component);
        }
    }

    public enum Component {
        RED,
        GREEN,
        BLUE
    }
}
