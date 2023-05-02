package com.mygdx.game.levels;

import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Ingredient;
import com.mygdx.game.actors.Profile;
import com.mygdx.game.actors.Spot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Difficulty {
    final public String name;
    final public List<Profile> startingProfiles;
    final public int startingGroupMinSize;
    final public int startingGroupMaxSize;
    final public int startingCustomers;
    final public int days;
    final public float startingDayLength;


    public Difficulty(String name, List<Profile> startingProfiles, int startingGroupMinSize,
          int startingGroupMaxSize, int startingCustomers, int days, float startingDayLength) {
        this.name = name;
        this.startingProfiles = startingProfiles;
        this.startingGroupMinSize = startingGroupMinSize;
        this.startingGroupMaxSize = startingGroupMaxSize;
        this.startingCustomers = startingCustomers;
        this.days = days;
        this.startingDayLength = startingDayLength;
    }

    public static List<Difficulty> loadFromJson(
          JsonValue jsonDifficulties,
          JsonValue jsonProfiles,
          HashMap<String, Ingredient> ingredientHashMap,
          HashMap<String, Spot> spotHashMap,
          List<Spot> eatingSpots
    ) {
        ArrayList<Difficulty> difficulties = new ArrayList<>();
        for (JsonValue jsonDifficulty: jsonDifficulties) {
            List<Profile> levelProfiles = Profile.loadfromJson(
                  jsonProfiles,
                  jsonDifficulty.get("profiles"),
                  ingredientHashMap,
                  spotHashMap,
                  eatingSpots
            );
            difficulties.add(new Difficulty(
                  jsonDifficulty.getString("name"),
                  levelProfiles,
                  jsonDifficulty.getInt("starting-min-group-size"),
                  jsonDifficulty.getInt("starting-max-group-size"),
                  jsonDifficulty.getInt("starting-customers"),
                  jsonDifficulty.getInt("days"),
                  jsonDifficulty.getFloat("starting-day-length")));
        }
        return difficulties;
    }
}
