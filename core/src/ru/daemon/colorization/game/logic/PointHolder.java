package ru.daemon.colorization.game.logic;

public class PointHolder {
    public final int min;
    public final int max;
    private int points;

    public PointHolder(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public PointHolder(int min, int max, int points) {
        this.min = min;
        this.max = max;
        this.points = points;
    }

    public int get() {
        return points;
    }

    public void set(int points) {
        if (points < min || points > max) {
            String msg = String.format("Points should be between %d and %d. Input = %d", min, max, points);
            throw new IllegalArgumentException(msg);
        }
        this.points = points;
    }

    public int take(int value){
        if (value < 0) throw new IllegalArgumentException("Taking a negative value is not correct");
        int delta = points - value < min ? min - points : value;
        points -= delta;
        return delta;
    }

    public int give(int value){
        if (value < 0) throw new IllegalArgumentException("Giving a negative value is not correct");
        int delta = points + value > max ? max - points : value;
        points += delta;
        return delta;
    }
}
