package fi.tamk.FoodManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {
    private SpriteBatch batch;
    private Sprite tamk;
    private Sprite tiko;
    private Sprite msml;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(tamk, 0, 0);
        batch.end();
    }

    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void show() {
        batch = new SpriteBatch();
        Texture splashTextureTamk = new Texture("tamk_logo.png");
        Texture splashTextureTiko = new Texture("tiko_logo.png");
        Texture splashTextureMsml = new Texture("msml_logo.jpg");

        tamk = new Sprite(splashTextureTamk);
        tamk.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tiko = new Sprite(splashTextureTiko);
        tiko.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        msml = new Sprite(splashTextureMsml);
        msml.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void dispose() {
        batch.dispose();
    }
}