package aght.raycasting;

import aght.graphics.Color;
import aght.graphics.shape.Rectangle;
import aght.utils.MathUtils;
import org.joml.Vector2f;
import org.lwjgl.system.MathUtil;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;


public class Camera {
    private static final int RAY_COUNT = 200;

    private long ctx;

    private Vector2f position;

    private float fov;
    private float heading;

    private List<Ray> rays;

    private Rectangle body;

    public Camera(long ctx, float x, float y, float fov) {
        this.position = new Vector2f(x, y);
        this.fov = fov;
        this.ctx = ctx;
        this.rays = generateRays();
        this.heading = 0;

        body = new Rectangle(ctx, 0, 0, 10, 10);
    }

    private List<Ray> generateRays() {
        List<Ray> rays = new ArrayList<>();

        float rayAngle = this.heading - (toRadians(fov) / 2);

        for (int i = 0; i < RAY_COUNT; i++) {
            rays.add(new Ray(this.position, rayAngle));
            rayAngle += toRadians(fov) / RAY_COUNT;
        }

        return rays;
    }

    private void updateRays() {
        float rayAngle = this.heading - (toRadians(fov) / 2);

        for (int i = 0; i < RAY_COUNT; i++) {
            rays.get(i).setAngle(rayAngle);
            rayAngle += toRadians(fov) / RAY_COUNT;
        }
    }

    public void setRotation(float angle) {
        this.heading = angle;
        updateRays();
    }

    public void move(float step) {
        position.add(new Vector2f((float) Math.cos(heading), (float) Math.sin(heading))
                .normalize()
                .mul(step));
    }

    public void renderView(List<Wall> walls, float width, float height) {
        int j = 0;
        for (Ray ray : rays) {
            Wall wall = null;

            float minDistance = Float.MAX_VALUE;

            for (int i = 0; i < walls.size(); i++) {
                Vector2f intersection = ray.cast(walls.get(i));
                if (intersection != null) {
                    float distance = this.position.distance(intersection);

                    if (distance < minDistance) {
                        minDistance = distance;
                        wall = walls.get(i);
                    }
                }
            }

            float correctedDistance = minDistance * (float) Math.cos(ray.getAngle() - heading);
            float projectionPlane = (width / 2) / (float) Math.tan(toRadians(fov) / 2);
            float stripWidth = width / RAY_COUNT;
            float stripHeight = (32 / correctedDistance) * projectionPlane;

            float alpha = 150 / correctedDistance;
            int mappedAlpha = (int) MathUtils.map(alpha, 0, 1, 0, 255);

            Color wallColor = wall.getColor();
            wallColor.a(mappedAlpha);

            Rectangle section = new Rectangle(ctx, j * stripWidth, height / 2, stripWidth, stripHeight);
            section.setOrigin(section.getX() + section.getWidth() / 2, section.getY() + section.getHeight() / 2);
            section.setFillColor(wallColor);
            section.render();

            j++;
        }
    }

    public float getRotation() {
        return heading;
    }

    private float toRadians(float angle) {
        return angle * ((float) Math.PI / 180);
    }
}
