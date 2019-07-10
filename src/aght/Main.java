package aght;

import aght.raycasting.Camera;
import aght.window.Context;
import aght.window.input.keyboard.Keyboard;

import java.security.Key;

import static org.lwjgl.glfw.GLFW.*;

public class Main extends Context {

    private long ctx;

    private int width;
    private int height;

    private Camera camera;

    public Main() {
        super(1000, 600, "Raycasting", 8);
        width = getWidth();
        height = getHeight();
    }

    @Override
    public void setup() {
        ctx = getNvgctx();

        camera = new Camera(ctx, width / 2, height / 2, 90);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyDown(GLFW_KEY_RIGHT)) {
            camera.setRotation(camera.getRotation() + 0.05f);
        }

        if (Keyboard.isKeyDown(GLFW_KEY_LEFT)) {
            camera.setRotation(camera.getRotation() - 0.05f);
        }

        if (Keyboard.isKeyDown(GLFW_KEY_UP)) {
            camera.move(1);
        }

        if (Keyboard.isKeyDown(GLFW_KEY_DOWN)) {
            camera.move(-1);
        }
    }

    @Override
    public void render() {
        camera.render();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}