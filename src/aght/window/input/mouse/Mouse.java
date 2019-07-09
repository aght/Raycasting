package aght.window.input.mouse;

import org.joml.Vector2f;

public class Mouse {
    public static Vector2f getCursorPosition() {
        return MousePosition.getCursorPosition();
    }

    public static float getCursorX() {
        return MousePosition.getCursorPosition().x();
    }

    public static float getCursorY() {
        return MousePosition.getCursorPosition().y();
    }

    public static float getScrollX() {
        return Mouse.getScrollX();
    }

    public static float getScrollY() {
        return Mouse.getScrollY();
    }
}
