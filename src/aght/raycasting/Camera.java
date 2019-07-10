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

    public void renderView(List<Wall> walls, int width, int height) {
        List<Float> distances = new ArrayList<>();
        List<Color> colors = new ArrayList<>();

        for (Ray ray : rays) {
            Vector2f closest = null;
            Color color = new Color(255, 0, 0, 100);

            float minDistance = Float.MAX_VALUE;

            int bestIndex = 0;

            for (int i = 0; i < walls.size(); i++) {
                Vector2f intersection = ray.cast(walls.get(i));
                if (intersection != null) {
                    float distance = this.position.distance(intersection);
                    if (distance < minDistance) {
                        closest = intersection;
                        minDistance = distance;
                        bestIndex = i;
                    }
                }
            }

            if (closest != null) {
                nvgSave(ctx);
                nvgBeginPath(ctx);
                nvgMoveTo(ctx, this.position.x(), this.position.y());
                nvgLineTo(ctx, closest.x(), closest.y());

                nvgStrokeColor(ctx, new Color(0, 255, 0).nvgColor());
                nvgStroke(ctx);

                nvgRestore(ctx);
            }

            color.b((int) MathUtils.map(bestIndex, 0, walls.size(), 0, 255));
            color.g((int) MathUtils.map(bestIndex, 0, walls.size(), 0, 120));

            distances.add(minDistance);
            colors.add(color);
        }

        int numSections = width / distances.size();

        for (int i = 0; i < distances.size(); i++) {
            if (distances.get(i) < height) {
                float h = MathUtils.map(distances.get(i), 0, width / 2, height, 0);
                Rectangle section = new Rectangle(ctx, numSections * i, height / 2, numSections, h);
                section.setOrigin(section.getX() + section.getWidth() / 2, section.getY() + section.getHeight() / 2);
                section.setFillColor(colors.get(i));
                section.render();
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
