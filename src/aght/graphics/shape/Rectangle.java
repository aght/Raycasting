package aght.graphics.shape;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import static org.lwjgl.nanovg.NanoVG.*;

public class Rectangle extends Shape {

    private long ctx;

    private float w;
    private float h;

    public Rectangle(long ctx, float x, float y, float w, float h) {
        super();

        this.ctx = ctx;
        this.w = w;
        this.h = h;

        setPosition(new Vector2f(x, y));
        setOrigin(new Vector2f(x, y));
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

        nvgRect(ctx, getPosition().x(), getPosition().y(), w, h);

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

    public float getX() {
        return getPositionX();
    }

    public void setX(float x) {
        setPositionX(x);
    }

    public float getY() {
        return getPositionY();
    }

    public void setY(float y) {
        setPositionY(y);
    }

    public float getWidth() {
        return w;
    }

    public void setWidth(float w) {
        this.w = w;
    }

    public float getHeight() {
        return h;
    }

    public void setHeight(float h) {
        this.h = h;
    }
}
