package aght.raycasting;

import aght.graphics.Color;
import aght.graphics.shape.Rectangle;
import aght.utils.MathUtils;
import org.joml.Math;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Camera {
    private static final int RAY_COUNT = 300;

    private long ctx;

    private Vector2f position;

    private float fov;
    private float heading;

    public Camera(long ctx, float x, float y, float fov) {
        this.position = new Vector2f(x, y);
        this.fov = MathUtils.toRadians(fov);
        this.ctx = ctx;
        this.heading = 0;
    }

    private List<Ray> generateRays() {
        List<Ray> rays = new ArrayList<>();

        float rayAngle = this.heading - (this.fov / 2);
        float angleStep = this.fov / RAY_COUNT;

        for (int i = 0; i < RAY_COUNT; i++) {
            rays.add(new Ray(this.position, rayAngle));
            rayAngle += angleStep;
        }

        return rays;
    }

    public void move(float stepAmount) {
        float nX = (float) Math.cos(this.heading);
        float nY = (float) Math.sin(this.heading);

        this.position.add(new Vector2f(nX, nY).normalize().mul(stepAmount));
    }

    public void renderView(List<Wall> walls, float width, float height) {
        List<Ray> rays = generateRays();

        for (int i = 0; i < RAY_COUNT; i++) {
            Ray ray = rays.get(i);

            Wall hitWall = null;

            float minDistance = Float.MAX_VALUE;

            for (int k = 0; k < walls.size(); k++) {
                Vector2f intersection = ray.cast(walls.get(k));
                if (intersection != null) {
                    float distance = this.position.distance(intersection);

                    if (distance < minDistance) {
                        minDistance = distance;
                        hitWall = walls.get(k);
                    }
                }
            }

            float correctedDistance = minDistance * (float) Math.cos(ray.getAngle() - this.heading);
            float projectionPlane = (width / 2) / (float) Math.tan(this.fov / 2);
            float stripWidth = width / RAY_COUNT;
            float stripHeight = (32 / correctedDistance) * projectionPlane;

            float alpha = 150 / correctedDistance;
            int mappedAlpha = (int) MathUtils.map(alpha, 0, 1, 0, 255);

            if (hitWall != null) {
                Color wallColor = hitWall.getColor();
                wallColor.a(mappedAlpha);

                Rectangle section = new Rectangle(ctx, i * stripWidth, height / 2, stripWidth, stripHeight);
                section.setOrigin(section.getX() + section.getWidth() / 2, section.getY() + section.getHeight() / 2);
                section.setFillColor(wallColor);
                section.render();
            }
        }
    }

    public float getRotation() {
        return this.heading;
    }

    public void setRotation(float angle) {
        this.heading = angle;
    }
}
