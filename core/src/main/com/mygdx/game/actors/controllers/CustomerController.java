package com.mygdx.game.actors.controllers;

import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.mygdx.game.actors.Customer;

public class CustomerController extends Controller {
    Customer controlled;


    public CustomerController(Customer controlled) {
        this.controlled = controlled;
    }

    @Override
    public void update(float delta) {
        x =  controlled.spot.posX - controlled.posX;
        y =  controlled.spot.posY - controlled.posY;
        facingX = controlled.spot.facingX;
        facingY = controlled.spot.facingY;
    }

    public JsonValue saveGame() {
        JsonValue saveData = new JsonValue(ValueType.object);

        saveData.addChild("type", new JsonValue("customer"));

        return saveData;
    }
}
