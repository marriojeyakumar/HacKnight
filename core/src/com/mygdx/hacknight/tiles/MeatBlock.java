package com.mygdx.hacknight.tiles;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hacknight.GameHud;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.SoundManager;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.items.Carrot;
import com.mygdx.hacknight.items.ItemDef;
import com.mygdx.hacknight.items.Meat;
import com.mygdx.hacknight.items.Pizza;


public class MeatBlock extends PhysicalObject implements InteractableObject {
    private final TiledMapTileSet tileset;

    public MeatBlock(WorldRenderer wr, Rectangle hitbox) {
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
            wr.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 * HacKnight.SCALE), Meat.class));
        }

        getCell().setTile(tileset.getTile(28));
    }

    private boolean isBlankBlock() {
        return getCell().getTile().getId() == 28;
    }
}