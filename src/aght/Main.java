package aght;

import aght.raycasting.Camera;
import aght.raycasting.Wall;
import aght.utils.RandomUtils;
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
        super(400, 400, "Raycasting", 2);
        width = getWidth();
        height = getHeight();
    }

    private List<Wall> generateWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            float x1 = RandomUtils.intInRange(0, width);
            float y1 = RandomUtils.intInRange(0, height);
            float x2 = RandomUtils.intInRange(0, width);
            float y2 = RandomUtils.intInRange(0, height);

            walls.add(new Wall(ctx, x1, y1, x2, y2));
        }

        return walls;
    }

    @Override
    public void setup() {
        ctx = getNvgctx();

        camera = new Camera(ctx, width / 2, height / 2, 90);
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
            camera.move(1f);
        }

        if (Keyboard.isKeyDown(GLFW_KEY_DOWN)) {
            camera.move(-1f);
        }
    }

    @Override
    public void render() {
        for (Wall wall : walls) {
            wall.render();
        }

        camera.renderView(walls, width, height);
        camera.renderBody();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}