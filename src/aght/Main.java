package aght;

import aght.raycasting.Wall;
import aght.window.Context;

public class Main extends Context {

    private long ctx;

    private Wall wall;

    public Main() {
        super(1000, 600, "Raycasting", 8);
    }

    @Override
    public void setup() {
        ctx = getNvgctx();

        wall = new Wall(ctx, 0, 0, 1000, 600);
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        wall.render();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}