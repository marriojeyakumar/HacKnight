package com.mygdx.hacknight.screens;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.hacknight.GameHud;
import com.mygdx.hacknight.HacKnight;
import com.mygdx.hacknight.SoundManager;
import com.badlogic.gdx.Gdx;
import com.mygdx.hacknight.characters.Mario;


public class GameOverScreen implements Screen {
    private final Viewport vport;
    public final Stage stage;
    private final HacKnight game;

    public final SpriteBatch batch;

    private final OrthographicCamera camera;

    public GameOverScreen (HacKnight game) {
        this.camera = new OrthographicCamera();
        this.game = game;

        vport = new FitViewport(Mario.marioWidth, Mario.marioHeight, camera);
        batch = new SpriteBatch();
        stage = new Stage(vport, batch);

        camera.position.set(4, 4, 0);
        camera.zoom += 25f;
        camera.update();


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER", font);
        Label restartLabel = new Label("Press R to play again", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(restartLabel).expandX().padTop(20);

        stage.addActor(table);
    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            HacKnight.START_TIME = 0.0f;
            game.setScreen(new PlayScreen(game, new GameHud(HacKnight.batch), 1));
            SoundManager.GAME_OVER_SOUND.stop();
            game.playThemeSong();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}