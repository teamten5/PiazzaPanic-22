package com.mygdx.game.actors.controllers;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;

public class NullController extends Controller {

    public boolean doCombination = false;

    public void update(float delta) {}

    @Override
    public JsonValue saveGame() {
        JsonValue saveData = new JsonValue(ValueType.object);

        saveData.addChild("type", new JsonValue("null"));
        saveData.addChild("x", new JsonValue(x));
        saveData.addChild("y", new JsonValue(y));
        saveData.addChild("doAction", new JsonValue(doAction));
        saveData.addChild("facing-x", new JsonValue(facingX));
        saveData.addChild("facing-y", new JsonValue(facingY));

        return saveData;
    }

    public NullController() {
        this(0, 0, 0, 0, false);
    }

    public NullController(float x, float y, float facing_y, float facing_x, boolean doAction) {
        this.x = x;
        this.y = y;
        this.doAction = doAction;
        this.facingX = facing_x;
        this.facingY = facing_y;

        doCombination = false;
    }



}
