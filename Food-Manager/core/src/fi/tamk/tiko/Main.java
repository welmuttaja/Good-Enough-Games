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

	public void updateStats(){
	    if(this.energy > 0 && this.weight > 0 && this.healthiness > 0 && this.happiness > 0) {
            this.energy -= 0.0001f;
            this.weight -= 0.0001f;
            this.healthiness -= 0.0001f;
            this.happiness -= 0.0001f;
        }

    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {

        this.energy = energy;

	    if(this.energy > 1) {
            this.energy = 1;
        }
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {

        this.weight = weight;

        if(this.weight > 1) {
            this.weight = 1;
        }
    }

    public float getHealthiness() {
        return healthiness;
    }

    public void setHealthiness(float healthiness) {

        this.healthiness = healthiness;

        if(this.healthiness > 1) {
            this.healthiness = 1;
        }
    }

    public float getHappiness() {
        return happiness;
    }

    public void setHappiness(float happiness) {

        this.happiness = happiness;

        if(this.happiness > 1) {
            this.happiness = 1;
        }
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

    private int type;
    private float energy;
	private float weight;
	private float healthiness;
	private float happiness;

    //Ruoka tavaroiden constructor
    public FoodActor(int type, float x, float y, float w, float h){
        this.type = type;
        switch(type){
            case 0:
                textureStr = "beans.png";
                energy = 0.2f;
                weight = 0.2f;
                healthiness = 0.2f;
                happiness = 0.1f;
                break;
            case 1:
                textureStr = "eggs.png";
				energy = 0.25f;
				weight = 0.15f;
				healthiness = 0.25f;
				happiness = 0.25f;
                break;
            case 2:
                textureStr = "rice.png";
				energy = 0.1f;
				weight = 0.2f;
				healthiness = 0.1f;
				happiness = 0.25f;
                break;
        }
        texture = new Texture(Gdx.files.internal(textureStr));
        setWidth(w);
        setHeight(h);
        setBounds(x, y, getWidth(), getHeight());
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    final Player player;
    final ArrayList<Integer> foods;

	OrthographicCamera camera;

	//päävalikon stage ja napit
	Stage menuStage;
	MyActor playButton;
	MyActor settingButton;
	MyActor HTPButton;

	//Päävalikon constructor, täällä määritellään uudet elementit
	public MainMenuScreen(final Main game, final Player player, final ArrayList<Integer> foods) {
		this.game = game;
        this.player = player;
        this.foods = foods;

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
				game.setScreen(new ApartmentScreen(game, player, foods));
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

    final Player player;
    final ArrayList<Integer> foods;

    ArrayList<FoodActor> foodActors=new ArrayList<FoodActor>();

	Boolean fridgeOpen = false;

	//Asuntonäkymän constructor
	public ApartmentScreen(final Main game, final Player player, final ArrayList<Integer> foods) {
		this.game = game;
        this.player = player;
        this.foods = foods;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

        apartmentbg = new Texture("apartmentbg.png");

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

        //Lisää ruoat jääkaappiin
        float leftMargin = 10;
        float topMargin = 10;

        for( int i = 0; i < foods.size(); i++ ){
            float x = fridgeMenuBg.getX() + leftMargin;
            float y = fridgeMenuBg.getTop() - 50 - topMargin;

            foodActors.add(new FoodActor(foods.get(i), x, y, 50, 50));
            foodActors.get(i).setName("food" + foods.get(i));
            foodActors.get(i).setVisible(fridgeOpen);

            if(leftMargin == 310){
                leftMargin = 10;
                topMargin += 60;
            } else{
                leftMargin += 60;
            }

        }

		//Lisää painikkeet stageen
        apartmentStage.addActor(statBg);
        apartmentStage.addActor(charEnergy);
        apartmentStage.addActor(charWeight);
        apartmentStage.addActor(charHealthiness);
        apartmentStage.addActor(charHappiness);

		apartmentStage.addActor(fridgeActor);
        apartmentStage.addActor(fridgeMenuBg);
        apartmentStage.addActor(exitButton);
        apartmentStage.addActor(shopButton);

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

                foods.clear();

                for(int i = 0; i < apartmentStage.getActors().size; i++) {

                    String str = apartmentStage.getActors().get(i).getName();

                    if (str != null) {
                        if (str.contains("food")) {

                            System.out.println("contains food");

                            StringBuilder sb = new StringBuilder();
                            boolean found = false;
                            for (char c : str.toCharArray()) {
                                if (Character.isDigit(c)) {
                                    sb.append(c);
                                    found = true;
                                } else if (found) {
                                    // If we already found a digit before and this char is not a digit, stop looping
                                    break;
                                }
                            }

                            String output = sb.toString();

                            System.out.println("contains " + output);

                            foods.add(Integer.valueOf(output));
                        }
                    }
                }

                //Siirtyy kauppa näkymään
                game.setScreen(new ShopScreen(game, player, foods));

                return false;
            }
        });

        //Lisää ruokiin kosketuksen tunnistamisen
        for(int i = 0; i < foodActors.size(); i++) {

            final int fIndex = i;

            foodActors.get(i).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    player.setEnergy(player.getEnergy() + foodActors.get(fIndex).getEnergy());
                    player.setWeight(player.getWeight() + foodActors.get(fIndex).getWeight());
                    player.setHealthiness(player.getHealthiness() + foodActors.get(fIndex).getHealthiness());
                    player.setHappiness(player.getHappiness() + foodActors.get(fIndex).getHappiness());

                    foodActors.get(fIndex).remove();

                    return false;
                }
            });
        }
	}


	@Override
	public void render(float delta) {

	    //päivittää pelaajan statsit ja statsi mittarit
	    player.updateStats();
        charEnergy.setWidth(player.getEnergy() * 280);
        charWeight.setWidth(player.getWeight() * 280);
        charHealthiness.setWidth(player.getHealthiness() * 280);
        charHappiness.setWidth(player.getHappiness() * 280);


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

class GameTime {

	// 1 sec = 8 min
	// 1 min = 480 min = 3 h
	// 3 min = 24 h

	private double time;
	private double minutes;
    private int hours;
    private int days;
    private int weeks;
    private int months;
    private int years;

	GameTime(){

	}

	public void updateTime(double dt){
		this.time = dt * 8;
        this.minutes += dt * 8;

        if( this.minutes == 60 ){
            this.minutes = 0;
            this.hours += 1;
        }
        if( this.hours == 24 ){
            this.hours = 0;
            this.days += 1;
        }
        if( this.days == 7 ){
            this.days = 0;
            this.weeks += 1;
        }
        if( this.weeks == 4 ){
            this.weeks = 0;
            this.months += 1;
        }
        if(this.months == 12 ){
            this.months = 0;
            this.years += 1;
        }
	}

	public double getTime(){
        return this.time;
    }

    public void setMinutes(int m){
	    this.minutes = m;
	}

	public int getMinutes(){
	    return (int) this.minutes;
    }

    public void setHours(int h){
        this.hours = h;
    }

    public int getHours(){
        return this.hours;
    }

    public void setDays(int d){
        this.days = d;
    }

    public int getDays(){
        return this.days;
    }

    public void setWeeks(int w){
        this.weeks = w;
    }

    public int getWeeks(){
        return this.weeks;
    }

    public void setMonths(int m){
        this.months = m;
    }

    public int getMonths(){
        return this.months;
    }

    public void setYears(int y){
        this.years = y;
    }

    public int getYears(){
        return this.years;
    }
}

public class Main extends Game {
	SpriteBatch batch;
	BitmapFont font;

	GameTime gt;

	Player player;
	ArrayList<Integer> foods;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		gt = new GameTime();

        player = new Player(0.5f, 0.5f, 0.5f, 0.5f);
		foods = new ArrayList<Integer>();

        foods.add(0);
        foods.add(1);
        foods.add(2);
        foods.add(0);
        foods.add(1);
        foods.add(2);
        foods.add(0);
        foods.add(1);
        foods.add(2);
        foods.add(0);

		//Asettaa päävalikon näkymäksi pelin auetessa.
		this.setScreen(new MainMenuScreen(this, player, foods));
	}

	@Override
	public void render () {
		super.render();
		//päivittää peliaikaa
		gt.updateTime(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
