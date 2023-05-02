package com.mygdx.game.actors.controllers;

import com.badlogic.gdx.utils.JsonValue;

public abstract class Controller {

    public float x = 0;
    public float y = 0;

    public float facingX = 0;
    public float facingY = 0;
    public boolean doAction = false;
    public boolean doCombination = false;

    public boolean swapChef = false;

    abstract public void update(float delta);

    abstract public JsonValue saveGame();
}