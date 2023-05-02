package com.mygdx.game;
import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.backends.headless.mock.audio.MockSound;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.actors.PlayerType;
import com.mygdx.game.actors.Profile;
import com.mygdx.game.actors.Spot;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.levels.Difficulty;
import com.mygdx.game.levels.LevelType;
import com.mygdx.game.util.TestingController;
import org.junit.jupiter.api.BeforeEach;
import com.mygdx.game.levels.Level;
import com.mygdx.game.actors.Player;
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

import static org.junit.Assert.assertTrue;

import java.sql.Array;
import java.util.*;

import static org.mockito.Mockito.mock;
import com.mygdx.game.actors.controllers.Controller;

@RunWith(TestingApplicationListener.class)

public class Playertest1 {
    FileHandle handle = Gdx.files.internal("assets");

    FileHandle[] files = Gdx.files.internal("./").list();
    TestingController controller1 = new TestingController();
    List<com.badlogic.gdx.math.Rectangle> validArea = new ArrayList<Rectangle>(Arrays.asList(new Rectangle(-10, -10, 100, 100)));
    Texture testchef = new Texture("textures/temp_chef_1.png");
    PlayerType testingplayer = new PlayerType(testchef, new Spot(1,1,1,1,"testing"),"testing");
    LevelType leveltype = new LevelType(
            new ArrayList<>(),
            validArea,
            10,
            10,
            new ArrayList<>(),
            new ArrayList<>(),
            List.of(testingplayer),
            "potato"
    );
    Profile testingprofile = new Profile(testchef,new ArrayList<>(),1,1,1,1,1,1,1,1,new Spot(1,1,1,1,"testing"),List.of(new Spot(1,1,1,1,"testing1")), new ArrayList<>(), new MockSound(), "testing");
    Difficulty testingdiff = new Difficulty("test",List.of(testingprofile),1,10,10,5,5);
    Level level = new Level(leveltype, testingdiff);
    Player player = new Player(testingplayer, controller1 , level);
    @BeforeEach
    public void setup(){

    }
    @Test
    public void testUpdate() {
        float delta = 0.1f; // set the time interval
        player.update(delta); // update the player
        assertEquals(1, player.posX); // assert that the x-position is still 0
        assertEquals(1, player.posX); // assert that the y-position is still 0
    }

    @Test
    public void testControllers(){
        float delta = 0.2f;
        float oldx = player.posX;
        float oldy = player.posY;
        controller1.movement("left",delta);
        controller1.movement("down",delta);
        player.update(delta);
        assertTrue(player.posX == oldx);
        assertFalse((player.posY == oldy));
    }
}
