package com.mygdx.hacknight.tiles;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.tiles.PhysicalObject;

public class Flagpole extends PhysicalObject {
    public Flagpole(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setSensor(true);
        fixture.setUserData(this);
        setCategory(HacKnight.FLAGPOLE_COL);
    }
}