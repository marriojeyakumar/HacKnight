package com.mygdx.hacknight.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.hacknight.GameHud;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.SoundManager;
import com.mygdx.hacknight.WorldRenderer;
import com.mygdx.hacknight.screens.PlayScreen;


public class Mario extends Sprite implements Disposable {
    // Body dimensions
    public static float marioWidth = HacKnight.TILE_LENGTH / 2 - 2.35f;
    public static float marioHeight = HacKnight.TILE_LENGTH / 2 - 3f;
    private final World world;
    private final Body mario;
    private final float marioMaxSpeed = 11.7f;
    private final Animation marioRun;
    private final Animation marioJump;
    public TextureRegion marioStand;
    public State currentState;
    public State previousState;
    private boolean isDead;
    private boolean flagpoleHit = false;
    private float stateTimer;
    private boolean runningRight;

    private boolean walkedOff = false;
    private boolean wentThroughExitDoor = false;

    public Mario(World world) {
        super(PlayScreen.atlas.findRegion("small_mario"));
        this.world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 15, 16) );
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 15, 16));
        marioJump = new Animation(0.1f, frames);

        this.isDead = false;

        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 1, 1);
        setRegion(marioStand);


        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((marioWidth + HacKnight.TILE_LENGTH * 5) * HacKnight.SCALE,
                (marioHeight + HacKnight.TILE_LENGTH) * HacKnight.SCALE);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mario = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
//        CircleShape hitbox = new CircleShape();
//        hitbox.setRadius(6 * HacKnight.SCALE);
        PolygonShape hitbox = new PolygonShape();
        hitbox.setAsBox(marioWidth * HacKnight.SCALE, marioHeight * HacKnight.SCALE);

        fixtureDef.filter.categoryBits = HacKnight.MARIO_COL;
        fixtureDef.filter.maskBits = HacKnight.GROUND_COL | HacKnight.COIN_COl
                | HacKnight.BRICK_COL | HacKnight.COIN_BLOCK_COL | HacKnight.ENEMY_COL
                | HacKnight.ENEMY_HEAD_COL | HacKnight.DEFAULT_COL | HacKnight.FLAGPOLE_COL
                | HacKnight.EXIT_DOOR_COL | HacKnight.ITEM_COL;

        fixtureDef.shape = hitbox;
        fixtureDef.friction = 0.3f;
        mario.createFixture(fixtureDef).setUserData(this);

        EdgeShape top = new EdgeShape();
        top.set(new Vector2((-marioHeight / 1.7f) * HacKnight.SCALE, (marioHeight + 1f) * HacKnight.SCALE),
                new Vector2((marioHeight / 1.7f) * HacKnight.SCALE, (marioHeight + 0.1f) * HacKnight.SCALE));
        fixtureDef.shape = top;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = HacKnight.MARIO_HEAD_COL;
        mario.createFixture(fixtureDef).setUserData(this);
    }

    public void update(float delta) {
        setPosition(getXCoordinate() - getWidth() / 2, getYCoordinate() - getHeight() / 2 + 0.15f);
        setRegion(getFrame(delta));
        if (flagpoleHit) {
            mario.setLinearVelocity(new Vector2(0f, -10f));
        }
    }
    public void walkOffStage(float delta) {
        if (wentThroughExitDoor)
            setPosition(0, 0);
        else {
            setPosition(getXCoordinate() - getWidth() / 2, getYCoordinate() - getHeight() / 2);
            setRegion(getFrame(delta));
        }

        if (!walkedOff) {
            SoundManager.STAGE_WIN_SOUND.play();
            walkedOff = true;
        }

        mario.setLinearVelocity(new Vector2(15f, -45f));
    }

    public void goThroughExitDoor() {
        wentThroughExitDoor = true;
    }

    public void bounceUpAfterEnemyHit() {
        float yVelocity = mario.getLinearVelocity().y;
        mario.applyLinearImpulse(new Vector2(0f, yVelocity > -10 ? yVelocity * -1f + 9f : yVelocity * (yVelocity > -25f ? -1.8f : -1.6f)), mario.getWorldCenter(), true);
    }

    public void hitFlagpole() {
        SoundManager.THEME_SONG.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        SoundManager.FLAGPOLE_SOUND.play();
        flagpoleHit = true;
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        if ((mario.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((mario.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (mario.getLinearVelocity().y > 0 || (mario.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (mario.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (mario.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void die() {
        resetPosition();
    }

    public void jump() {
        if (isOnGround()) {
            mario.applyLinearImpulse(new Vector2(0, 24f), mario.getWorldCenter(), true);
            SoundManager.JUMP_SOUND.play(0.05f);
        }
    }

    public void moveRight() {
        if (isBelowMaxSpeedRight())
            mario.applyLinearImpulse(new Vector2(0.55f, 0), mario.getWorldCenter(), true);
    }

    public void moveLeft() {
        if (isBelowMaxSpeedLeft())
            mario.applyLinearImpulse(new Vector2(-0.55f, 0), mario.getWorldCenter(), true);
    }

    public void resetPosition() {
        mario.setTransform(new Vector2(5f, 2f), mario.getAngle());
    }

    public boolean isDead() {
        return getYCoordinate() <= WorldRenderer.getCameraY() - 8.8f || GameHud.worldTimer <= 3 || isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public float getXCoordinate() {
        return mario.getPosition().x;
    }

    public float getYCoordinate() {
        return mario.getWorldCenter().y;
    }

    private boolean isBelowMaxSpeedRight() {
        return mario.getLinearVelocity().x < marioMaxSpeed;
    }

    private boolean isBelowMaxSpeedLeft() {
        return mario.getLinearVelocity().x > -marioMaxSpeed;
    }

    private boolean isOnGround() {
        return mario.getLinearVelocity().y <= 0.01 && mario.getLinearVelocity().y >= -0.01;
    }

    public boolean isFlagpoleHit() {
        return flagpoleHit;
    }

    public Body getBody() {
        return mario;
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    public enum State {FALLING, JUMPING, STANDING, RUNNING}
}