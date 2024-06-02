package com.mygdx.hacknight.characters;

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


public class Koopa extends Enemy {

    private KoopaState currentState = KoopaState.WALKING;
    private KoopaState previousState;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion shell;
    private float stateTime = 0.0f;

    public enum KoopaState {WALKING, SHELL, MOVING_SHELL}

    private boolean isDead = false;
    private boolean hasDied = false;
    private boolean setToObliterate;
    private boolean obliterated;

    public Koopa(WorldRenderer worldRenderer, float x, float y) {
        super(worldRenderer, x, y);

        movement = new Vector2(5f * (Math.random() - 0.5 >= 0 ? 1 : -1), -8.5f);

        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(PlayScreen.atlas.findRegion("turtle"), 0, 0, 16, 24));
        frames.add(new TextureRegion(PlayScreen.atlas.findRegion("turtle"), 16, 0, 16, 24));
        shell = new TextureRegion(PlayScreen.atlas.findRegion("turtle"), 64, 0, 16, 24);

        walkAnimation = new Animation(0.2f, frames);
        currentState = previousState = KoopaState.WALKING;

        setBounds(getX(), getY(), 1f, 1.2f);


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
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-5.35f, 10f).scl(HacKnight.SCALE);
        vertices[1] = new Vector2(5.35f, 10f).scl(HacKnight.SCALE);
        vertices[2] = new Vector2(-3f, 3).scl(HacKnight.SCALE);
        vertices[3] = new Vector2(3f, 3).scl(HacKnight.SCALE);
        head.set(vertices);

        fixtureDef.shape = head;
        fixtureDef.filter.categoryBits = HacKnight.ENEMY_HEAD_COL;
        fixtureDef.filter.maskBits = HacKnight.MARIO_COL;
        body.createFixture(fixtureDef).setUserData(this);

        body.setActive(false);
    }
    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case SHELL:
            case MOVING_SHELL:
                region = shell;
                break;
            case WALKING:
            default:
                region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if(movement.x > 0 && !region.isFlipX()){
            region.flip(true, false);
        }
        if(movement.x < 0 && region.isFlipX()){
            region.flip(true, false);
        }

        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }
    @Override
    public void dispose() {

    }

    @Override
    public void update(float dt) {
        if (hasDied && !isDead) {
            world.destroyBody(body);
            isDead = true;
        }

        stateTime += dt;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2 + 0.2f);
        setRegion(getFrame(dt));
        if (currentState == KoopaState.SHELL && stateTime > 4.5) {
            currentState = KoopaState.WALKING;
            movement.x = 5f * (Math.random() - 0.5 >= 0 ? 1 : -1);
            stateTime = 0;
        }

        body.setLinearVelocity(movement);
    }

    @Override
    public void receiveHit() {
        if (currentState != KoopaState.SHELL) {
            currentState = KoopaState.SHELL;
            movement.x = 0;
            stateTime = 0f;
        }
        SoundManager.ENEMY_HIT_SOUND.play();
    }

    @Override
    public void obliterate() {
        setToObliterate = true;
        hasDied = true;
        SoundManager.ENEMY_HIT_SOUND.play();
        GameHud.updateScore(300);
    }

    public void kickShell(boolean kickingLeft) {
        if (kickingLeft)
            movement.x = -14f;
        else
            movement.x = 14f;

        currentState = KoopaState.MOVING_SHELL;
        SoundManager.ENEMY_HIT_SOUND.play();
        stateTime = 0;
    }
    public void draw(Batch batch) {
        if(setToObliterate) {
            setPosition(-10, -10);
        }
        else {
            super.draw(batch);
        }
    }
    public KoopaState getCurrentState() {
        return currentState;
    }

    public KoopaState getPreviousState() {
        return previousState;
    }
}
