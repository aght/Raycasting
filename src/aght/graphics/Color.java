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

    public static final Color White = new Color(255, 255, 255);
    public static final Color Silver = new Color(192, 192, 192);
    public static final Color Gray = new Color(128, 128, 128);
    public static final Color Black = new Color(0, 0, 0);
    public static final Color Red = new Color(255, 0, 0);
    public static final Color Maroon = new Color(128, 0, 0);
    public static final Color Yellow = new Color(255, 255, 0);
    public static final Color Olive = new Color(128, 128, 0);
    public static final Color Lime = new Color(0, 255, 0);
    public static final Color Green = new Color(0, 128, 0);
    public static final Color Aqua = new Color(0, 255, 255);
    public static final Color Teal = new Color(0, 128, 128);
    public static final Color Blue = new Color(0, 0, 255);
    public static final Color Navy = new Color(0, 0, 128);
    public static final Color Fuchsia = new Color(255, 0, 255);
    public static final Color Purple = new Color(128, 0, 128);

}
