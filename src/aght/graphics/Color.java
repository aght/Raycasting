package aght.graphics;

import aght.utils.NVGUtils;
import org.lwjgl.nanovg.NVGColor;

public class Color {
    private NVGColor nvgColor;

    private int r;
    private int g;
    private int b;
    private int a;

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        update();
    }

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 255;

        update();
    }

    private void update() {
        this.nvgColor = NVGUtils.rgba(r, g, b, a);
    }

    public int r() {
        return r;
    }

    public int g() {
        return g;
    }

    public int b() {
        return g;
    }

    public int a() {
        return a;
    }

    public void r(int r) {
        this.r = r;
        update();
    }

    public void g(int g) {
        this.g = g;
        update();
    }

    public void b(int b) {
        this.b = b;
        update();
    }

    public void a(int a) {
        this.a = a;
        update();
    }

    public NVGColor nvgColor() {
        return nvgColor;
    }
}
