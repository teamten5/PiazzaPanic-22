package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameViewport extends Viewport {
    int scalingX;
    int scalingY;
    public GameViewport(
          float worldWidth,
          float worldHeight,
          Camera camera,
          int unitInPixelsX,
          int unitInPixelsY,
          int scaling
    ) {
        setWorldSize(worldWidth, worldHeight);
        setCamera(camera);
        this.scalingX = unitInPixelsX * scaling;
        this.scalingY = unitInPixelsY * scaling;
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        int viewportWidth = screenWidth + scalingX - screenWidth % scalingX;
        int viewportHeight = screenHeight + scalingY - screenHeight % scalingY;
        setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);
        setWorldSize(viewportWidth / scalingX, viewportHeight / scalingY);
        apply(centerCamera);
    }
}
