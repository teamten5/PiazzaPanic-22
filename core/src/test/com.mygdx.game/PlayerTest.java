/*package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.mygdx.game.player.controllers.PlayerController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerControllerTest {

    private static final float DELTA = 0.01f;

    @Test
    void testUpdateMovesPlayerUpWhenWIsPressed() {
        PlayerController controller = new PlayerController();
        controller.update(DELTA);
        float initialY = controller.getY();
        controller.keyDown(Keys.W);
        controller.update(DELTA);
        float newY = controller.getY();
        assertTrue(newY > initialY);
    }

    @Test
    void testUpdateMovesPlayerDownWhenSIsPressed() {
        PlayerController controller = new PlayerController();
        controller.update(DELTA);
        float initialY = controller.getY();
        controller.keyDown(Keys.S);
        controller.update(DELTA);
        float newY = controller.getY();
        assertTrue(newY < initialY);
    }

    @Test
    void testUpdateMovesPlayerRightWhenDIsPressed() {
        PlayerController controller = new PlayerController();
        controller.update(DELTA);
        float initialX = controller.getX();
        controller.keyDown(Keys.D);
        controller.update(DELTA);
        float newX = controller.getX();
        assertTrue(newX > initialX);
    }

    @Test
    void testUpdateMovesPlayerLeftWhenAIsPressed() {
        PlayerController controller = new PlayerController();
        controller.update(DELTA);
        float initialX = Player.getXPos();
        controller.keyDown(Input.Keys.A);
        controller.update(DELTA);
        float newX = controller.getX();
        assertTrue(newX < initialX);
    }

    @Test
    void testUpdateDoesNotMovePlayerWhenNoKeysArePressed() {
        PlayerController controller = new PlayerController();
        controller.update(DELTA);
        float initialX = controller.getX();
        float initialY = controller.getY();
        controller.update(DELTA);
        float newX = controller.getX();
        float newY = controller.getY();
        assertEquals(initialX, newX, 0.001f);
        assertEquals(initialY, newY, 0.001f);
    }

    @Test
    void testUpdateSetsDoActionToTrueWhenSpaceIsPressed() {
        PlayerController controller = new PlayerController();
        controller.update(DELTA);
        assertFalse(controller.getDoAction());
        controller.keyDown(Keys.SPACE);
        controller.update(DELTA);
        assertTrue(controller.getDoAction());
    }

    @Test
    void testUpdateSetsDoCombinationToTrueWhenShiftIsPressed() {
        PlayerController controller = new PlayerController();
        controller.update(DELTA);
        assertFalse(controller.getDoCombination());
        controller.keyDown(Keys.SHIFT_LEFT);
        controller.update(DELTA);
        assertTrue(controller.getDoCombination());
    }

    @Test
    void testUpdateSetsSwapChefToTrueWhenTabIsPressed() {
        PlayerController controller = new PlayerController();
        controller.update(DELTA);
        assertFalse(controller.getSwapChef());
        controller.keyDown(Keys.TAB);
        controller.update(DELTA);
        assertTrue(controller.getSwapChef());
    }
}

 */