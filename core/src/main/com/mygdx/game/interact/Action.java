package com.mygdx.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Ingredient;
import java.util.ArrayList;
import java.util.HashMap;

public class Action {
    final InteractableType interactableType;
    final Ingredient input;
    final Ingredient output;
    final float timeToComplete;
    final boolean chefRequired;

    public Action(InteractableType interactableType, Ingredient input, Ingredient output, float timeToComplete,
          boolean chefRequired) {
        this.interactableType = interactableType;
        this.input = input;
        this.output = output;
        this.timeToComplete = timeToComplete;
        this.chefRequired = chefRequired;
    }

    static public HashMap<InteractableType, HashMap<Ingredient, Action>> loadFromJson(
          JsonValue jsonActions,
          HashMap<String, Ingredient> ingredientHashMap,
          HashMap<String, InteractableType> interactableTypeHashMap
    ) {
        HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap = new HashMap<>();
        for (JsonValue jsonAction: jsonActions) {
            for (String interactableName: jsonAction.get("stations").asStringArray()) {
                InteractableType interactableType = interactableTypeHashMap.get(interactableName);
                Action action = new Action(
                      interactableType,
                      ingredientHashMap.get(jsonAction.getString("start")),
                      ingredientHashMap.get(jsonAction.getString("end")),
                      jsonAction.getFloat("time") / 1000,
                      jsonAction.getBoolean("chef-required")
                );
                addActionToHashmap(actionHashmap, action);

            }
        }
        Gdx.app.log("JSON/Action", "Created " + actionHashmap.values().stream().mapToInt(
              HashMap::size).sum() + " actions");
        return actionHashmap;
    }

    static public void addActionToHashmap(
          HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap,
          Action action
    ) {
        HashMap<Ingredient, Action> innerHashmap = actionHashmap.computeIfAbsent(
              action.interactableType, k -> new HashMap<>());
        innerHashmap.put(action.input, action);
    }
}
