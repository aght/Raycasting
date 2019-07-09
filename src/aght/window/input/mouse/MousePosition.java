package aght.window.input.mouse;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MousePosition extends GLFWCursorPosCallback {
    private static Vector2f position;

    public MousePosition() {
        position = new Vector2f();
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        position.set((float) xpos, (float) ypos);
    }

    public static Vector2f getCursorPosition() {
        return position;
    }
}
