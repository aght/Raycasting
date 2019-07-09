package aght.graphics.shape;

public class Circle extends Ellipse {
    public Circle(long ctx, float cx, float cy, float r) {
        super(ctx, cx, cy, r, r);
    }

    public float getRadius() {
        return getRadiusX();
    }

    public void setRadius(float r) {
        setRadiusX(r);
        setRadiusY(r);
    }
}
