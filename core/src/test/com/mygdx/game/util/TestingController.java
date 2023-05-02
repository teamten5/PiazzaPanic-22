package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.actors.controllers.Controller;

import java.util.ArrayList;
import java.util.List;

public class TestingController extends Controller {

    public List<String> directions = new ArrayList<String>();


    public void movement(String direction, float delta) {
        directions.add(direction.toLowerCase());
    }

    @Override
    public void update(float delta) {
        if (directions.contains("up")) {
            this.y = y + 3.14f * delta;
            this.facingY = 0.5f;
        }

        else if (directions.contains("down")) {
            this.y = y - 3.14f * delta;
            this.facingY = -0.5f;
        }

        else if (directions.contains("right")) {
            this.x = x + 3.14f * delta;
            this.facingX = 0.8f;
        }

        else if (directions.contains("left")) {
            this.x = x - 3.14f * delta;
            this.facingX = -0.8f;
        }
    }

    @Override
    public JsonValue saveGame() {
        return null;
    }
}
