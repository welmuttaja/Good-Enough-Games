package fi.tamk.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

//Päävalikon napit
class MyActor extends Actor {

	private Texture texture;

	//Painikkeiden constructor
	public MyActor(String textureStr, float x, float y, float w, float h){
		texture = new Texture(Gdx.files.internal(textureStr));
		setWidth(w);
		setHeight(h);
		setBounds(x, y, getWidth(), getHeight());
	}

	@Override
	public void draw(Batch batch, float alpha){
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void act(float delta){
		super.act(delta);
	}
}

class FoodActor extends Actor {

    private Texture texture;

    //Ruoka tavaroiden constructor
    public FoodActor(String textureStr, float x, float y, float w, float h, String type){
        texture = new Texture(Gdx.files.internal(textureStr));
        setWidth(w);
        setHeight(h);
        setBounds(x, y, getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}

//Päävalikko
class MainMenuScreen implements Screen {

	final Main game;

	OrthographicCamera camera;

	//päävalikon stage ja napit
	Stage menuStage;
	MyActor playButton;
	MyActor settingButton;
	MyActor HTPButton;

	//Päävalikon constructor, täällä määritellään uudet elementit
	public MainMenuScreen(final Main game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		//Stagen määrittely
		menuStage = new Stage(new FitViewport(800, 600), game.batch);
		//Painikkeiden määrittely
		playButton = new MyActor("playbutton.png", 300, 400, 200, 50);
		settingButton = new MyActor("settingsbutton.png", 300, 300, 200, 50);
		HTPButton = new MyActor("howtoplaybutton.png", 300, 200, 200, 50);
		//Lisää painikkeet stageen
		menuStage.addActor(playButton);
		menuStage.addActor(settingButton);
		menuStage.addActor(HTPButton);

		//Lisää stageen inputprocessorin
		Gdx.input.setInputProcessor(menuStage);
		//Lisää play painikkeeseen kosketuksen tunnistamisen
        playButton.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//Vaihtaa asunto näkymään
				game.setScreen(new ShopScreen(game));
				return false;
			}
		});
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

		//tekee actorien toiminnot
        menuStage.act(Gdx.graphics.getDeltaTime());
        //piirtää stagen actorit
        menuStage.draw();

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

	Stage apartmentStage;
	MyActor fridgeActor;
	MyActor fridgeMenuBg;
	String[] foods = {"apple", "banana", "noodles"};
    ArrayList<FoodActor> foodActors = new ArrayList<FoodActor>();

	Boolean fridgeOpen = false;

	//Asuntonäkymän constructor
	public ApartmentScreen(final Main game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		//Stagen määrittely
		apartmentStage = new Stage(new FitViewport(800, 600), game.batch);
		//Painikkeiden määrittely
		fridgeActor = new MyActor("test.png", 300, 200, 200, 200);
		//Jääkaapin valikon tausta
        fridgeMenuBg = new MyActor("test.png", 200, 100, 400, 400);
        fridgeMenuBg.setVisible(fridgeOpen);
        //Ruoka testi
        for( int i = 0; i < foods.length; i++ ){
            float margin = 10;
            float x = fridgeMenuBg.getX() + margin;
            float y = fridgeMenuBg.getTop() - 60;
            foodActors.add(new FoodActor("test.png", x, y, 50, 50, foods[i]));
            foodActors.get(i).setVisible(fridgeOpen);

            margin += 60;
        }

        for( int i = 0; i < foodActors.size(); i++){
            apartmentStage.addActor(foodActors.get(i));
        }

		//Lisää painikkeet stageen
		apartmentStage.addActor(fridgeActor);
        apartmentStage.addActor(fridgeMenuBg);

		//Lisää stageen inputprocessorin
		Gdx.input.setInputProcessor(apartmentStage);
		//Lisää jääkaappiin kosketuksen tunnistamisen
		fridgeActor.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				//Avaa fridge näkymän
                if(fridgeOpen) {
                    fridgeOpen = false;
                } else {
                    fridgeOpen = true;
                }

                fridgeMenuBg.setVisible(fridgeOpen);
                //testFood.setVisible(fridgeOpen);

				return false;
			}
		});
	}


	@Override
	public void render(float delta) {
		//Asettaa taustan värin
		Gdx.gl.glClearColor(0.5f, 0.7f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Päivittää kameran
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		//tekee actorien toiminnot
		apartmentStage.act(Gdx.graphics.getDeltaTime());
		//piirtää stagen actorit
		apartmentStage.draw();

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
