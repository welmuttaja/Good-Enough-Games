package fi.tamk.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Päävalikko
class MainMenuScreen implements Screen {

	final Main game;

	OrthographicCamera camera;

	//Päävalikon constructor, täällä määritellään uudet elementit
	public MainMenuScreen(final Main game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
	    //Asettaa taustan värin
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Päivittää kameran
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
        //Ruutua klikatessa vaihtaa asunto pelinäkymään. Testausta varten, saa poistaa tarvittaessa.
        if (Gdx.input.justTouched()) {
            game.setScreen(new ApartmentScreen(game));
        }

		game.batch.begin();

		game.batch.end();
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
//Asunto pelinäkymä
class ApartmentScreen implements Screen {
	final Main game;

	OrthographicCamera camera;

	//Asuntonäkymän constructor
	public ApartmentScreen(final Main game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}

	@Override
	public void render(float delta) {
        //Asettaa taustan värin
		Gdx.gl.glClearColor(0.5f, 0.7f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Päivitää kameran
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
        //Ruutua klikatessa vaihtaa asunto pelinäkymään. Testausta varten, saa poistaa tarvittaessa.
        if (Gdx.input.justTouched()) {
            game.setScreen(new ShopScreen(game));
        }

		game.batch.begin();

		game.batch.end();
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
//Kaupan pelinäkymä
class ShopScreen implements Screen {

	final Main game;

	OrthographicCamera camera;

	//Kauppanäkymän constructor
	public ShopScreen(final Main game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
        //Asettaa taustan värin
		Gdx.gl.glClearColor(0.7f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Päivittää kameran
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
        //Ruutua klikatessa vaihtaa päävalikkoon. Testausta varten, saa poistaa tarvittaessa.
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
        }

		game.batch.begin();

		game.batch.end();
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

public class Main extends Game {
	SpriteBatch batch;
	BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		//Asettaa päävalikon näkymäksi pelin auetessa.
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
