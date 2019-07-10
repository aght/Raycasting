package aght.raycasting;

import aght.graphics.shape.Rectangle;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Camera {
    private long ctx;

    private float fov;
    private Vector2f position;
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
        int i = 0;
        for (int angle = (int) -fov / 2; angle < fov / 2; angle++, i++) {
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

    }

    public void renderBody() {
        body.setOrigin(body.getX() + body.getWidth() / 2, body.getY() + body.getHeight() / 2);
        body.setX(position.x());
        body.setY(position.y());
        body.setRotation(heading);
        body.render();

        for (Ray ray : rays) {
            ray.render(ctx);
        }
    }

    public float getRotation() {
        return heading;
    }

    private float toRadians(float angle) {
        return angle * ((float) Math.PI / 180);
    }
}
