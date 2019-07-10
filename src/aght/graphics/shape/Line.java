package aght.graphics.shape;

import aght.graphics.Color;
import org.joml.Matrix3f;
import org.joml.Vector2f;

import static org.lwjgl.nanovg.NanoVG.*;

public class Line extends Shape {
    private long ctx;

    private Vector2f end;

    public Line(long ctx, float x1, float y1, float x2, float y2) {
        super();
        this.ctx = ctx;

        this.end = new Vector2f(x2, y2);

        setPosition(x1, y1);
        setOrigin(x1, y1);
        setStrokeWidth(1);
        setStrokeColor(new Color(255, 255, 255));
    }

    @Override
    public void render() {
        nvgSave(ctx);

        Matrix3f transform = getTransform();

        float a = transform.m00();
        float b = transform.m10();
        float c = transform.m01();
        float d = transform.m11();
        float e = transform.m02();
        float f = transform.m12();

        nvgTransform(ctx, a, b, c, d, e, f);

        nvgBeginPath(ctx);
        nvgMoveTo(ctx, getPositionX(), getPositionY());
        nvgLineTo(ctx, end.x(), end.y());

        nvgStrokeColor(ctx, getStrokeColor().nvgColor());
        nvgStrokeWidth(ctx, getStrokeWidth());
        nvgStroke(ctx);

        nvgRestore(ctx);
    }

    public float getStartX() {
        return getPositionX();
    }

    public float getStartY() {
        return getPositionY();
    }

    public float getEndX() {
        return end.x();
    }

    public float getEndY() {
        return end.y();
    }

    public void setStartX(float x) {
        setPositionX(x);
    }

    public void setStartY(float y) {
        setPositionY(y);
    }

    public void setEndX(float x) {
        this.end.x = x;
    }

    public void setEndY(float y) {
        this.end.y = y;
    }
}
