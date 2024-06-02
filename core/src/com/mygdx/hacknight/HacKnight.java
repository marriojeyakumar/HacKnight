package com.mygdx.hacknight;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.hacknight.SoundManager;

import com.badlogic.gdx.Game;
import com.mygdx.hacknight.screens.PlayScreen;

public class HacKnight extends Game {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 240;

	/*
	By default, Box2D uses MKS (meters, kg, and sec) as units.
	Out Mario is an 8 x 8 rectangle, so 64m squared in volume, so
	he is affected by gravity and linear impulse very weird in a world
	that was coded based off pixels.
	Everything must be scaled from pixels to meters.
	This game will have 16pixels / 1m.
	each tile is 1m x 1m.
	The rendering env and actors must be tailored to this.
	 */
	public static final float SCALE = 1 / 16f;
	public static final float TILE_LENGTH = 16f;

	/*
	These bytes are used to identify what mario can collide
	with in his fixture filter

	DESTROYED_BYTE is for when a tile gets destroyed
	 */
	public static final byte GROUND_COL = 1;
	public static final byte MARIO_COL = 2;
	public static final byte COIN_COl = 4;
	public static final byte BRICK_COL = 8;
	public static final byte COIN_BLOCK_COL = 16;
	public static final byte ENEMY_COL = 32;
	public static final byte DESTROYED_COL = 64;
	public static final short DEFAULT_COL = 128;
	public static final short ENEMY_HEAD_COL = 256;
	public static final short MARIO_HEAD_COL = 512;
	public static final short FLAGPOLE_COL = 1024;
	public static final short EXIT_DOOR_COL = 2048;
	public static final short ENEMY_BORDER_COL = 4096;

	public static final int ITEM_COL = 8192;

	// Holds all sprites and images
	// Public so all screens can have access to it
	public static SpriteBatch batch;

	public static float START_TIME = 0.0f;


	/*
	init sprite batch and set the screen to PlayScreen
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		super.setScreen(new PlayScreen(this, new com.mygdx.hacknight.GameHud(batch), 6));
	}

	@Override
	public void dispose() {
		super.dispose();
		SoundManager.THEME_SONG.dispose();
	}

	/*
        render frame
         */
	@Override
	public void render () {
		super.render();
	}

	public void playThemeSong() {
		SoundManager.THEME_SONG.setLooping(true);
		SoundManager.THEME_SONG.play();
	}
}