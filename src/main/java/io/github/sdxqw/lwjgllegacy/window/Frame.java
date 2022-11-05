package io.github.sdxqw.lwjgllegacy.window;

import lombok.Getter;
import lombok.SneakyThrows;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.Display.update;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

public class Frame {

    @Getter
    private static final Frame instance = new Frame();
    private DisplayMode display;

    public void startFrame() {
        initialize();
        mainLoop();
        cleanup();
    }

    private void initialize() {
        try {
            display = new DisplayMode(800, 800);
            Display.setDisplayMode(display);
            Display.create();
            glInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private void mainLoop() {
        while (!Display.isCloseRequested()) {
            FPSCounter.getInstance().updateFPS();

            long startTime = this.getTime();

            render();

            long endTime = getTime();
            long elapsedTime = endTime - startTime;

            Thread.sleep(5 - elapsedTime);

            update();
        }
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        glPushMatrix();
        glColor3f(1.0f, 1.0f, 1.0f);
        glBegin(GL_QUADS);
        glVertex2i(-50, -50);
        glVertex2i(50, -50);
        glVertex2i(50, 50);
        glVertex2i(-50, 50);
        glEnd();
        glPopMatrix();
    }

    private void cleanup() {
        Display.destroy();
    }

    private void glInit() {
        glColor3f(1.0f, 1.0f, 1.0f);
        glDisable(GL_ALPHA_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluOrtho2D(0, display.getWidth(), 0, display.getHeight());
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glViewport(0, 0, display.getWidth(), display.getHeight());
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Display.setVSyncEnabled(false);
    }
}
