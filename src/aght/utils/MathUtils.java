package aght.utils;

public class MathUtils {
    public static float map(float val, float inLowerBound,
                            float inUpperBound, float outLowerBound, float outUpperBound) {
        return (((val - inLowerBound) * (outUpperBound - outLowerBound))
                / ((inUpperBound - inLowerBound))) + outLowerBound;
    }

    public static float toRadians(float angle) {
        return angle * ((float) Math.PI / 180);
    }
}
