package aght.raycasting;

import aght.graphics.Color;
import aght.graphics.shape.Line;

/**
 * Simple class to allow a line to be referred to as a wall for the purposes of raycasting
 */
public class Wall extends Line {
    private Color color;

    public Wall(long ctx, float x1, float y1, float x2, float y2, Color color) {
        super(ctx, x1, y1, x2, y2);

        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
