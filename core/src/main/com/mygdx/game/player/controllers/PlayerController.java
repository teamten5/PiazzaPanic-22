package com.mygdx.game.player.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.Config;

public class PlayerController extends Controller {

    private boolean combinationJustDone = false;
    private boolean chefSwapJustDone = false;

    @Override
    public void update(float delta) {
        y = 0;
        x = 0;
        doCombination = false;
        doAction = false;
        swapChef = false;
        boolean up = Gdx.input.isKeyPressed(Config.KBUp);
        boolean down = Gdx.input.isKeyPressed(Config.KBDown);
        boolean left = Gdx.input.isKeyPressed(Config.KBLeft);
        boolean right = Gdx.input.isKeyPressed(Config.KBRight);
        if (up) {
            y = y + 3.14f * delta;
            facing_y = 0.5f;
            if (!(right || left)) {
                facing_x = 0f;
            }
        }

        if (down) {
            y = y - 3.14f * delta;
            facing_y = -0.5f;
            if (!(right || left)) {
                facing_x = 0f;
            }
        }

        if (right) {
            x = x + 3.14f * delta;
            facing_x = 0.8f;
            if (!(up || down)) {
                facing_y = 0f;
            }
        }

        if (left) {
            x = x - 3.14f * delta;
            facing_x = -0.8f;
            if (!(up || down)) {
                facing_y = 0f;
            }
        }

        if (Gdx.input.isKeyPressed(Config.KBDoCombination) && !combinationJustDone) {
            doCombination = true;
            combinationJustDone = true;
        } else if (!Gdx.input.isKeyPressed(Config.KBDoCombination)) {
            combinationJustDone = false;
        }

        if (Gdx.input.isKeyPressed(Config.KBSwapChefs) && !chefSwapJustDone) {
            swapChef = true;
            chefSwapJustDone = true;
        } else if (!Gdx.input.isKeyPressed(Config.KBSwapChefs)) {
            chefSwapJustDone = false;
        }

        if (Gdx.input.isKeyPressed(Config.KBDoAction)) {
            doAction = true;
        }
    }
}
