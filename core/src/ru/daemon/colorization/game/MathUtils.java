package ru.daemon.colorization.game;

public class MathUtils {
    public static int log2(int x) {
        if (x <= 0) return 0;
        return 31 - Integer.numberOfLeadingZeros(x);
    }

    public static int infoQuantity(int x) {
        return MathUtils.log2(x) + x % 2;
    }
}
