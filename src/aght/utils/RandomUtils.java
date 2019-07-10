package aght.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    private static Random random = new Random();

    private RandomUtils() {}

    /**
     * Generate a random integer with a range (inclusive)
     *
     * @param min lower bound
     * @param max upper bound
     * @return random number between min and max (inclusive)
     */
    public static int intInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
