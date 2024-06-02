package com.mygdx.hacknight.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.HacKnight;


public class EnemyBorder extends PhysicalObject {
    public EnemyBorder(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setSensor(true);
        fixture.setUserData(this);
        setCategory(HacKnight.ENEMY_BORDER_COL);
    }
}
