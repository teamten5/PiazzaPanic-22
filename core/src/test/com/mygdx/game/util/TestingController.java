package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.player.controllers.Controller;

import java.util.ArrayList;
import java.util.List;

public class TestingController extends Controller {
    public float x = 0;
    public float y = 0;

    public float facing_x = 0;
    public float facing_y = 0;
    public List<String> directions = new ArrayList<String>();


    public void movement(String direction, float delta) {
        directions.add(direction.toLowerCase());
    }

    @Override
    public void update(float delta) {
        if (directions.contains("up")) {
            y = y + 3.14f * delta;
            facing_y = 0.5f;
        }

        else if (directions.contains("down")) {
            y = y - 3.14f * delta;
            facing_y = -0.5f;
        }

        else if (directions.contains("right")) {
            x = x + 3.14f * delta;
            facing_x = 0.8f;
        }

        else if (directions.contains("left")) {
            x = x - 3.14f * delta;
            facing_x = -0.8f;
        }
    }
}
