package fi.tamk.tiko;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import static java.lang.String.valueOf;

class GameOverScreen implements Screen {

    final Main game;
    final Preferences prefs;
    final float points;

    final int SCREEN_WIDTH = 800;
    final int SCREEN_HEIGHT = 600;

    OrthographicCamera camera;

    Stage gameOverStage;

    Texture gameoverbg;
    MyActor exitbutton;

    public GameOverScreen(final Main game, final float points) {

        this.game = game;
        this.prefs = Gdx.app.getPreferences(Gdx.app.getPreferences("preferences_lang").getString("lang"));
        this.points = points;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        gameOverStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), game.batch);

        gameoverbg = new Texture("gameoverbg.png");
        exitbutton = new MyActor(prefs.getString("exit"), 500, 150, 240, 80);

        gameOverStage.addActor(exitbutton);

        Gdx.input.setInputProcessor(gameOverStage);

        exitbutton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                ArrayList<Integer> foods = new ArrayList<Integer>();

                foods.add(0);
                foods.add(1);
                foods.add(2);

                game.setScreen(new MainMenuScreen(game, new GameTime(), new Player(0.5f, 0.5f, 0.5f, 0.5f, 50f), foods));

                return false;
            }
        });
    }

    @Override
    public void render(float delta) {

        //Asettaa taustan värin
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Päivittää kameran
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        //Piirtää taustan
        game.batch.draw(gameoverbg, 0, 100, SCREEN_WIDTH, SCREEN_HEIGHT);

        game.font_white.draw(game.batch, prefs.getString("points") + points, 100, 200);

        game.batch.end();

        //tekee actorien toiminnot
        gameOverStage.act(Gdx.graphics.getDeltaTime());
        //piirtää stagen actorit
        gameOverStage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }
}