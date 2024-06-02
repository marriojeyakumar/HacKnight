package com.mygdx.hacknight.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.hacknight.GameHud;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.SoundManager;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.screens.PlayScreen;


public class Goomba extends Enemy {
    private boolean isDead = false;
    private boolean hasDied = false;

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean setToObliterate;
    private boolean obliterated;

    public Goomba(WorldRenderer worldRenderer, float x, float y){
        super(worldRenderer, x, y);
        destroyed = false;
        setToDestroy = false;
        movement = new Vector2(5f * (Math.random() - 0.5 >= 0 ? 1 : -1), -8f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(enemyWidth * HacKnight.SCALE, enemyHeight * HacKnight.SCALE);

        fixtureDef.filter.categoryBits = HacKnight.ENEMY_COL;
        fixtureDef.filter.maskBits = HacKnight.DEFAULT_COL | HacKnight.BRICK_COL |
                HacKnight.COIN_BLOCK_COL | HacKnight.ENEMY_COL
                | HacKnight.MARIO_COL | HacKnight.GROUND_COL | HacKnight.ENEMY_BORDER_COL;

        fixtureDef.shape = hitbox;
        body.createFixture(fixtureDef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5.35f, 10f).scl(HacKnight.SCALE);
        vertice[1] = new Vector2(5.35f, 10f).scl(HacKnight.SCALE);
        vertice[2] = new Vector2(-3f, 3f).scl(HacKnight.SCALE);
        vertice[3] = new Vector2(3f, 3f).scl(HacKnight.SCALE);
        head.set(vertice);

        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = HacKnight.ENEMY_HEAD_COL;
        fixtureDef.filter.maskBits = HacKnight.MARIO_COL;

        body.createFixture(fixtureDef).setUserData(this);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(PlayScreen.atlas.findRegion("goomba"), i * 16, 0, 16, 16));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 1, 1);
        setToDestroy = false;
        destroyed = false;

        body.setActive(false);
    }


    @Override
    public void dispose() {

    }


    public void update(float delta) {
        if (hasDied && !isDead) {
            world.destroyBody(body);
            isDead = true;
        }

        body.setLinearVelocity(movement);

        stateTime += delta;
        if(setToDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
            setRegion(new TextureRegion(PlayScreen.atlas.findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0;
        }
        if(setToObliterate && !obliterated){
            body.applyLinearImpulse(new Vector2(0f, 20f), body.getWorldCenter(),true);
            obliterated = true;
            setRegion(new TextureRegion(PlayScreen.atlas.findRegion("goomba"), 0, 0, 16, 16));
            flip(false, true);
            world.destroyBody(body);
            stateTime = 0;
        }
        else if(!destroyed && !obliterated) {
            body.setLinearVelocity(movement);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 0.1f);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    public void draw(Batch batch) {
        if(setToObliterate) {
            setPosition(-100, -100);
        }
        if(!destroyed || stateTime < 0.3) {
            super.draw(batch);
        }
    }
    @Override
    public void receiveHit() {
        setToDestroy = true;
        hasDied = true;
        SoundManager.ENEMY_HIT_SOUND.play();
        GameHud.updateScore(300);
    }

    @Override
    public void obliterate() {
        setToObliterate = true;
        hasDied = true;
        SoundManager.ENEMY_HIT_SOUND.play();
        GameHud.updateScore(300);
    }
}