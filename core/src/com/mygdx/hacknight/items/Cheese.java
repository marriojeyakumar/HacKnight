package com.mygdx.hacknight.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.hacknight.GameHud;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.SoundManager;
import com.mygdx.hacknight.screens.PlayScreen;

public class Cheese extends Item {
    public Cheese(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(PlayScreen.atlas.findRegion("cheese"), 0, 0, 16, 16);
        velocity = new Vector2(0, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 * HacKnight.SCALE);
        fdef.filter.categoryBits = HacKnight.ITEM_COL;
        fdef.filter.maskBits = HacKnight.MARIO_COL;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use() {
        destroy();
        GameHud.updateScore(1000);
        SoundManager.EAT_SOUND.play();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setLinearVelocity(velocity);
    }
}
