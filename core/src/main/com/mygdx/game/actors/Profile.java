package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.mygdx.game.Ingredient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Profile {

    final public Texture texture;
    final public List<Ingredient> orders;
    final public float walkSpeed;

    final public float waitForSeatPatience;
    final public float pickSpeed;
    final public float waitForOrderPatience;
    final public float orderSpeed;
    final public float waitForFoodPatience;
    final public float waitForGroupFoodPatience;
    final public float eatSpeed;

    final public Spot spawnLocation;
    final public List<Spot> waitingSpots;
    final public List<Spot> eatingSpots;

    final public Sound soundNeutralInteract;

    final public String name;

    public Profile(Texture texture, List<Ingredient> orders, float walkSpeed,
          float waitForSeatPatience, float pickSpeed, float waitForOrderPatience, float orderSpeed,
          float waitForFoodPatience, float waitForGroupFoodPatience, float eatSpeed,
          Spot spawnLocation, List<Spot> waitingSpots, List<Spot> eatingSpots,
          Sound soundNeutralInteract, String name) {
        this.texture = texture;
        this.orders = orders;
        this.walkSpeed = walkSpeed;
        this.waitForSeatPatience = waitForSeatPatience;
        this.pickSpeed = pickSpeed;
        this.waitForOrderPatience = waitForOrderPatience;
        this.orderSpeed = orderSpeed;
        this.waitForFoodPatience = waitForFoodPatience;
        this.waitForGroupFoodPatience = waitForGroupFoodPatience;
        this.eatSpeed = eatSpeed;
        this.spawnLocation = spawnLocation;
        this.waitingSpots = waitingSpots;
        this.eatingSpots = eatingSpots;
        this.soundNeutralInteract = soundNeutralInteract;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Profile{" +
              "orders=" + orders +
              ", walkSpeed=" + walkSpeed +
              ", waitForSeatPatience=" + waitForSeatPatience +
              ", pickSpeed=" + pickSpeed +
              ", waitForOrderPatience=" + waitForOrderPatience +
              ", orderSpeed=" + orderSpeed +
              ", waitForFoodPatience=" + waitForFoodPatience +
              ", waitForGroupFoodPatience=" + waitForGroupFoodPatience +
              ", eatSpeed=" + eatSpeed +
              ", SpawnLocation=" + spawnLocation +
              ", WaitLocations=" + waitingSpots +
              ", eatingSpots=" + eatingSpots +
              '}';
    }

    public static List<Profile> loadfromJson(
          JsonValue jsonProfiles,
          JsonValue jsonLevelProfiles,
          HashMap<String, Ingredient> ingredientHashMap,
          HashMap<String, Spot> spotHashMap,
          List<Spot> eatingSpots
    ) {
        ArrayList<Profile> profiles = new ArrayList<>();

        for (JsonValue jsonLevelProfile: jsonLevelProfiles) {

            JsonValue jsonProfile = jsonProfiles.get(jsonLevelProfile.getString("name"));
            List<Ingredient> orders = new ArrayList<>();
            for (String orderName: jsonProfile.get("orders").asStringArray()) {
                orders.add(ingredientHashMap.get(orderName));
            }
            Profile profile = new Profile(

                  new Texture("textures/" + jsonProfile.getString("texture")),
                  orders,
                  jsonProfile.getFloat("walk-speed"),
                  jsonProfile.getFloat("wait-for-seat-patience"),
                  jsonProfile.getFloat("pick-speed"),
                  jsonProfile.getFloat("wait-for-order-patience"),
                  jsonProfile.getFloat("order-speed"),
                  jsonProfile.getFloat("wait-for-food-patience"),
                  jsonProfile.getFloat("wait-for-group-patience"),
                  jsonProfile.getFloat("eat-speed"),
                  spotHashMap.get(jsonLevelProfile.getString("spawn")),
                  Arrays.stream(jsonLevelProfile.get("waiting-spots").asStringArray()).map(spotHashMap::get).toList(),
                  eatingSpots,
                  Gdx.audio.newSound(Gdx.files.internal("sounds/"+
                        jsonProfile.getString("neutral-interact-sound"))),
                  jsonProfile.name);
            profiles.add(profile);
            Gdx.app.debug("JSON/Profile", "Created Profile" + profile);
        }

        return  profiles;
    }


    public JsonValue saveGame() {
        JsonValue saveData = new JsonValue(ValueType.object);

        saveData.addChild("type", new JsonValue(name));
        saveData.addChild("spawn", new JsonValue(spawnLocation.name));
        JsonValue waitingSpotSaveData = new JsonValue(ValueType.array);
        for (Spot waitingSpot: waitingSpots) {
            waitingSpotSaveData.addChild(new JsonValue(waitingSpot.name));
        }
        saveData.addChild("waiting-spots", waitingSpotSaveData);


        return saveData;
    }

}


