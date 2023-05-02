package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.util.TestingApplicationListener;
import com.mygdx.game.util.TestingLauncher;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Objects;
import static org.mockito.Mockito.mock;


@RunWith(TestingApplicationListener.class)
public class IngredientJsonTest {
    private PiazzaPanic application;
    @BeforeEach
    public void init() throws Exception{

    }
    @Test
    public void JsonTest() {

        FileHandle handle = Gdx.files.internal("assets");

        FileHandle[] files = Gdx.files.internal("./").list();
        for(FileHandle file: files) {
            System.out.print(file);
            // do something interesting here
        }
        // Gdx.gl = mock(GL20.class);
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonRoot = jsonReader.parse(Gdx.files.absolute("testing/data/ingredient-test1.json"));
        //System.out.print("");
        Texture testingtexture = new Texture("textures/ingredient_bun_toasted.png");
        HashMap<String, Ingredient> ingredientHashMap = Ingredient.loadFromJson1(jsonRoot);
        Ingredient ingredient = new Ingredient(testingtexture, "buns-toasted");

        assertTrue(ingredientEquals(ingredientHashMap.get("buns-toasted"), ingredient));
        assertEquals(ingredientHashMap.get("buns-toasted").toString(),ingredient.toString());
    }
    public boolean ingredientEquals(Ingredient a, Ingredient b){
        if (a == null || b == null) {
            return false;
        }
        //System.out.print("hjdgchxgxhcgxc" + a.texture.toString());
        return Objects.equals(a.texture.toString(), b.texture.toString());
    }
}
