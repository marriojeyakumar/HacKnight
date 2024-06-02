package com.mygdx.hacknight.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.HacKnight;


public abstract class Enemy extends Sprite implements Disposable {
    protected World world;
    protected Body body;
    public Vector2 movement;

    protected final float enemyWidth = HacKnight.TILE_LENGTH / 2 - 1.5f;
    protected final float enemyHeight = HacKnight.TILE_LENGTH / 2 - 3.5f;

    public Enemy(WorldRenderer worldRenderer, float x, float y) {
        this.world = worldRenderer.getWorld();
        setPosition(x, y);
    }

    public abstract void update(float dt);

    public abstract void receiveHit();

    public abstract void obliterate();

    public void reverse() {
        movement.x = -movement.x;
    }

    public Body getBody() {
        return body;
    }

    public float getXCoordinate() {
        return body.getPosition().x;
    }
}
