package aght.graphics.shape;

import aght.graphics.Color;
import org.joml.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shape is defined in CCW order
 */
public abstract class Shape {
    private List<Vector2f> vertices;

    private Color strokeColor;
    private Color fillColor;

    private float strokeWidth;

    private Matrix3f transform;

    private Vector2f position;
    private Vector2f origin;
    private Vector2f scale;
    private float rotation;

    private boolean updateTransform;

    protected Shape() {
        strokeColor = new Color(255, 255, 255);
        fillColor = new Color(255, 255, 255);
        strokeWidth = 0;

        position = new Vector2f(0, 0);
        origin = new Vector2f(0, 0);
        scale = new Vector2f(1, 1);
        rotation = 0;

        updateTransform = true;

        fillColor = new Color(255, 255, 255);
    }

    protected Shape(List<Vector2f> vertices) {
        this();
        this.vertices = vertices;
    }

    protected Shape(Vector2f[] vertices) {
        this();
        this.vertices = new ArrayList<Vector2f>(Arrays.asList(vertices));
    }

    protected Shape(float[] positions) {
        this();

        if (positions.length % 2 != 0) {
            throw new RuntimeException("Cannot have an odd number of positions");
        }

        vertices = new ArrayList<Vector2f>();

        for (int i = 0; i < positions.length; i += 2) {
            float vx = positions[i];
            float vy = positions[i + 1];

            Vector2f vertex = new Vector2f(vx, vy);

            vertices.add(vertex);
        }
    }

    // #################################################################

    protected Vector2f getPosition() {
        return position;
    }

    protected float getPositionX() {
        return position.x();
    }

    protected float getPositionY() {
        return position.y();
    }

    protected void setPosition(Vector2f position) {
        this.position = position;
        updateTransform = true;
    }

    protected void setPosition(float x, float y) {
        position.set(x, y);
        updateTransform = true;
    }

    protected void setPositionX(float x) {
        position.set(x, position.y());
        updateTransform = true;
    }

    protected void setPositionY(float y) {
        position.set(position.x(), y);
        updateTransform = true;
    }

    public Vector2f getOrigin() {
        return origin;
    }

    public float getOriginX() {
        return origin.x();
    }

    public float getOriginY() {
        return origin.y();
    }

    public void setOrigin(Vector2f origin) {
        this.origin = origin;
        updateTransform = true;
    }

    public void setOrigin(float x, float y) {
        origin.set(x, y);
        updateTransform = true;
    }

    public void setOriginX(float x) {
        origin.set(x, origin.y());
        updateTransform = true;
    }

    public void setOriginY(float y) {
        origin.set(origin.x(), y);
        updateTransform = true;
    }

    public Vector2f getScale() {
        return scale;
    }

    public float getScaleX() {
        return scale.x();
    }

    public float getScaleY() {
        return scale.y();
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
        updateTransform = true;
    }

    public void setScale(float x, float y) {
        scale.set(x, y);
        updateTransform = true;
    }

    public void setScaleX(float x) {
        scale.set(x, scale.y());
        updateTransform = true;
    }

    public void setScaleY(float y) {
        scale.set(scale.x(), y);
        updateTransform = true;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        updateTransform = true;
    }

    protected Matrix3f getTransform() {
        if (updateTransform) {
            float cos = (float) Math.cos(-rotation);
            float sin = (float) Math.sin(-rotation);

            float sx = scale.x() * cos;
            float sy = scale.y() * cos;
            float ky = scale.x() * sin;
            float kx = scale.y() * sin;
            float tx = -origin.x() * sx - origin.y() * kx + position.x();
            float ty = origin.x() * ky - origin.y() * sy + position.y();

            transform = new Matrix3f(sx, kx, tx, -ky, sy, ty, 0, 0, 1);

            updateTransform = false;
        }

        return transform;
    }

    // #################################################################

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    // #################################################################
    // # These can be have their visibility overridden to allow public #
    // # access.                                                       #
    // #################################################################
    protected List<Vector2f> getVertices() {
        return vertices;
    }

    protected void setVertices(List<Vector2f> vertices) {
        this.vertices = vertices;
    }

    protected void setVertices(Vector2f[] vertices) {
        this.vertices = new ArrayList<Vector2f>(Arrays.asList(vertices));
    }

    protected void setVertex(Vector2f vertex, int index) {
        vertices.set(index, vertex);
    }

    protected Vector2f getVertex(int index) {
        return vertices.get(index);
    }

    protected void addVertex(Vector2f vertex) {
        this.vertices.add(vertex);
    }

    protected void addVertex(Vector2f vertex, int index) {
        this.vertices.add(index, vertex);
    }

    protected void removeVertex(int index) {
        vertices.remove(index);
    }

    // #################################################################

    public abstract void render();
}
