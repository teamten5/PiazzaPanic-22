package com.mygdx.game;
import static org.junit.jupiter.api.Assertions.*;

import com.mygdx.game.levels.LevelType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mygdx.game.Ingredient;
import com.mygdx.game._convenience.IngredientStack;
import com.mygdx.game.levels.Level;
import com.mygdx.game.player.Player;
public class Playertest1 {

    @beforeEach
    void setup(){
        LevelType leveltype = new LevelType(null, null, );
        Level level = new Level();
        player = new Player(controller, 0, 0, "temp_chef_1.png", level);
    }
    @Test
    void testUpdate() {
        float delta = 0.1f; // set the time interval
        player.update(delta); // update the player
        assertEquals(0, player.getXPos()); // assert that the x-position is still 0
        assertEquals(0, player.getYPos()); // assert that the y-position is still 0
    }
}
