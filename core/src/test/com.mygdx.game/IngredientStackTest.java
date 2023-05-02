/*
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game._convenience.IngredientStack;
import com.mygdx.game.util.TestingApplicationListener;
import com.mygdx.game.util.TestingLauncher;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Objects;
import static org.mockito.Mockito.mock;


@RunWith(TestingApplicationListener.class)
public class IngredientStackTest{
    private PiazzaPanic application;
    @Test
    public void jsTests() {

        FileHandle handle = Gdx.files.internal("assets");

        FileHandle[] files = Gdx.files.internal("./").list();
        //Gdx.gl = mock(GL20.class);

        Ingredient ingredient1 = new Ingredient(new Texture(Gdx.files.internal("textures/ingredient_bun_toasted.png")));
        Ingredient ingredient2 = new Ingredient(new Texture(Gdx.files.internal("textures/ingredient-onion-cut.png")));
        Ingredient ingredient3 = new Ingredient(new Texture(Gdx.files.internal("textures/ingredient-tomato-onion-cut.png")));
        IngredientStack ingredientstack = new IngredientStack();
        ingredientstack.push(ingredient1);
        ingredientstack.push(ingredient2);
        ingredientstack.push(ingredient3);
        assertTrue(ingredientEquals(ingredientstack.peek(), ingredient3));
        assertTrue(true);
    }

    public boolean ingredientEquals(Ingredient a, Ingredient b){
        if (a == null || b == null) {
            return false;
        }
        //System.out.print("hjdgchxgxhcgxc" + a.texture.toString());
        return Objects.equals(a.texture.toString(), b.texture.toString());
    }
}


 */