package com.mygdx.hacknight.tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.WorldRenderer;


public class PhysicalObject {
    protected final World world;
    protected Body body;
    protected Fixture fixture;
    protected final Rectangle hitbox;
    protected final TiledMap map;

    public PhysicalObject(WorldRenderer wr, Rectangle hitbox) {
        this.world = wr.getWorld();
        this.map = wr.getMap();
        this.hitbox = hitbox;

        constructMapObj();
    }
    public void constructMapObj() {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // StaticBody means not effected by gravity, cant move, etc.
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((hitbox.getX() + hitbox.getWidth() / 2) * HacKnight.SCALE, (hitbox.getY() + hitbox.getHeight() / 2)  * HacKnight.SCALE);

        shape.setAsBox(hitbox.getWidth() / 2 * HacKnight.SCALE, hitbox.getHeight() / 2 * HacKnight.SCALE);
        fixtureDef.shape = shape;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
    }

    public Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (body.getPosition().x / HacKnight.SCALE / HacKnight.TILE_LENGTH),
                (int) (body.getPosition().y / HacKnight.SCALE / HacKnight.TILE_LENGTH));
    }

    public void setCategory(short filterByte) {
        Filter filter = new Filter();
        filter.categoryBits = filterByte;
        fixture.setFilterData(filter);
    }
}
