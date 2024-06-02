package com.mygdx.hacknight.tiles;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.hacknight.GameHud;
import com.mygdx.hacknight.SoundManager;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.HacKnight;



public class Coin extends PhysicalObject implements InteractableObject {

    public Coin(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setSensor(true);
        fixture.setUserData(this);
        setCategory(HacKnight.COIN_COl);
    }

    @Override
    public void hitMarioHead() {
        setCategory(HacKnight.DESTROYED_COL);
        getCell().setTile(null);
        GameHud.updateScore(200);
        GameHud.updateCoins(1);
        SoundManager.COIN_SOUND.play(0.25f);
    }
}