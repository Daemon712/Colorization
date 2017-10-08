package ru.daemon.colorization.game.map;

public class ScaledPerlinNoise2D {
    private final PerlinNoise2D perlinNoise;
    private final float scale;

    public ScaledPerlinNoise2D(long seed, int width, int height, float scale) {
        this.scale = scale;
        int gradWidth = (int) Math.ceil(width * scale) + 1;
        int gradHeight = (int) Math.ceil(height * scale) + 1;
        this.perlinNoise = new PerlinNoise2D(seed, gradWidth, gradHeight);
    }

    public float perlin(int x, int y){
        return perlinNoise.perlin(x*scale, y*scale);
    }
}
