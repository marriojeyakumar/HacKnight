package com.mygdx.hacknight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.hacknight.characters.Enemy;
import com.mygdx.hacknight.characters.Koopa;
import com.mygdx.hacknight.characters.Mario;
import com.mygdx.hacknight.items.Item;
import com.mygdx.hacknight.tiles.Coin;
import com.mygdx.hacknight.tiles.InteractableObject;






public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int col = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null)
            return;

        if (col == (HacKnight.ENEMY_COL)) {
            if (fixtureA.getUserData() instanceof Koopa && ((Koopa) fixtureA.getUserData()).getCurrentState() == Koopa.KoopaState.MOVING_SHELL) {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.obliterate();
            } else if (fixtureB.getUserData() instanceof Koopa && ((Koopa) fixtureB.getUserData()).getCurrentState() == Koopa.KoopaState.MOVING_SHELL) {
                Enemy enemy = (Enemy) fixtureA.getUserData();
                enemy.obliterate();
            } else {
                Enemy enemy1 = (Enemy) fixtureA.getUserData();
                Enemy enemy2 = (Enemy) fixtureB.getUserData();
                enemy1.reverse();
                enemy2.reverse();
            }
        }

        else if (col == (HacKnight.MARIO_COL | HacKnight.ENEMY_COL)) {
            if (fixtureB.getUserData() instanceof Koopa && ((Koopa) fixtureB.getUserData()).getCurrentState() == Koopa.KoopaState.SHELL) {
                Mario mario = (Mario) fixtureA.getUserData();
                Koopa koopa = (Koopa) fixtureB.getUserData();
                koopa.kickShell(mario.getXCoordinate() >= koopa.getXCoordinate());
            } else if (fixtureA.getUserData() instanceof Koopa && ((Koopa) fixtureA.getUserData()).getCurrentState() == Koopa.KoopaState.SHELL) {
                Koopa koopa = (Koopa) fixtureA.getUserData();
                Mario mario = (Mario) fixtureB.getUserData();
                koopa.kickShell(mario.getXCoordinate() >= koopa.getXCoordinate());
            } else {
                if (fixtureA.getFilterData().categoryBits == HacKnight.MARIO_COL) {
                    Mario mario = (Mario) fixtureA.getUserData();
                    mario.setDead(true);
                } else {
                    Mario mario = (Mario) fixtureB.getUserData();
                    mario.setDead(true);
                }
            }
        }

        if (col == (HacKnight.ENEMY_COL | HacKnight.DEFAULT_COL)) {
            if (fixtureA.getFilterData().categoryBits == HacKnight.ENEMY_COL) {
                Enemy enemy = (Enemy) fixtureA.getUserData();
                enemy.reverse();
            } else {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.reverse();
            }
        }

        else if (col == (HacKnight.MARIO_HEAD_COL | HacKnight.BRICK_COL) ||
                col == (HacKnight.MARIO_HEAD_COL | HacKnight.COIN_BLOCK_COL) ||
                col == (HacKnight.MARIO_HEAD_COL | HacKnight.COIN_COl) ||
                col == (HacKnight.MARIO_HEAD_COL | HacKnight.CARROT_BLOCK_COL) ||
                col == (HacKnight.MARIO_HEAD_COL | HacKnight.CHEESE_BLOCK_COL) ||
                col == (HacKnight.MARIO_HEAD_COL | HacKnight.MEAT_BLOCK_COL)) {
            if (fixtureA.getFilterData().categoryBits == HacKnight.MARIO_HEAD_COL) {
                InteractableObject obj = (InteractableObject) fixtureB.getUserData();
                obj.hitMarioHead();
            } else {
                InteractableObject obj = (InteractableObject) fixtureA.getUserData();
                obj.hitMarioHead();
            }
        }

        else if (col == (HacKnight.MARIO_COL | HacKnight.COIN_COl)) {
            if (fixtureA.getFilterData().categoryBits == HacKnight.MARIO_COL) {
                Coin coin = (Coin) fixtureB.getUserData();
                coin.hitMarioHead();
            } else {
                Coin coin = (Coin) fixtureA.getUserData();
                coin.hitMarioHead();
            }
        }

        else if (col == (HacKnight.MARIO_COL | HacKnight.ENEMY_HEAD_COL)) {
            if (fixtureA.getUserData() instanceof Koopa && ((Koopa) fixtureA.getUserData()).getCurrentState() == Koopa.KoopaState.SHELL) {
                Koopa koopa = (Koopa) fixtureA.getUserData();
                Mario mario = (Mario) fixtureB.getUserData();
                koopa.kickShell(mario.getXCoordinate() >= koopa.getXCoordinate());
            } else if (fixtureB.getUserData() instanceof Koopa && ((Koopa) fixtureB.getUserData()).getCurrentState() == Koopa.KoopaState.SHELL) {
                Koopa koopa = (Koopa) fixtureB.getUserData();
                Mario mario = (Mario) fixtureA.getUserData();
                koopa.kickShell(mario.getXCoordinate() >= koopa.getXCoordinate());
            } else if (fixtureA.getFilterData().categoryBits == HacKnight.MARIO_COL) {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                Mario mario = (Mario) fixtureA.getUserData();
                mario.bounceUpAfterEnemyHit();
                enemy.receiveHit();
            } else {
                Enemy enemy = (Enemy) fixtureA.getUserData();
                Mario mario = (Mario) fixtureB.getUserData();
                mario.bounceUpAfterEnemyHit();
                enemy.receiveHit();
            }
        }

        else if (col == (HacKnight.MARIO_COL | HacKnight.FLAGPOLE_COL)) {
            if (fixtureA.getFilterData().categoryBits == HacKnight.MARIO_COL) {
                Mario mario = (Mario) fixtureA.getUserData();
                mario.hitFlagpole();
            } else {
                Mario mario = (Mario) fixtureB.getUserData();
                mario.hitFlagpole();
            }
        }

        else if (col == (HacKnight.MARIO_COL | HacKnight.EXIT_DOOR_COL)) {
            if (fixtureA.getFilterData().categoryBits == HacKnight.MARIO_COL) {
                Mario mario = (Mario) fixtureA.getUserData();
                mario.goThroughExitDoor();
            } else {
                Mario mario = (Mario) fixtureB.getUserData();
                mario.goThroughExitDoor();
            }
        }

        else if (col == (HacKnight.ENEMY_COL | HacKnight.ENEMY_BORDER_COL)) {
            if (fixtureA.getFilterData().categoryBits == HacKnight.MARIO_COL) {
                if (!(fixtureA.getUserData() instanceof Koopa && ((Koopa) fixtureA.getUserData()).getCurrentState() == Koopa.KoopaState.MOVING_SHELL)) {
                    Enemy enemy = (Enemy) fixtureA.getUserData();
                    enemy.reverse();
                }
            } else if (!(fixtureB.getUserData() instanceof Koopa && ((Koopa) fixtureB.getUserData()).getCurrentState() == Koopa.KoopaState.MOVING_SHELL)) {
                Enemy enemy = (Enemy) fixtureB.getUserData();
                enemy.reverse();
            }
        }

        else if (col == (HacKnight.MARIO_COL | HacKnight.ITEM_COL)) {
            if (fixtureA.getFilterData().categoryBits == HacKnight.MARIO_COL) {
                Item item = (Item) fixtureB.getUserData();
                item.use();
            } else {
                Item item = (Item) fixtureA.getUserData();
                item.use();
            }
        }

        else if (col == (HacKnight.GROUND_COL | HacKnight.ITEM_COL) ||
                col == (HacKnight.DEFAULT_COL | HacKnight.ITEM_COL) ||
                col == (HacKnight.COIN_BLOCK_COL | HacKnight.ITEM_COL) ||
                col == (HacKnight.DESTROYED_COL | HacKnight.ITEM_COL) ||
                col == (HacKnight.BRICK_COL | HacKnight.ITEM_COL)) {
            if (fixtureA.getFilterData().categoryBits == HacKnight.ITEM_COL) {
                Item item = (Item) fixtureA.getUserData();
                item.destroy();
            } else {
                Item item = (Item) fixtureB.getUserData();
                item.destroy();
            }
        }
    }

    /*
    when two fixtures stop colliding
     */
    @Override
    public void endContact(Contact contact) {

    }

    /*
    changing characteristics of a collision before it happens
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /*
    updating the fixtures after they collide
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}