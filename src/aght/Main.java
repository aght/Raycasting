package aght;

import aght.window.Context;

public class Main extends Context {

    private long ctx;

    public Main() {
        super(1000, 600, "Raycasting", 8);
    }

    @Override
    public void setup() {
        ctx = getNvgctx();
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
    }

    public static void main(String[] args) {
        new Main().run();
    }
}