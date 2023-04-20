package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.interact.Interactable;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerEngine;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Level {

    public final LevelType type;

    private ArrayList<Player> players;
    private Interactable[] interactables;
    private ArrayDeque<Integer> unactiveChefs;
    public Level(LevelType type) {
        this.type = type;

        interactables = Arrays.stream(type.interactables).map(InteractableInLevel::initialise).toArray(Interactable[]::new);
    }
    public void update(float delta) {
        for (Interactable interactable: interactables) {
            interactable.update(delta);
        }
    }

    public void render(PolygonSpriteBatch batch) {
        for (Interactable interactable: interactables) {
            interactable.renderBottom(batch);
        }
        PlayerEngine.render(batch);
        for (Interactable interactable: interactables) {
            interactable.renderTop(batch);
        }
    }
    public void renderShapes(ShapeRenderer shapeRenderer) {
        if (true) {
            shapeRenderer.setColor(0, 1, 0,1);
            for (Rectangle rect: type.chefValidAreas) {
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }
}
