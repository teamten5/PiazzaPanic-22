package com.mygdx.game.interact;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Ingredient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Combination {
    final public InteractableType interactableType;
    final public Ingredient startingChefCarrying;
    final public Ingredient startingOnStation;
    final public Ingredient endingChefCarrying;
    final public Ingredient endingOnStation;
    final boolean resetTime;

    public Combination(InteractableType interactableType, Ingredient startingChefCarrying,
          Ingredient startingOnStation, Ingredient endingChefCarrying, Ingredient endingOnStation,
          boolean resetTime) {
        this.interactableType = interactableType;
        this.startingChefCarrying = startingChefCarrying;
        this.startingOnStation = startingOnStation;
        this.endingChefCarrying = endingChefCarrying;
        this.endingOnStation = endingOnStation;
        this.resetTime = resetTime;
    }

    static public HashMap<InteractableType, ArrayList<Combination>> loadFromJson(JsonValue jsonCombinations,
          JsonValue jsonInteractables,
          JsonValue jsonIngredients,
          HashMap<String, Ingredient> ingredientHashMap,
          HashMap<String, InteractableType> interactableTypeHashMap
    ) {
        HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap = new HashMap<>();
        for (JsonValue jsonCombination: jsonCombinations) {
            for (String interactableName: jsonCombination.get("allowed-stations").asStringArray()) {
                InteractableType interactableType = interactableTypeHashMap.get(interactableName);
                addCombinationToHashmap(combinationsHashmap, new Combination(
                      interactableType,
                      ingredientHashMap.get(jsonCombination.getString("player-start")),
                      ingredientHashMap.get(jsonCombination.getString("interactable-start")),
                      ingredientHashMap.get(jsonCombination.getString("player-end")),
                      ingredientHashMap.get(jsonCombination.getString("interactable-end")),
                      true
                ));

                for (JsonValue jsonModifier: jsonCombination.get("modifiers")) {
                    if (Objects.equals(jsonModifier.name, "start-swappable")) {
                        addCombinationToHashmap(combinationsHashmap, new Combination(
                              interactableType,
                              ingredientHashMap.get(jsonCombination.getString("interactable-start")),
                              ingredientHashMap.get(jsonCombination.getString("player-start")),
                              ingredientHashMap.get(jsonCombination.getString("player-end")),
                              ingredientHashMap.get(jsonCombination.getString("interactable-end")),
                              true
                        ));
                    } else if (Objects.equals(jsonModifier.name, "plated-versions")) {
                        // TODO
                    } else {
                        System.out.println("unknown modifier: " + jsonModifier.name);
                    }
                }
            }
        }

        // generate combinations from Interactable modifiers
        for (JsonValue jsonInteractable: jsonInteractables) {
            for (JsonValue jsonModifier: jsonInteractable.get("modifiers")) {
                if (Objects.equals(jsonModifier.name, "bin")) {
                    for (Ingredient ingredient: ingredientHashMap.values()) {
                        addCombinationToHashmap(combinationsHashmap,
                              new Combination(
                                    interactableTypeHashMap.get(jsonInteractable.name),
                                    ingredient,
                                    null,
                                    null,
                                    null,
                                    true
                              ));
                    }
                } else if (Objects.equals(jsonModifier.name, "pantry-of")) {
                    addCombinationToHashmap(combinationsHashmap,
                          new Combination(
                                interactableTypeHashMap.get(jsonInteractable.name),
                                null,
                                null,
                                ingredientHashMap.get(jsonModifier.asString()),
                                null,
                                true
                          ));
                }
            }
        }

        // generate combinations from Ingredient modifiers
        for (JsonValue jsonIngredient: jsonIngredients) {
            for (JsonValue jsonModifier: jsonIngredient.get("modifiers")) {
                if (Objects.equals(jsonModifier.name, "place-on")) {
                    for (String InteractableName: jsonModifier.asStringArray()) {
                        addCombinationToHashmap(combinationsHashmap,
                              new Combination(
                                    interactableTypeHashMap.get(InteractableName),
                                    ingredientHashMap.get(jsonIngredient.name),
                                    null,
                                    null,
                                    ingredientHashMap.get(jsonIngredient.name),
                                    true
                              ));
                        addCombinationToHashmap(combinationsHashmap,
                              new Combination(
                                    interactableTypeHashMap.get(InteractableName),
                                    null,
                                    ingredientHashMap.get(jsonIngredient.name),
                                    ingredientHashMap.get(jsonIngredient.name),
                                    null,
                                    true
                              ));
                    }
                }
            }
        }
        return combinationsHashmap;
    }

    static public void addCombinationToHashmap(
          HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
          Combination combination
    ) {
        ArrayList<Combination> innerArray = combinationsHashmap.computeIfAbsent(combination.interactableType,
              k -> new ArrayList<>());
        innerArray.add(combination);
    }
}
