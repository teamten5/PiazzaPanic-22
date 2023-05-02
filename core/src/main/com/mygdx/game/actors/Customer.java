package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.mygdx.game.Config;
import com.mygdx.game.actors.controllers.Controller;
import com.mygdx.game.actors.controllers.CustomerController;
import java.util.List;

public class Customer {
    public enum State {
        ENTERING,
        PICKING,
        WAITING_FOR_ORDER_TO_BE_TAKEN,
        ORDERING,
        WAITING_FOR_FOOD,
        WAITING_FOR_GROUP_FOOD,
        EATING,
        LEAVING
    }

    public float posX;
    public float posY;
    public float facingX;
    public float facingY;
    public Controller controller = new CustomerController(this);

    public Spot spot;
    public Profile profile;
    public int currentOrder = -1;
    public float progress = 0;
    public State state = State.ENTERING;

    public Group group;

    public int placeInQueue = 0;

    public Customer(Profile profile, Group group) {
        this.profile = profile;
        this.group = group;

        spot = profile.spawnLocation;

    }

    public void update(float delta) {
        progress += delta;

        controller.update(delta);
        posX = posX + controller.x;
        posY = posY + controller.y;
        facingX = controller.facingX;
        facingY = controller.facingY;

        switch (state) {

            case ENTERING:
                if (profile.eatingSpots.contains(spot) && posX == spot.posX && posY == spot.posY) {
                    setState(State.PICKING);
                }

                lookingForNewSpot: {
                    eatingSpotsLoop: for (Spot eatingSpot: profile.eatingSpots) {
                        if (eatingSpot.occupiedBy == null) {
                            for (Spot tableSeat: eatingSpot.attached_table.attachedSpots) {
                                if (tableSeat.occupiedBy != null && !group.members.contains(tableSeat.occupiedBy)) {
                                    continue eatingSpotsLoop;
                                }
                            }
                            setSpot(eatingSpot);
                            break lookingForNewSpot;
                        }
                    }
                    for (int i = placeInQueue; i < profile.waitingSpots.size(); i++) {
                        Spot waitingSpot = profile.waitingSpots.get(i);
                        if (waitingSpot.occupiedBy == null) {
                            setSpot(waitingSpot);
                            placeInQueue = i;
                            break lookingForNewSpot;
                        }
                    }
                }

                controller.update(delta);
                posX = posX + controller.x * profile.walkSpeed;
                posY = posY + controller.y * profile.walkSpeed;
                facingX = controller.facingX * profile.walkSpeed;
                facingY = controller.facingY * profile.walkSpeed;
                break;

            case PICKING:
                if (progress >= profile.pickSpeed) {
                    setState(State.WAITING_FOR_ORDER_TO_BE_TAKEN);
                }
                break;
            case WAITING_FOR_ORDER_TO_BE_TAKEN:
                if (progress >= profile.waitForOrderPatience) {
                    setState(State.LEAVING);
                }
                break;
            case ORDERING:
                if (progress >= profile.orderSpeed) {
                    setState(State.WAITING_FOR_FOOD);
                    currentOrder += 1;
                }
                break;
            case WAITING_FOR_FOOD:
                System.out.println(profile.orders.get(currentOrder));
                if (progress >= profile.waitForFoodPatience) {
                    setState(State.LEAVING);
                }
                if (profile.orders.get(currentOrder) == spot.attached_table.currentIngredient) {
                    spot.attached_table.setIngredient(null);
                    setState(State.WAITING_FOR_GROUP_FOOD);
                }
                break;
            case WAITING_FOR_GROUP_FOOD:
                if (progress >= profile.waitForGroupFoodPatience) {
                    setState(State.LEAVING);
                }
                if (group.everyoneHasTheFood()) {
                    setState(State.EATING);
                }
                break;
            case EATING:
                if (progress >= profile.eatSpeed) {
                    if (currentOrder == profile.orders.size() ) {
                        setState(State.LEAVING);
                    } else {
                        setState(State.WAITING_FOR_ORDER_TO_BE_TAKEN);
                    }
                }
                break;
            case LEAVING:
                setSpot(profile.spawnLocation);
                if (group.readyToLeave()) {
                    group.active = false;
                }
                break;
        }
    }

    public void setState(State state) {
        System.out.println(state);
        this.state = state;
        progress = 0;
    }

    public void setSpot(Spot spot) {
        this.spot.occupiedBy = null;
        spot.occupiedBy = this;
        this.spot = spot;
    }

    public void render(Batch batch) {
        batch.draw(
              profile.texture,
              posX,
              posY,
              (float) profile.texture.getWidth() / Config.unitWidthInPixels,
              (float) profile.texture.getHeight() / Config.unitHeightInPixels
        );
    }

    public void interactWith(Player player) {
        if (state == State.WAITING_FOR_ORDER_TO_BE_TAKEN) {
            System.out.println("exciting!");
            group.takeOrders();
        }
        profile.soundNeutralInteract.play();
    }

    public JsonValue saveGame() {
        JsonValue saveData = new JsonValue(ValueType.object);

        saveData.addChild("x", new JsonValue(posX));
        saveData.addChild("y", new JsonValue(posY));
        saveData.addChild("controller", controller.saveGame());
        saveData.addChild("spot", new JsonValue(spot.name));
        saveData.addChild("profile", profile.saveGame());
        saveData.addChild("current-order", new JsonValue(currentOrder));
        saveData.addChild("progress", new JsonValue(progress));
        saveData.addChild("state", new JsonValue(state.name()));
        saveData.addChild("place-in-queue", new JsonValue(placeInQueue));

        return saveData;
    }

}

