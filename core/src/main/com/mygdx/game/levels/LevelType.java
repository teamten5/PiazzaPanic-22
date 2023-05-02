package com.mygdx.game.levels;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Ingredient;
import com.mygdx.game.actors.PlayerType;
import com.mygdx.game.actors.Spot;
import com.mygdx.game.interact.Action;
import com.mygdx.game.interact.Combination;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.interact.InteractableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.javatuples.Triplet;

public class LevelType {
    final List<InteractableInLevel> interactables;
    final public List<Rectangle> chefValidAreas;
    final public int levelSizeX;
    final public int levelSizeY;

    final public List<InteractableInLevel> customerTables;

    final public List<Difficulty> difficulties;

    final public List<PlayerType> playerTypes;

    final public String name;

    public LevelType(List<InteractableInLevel> interactables, List<Rectangle> chefValidAreas,
          int levelSizeX, int levelSizeY, List<InteractableInLevel> customerTables,
          List<Difficulty> difficulties, List<PlayerType> playerTypes, String name) {
        this.interactables = interactables;
        this.chefValidAreas = chefValidAreas;
        this.levelSizeX = levelSizeX;
        this.levelSizeY = levelSizeY;
        this.customerTables = customerTables;
        this.difficulties = difficulties;
        this.playerTypes = playerTypes;
        this.name = name;
    }

    public Level instantiate(int difficulty) {
        return new Level(this, difficulties.get(difficulty));
    }

    public static HashMap<String, LevelType> loadFromJson(
          JsonValue levelsJson,
          HashMap<String, InteractableType> interactableTypeHashMap,
          HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
          HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap,
          JsonValue profilesJson,
          HashMap<String, Ingredient> ingredientHashMap
    ) {
        HashMap<String, LevelType> levelTypeHashMap = new HashMap<>();
        for (JsonValue levelJson: levelsJson) {
            levelTypeHashMap.put(levelJson.name,
                  loadFromOneLevelJson(
                        levelJson,
                        interactableTypeHashMap,
                        combinationsHashmap,
                        actionHashmap,
                        profilesJson,
                        ingredientHashMap
                  ));
        }
        return levelTypeHashMap;
    }

    public static LevelType loadFromOneLevelJson(
          JsonValue levelJson,
          HashMap<String, InteractableType> interactableTypeHashMap,
          HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
          HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap,
          JsonValue profilesJson,
          HashMap<String, Ingredient> ingredientHashMap
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
        List<InteractableInLevel> interactables = new ArrayList<>(interactablesJson.size);
        for (int i = 0; i < interactablesJson.size; i++) {
            JsonValue currentInteractable = interactablesJson.get(i);
            InteractableType currentInteractbaleType = interactableTypeHashMap.get(currentInteractable.getString("type"));
            interactables.add(currentInteractbaleType.instantiate(
                  currentInteractable.getInt("x") + bl_x,
                  currentInteractable.getInt("y") + bl_y,
                  combinationsHashmap.get(currentInteractbaleType),
                  actionHashmap.get(currentInteractbaleType)
            ));

        }

        // generate chairs

        // TODO check chairs don't overlap with something or are outside the level


        List<Spot> eatingSpots = new ArrayList<>();
        List<InteractableInLevel> customerTables = new ArrayList<>();

        for (int i = 0; i < interactables.size(); i++) {
            InteractableInLevel interactable = interactables.get(i);
            if (interactable.type._customerTable.isEmpty()) {
                continue;
            }

            customerTables.add(interactable);

            for (Triplet<String, Integer, Integer> chairData : interactable.type._customerTable) {
                InteractableType currentInteractableType = interactableTypeHashMap.get(
                      chairData.getValue0());
                interactables.add(currentInteractableType.instantiate(
                      interactable.xPos + chairData.getValue1(),
                      interactable.yPos + chairData.getValue2(),
                      combinationsHashmap.get(currentInteractableType),
                      actionHashmap.get(currentInteractableType)
                ));

                Spot eatingSpot = new Spot(
                      interactable.xPos + chairData.getValue1(),
                      interactable.yPos + chairData.getValue2(),
                      -chairData.getValue1(),
                      -chairData.getValue2(),
                      null);
                eatingSpots.add(eatingSpot);
                interactable.attachedSpots.add(eatingSpot);

            }
        }

        // create a list of collision areas with interactables
        for (InteractableInLevel interactable : interactables) {

            if (interactable.type.collision) {
                cutouts.add(new Rectangle(
                      interactable.xPos - bl_x,
                      interactable.yPos - bl_y,
                      interactable.type.xSize,
                      interactable.type.ySize
                ));
            }
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

        HashMap<String, Spot> spotHashMap = new HashMap<>();
        for (JsonValue spotJson: mapJson.get("spots")) {
            Spot spot = new Spot(
                  spotJson.getFloat("x") + bl_x,
                  spotJson.getFloat("y") + bl_y,
                  spotJson.getFloat("facing-x"),
                  spotJson.getFloat("facing-y"),
                  spotJson.name);
            spotHashMap.put(spotJson.name, spot);
        }

        // difficulties and profiles

        List<Difficulty> difficulties = Difficulty.loadFromJson(
              levelJson.get("level-difficulties"),
              profilesJson,
              ingredientHashMap,
              spotHashMap,
              eatingSpots
        );

        // Chefs

        List<PlayerType> playerTypes = PlayerType.loadFromJson(
              levelJson.get("chefs"),
              spotHashMap
        );



        return new LevelType(
              interactables,
              chefCollisionArea,
              max_x-min_x,
              max_y-min_y,
              customerTables,
              difficulties,
              playerTypes,
              levelJson.name);
    }
}
