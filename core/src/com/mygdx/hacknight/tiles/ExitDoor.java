package com.mygdx.hacknight.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.WorldRenderer;


public class ExitDoor extends PhysicalObject {
    public ExitDoor(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(HacKnight.EXIT_DOOR_COL);
    }
}
