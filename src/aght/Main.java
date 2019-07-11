package aght;

import aght.graphics.Color;
import aght.raycasting.Camera;
import aght.raycasting.Wall;
import aght.window.Context;
import aght.window.input.keyboard.Keyboard;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Main extends Context {

    private long ctx;

    private int width;
    private int height;

    private Camera camera;

    private List<Wall> walls;

    public Main() {
        super(1000, 768, "Raycasting", 2);
        width = getWidth();
        height = getHeight();
    }

    private List<Wall> generateWalls() {
        List<Wall> walls = new ArrayList<>();

        walls.add(new Wall(ctx, 0, 0, width, 0, Color.White));
        walls.add(new Wall(ctx, 0, 0, 0, height, Color.White));
        walls.add(new Wall(ctx, 0, height, width, height, Color.White));
        walls.add(new Wall(ctx, width, height, width, 0, Color.White));

        float x = 60;
        float y = 60;
        float w = 100;
        float h = 100;

        walls.add(new Wall(ctx, x, y, x + w, y, Color.Red));
        walls.add(new Wall(ctx, x + w, y, x + w, y + h, Color.Aqua));
        walls.add(new Wall(ctx, x + w, y + h, x, y + h, Color.Fuchsia));
        walls.add(new Wall(ctx, x, y + h, x, y, Color.Lime));

        return walls;
    }

    @Override
    public void setup() {
        ctx = getNvgctx();

        camera = new Camera(ctx, width / 2, height / 2, 60);
        walls = generateWalls();
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
            camera.move(2f);
        }

        if (Keyboard.isKeyDown(GLFW_KEY_DOWN)) {
            camera.move(-2f);
        }
    }

    @Override
    public void render() {
        camera.renderView(walls, width, height);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}