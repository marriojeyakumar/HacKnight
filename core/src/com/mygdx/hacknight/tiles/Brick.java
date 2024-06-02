package com.mygdx.hacknight.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.hacknight.GameHud;
import com.mygdx.hacknight.SoundManager;
import com.mygdx.hacknight.WorldRenderer;

import com.mygdx.hacknight.HacKnight;



public class Brick extends PhysicalObject implements InteractableObject {


    public Brick(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(HacKnight.BRICK_COL);
    }

    @Override
    public void hitMarioHead() {

        setCategory(HacKnight.DESTROYED_COL);
        getCell().setTile(null);
        GameHud.updateScore(50);
        SoundManager.BRICK_BREAKING_SOUND.play();
    }
}