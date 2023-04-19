package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class IngredientJsonTest {
    @Test
    public void JsonTest() {
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonRoot = jsonReader.parse(Gdx.files.internal("testing/data/ingredient-test1.json"));
        HashMap<String, Ingredient> ingredientHashMap = Ingredient.loadFromJson(jsonRoot.get("ingredients"));
        Ingredient ingredient = new Ingredient(new Texture(Gdx.files.internal("textures/ingredient_bun_toasted.png")));
        assertTrue(Objects.equals(ingredientHashMap.get("buns_toasted"), ingredient));
    }
}