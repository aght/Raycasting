package aght.raycasting;

import org.joml.Vector2f;

public class Ray {
    Vector2f position;
    Vector2f direction;

    public Ray(Vector2f position, float angle) {
        this.position = position;
        setAngle(angle);
    }

    public void pointAt(float x, float y) {
        this.direction.set(x, y);
        this.direction.normalize();
    }

    public void setAngle(float angle) {
        this.direction = new Vector2f((float) Math.cos(angle), (float) Math.sin(angle));
    }

    /**
     * Calculate intersection point between this Ray and the Wall at which the Ray is cast at. If no intersection is
     * found or the denominator is 0, null will be returned.
     */
    public Vector2f cast(Wall wall) {
        final float x1 = wall.getStartX();
        final float y1 = wall.getStartY();
        final float x2 = wall.getEndX();
        final float y2 = wall.getEndY();

        final float x3 = this.position.x();
        final float y3 = this.position.y();
        final float x4 = this.position.x() + this.direction.x();
        final float y4 = this.position.y() + this.direction.y();

        final float denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (denominator == 0) {
            return null;
        }

        final float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        final float u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        if (t > 0 && t < 1 && u > 0) {
            return new Vector2f(x1 + t * (x2 - x1), y1 + t * (y2 - y1));
        }

        return null;
    }
}
