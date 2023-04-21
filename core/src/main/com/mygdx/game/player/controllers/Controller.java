package com.mygdx.game.player.controllers;

public abstract class Controller {

    public float x = 0;
    public float y = 0;

    public float facing_x = 0;
    public float facing_y = 0;
    boolean doAction = false;
    public boolean doCombination = false;

    boolean swapChef = false;

    abstract public void update(float delta);
}