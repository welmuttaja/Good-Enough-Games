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

class Player {
	private float energy;
	private float weight;
	private float healthiness;
	private float happiness;

	public Player(float en, float we, float he, float ha){
		energy = en;
		weight = we;
		healthiness = he;
		happiness = ha;
	}

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHealthiness() {
        return healthiness;
    }

    public void setHealthiness(float healthiness) {
        this.healthiness = healthiness;
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }
}

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
    private String textureStr;

    private float energy;
	private float weight;
	private float healthiness;
	private float happiness;

    //Ruoka tavaroiden constructor
    public FoodActor(int type, float x, float y, float w, float h){
        switch(type){
            case 0:
                textureStr = "beans.png";
                energy = 0.25f;
                weight = 0.25f;
                healthiness = 0.25f;
                happiness = 0.25f;
                break;
            case 1:
                textureStr = "eggs.png";
				energy = 0.25f;
				weight = 0.25f;
				healthiness = 0.25f;
				happiness = 0.25f;
                break;
            case 2:
                textureStr = "rice.png";
				energy = 0.25f;
				weight = 0.25f;
				healthiness = 0.25f;
				happiness = 0.25f;
                break;
        }
        texture = new Texture(Gdx.files.internal(textureStr));
        setWidth(w);
        setHeight(h);
        setBounds(x, y, getWidth(), getHeight());
    }

	public float getEnergy(){
    	return this.energy;
	}

	public float getWeight(){
		return this.weight;
	}

	public float getHealthiness(){
		return this.healthiness;
	}

	public float getHappiness(){
		return this.happiness;
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
				game.setScreen(new ApartmentScreen(game));
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

	Texture apartmentbg;

	Player player;

	Stage apartmentStage;
	MyActor fridgeActor;
	MyActor fridgeMenuBg;
	MyActor shopButton;
    MyActor exitButton;

    MyActor statBg;
    MyActor charEnergy;
    MyActor charWeight;
    MyActor charHealthiness;
    MyActor charHappiness;

	int[] foods = {0, 1, 2};
    ArrayList<FoodActor> foodActors = new ArrayList<FoodActor>();

	Boolean fridgeOpen = false;

	//Asuntonäkymän constructor
	public ApartmentScreen(final Main game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		apartmentbg = new Texture("apartmentbg.png");

		player = new Player(0.30f, 0.50f, 0.20f, 0.75f);

		//Stagen määrittely
		apartmentStage = new Stage(new FitViewport(800, 600), game.batch);
		//Painikkeiden määrittely
        shopButton = new MyActor("kauppa.png", 0, 0, 80, 80);
		fridgeActor = new MyActor("test-transparent.png", 225, 45, 100, 260);
		//Jääkaapin valikon tausta
        fridgeMenuBg = new MyActor("menubg.png", 200, 100, 400, 400);
        fridgeMenuBg.setVisible(fridgeOpen);
        //Jääkaapin sulje painike
        exitButton = new MyActor("exit.png", fridgeMenuBg.getX() + 230, fridgeMenuBg.getY() + 10, 150, 50);
        exitButton.setVisible(fridgeOpen);

        statBg = new MyActor("menubg.png", 490, 460, 300, 130);
        charEnergy = new MyActor("blue.png", 500, 560, 280 * player.getEnergy(), 20);
        charWeight = new MyActor("red.png", 500, 530, 280 * player.getWeight(), 20);
        charHealthiness = new MyActor("green.png", 500, 500, 280 * player.getHealthiness(), 20);
        charHappiness = new MyActor("yellow.png", 500, 470, 280 * player.getHappiness(), 20);

        //Ruoka testi
        float margin = 10;

        for( int i = 0; i < foods.length; i++ ){
            float x = fridgeMenuBg.getX() + margin;
            float y = fridgeMenuBg.getTop() - 60;

            foodActors.add(new FoodActor(foods[i], x, y, 50, 50));
            foodActors.get(i).setVisible(fridgeOpen);

            margin += 60;
        }

		//Lisää painikkeet stageen
		apartmentStage.addActor(fridgeActor);
        apartmentStage.addActor(fridgeMenuBg);
        apartmentStage.addActor(exitButton);
        apartmentStage.addActor(shopButton);

        apartmentStage.addActor(statBg);
        apartmentStage.addActor(charEnergy);
        apartmentStage.addActor(charWeight);
        apartmentStage.addActor(charHealthiness);
        apartmentStage.addActor(charHappiness);

        //Lisää ruokatavarat stageen
        for( int i = 0; i < foodActors.size(); i++){
            apartmentStage.addActor(foodActors.get(i));
        }

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
                exitButton.setVisible(fridgeOpen);
                for(int i = 0; i < foodActors.size(); i++){
                    foodActors.get(i).setVisible(fridgeOpen);
                }

				return false;
			}
		});
		//Lisää jääkaapin sulje nappiin kosketuksen tunnistamisen
        exitButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Sulkee fridge näkymän
                if(fridgeOpen) {
                    fridgeOpen = false;
                } else {
                    fridgeOpen = true;
                }

                fridgeMenuBg.setVisible(fridgeOpen);
                exitButton.setVisible(fridgeOpen);
                for(int i = 0; i < foodActors.size(); i++){
                    foodActors.get(i).setVisible(fridgeOpen);
                }

                return false;
            }
        });

        //Lisää kauppa painikkeeseen kosketuksen tunnistamisen
        shopButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Siirtyy kauppa näkymään
                game.setScreen(new ShopScreen(game));

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

        game.batch.begin();
        //Piirtää taustan
        game.batch.draw(apartmentbg, 0, 0, 800, 600);
        game.batch.end();

		//tekee actorien toiminnot
		apartmentStage.act(Gdx.graphics.getDeltaTime());
		//piirtää stagen actorit
		apartmentStage.draw();
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
