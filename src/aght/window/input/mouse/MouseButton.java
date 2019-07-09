package aght.window.input.mouse;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseButton extends GLFWMouseButtonCallback {
    public static int MOUSE_BUTTON_1 = 0;
    public static int MOUSE_BUTTON_2 = 1;
    public static int MOUSE_BUTTON_3 = 2;
    public static int MOUSE_BUTTON_4 = 3;
    public static int MOUSE_BUTTON_5 = 4;
    public static int MOUSE_BUTTON_6 = 5;
    public static int MOUSE_BUTTON_7 = 6;
    public static int MOUSE_BUTTON_8 = 7;
    public static int MOUSE_BUTTON_LEFT = MOUSE_BUTTON_1;
    public static int MOUSE_BUTTON_RIGHT = MOUSE_BUTTON_2;
    public static int MOUSE_BUTTON_MIDDLE = MOUSE_BUTTON_3;
    public static int MOUSE_BUTTON_LAST = MOUSE_BUTTON_8;

    private static boolean[] pressedButtons = new boolean[8];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            pressedButtons[button] = true;
        } else if (action == GLFW_RELEASE) {
            pressedButtons[button] = false;
        }
    }

    public static boolean isButtonPressed(int button) {
        return pressedButtons[button];
    }
}
