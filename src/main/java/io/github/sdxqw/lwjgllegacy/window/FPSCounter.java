package io.github.sdxqw.lwjgllegacy.window;

import lombok.Getter;

public class FPSCounter {

    @Getter
    private static final FPSCounter instance = new FPSCounter();
    private int fps;
    private long lastFPS;

    public void updateFPS() {
        fps++;
        if (System.nanoTime() > lastFPS + 1000000000) {
            lastFPS = System.nanoTime();
            int counter = fps;
            fps = 0;
            System.out.println(counter);
        }
    }
}
