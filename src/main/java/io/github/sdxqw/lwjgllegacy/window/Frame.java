package io.github.sdxqw.lwjgllegacy.window;

import lombok.Getter;
import lombok.SneakyThrows;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.Display.update;
import static org.lwjgl.opengl.GL11.*;

public class Frame {

    @Getter
    private static final Frame instance = new Frame();

    public void startFrame() {
        initialize();
        mainLoop();
        cleanup();
    }

    private void initialize() {
        try {
            DisplayMode display = new DisplayMode(800, 800);
            Display.setDisplayMode(display);
            Display.create(new PixelFormat());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private void mainLoop() {
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();

            glInit();

            Display.sync(120);
            Display.update();

            update();
        }
        cleanup();
    }

    private void cleanup() {
        Display.destroy();
    }

    private void glInit() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_LIGHTING);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0f, Display.getWidth(), Display.getHeight(), 0.0f, -1.0f, 1.0f);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glDisable(GL_DEPTH_TEST);

        glEnable(GL_ALPHA_TEST);
        glAlphaFunc(GL_GREATER, 0.4f);
    }
}
