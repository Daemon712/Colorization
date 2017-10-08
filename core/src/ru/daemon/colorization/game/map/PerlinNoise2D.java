package ru.daemon.colorization.game.map;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class PerlinNoise2D {
    private Vector2[][] gradient;

    public PerlinNoise2D(long seed, int gradWidth, int gradHeight) {
        gradient = new Vector2[gradWidth][gradHeight];
        Random random = new Random(seed);
        for (int i = 0; i < gradWidth; i++) {
            for (int j = 0; j < gradHeight; j++) {
                Vector2 v = new Vector2(1, 0);
                v.rotateRad((float) (random.nextFloat() * 2 * Math.PI));
                gradient[i][j] = v;
            }
        }
    }

    // Compute Perlin noise at coordinates x, y
    public float perlin(float x, float y) {
        // Determine grid cell coordinates
        int x0 = (int) x;
        int x1 = x0 + 1;
        int y0 = (int) y;
        int y1 = y0 + 1;

        // Determine interpolation weights
        // Could also use higher order polynomial/s-curve here
        float sx = x - (float) x0;
        float sy = y - (float) y0;

        // Interpolate between grid point gradients
        float n0 = dotGridGradient(x0, y0, x, y);
        float n1 = dotGridGradient(x1, y0, x, y);
        float ix0 = lerp(n0, n1, sx);
        float m0 = dotGridGradient(x0, y1, x, y);
        float m1 = dotGridGradient(x1, y1, x, y);
        float ix1 = lerp(m0, m1, sx);

        return lerp(ix0, ix1, sy);
    }

    // Computes the dot product of the distance and gradient vectors.
    private float dotGridGradient(int ix, int iy, float x, float y) {
        // Compute the distance vector
        float dx = x - (float) ix;
        float dy = y - (float) iy;

        // Compute the dot-product
        return (dx * gradient[ix][iy].x + dy * gradient[ix][iy].y);
    }

    // Function to linearly interpolate between a0 and a1
    // Weight w should be in the range [0.0, 1.0]
    private static float lerp(float a0, float a1, float w) {
        return (1 - w) * a0 + w * a1;
    }
}