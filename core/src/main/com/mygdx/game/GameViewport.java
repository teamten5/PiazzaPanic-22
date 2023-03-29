package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameViewport extends Viewport {
    int scaling;
    public GameViewport(float worldWidth, float worldHeight, Camera camera, int unitInPixels, int scaling) {
        setWorldSize(worldWidth, worldHeight);
        setCamera(camera);
        this.scaling = unitInPixels * scaling;
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        int viewportWidth = screenWidth + scaling - screenWidth % scaling;
        int viewportHeight = screenHeight + scaling - screenHeight % scaling;
        setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);
        setWorldSize(viewportWidth / scaling, viewportHeight / scaling);
        apply(centerCamera);
    }
}
