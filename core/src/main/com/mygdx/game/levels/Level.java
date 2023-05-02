package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.mygdx.game.Config;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.actors.Group;
import com.mygdx.game.actors.Profile;
import com.mygdx.game.actors.Spot;
import com.mygdx.game.interact.Interactable;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.actors.Player;
import com.mygdx.game.actors.controllers.NullController;
import com.mygdx.game.actors.controllers.UserController;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Level {

    public final LevelType type;

    public List<Player> players;
    private Interactable[] interactables;
    private ArrayDeque<Integer> unactiveChefs;


    private int minGroupSize;
    private int maxGroupSize;
    private int dailyCustomers;
    private int day = 0;
    private float dayLength;
    private float timeInDay;
    private List<Profile> profiles;

    private List<Group> futureGroups = new ArrayList<>();

    private List<Group> currentGroups = new ArrayList<>();
    int groupsToday;



    private Difficulty difficulty;
    public Level(LevelType type, Difficulty difficulty) {
        this.type = type;
        this.difficulty = difficulty;

        players = type.playerTypes.stream().map(x -> x.instantiate(new NullController(), this)).toList();
        players.get(0).controller = new UserController();

        interactables = type.interactables.stream().map(InteractableInLevel::initialise).toArray(Interactable[]::new);
        for (Interactable interactable: interactables) {
            for (Spot attachedSpot: interactable.attachedSpots) {
                attachedSpot.attached_table = interactable;
            }
        }

        minGroupSize = difficulty.startingGroupMinSize;
        maxGroupSize = difficulty.startingGroupMaxSize;
        dailyCustomers = difficulty.startingCustomers;
        dayLength = difficulty.startingDayLength;
        profiles = List.copyOf(difficulty.startingProfiles);



        generateNewDayCustomers();
    }
    public void update(float delta) {
        timeInDay += delta;

        if (timeInDay <= dayLength && timeInDay / dayLength > (float)currentGroups.size() / groupsToday) {
            currentGroups.add(futureGroups.remove(0));
        }

        for (Interactable interactable: interactables) {
            interactable.update(delta);
        }
        for (Player player: players) {
            player.update(delta);
        }
        for (Group group: currentGroups) {
            group.update(delta);
        }
    }

    public void render(PolygonSpriteBatch batch) {
        for (Interactable interactable: interactables) {
            interactable.renderBottom(batch);
        }

        for (Group group: currentGroups) {
            group.render(batch);
        }

        for(Player player : players) {
            player.render(batch);
        }

        for (Interactable interactable: interactables) {
            interactable.renderTop(batch);
        }
    }
    public void renderShapes(ShapeRenderer shapeRenderer) {
        if (Config.debugRendering) {
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

    private void generateNewDayCustomers() {
        futureGroups.clear();
        currentGroups.clear();
        while (dailyCustomers > 0) {
            int size;
            if (dailyCustomers > minGroupSize) {
                size = PiazzaPanic.random.nextInt(minGroupSize, maxGroupSize + 1);
                dailyCustomers -= size;
            } else {
                size = dailyCustomers;
                dailyCustomers = 0;
            }
            List<Profile> groupProfiles = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                groupProfiles.add(profiles.get(PiazzaPanic.random.nextInt(profiles.size())));
            }
            futureGroups.add(new Group(groupProfiles));
        }
        groupsToday = futureGroups.size();
    }

    public JsonValue saveGame() {
        JsonValue saveData = new JsonValue(ValueType.object);

        saveData.addChild("type", new JsonValue(type.name));
        JsonValue playersSaveData = new JsonValue(ValueType.array);
        for (Player player: players) {
            playersSaveData.addChild(player.saveGame());
        }
        saveData.addChild("players", playersSaveData);

        saveData.addChild("min-group-size", new JsonValue(minGroupSize));
        saveData.addChild("max-group-size", new JsonValue(maxGroupSize));
        saveData.addChild("daily-customers", new JsonValue(dailyCustomers));
        saveData.addChild("day", new JsonValue(day));
        saveData.addChild("day-length", new JsonValue(dayLength));
        saveData.addChild("time-in-day", new JsonValue(timeInDay));

        JsonValue profilesSaveData = new JsonValue(ValueType.array);
        for (Profile profile: profiles) {
            profilesSaveData.addChild(profile.saveGame());
        }
        saveData.addChild("players", playersSaveData);

        JsonValue futureGroupsSaveData = new JsonValue(ValueType.array);
        for (Group group: futureGroups) {
            futureGroupsSaveData.addChild(group.saveGame());
        }
        saveData.addChild("future-groups", playersSaveData);

        JsonValue currentGroupsSaveData = new JsonValue(ValueType.array);
        for (Group group: futureGroups) {
            currentGroupsSaveData.addChild(group.saveGame());
        }
        saveData.addChild("current-groups", playersSaveData);

        return saveData;

    }

    public static Level loadGame(JsonValue JsonSaveData) {
        return null;
    }
}
