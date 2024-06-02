package com.mygdx.hacknight.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.WorldRenderer;

public class Ground extends PhysicalObject {
    public Ground(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(HacKnight.GROUND_COL);
    }
}