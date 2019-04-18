package fi.tamk.tiko;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
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

    private static ArrayList<Integer> foods;
    private Texture texture;
    private String textureStr;

    private int type;
    private float energy;
    private float weight;
    private float healthiness;
    private float happiness;
    private float money;

    private float cartX = 315;
    private float cartY = 49;

    Sound sound = Gdx.audio.newSound(Gdx.files.internal("cha-ching.wav"));

    //Ruoka tavaroiden constructor
    public FoodActor(final int type, final float x, final float y, final float w, final float h){
        this.type = type;
        foods = new ArrayList<Integer>();
        switch(type){
            case 0:
                textureStr = "beans.png";
                energy = 0.2f;
                weight = 0.2f;
                healthiness = 0.2f;
                happiness = 0.1f;
                money = 0.1f;
                break;
            case 1:
                textureStr = "eggs.png";
                energy = 0.25f;
                weight = 0.15f;
                healthiness = 0.25f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 2:
                textureStr = "rice.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 3:
                textureStr = "tuna.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 4:
                textureStr = "macaroni.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 5:
                textureStr = "mikropizza.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 6:
                textureStr = "meatballs.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 7:
                textureStr = "salmonsoup.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 8:
                textureStr = "porridge.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 9:
                textureStr = "pastabolognese.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 10:
                textureStr = "makaronilaatikko.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 11:
                textureStr = "munakas.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 12:
                textureStr = "noodlesoup.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 13:
                textureStr = "noodles.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 14:
                textureStr = "chocolatecereal.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 15:
                textureStr = "jogurttimyslillä.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 16:
                textureStr = "coffee.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 17:
                textureStr = "ratatouille.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 18:
                textureStr = "chips.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
            case 19:
                textureStr = "kaalilaatikko.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                money = 0.1f;
                break;
        }
        texture = new Texture(Gdx.files.internal(textureStr));
        setWidth(w);
        setHeight(h);
        setBounds(x, y, getWidth(), getHeight());

        // Ruoan draggaus kauppanäkymässä.
        addListener(new DragListener() {
            public void drag(InputEvent event, float xx, float yy, int pointer) {
                moveBy(xx - getWidth() / 2, yy - getHeight() / 2);
                toFront();
                System.out.println(getX());
                System.out.println(getY());
                if(getX() > cartX && getY() < cartY && getX() < cartX + 100) {
                    setX(x);
                    setY(y);
                    long id = sound.play(1.0f);
                    cancel();
                    foods.add(type);
                }
                if(getX() < -50 || getY() < -50 || getX() > 750 || getY() > 550) {
                    setX(x);
                    setY(y);
                    cancel();
                }
            }
        });



    }

    public void setFoods(ArrayList<Integer> foods) {
        this.foods = foods;
    }

    public static ArrayList<Integer> getFoods() {
        return foods;
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
	    this.minutes = 1;
	    this.hours = 12;
        this.days = 1;
        this.months = 1;
        this.years = 1;
	}

	public void updateTime(double dt){
		this.time = dt * 8;
        this.minutes += dt * 8;

        if( Math.round(this.minutes) == 60 ){
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
    FreeTypeFontGenerator generator;

	GameTime gt;

	Player player;
	ArrayList<Integer> foods;

	@Override
	public void create () {
		batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        font = generator.generateFont(parameter);

		gt = new GameTime();

        player = new Player(0.5f, 0.5f, 0.5f, 0.5f, 50f);
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
        foods.add(1);
        foods.add(2);
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
        foods.add(1);
        foods.add(2);
        foods.add(0);
        foods.add(1);

		//Asettaa päävalikon näkymäksi pelin auetessa.
		this.setScreen(new MainMenuScreen(this, gt, player, foods));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
        generator.dispose();
	}
}
