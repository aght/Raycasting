package aght;

import aght.graphics.Color;
import aght.raycasting.Box;
import aght.raycasting.Camera;
import aght.raycasting.Wall;
import aght.window.Context;
import aght.window.input.keyboard.Keyboard;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Main extends Context {

    private static final int width = 768;
    private static final int height = 480;
    private static final int msaa = 2;

    private long ctx;

    private Camera camera;

    private List<Wall> walls;

    @Override
    public void setup() {
        ctx = getNvgctx();

        camera = new Camera(ctx, width / 2, height / 2, 70, width, height);
        walls = generateWalls();
    }

    @Override
    public void update() {
        if (Keyboard.isKeyDown(GLFW_KEY_RIGHT) || Keyboard.isKeyDown(GLFW_KEY_D)) {
            camera.setRotation(camera.getRotation() + 0.05f);
        }

        if (Keyboard.isKeyDown(GLFW_KEY_LEFT) || Keyboard.isKeyDown(GLFW_KEY_A)) {
            camera.setRotation(camera.getRotation() - 0.05f);
        }

        if (Keyboard.isKeyDown(GLFW_KEY_UP) || Keyboard.isKeyDown(GLFW_KEY_W)) {
            camera.move(2f);
        }

        if (Keyboard.isKeyDown(GLFW_KEY_DOWN) || Keyboard.isKeyDown(GLFW_KEY_S)) {
            camera.move(-2f);
        }
    }

    @Override
    public void render() {
        camera.renderScene(walls);
    }

    private List<Wall> generateWalls() {
        List<Wall> walls = new ArrayList<>();

        Box bounds = new Box(ctx, 0, 0, width, height);
        walls.addAll(bounds.getWalls());

        Box b1 = new Box(ctx, 30, 30, width / 5, 100, Color.Red);
        walls.addAll(b1.getWalls());

        Box b2 = new Box(ctx, 200, height / 2, 30, 30, Color.Lime);
        walls.addAll(b2.getWalls());

        float boxWidth = 50;
        float spacing = 50;
        int max = width / (int) (boxWidth + spacing);

        for (int i = 0; i < max; i++) {
            float x = i * (boxWidth + spacing);
            float y = height - spacing * 2 - boxWidth;
            float w = boxWidth;
            float h = boxWidth;

            Box b = new Box(ctx, x, y, w, h, Color.Yellow);
            walls.addAll(b.getWalls());
        }

        return walls;
    }

    public static void main(String[] args) {
        new Main().run(width, height, "Raycasting", msaa);
    }
}