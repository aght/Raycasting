package aght.raycasting;

import aght.graphics.Color;
import aght.graphics.shape.Circle;
import aght.graphics.shape.Line;
import aght.graphics.shape.Rectangle;
import aght.utils.MathUtils;
import org.joml.Math;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Camera {
    private static final int RAY_COUNT = 200;
    private static final int LUMINANCE_RATIO = 170;
    private static final int RENDER_HEIGHT_RATIO = 48;
    private static final float MAP_SCALE = 0.2f;

    private long ctx;

    private Vector2f position;

    private float fov;
    private float screenWidth;
    private float screenHeight;
    private float heading;

    public Camera(long ctx, float x, float y, float fov, float screenWidth, float screenHeight) {
        this.ctx = ctx;
        this.position = new Vector2f(x, y);
        this.fov = MathUtils.toRadians(fov);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.heading = 0;
    }

    private List<Ray> generateRays() {
        List<Ray> rays = new ArrayList<>();

        float rayAngle = heading - (fov / 2);
        float angleStep = fov / RAY_COUNT;

        for (int i = 0; i < RAY_COUNT; i++) {
            rays.add(new Ray(position, rayAngle));
            rayAngle += angleStep;
        }

        return rays;
    }

    public void move(float stepAmount) {
        float nX = (float) Math.cos(heading);
        float nY = (float) Math.sin(heading);

        position.add(new Vector2f(nX, nY).normalize().mul(stepAmount));
    }

    public void renderScene(List<Wall> walls) {
        List<RaycastResult> results = new ArrayList<>();

        List<Ray> rays = generateRays();

        for (int i = 0; i < RAY_COUNT; i++) {
            Ray ray = rays.get(i);

            Wall hitWall = null;
            Vector2f minIntersection = null;
            float minDistance = Float.MAX_VALUE;

            for (int j = 0; j < walls.size(); j++) {
                Vector2f intersection = ray.cast(walls.get(j));
                if (intersection != null) {
                    float distance = position.distance(intersection);

                    if (distance < minDistance) {
                        hitWall = walls.get(j);
                        minIntersection = intersection;
                        minDistance = distance;
                    }
                }
            }

            float correctedDistance = minDistance * (float) Math.cos(ray.getAngle() - heading);
            float projectionPlane = (screenWidth / 2) / (float) Math.tan(fov / 2);
            float stripWidth = screenWidth / RAY_COUNT;
            float stripHeight = (RENDER_HEIGHT_RATIO / correctedDistance) * projectionPlane;

            float alpha = LUMINANCE_RATIO / correctedDistance;
            int mappedAlpha = (int) MathUtils.map(alpha, 0, 1, 0, 255);

            if (hitWall != null) {
                Color wallColor = hitWall.getColor();
                wallColor.a(mappedAlpha);

                Rectangle section = new Rectangle(ctx, i * stripWidth, screenHeight / 2, stripWidth, stripHeight);
                section.setOriginY(section.getY() + section.getHeight() / 2);
                section.setFillColor(wallColor);
                section.render();
            }

            results.add(new RaycastResult(minIntersection, hitWall, minDistance));
        }

        renderMap(results, walls);
    }

    private void renderMap(List<RaycastResult> results, List<Wall> walls) {
        renderMapBackground();
        renderMapWalls(walls);

        for (int i = 0; i < results.size(); i += 2) {
            Vector2f intersection = results.get(i).getIntersection();

            if (intersection != null) {
                renderMapRay(results.get(i).getIntersection());
            }
        }

        renderPlayer();
    }

    private void renderMapRay(Vector2f intersection) {
        float x1 = position.x() * MAP_SCALE;
        float y1 = position.y() * MAP_SCALE;
        float x2 = intersection.x() * MAP_SCALE;
        float y2 = intersection.y() * MAP_SCALE;

        Line ray = new Line(ctx, x1, y1, x2, y2);
        ray.setStrokeColor(Color.Red);
        ray.render();
    }

    private void renderMapWalls(List<Wall> walls) {
        for (Wall wall : walls) {
            float x1 = wall.getStartX() * MAP_SCALE;
            float y1 = wall.getStartY() * MAP_SCALE;
            float x2 = wall.getEndX() * MAP_SCALE;
            float y2 = wall.getEndY() * MAP_SCALE;

            Line line = new Line(ctx, x1, y1, x2, y2);
            line.render();
        }
    }

    private void renderPlayer() {
        float cx = position.x() * MAP_SCALE;
        float cy = position.y() * MAP_SCALE;
        float r = 10 * MAP_SCALE;

        Circle player = new Circle(ctx, cx, cy, r);
        player.render();
    }

    private void renderMapBackground() {
        float x = 0;
        float y = 0;
        float width = screenWidth * MAP_SCALE;
        float height = screenHeight * MAP_SCALE;

        Rectangle background = new Rectangle(ctx, x, y, width, height);
        background.setFillColor(Color.Black);
        background.render();
    }

    public float getRotation() {
        return heading;
    }

    public void setRotation(float angle) {
        this.heading = angle;
    }

    private class RaycastResult {
        private Vector2f intersection;
        private Wall hitWall;
        private float distance;

        public RaycastResult(Vector2f intersection, Wall hitWall, float distance) {
            this.intersection = intersection;
            this.hitWall = hitWall;
            this.distance = distance;
        }

        public Vector2f getIntersection() {
            return intersection;
        }

        public Wall getHitWall() {
            return hitWall;
        }

        public float getDistance() {
            return distance;
        }
    }
}
