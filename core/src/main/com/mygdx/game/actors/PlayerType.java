package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.actors.controllers.Controller;
import com.mygdx.game.levels.Level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerType {
    final Texture texture;


    public float sizeX;
    public float sizeY;

    final Spot spawn;
    final String name;


    public PlayerType(Texture texture, Spot spawn, String name) {
        this.texture = texture;
        this.spawn = spawn;
        this.name = name;

        sizeX = 0.7f;
        sizeY = 0.4f;
    }

    public Player instantiate(Controller controller, Level level) {
        return new Player(
              this,
              controller,
              level
        );
    }

    public static List<PlayerType> loadFromJson(
          JsonValue jsonChefs,
          HashMap<String, Spot> spotHashMap
          ) {
        ArrayList<PlayerType> chefs = new ArrayList<>();
        for (JsonValue jsonChef: jsonChefs) {
            chefs.add(new PlayerType(
                  new Texture("textures/" + jsonChef.getString("texture")),
                  spotHashMap.get(jsonChef.getString("spawn")),
                  jsonChef.name));
        }
        return chefs;
    }

}
