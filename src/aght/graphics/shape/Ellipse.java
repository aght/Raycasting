package aght.graphics.shape;

import org.joml.Matrix3f;

import static org.lwjgl.nanovg.NanoVG.*;

public class Ellipse extends Shape {

    private long ctx;

    private float rx;
    private float ry;

    public Ellipse(long ctx, float cx, float cy, float rx, float ry) {
        super();

        this.ctx = ctx;
        this.rx = rx;
        this.ry = ry;

        setPosition(cx, cy);
        setOrigin(cx, cy);
    }

    public void render() {
        nvgSave(ctx);
        nvgBeginPath(ctx);

        Matrix3f transform = getTransform();

        float a = transform.m00();
        float b = transform.m10();
        float c = transform.m01();
        float d = transform.m11();
        float e = transform.m02();
        float f = transform.m12();

        nvgTransform(ctx, a, b, c, d, e, f);

        nvgEllipse(ctx, getPositionX(), getPositionY(), rx, ry);

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

    public float getCenterX() {
        return getPositionX();
    }

    public void setCenterX(float cx) {
        setPositionX(cx);
    }

    public float getCenterY() {
        return getPositionY();
    }

    public void setCenterY(float cy) {
        setPositionY(cy);
    }

    public float getRadiusX() {
        return rx;
    }

    public void setRadiusX(float rx) {
        this.rx = rx;
    }

    public float getRadiusY() {
        return ry;
    }

    public void setRadiusY(float ry) {
        this.ry = ry;
    }
}
