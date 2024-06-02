package com.mygdx.hacknight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
public class SoundManager {
    public static Music THEME_SONG;
    public static Music SPED_UP_THEME_SONG;
    public static final Sound COIN_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/coin.wav"));
    public static final Sound BLANK_BLOCK_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/bump.wav"));
    public static final Sound ONE_UP_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario 1-UP Sound Effect.mp3"));
    public static final Sound JUMP_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario Jump Sound Effect.mp3"));
    public static final Sound BRICK_BREAKING_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/breakblock.wav"));
    public static final Music DEATH_SOUND = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Mario Death Sound Effect.mp3"));
    public static final Sound GAME_OVER_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Mario Game Over Sound Effect.mp3"));
    public static final Sound ENEMY_HIT_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Goomba Death Sound Effect.mp3"));
    public static final Sound FLAGPOLE_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Flagpole.mp3"));
    public static final Sound STAGE_WIN_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/Stage Win (2).mp3"));
    public static final Sound ITSA_ME_SOUND = Gdx.audio.newSound(Gdx.files.internal("Downloads/Sounds & Music/It's-a Me Mario!.mp3"));
    public static final Music GAME_WON_SONG = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Game Won.mp3"));
    public static final Music SPEED_UP_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Speed Up.wav"));
    public static final Music COLOSSEUM_FIGHT = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Colosseum Song.mp3"));
    public static final Music COLOSSEUM_FIGHT_SLOW = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/Colosseum Fight Slow.mp3"));
    public static final Music EAT_SOUND = Gdx.audio.newMusic(Gdx.files.internal("Downloads/Sounds & Music/eating sound effect.wav"));


}