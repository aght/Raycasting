package aght.window;

import aght.window.input.keyboard.Keyboard;
import aght.window.input.mouse.MouseButton;
import aght.window.input.mouse.MousePosition;
import aght.window.input.mouse.MouseScroll;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.nanovg.NanoVGGL3.nvgDelete;
import static org.lwjgl.opengl.GL.setCapabilities;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL11C.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class Context {
    private long window;
    private long nvgctx;

    private static int frameBufferWidth;
    private static int frameBufferHeight;

    private static float contentScaleX;
    private static float contentScaleY;

    private int width;
    private int height;
    private int msaa;

    private String title;

    protected Context(int width, int height, String title, int msaa) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.msaa = msaa;
    }

    public void run() {
        init();
        setup();
        loop();
        cleanUp();
    }

    private void init() {
        if (!glfwInit()) {
            throw new RuntimeException("Failed to init GLFW.");
        }

        glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);

        glfwWindowHint(GLFW_SAMPLES, msaa);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        glfwSetKeyCallback(window, new Keyboard());
        glfwSetCursorPosCallback(window, new MousePosition());
        glfwSetMouseButtonCallback(window, new MouseButton());
        glfwSetScrollCallback(window, new MouseScroll());

        if (window == NULL) {
            glfwTerminate();
            throw new RuntimeException();
        }

        try (MemoryStack stack = stackPush()) {
            IntBuffer fw = stack.mallocInt(1);
            IntBuffer fh = stack.mallocInt(1);
            FloatBuffer sx = stack.mallocFloat(1);
            FloatBuffer sy = stack.mallocFloat(1);

            glfwGetFramebufferSize(window, fw, fh);
            frameBufferWidth = fw.get(0);
            frameBufferHeight = fh.get(0);

            glfwGetWindowContentScale(window, sx, sy);
            contentScaleX = sx.get(0);
            contentScaleY = sy.get(0);
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(0);

        nvgctx = nvgCreate(0);

        if (nvgctx == NULL) {
            throw new RuntimeException("Could not init NanoVG.");
        }
    }

    private void cleanUp() {
        nvgDelete(nvgctx);

        setCapabilities(null);

        glfwFreeCallbacks(window);
        glfwTerminate();
    }

    private void loop() {
        final int width = (int) (frameBufferWidth / contentScaleX);
        final int height = (int) (frameBufferHeight / contentScaleY);
        final float devicePixelRatio = Math.max(contentScaleX, contentScaleY);

        glViewport(0, 0, frameBufferWidth, frameBufferHeight);
        glClearColor(0, 0, 0, 0);

        final int updatesPerSecond = 60;
        final long skipTime = 1000000000 / updatesPerSecond;
        final int maxFrameSkip = 5;

        long prevTime = System.nanoTime();

        int loops;

        while (!glfwWindowShouldClose(window)) {
            loops = 0;

            long currTime = System.nanoTime();

            while (currTime > prevTime && loops < maxFrameSkip) {

                glfwPollEvents();
                update();

                prevTime += skipTime;
                loops++;
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
            nvgBeginFrame(nvgctx, width, height, devicePixelRatio);
            render();
            nvgEndFrame(nvgctx);
            glfwSwapBuffers(window);
        }
    }

    public abstract void setup();

    public abstract void update();

    public abstract void render();

    public long getWindow() {
        return window;
    }

    public long getNvgctx() {
        return nvgctx;
    }

    public static int getFrameBufferWidth() {
        return frameBufferWidth;
    }

    public static int getFrameBufferHeight() {
        return frameBufferHeight;
    }

    public static float getContentScaleX() {
        return contentScaleX;
    }

    public static float getContentScaleY() {
        return contentScaleY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMSAA() {
        return msaa;
    }

    public String getTitle() {
        return title;
    }
}
