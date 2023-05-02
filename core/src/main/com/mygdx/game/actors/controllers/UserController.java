package com.mygdx.game.actors.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.mygdx.game.Config;

public class UserController extends Controller {

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
            facingY = 0.5f;
            if (!(right || left)) {
                facingX = 0f;
            }
        }

        if (down) {
            y = y - 3.14f * delta;
            facingY = -0.5f;
            if (!(right || left)) {
                facingX = 0f;
            }
        }

        if (right) {
            x = x + 3.14f * delta;
            facingX = 0.8f;
            if (!(up || down)) {
                facingY = 0f;
            }
        }

        if (left) {
            x = x - 3.14f * delta;
            facingX = -0.8f;
            if (!(up || down)) {
                facingY = 0f;
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

    @Override
    public JsonValue saveGame() {
        JsonValue saveData = new JsonValue(ValueType.object);

        saveData.addChild("type", new JsonValue("user"));

        return saveData;
    }
}
