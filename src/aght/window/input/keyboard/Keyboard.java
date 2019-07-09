package aght.window.input.keyboard;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard extends GLFWKeyCallback {
    private static boolean[] pressedKeys;

    public Keyboard() {
        pressedKeys = new boolean[Short.MAX_VALUE];
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            pressedKeys[key] = true;
        } else if (action == GLFW_RELEASE) {
            pressedKeys[key] = false;
        }
    }

    public static boolean isKeyDown(int key) {
        return pressedKeys[key];
    }
}
