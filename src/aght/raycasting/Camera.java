package aght.raycasting;

import aght.graphics.Color;
import aght.graphics.shape.Rectangle;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class Camera {
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

        for (int angle = (int) -fov / 2; angle < fov / 2; angle++) {
            rays.add(new Ray(position, toRadians(angle) + heading));
        }

        return rays;
    }

    private void updateRays() {
        for (int i = 0, angle = (int) -fov / 2; angle < fov / 2; angle++, i++) {
            rays.get(i).setAngle(toRadians(angle) + heading);
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

    public void renderView(List<Wall> walls) {
        for (Ray ray : rays) {
            Vector2f closest = null;

            float minDistance = Float.MAX_VALUE;

            for (Wall wall : walls) {
                Vector2f intersection = ray.cast(wall);
                if (intersection != null) {
                    float distance = this.position.distanceSquared(intersection);
                    if (distance < minDistance) {
                        closest = intersection;
                        minDistance = distance;
                    }
                }
            }

            if (closest != null) {

                // Line class causes a lot of stutters
                // For some reason this is faster than using the Line class
                // Possible that the creation of the affine matrix is too slow
                nvgSave(ctx);
                nvgBeginPath(ctx);
                nvgMoveTo(ctx, this.position.x(), this.position.y());
                nvgLineTo(ctx, closest.x(), closest.y());

                nvgStrokeColor(ctx, new Color(255, 0, 0).nvgColor());
                nvgStroke(ctx);

                nvgRestore(ctx);
            }
        }
    }

    public void renderBody() {
        body.setOrigin(body.getX() + body.getWidth() / 2, body.getY() + body.getHeight() / 2);
        body.setX(position.x());
        body.setY(position.y());
        body.setRotation(heading);
        body.render();
    }

    public float getRotation() {
        return heading;
    }

    private float toRadians(float angle) {
        return angle * ((float) Math.PI / 180);
    }
}
