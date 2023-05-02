package com.mygdx.game.interact;

import com.badlogic.gdx.Gdx;
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

    @Override
    public String toString() {
        return "Combination{" +
              "interactableType=" + interactableType +
              ", startingChefCarrying=" + startingChefCarrying +
              ", startingOnStation=" + startingOnStation +
              ", endingChefCarrying=" + endingChefCarrying +
              ", endingOnStation=" + endingOnStation +
              ", resetTime=" + resetTime +
              '}';
    }

    static public HashMap<InteractableType, ArrayList<Combination>> loadFromJson(
          JsonValue jsonCombinations,
          JsonValue jsonInteractables,
          JsonValue jsonProfiles,
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

                boolean startSwappable = false;
                boolean bothSwappable = false;
                boolean platedVariants = false;
                boolean playerStartPlatedVariants = false;

                for (JsonValue jsonModifier: jsonCombination.get("modifiers")) {
                    switch (jsonModifier.name) {
                        case "start-swappable":
                            if (!jsonModifier.asBoolean()) {break;}

                            startSwappable = true;
                            addCombinationToHashmap(combinationsHashmap, new Combination(
                                  interactableType,
                                  ingredientHashMap.get(jsonCombination.getString("interactable-start")),
                                  ingredientHashMap.get(jsonCombination.getString("player-start")),
                                  ingredientHashMap.get(jsonCombination.getString("player-end")),
                                  ingredientHashMap.get(jsonCombination.getString("interactable-end")),
                                  true
                            ));
                            break;
                        case "both-swappable":
                            if (!jsonModifier.asBoolean()) {break;}

                            bothSwappable = true;
                            addCombinationToHashmap(combinationsHashmap, new Combination(
                                  interactableType,
                                  ingredientHashMap.get(jsonCombination.getString("interactable-start")),
                                  ingredientHashMap.get(jsonCombination.getString("player-start")),
                                  ingredientHashMap.get(jsonCombination.getString("interactable-end")),
                                  ingredientHashMap.get(jsonCombination.getString("player-end")),
                                  true
                            ));

                            break;
                        case "plated-variants":
                            if (!jsonModifier.asBoolean()) {break;}

                            platedVariants = true;
                            addCombinationToHashmap(combinationsHashmap, new Combination(
                                  interactableType,
                                  ingredientHashMap.get(jsonCombination.getString("player-start"))._plated,
                                  ingredientHashMap.get(jsonCombination.getString("interactable-start"))._unplated,
                                  ingredientHashMap.get(jsonCombination.getString("interactable-end")),
                                  ingredientHashMap.get(jsonCombination.getString("player-end")),
                                  true
                            ));
                            // TODO: if there are multiple plate types it will always used the first one listed
                            addCombinationToHashmap(combinationsHashmap, new Combination(
                                  interactableType,
                                  ingredientHashMap.get(jsonCombination.getString("player-start"))._plated,
                                  ingredientHashMap.get(jsonCombination.getString("interactable-start")),
                                  Ingredient._plates.get(0),
                                  ingredientHashMap.get(jsonCombination.getString("player-end")),
                                  true
                            ));
                            break;
                        case "player-start-plated-variant":
                            if (!jsonModifier.asBoolean()) {break;}

                            playerStartPlatedVariants = true;
                            addCombinationToHashmap(combinationsHashmap,
                                  new Combination(
                                        interactableType,
                                        ingredientHashMap.get(jsonCombination.getString("player-start"))._plated,
                                        ingredientHashMap.get(jsonCombination.getString("interactable-start")),
                                        Ingredient._plates.get(0),
                                        ingredientHashMap.get(jsonCombination.getString("player-end")),
                                        true
                                  ));

                            break;
                        default:
                            Gdx.app.log("JSON/Combination", "Unknown modifier: " + jsonModifier.name + " on " + jsonCombination.name);
                            break;
                    }
                }

                if (startSwappable && bothSwappable) {
                    Gdx.app.error("JSON/Combination", "Combination: " + jsonCombination.name + " has both startSwappable and bothSwappable modifiers this probably isn't intended");
                }
                if (platedVariants && playerStartPlatedVariants) {
                    Gdx.app.error("JSON/Combination", "Combination: " + jsonCombination.name + " has both platedVariants and playerStartPlatedVariants modifiers this probably isn't intended");
                }

                if ((startSwappable || bothSwappable) && platedVariants) {
                    addCombinationToHashmap(combinationsHashmap, new Combination(
                          interactableType,
                          ingredientHashMap.get(jsonCombination.getString("interactable-start"))._unplated,
                          ingredientHashMap.get(jsonCombination.getString("player-start"))._plated,
                          ingredientHashMap.get(jsonCombination.getString("player-end")),
                          ingredientHashMap.get(jsonCombination.getString("interactable-end")),
                          true
                    ));
                    // TODO: if there are multiple plate types it will always used the first one listed
                    addCombinationToHashmap(combinationsHashmap, new Combination(
                          interactableType,
                          ingredientHashMap.get(jsonCombination.getString("interactable-start")),
                          ingredientHashMap.get(jsonCombination.getString("player-start"))._plated,
                          Ingredient._plates.get(0),
                          ingredientHashMap.get(jsonCombination.getString("player-end")),
                          true
                    ));
                }
                if ((startSwappable || bothSwappable) && playerStartPlatedVariants) {
                    // TODO: if there are multiple plate types it will always used the first one listed
                    addCombinationToHashmap(combinationsHashmap, new Combination(
                          interactableType,
                          ingredientHashMap.get(jsonCombination.getString("interactable-start")),
                          ingredientHashMap.get(jsonCombination.getString("player-start"))._plated,
                          Ingredient._plates.get(0),
                          ingredientHashMap.get(jsonCombination.getString("player-end")),
                          true
                    ));
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
                } else if (Objects.equals(jsonModifier.name, "customer-table")) {
                    for (JsonValue jsonProfile: jsonProfiles) {
                        for (String ingredientName: jsonProfile.get("orders").asStringArray()) {

                            addCombinationToHashmap(combinationsHashmap,
                                  new Combination(
                                        interactableTypeHashMap.get(jsonInteractable.name),
                                        ingredientHashMap.get(ingredientName),
                                        null,
                                        null,
                                        ingredientHashMap.get(ingredientName),
                                        true
                                  ));
                            addCombinationToHashmap(combinationsHashmap,
                                  new Combination(
                                        interactableTypeHashMap.get(jsonInteractable.name),
                                        null,
                                        ingredientHashMap.get(ingredientName),
                                        ingredientHashMap.get(ingredientName),
                                        null,
                                        true
                                  ));
                        }
                    }
                }
            }
        }

        // generate combinations from Ingredient modifiers
        for (Ingredient ingredient: ingredientHashMap.values()) {
            for (InteractableType interactableType: ingredient._placableOn) {
                addCombinationToHashmap(combinationsHashmap,
                      new Combination(
                            interactableType,
                            ingredient,
                            null,
                            null,
                            ingredient,
                            true
                      ));
                addCombinationToHashmap(combinationsHashmap,
                      new Combination(
                            interactableType,
                            null,
                            ingredient,
                            ingredient,
                            null,
                            true
                      ));
                if (ingredient._plated != null && ingredient._plated._placableOn.contains(interactableType)) {
                    for (Ingredient plate: Ingredient._plates) {
                        addCombinationToHashmap(combinationsHashmap,
                              new Combination(
                                    interactableType,
                                    plate,
                                    ingredient,
                                    ingredient._plated,
                                    null,
                                    true
                              ));
                        addCombinationToHashmap(combinationsHashmap,
                              new Combination(
                                    interactableType,
                                    ingredient,
                                    plate,
                                    null,
                                    ingredient._plated,
                                    true
                              ));
                    }

                }
            }

        }

        Gdx.app.log("JSON/Combination", "Created " + combinationsHashmap.values().stream().mapToInt(ArrayList::size).sum() + " combinations");
        return combinationsHashmap;
    }

    static public void addCombinationToHashmap(
          HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
          Combination combination
    ) {
        Gdx.app.debug("JSON/Combination", "Created Combination: " + combination);
        ArrayList<Combination> innerArray = combinationsHashmap.computeIfAbsent(combination.interactableType,
              k -> new ArrayList<>());
        innerArray.add(combination);
    }
}
