package com.mygdx.game.levels;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Ingredient;
import com.mygdx.game.interact.Action;
import com.mygdx.game.interact.Combination;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.interact.InteractableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelType {
    final InteractableInLevel[] interactables;
    final public List<Rectangle> chefValidAreas;
    final public int levelSizeX;
    final public int levelSizeY;

    public LevelType(InteractableInLevel[] interactables, List<Rectangle> chefValidAreas,
          int levelSizeX, int levelSizeY) {
        this.interactables = interactables;
        this.chefValidAreas = chefValidAreas;
        this.levelSizeX = levelSizeX;
        this.levelSizeY = levelSizeY;
    }

    public Level instantiate() {
        return new Level(this);
    }

    public static HashMap<String, LevelType> loadFromJson(
          JsonValue levelsJson,
          HashMap<String, InteractableType> interactableTypeHashMap,
          HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
          HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap
    ) {
        HashMap<String, LevelType> levelTypeHashMap = new HashMap<>();
        for (JsonValue levelJson: levelsJson) {
            levelTypeHashMap.put(levelJson.name,
                  loadFromOneLevelJson(
                        levelJson,
                        interactableTypeHashMap,
                        combinationsHashmap,
                        actionHashmap
                  ));
        }
        return levelTypeHashMap;
    }

    public static LevelType loadFromOneLevelJson(
          JsonValue levelJson,
          HashMap<String, InteractableType> interactableTypeHashMap,
          HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
          HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap
    ) {
        JsonValue mapJson = levelJson.get("map");

        List<com.badlogic.gdx.math.Rectangle> validAreaRectangles = new ArrayList<>();

        for (JsonValue rect: mapJson.get("valid-areas")) {
            validAreaRectangles.add(new com.badlogic.gdx.math.Rectangle(
                  rect.getInt("x"),
                  rect.getInt("y"),
                  rect.getInt("dx"),
                  rect.getInt("dy")
            ));
        }

        int max_x = Integer.MIN_VALUE, min_x = Integer.MAX_VALUE, max_y = Integer.MIN_VALUE, min_y = Integer.MAX_VALUE;
        for (com.badlogic.gdx.math.Rectangle rect: validAreaRectangles) {
            // these rectangles always have integer values
            min_x = rect.getX() < min_x ? (int) rect.getX() : min_x;
            min_y = rect.getY() < min_y ? (int) rect.getY() : min_y;
            max_x = rect.getX() + rect.width > max_x ? (int) (rect.getX() + rect.width) : max_x;
            max_y = rect.getY() + rect.height > max_y ? (int) (rect.getY() + rect.height) : max_y;
        }

        // for edge pieces
        max_y += 1;
        max_x += 1;
        min_x -= 1;
        min_y -= 1;

        // adjustment so centre of the map is 0,0
        int bl_x = -(max_x-min_x)/2 + 1;
        int bl_y = -(max_y-min_y)/2 + 1;

        // Interactables

        List<com.badlogic.gdx.math.Rectangle> cutouts = new ArrayList<>(); // used when making levels

        JsonValue interactablesJson = mapJson.get("interactables");
        InteractableInLevel[] interactables = new InteractableInLevel[interactablesJson.size];
        for (int i = 0; i < interactablesJson.size; i++) {
            JsonValue currentInteractable = interactablesJson.get(i);
            InteractableType currentInteractbaleType = interactableTypeHashMap.get(currentInteractable.getString("type"));
            interactables[i] = currentInteractbaleType.instantiate(
                  currentInteractable.getInt("x") + bl_x,
                  currentInteractable.getInt("y") + bl_y,
                  combinationsHashmap.get(currentInteractbaleType),
                  actionHashmap.get(currentInteractbaleType)
            );
            cutouts.add(new com.badlogic.gdx.math.Rectangle(
                  interactables[i].xPos - bl_x,
                  interactables[i].yPos - bl_y,
                  currentInteractbaleType.xSize,
                  currentInteractbaleType.ySize
            ));
        }

        int map[][] = new int[max_x-min_x+1][max_y-min_y+1];

        for (com.badlogic.gdx.math.Rectangle rect: validAreaRectangles) {
            for (int i = (int) rect.getX() - min_x; i < rect.getX() + rect.getWidth() - min_x; i++) {
                for (int j = (int) rect.getY() - min_y; j < rect.getY() + rect.getHeight() - min_y; j++) {
                    map[i][j] = 1;
                }
            }
        }
        for (com.badlogic.gdx.math.Rectangle rect: cutouts) {
            for (int i = (int) rect.getX() - min_x; i < rect.getX() + rect.getWidth() - min_x; i++) {
                for (int j = (int) rect.getY() - min_y; j < rect.getY() + rect.getHeight() - min_y; j++) {
                    map[i][j] = 0;
                }
            }
        }

        List<com.badlogic.gdx.math.Rectangle> chefCollisionArea = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 1) {
                    map[i][j] = 0;
                    int n = i;
                    while (map[n + 1][j] == 1) {
                        n += 1;
                        map[n][j] = 0;

                    }
                    int m = j - 1;
                    boolean carryOn = true;
                    while (carryOn) {
                        m += 1;
                        for (int k = i; k <= n; k++) {
                            map[k][m] = 0;
                        }
                        for (int k = i; k <= n; k++) {
                            if (map[k][m + 1] == 0) {
                                carryOn = false;
                                break;
                            }
                        }
                    }
                    chefCollisionArea.add(new com.badlogic.gdx.math.Rectangle(i +min_x + bl_x, j + min_y + bl_y, n - i + 1, m - j + 1));

                }
            }
        }
        return new LevelType(
              interactables,
              chefCollisionArea,
              max_x-min_x,
              max_y-min_y
        );
    }
}
