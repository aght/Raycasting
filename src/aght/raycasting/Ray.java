package aght.raycasting;

import org.joml.Vector2f;

public class Ray {
    private Vector2f position;
    private Vector2f direction;

    public Ray(Vector2f position, float angle) {
        this.position = position;
        this.direction = new Vector2f();
        setAngle(angle);
    }

    /**
     * Calculate intersection point between this Ray and the Wall at which the Ray is cast at. If no intersection is
     * found or the denominator is 0, null will be returned.
     */
    public Vector2f cast(Wall wall) {
        float x1 = wall.getStartX();
        float y1 = wall.getStartY();
        float x2 = wall.getEndX();
        float y2 = wall.getEndY();

        float x3 = position.x();
        float y3 = position.y();
        float x4 = position.x() + direction.x();
        float y4 = position.y() + direction.y();

        if ((x1 == x2 && y1 == y2) || (x3 == x4 && y3 == y4)) {
            return null;
        }

        float denominator = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

        if (denominator == 0) {
            return null;
        }

        float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
        float u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

        if (t > 0 && t < 1 && u > 0) {
            return new Vector2f(x1 + t * (x2 - x1), y1 + t * (y2 - y1));
        }

        return null;
    }

    public void setAngle(float angle) {
        direction.set((float) Math.cos(angle), (float) Math.sin(angle));
        direction.normalize();
    }

    public float getAngle() {
        return (float) Math.atan2(direction.y, direction.x);
    }
}
