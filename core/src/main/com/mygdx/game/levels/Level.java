package com.mygdx.game.levels;

import com.mygdx.game.interact.Interactable;
import com.mygdx.game.interact.InteractableInLevel;
import com.mygdx.game.player.Player;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Level {

    public final LevelType type;

    private ArrayList<Player> players;
    private Interactable[] interactables;
    private ArrayDeque<Integer> unactiveChefs;
    public Level(LevelType type) {
        this.type = type;

        interactables = Arrays.stream(type.interactables).map(InteractableInLevel::initialise).toArray(Interactable[]::new);
    }
}
