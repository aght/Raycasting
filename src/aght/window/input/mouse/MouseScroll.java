package aght.window.input.mouse;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWScrollCallback;

public class MouseScroll extends GLFWScrollCallback {

    private static float x;
    private static float y;

    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        x = (float) xoffset;
        y = (float) yoffset;
    }

    public static Vector2f getScroll() {
        return new Vector2f(x, y);
    }

    public static float getScrollX() {
        return x;
    }

    public static float getScrollY() {
        return y;
    }
}
