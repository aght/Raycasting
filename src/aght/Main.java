package aght;

import aght.raycasting.Camera;
import aght.raycasting.Wall;
import aght.window.Context;
import aght.window.input.keyboard.Keyboard;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class Main extends Context {

    private long ctx;

    private int width;
    private int height;

    private Camera camera;

    private List<Wall> walls;

    public Main() {
        super(1000, 600, "Raycasting", 8);
        width = getWidth();
        height = getHeight();
    }

    private List<Wall> generateWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

        }

        return walls;
    }

    @Override
    public void setup() {
        ctx = getNvgctx();

        camera = new Camera(ctx, width / 2, height / 2, 90);
        walls = new ArrayList<Wall>();
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
        camera.renderBody();

        for (Wall wall : walls) {
            wall.render();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}