package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ingredient;
import com.mygdx.game.interact.Interactable;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.controllers.NullController;
import com.mygdx.game.player.controllers.PlayerController;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Level {

    public final LevelType type;

    public ArrayList<Player> players;
    private Interactable[] interactables;
    private ArrayDeque<Integer> unactiveChefs;
    public Level(LevelType type) {
        this.type = type;

        players = new ArrayList<>(Arrays.asList(
              new Player(new PlayerController(),  -1,  -2, "textures/temp_chef_1.png", this),
              new Player(new NullController(),  -2,  -2, "textures/temp_chef_2.png", this)
        ));

        interactables = Arrays.stream(type.interactables).map(InteractableInLevel::initialise).toArray(Interactable[]::new);
    }
    public void update(float delta) {
        for (Interactable interactable: interactables) {
            interactable.update(delta);
        }
        for (Player player: players) {
            player.update(delta);
        }
    }

    public void render(PolygonSpriteBatch batch) {
        for (Interactable interactable: interactables) {
            interactable.renderBottom(batch);
        }
        for(Player player : players) {
            batch.draw(player.getSprite(), player.getXPos(), player.getYPos(), 0.8f, 0.8f * 1.5f);
            Ingredient ingredient = player.getCurrentIngredient();
            if (ingredient != null) {
                batch.draw(ingredient.texture, player.getXPos(), player.getYPos() + 1.1f, 0.7f, 0.7f);
            }
        }
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
    public Interactable interactableAt(float x, float y) {
        for (Interactable interactable : interactables) {
            if ((interactable.instanceOf.xPos <= x && x <= interactable.instanceOf.xPos + interactable.instanceOf.type.xSize) && interactable.instanceOf.yPos <= y
                  && y <= interactable.instanceOf.yPos + interactable.instanceOf.type.ySize) {
                return interactable;
            }
        }
        return null;
    }
}
