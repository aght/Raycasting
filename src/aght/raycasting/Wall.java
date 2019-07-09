package aght.raycasting;

import aght.graphics.shape.Line;

/**
 * Simple class to allow a line to be referred to as a wall for the purposes of raycasting
 */
public class Wall extends Line {
    public Wall(long ctx, float x1, float y1, float x2, float y2) {
        super(ctx, x1, y1, x2, y2);
    }
}
