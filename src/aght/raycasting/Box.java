package aght.raycasting;

import aght.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Box {
    private long ctx;
    private float x;
    private float y;
    private float w;
    private float h;

    private Color color;

    public Box(long ctx, float x, float y, float w, float h) {
        this.ctx = ctx;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = Color.White;
    }

    public Box(long ctx, float x, float y, float w, float h, Color color) {
        this.ctx = ctx;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    public List<Wall> getWalls() {
        return new ArrayList<Wall>() {{
           add(new Wall(ctx, x, y, x + w, y, color));
           add(new Wall(ctx, x + w, y, x + w, y + h, color));
           add(new Wall(ctx, x + w, y + h, x, y + h, color));
           add(new Wall(ctx, x, y + h, x, y, color));
        }};
    }
}
