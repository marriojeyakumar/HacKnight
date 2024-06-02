package com.mygdx.hacknight.tiles;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hacknight.GameHud;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.SoundManager;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.items.ItemDef;
import com.mygdx.hacknight.items.Pizza;


public class CoinBlock extends PhysicalObject implements InteractableObject {
    private final TiledMapTileSet tileset;

    public CoinBlock(WorldRenderer wr, Rectangle hitbox) {
        super(wr, hitbox);
        fixture.setUserData(this);
        setCategory(HacKnight.COIN_BLOCK_COL);
        tileset = map.getTileSets().getTileSet("MarioTileset");

    }

    @Override
    public void hitMarioHead() {
        if (isBlankBlock())
            SoundManager.BLANK_BLOCK_SOUND.play();
        else {
            SoundManager.COIN_SOUND.play();
            wr.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y * HacKnight.SCALE), Pizza.class));
        }

        getCell().setTile(tileset.getTile(28));
        GameHud.updateScore(1000);
        GameHud.updateCoins(5);

    }

    private boolean isBlankBlock() {
        return getCell().getTile().getId() == 28;
    }
}