package com.mygdx.hacknight.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.HacKnight;


public class DefaultTile extends PhysicalObject {
    public DefaultTile(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(HacKnight.DEFAULT_COL);
    }
}