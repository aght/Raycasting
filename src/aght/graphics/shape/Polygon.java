package aght.graphics.shape;

import org.joml.Matrix3f;
import org.joml.Vector2f;

import java.util.List;

import static org.lwjgl.nanovg.NanoVG.*;

public class Polygon extends Shape {

    private long ctx;

    public Polygon(long ctx, List<Vector2f> vertices) {
        super(vertices);

        this.ctx = ctx;
    }

    public Polygon(long ctx, Vector2f[] vertices) {
        super(vertices);

        this.ctx = ctx;
    }

    public Polygon(long ctx, float[] positions) {
        super(positions);

        this.ctx = ctx;
    }

    public void render() {
        List<Vector2f> vertices = getVertices();

        Vector2f vertex = vertices.get(0);

        nvgSave(ctx);
        nvgBeginPath(ctx);

        if (shouldUpdateTransform()) {
            Matrix3f transform = getTransform();

            float a = transform.m00();
            float b = transform.m10();
            float c = transform.m01();
            float d = transform.m11();
            float e = transform.m02();
            float f = transform.m12();

            nvgTransform(ctx, a, b, c, d, e, f);
        }

        nvgMoveTo(ctx, vertex.x(), vertex.y());

        for (int i = 1; i < vertices.size(); i++) {
            vertex = vertices.get(i);

            nvgLineTo(ctx, vertex.x(), vertex.y());
        }

        nvgClosePath(ctx);

        if (getFillColor().a() != 0) {
            nvgFillColor(ctx, getFillColor().nvgColor());
            nvgFill(ctx);
        }

        if (getStrokeColor().a() != 0 && getStrokeWidth() != 0) {
            nvgStrokeColor(ctx, getStrokeColor().nvgColor());
            nnvgStrokeWidth(ctx, getStrokeWidth());
            nvgStroke(ctx);
        }

        nvgRestore(ctx);
    }

    public static Vector2f centroid(Polygon polygon) {
        return centroid(polygon.getVertices());
    }

    public static Vector2f centroid(List<Vector2f> vertices) {
        float cx = 0;
        float cy = 0;
        float factor;
        float area = area(vertices);

        final int n = vertices.size();

        for (int i = 0; i < n; i++) {

            int j = (i + 1) % n;

            factor = (vertices.get(i).x() * vertices.get(j).y()
                    - vertices.get(j).x() * vertices.get(i).y());

            cx += (vertices.get(i).x() + vertices.get(j).x()) * factor;
            cy += (vertices.get(i).y() + vertices.get(j).y()) * factor;
        }

        factor = 1 / (area * 6);

        return new Vector2f(cx * factor, cy * factor);
    }

    private static float area(List<Vector2f> vertices) {
        float area = 0;

        final int n = vertices.size();

        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            area += vertices.get(i).x() * vertices.get(j).y();
            area -= vertices.get(j).x() * vertices.get(i).y();
        }

        return area / 2.0f;
    }

    public List<Vector2f> getVertices() {
        return super.getVertices();
    }

    public void setVertices(List<Vector2f> vertices) {
        super.setVertices(vertices);
    }

    public void setVertices(Vector2f[] vertices) {
        super.setVertices(vertices);
    }

    public void setVertex(Vector2f vertex, int index) {
        super.setVertex(vertex, index);
    }

    public Vector2f getVertex(int index) {
        return super.getVertex(index);
    }

    public void addVertex(Vector2f vertex) {
        super.addVertex(vertex);
    }

    public void addVertex(Vector2f vertex, int index) {
        super.addVertex(vertex, index);
    }

    public void removeVertex(int index) {
        super.removeVertex(index);
    }

    public Vector2f getPosition() {
        return super.getPosition();
    }

    public float getPositionX() {
        return super.getPositionX();
    }

    public float getPositionY() {
        return super.getPositionY();
    }

    public void setPosition(Vector2f position) {
        super.setPosition(position);
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public void setPositionX(float x) {
        super.setPositionX(x);
    }

    public void setPositionY(float y) {
        super.setPositionY(y);
    }
}
