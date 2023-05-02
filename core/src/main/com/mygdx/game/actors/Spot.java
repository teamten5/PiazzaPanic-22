package com.mygdx.game.actors;

import com.mygdx.game.interact.Interactable;

public class Spot {
    public float posX;
    public float posY;
    public float facingX;
    public float facingY;
    public Interactable attached_table;

    public Customer occupiedBy;

    final String name;

    public Spot(float posX, float posY, float facingX, float facingY,
          String name, Interactable attached_table) {
        this.posX = posX;
        this.posY = posY;
        this.facingX = facingX;
        this.facingY = facingY;
        this.name = name;
        attachTable(attached_table);

    }
    public Spot(float posX, float posY, float facingX, float facingY, String name) {
        this(posX, posY, facingX, facingY, name, null);
    }

    public void attachTable(Interactable attached_table) {
        this.attached_table = attached_table;
        if (attached_table != null) {
            attached_table.attachedSpots.add(this);
        }
    }



}

