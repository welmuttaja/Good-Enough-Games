package fi.tamk.FoodManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

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
    private float price;

    //Ruoka tavaroiden constructor
    public FoodActor(final int type, final float x, final float y, final float w, final float h){
        this.type = type;
        foods = new ArrayList<Integer>();
        switch(type){
            case 0:
                textureStr = "beans.png";
                energy = 0.2f;
                weight = 0.1f;
                healthiness = 0.2f;
                happiness = 0.1f;
                price = 1f;
                break;
            case 1:
                textureStr = "eggs.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.2f;
                happiness = 0.1f;
                price = 1.5f;
                break;
            case 2:
                textureStr = "rice.png";
                energy = 0.3f;
                weight = 0.2f;
                healthiness = 0.2f;
                happiness = 0.1f;
                price = 2.0f;
                break;
            case 3:
                textureStr = "tuna.png";
                energy = 0.3f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.1f;
                price = 1.0f;
                break;
            case 4:
                textureStr = "macaroni.png";
                energy = 0.2f;
                weight = 0.2f;
                healthiness = 0.2f;
                happiness = 0.1f;
                price = 1.0f;
                break;
            case 5:
                textureStr = "mikropizza.png";
                energy = 0.3f;
                weight = 0.3f;
                healthiness = 0.1f;
                happiness = 0.2f;
                price = 1.0f;
                break;
            case 6:
                textureStr = "meatballs.png";
                energy = 0.2f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.3f;
                price = 2.0f;
                break;
            case 7:
                textureStr = "salmonsoup.png";
                energy = 0.3f;
                weight = 0.2f;
                healthiness = 0.3f;
                happiness = 0.4f;
                price = 3.0f;
                break;
            case 8:
                textureStr = "porridge.png";
                energy = 0.1f;
                weight = 0.1f;
                healthiness = 0.4f;
                happiness = 0.1f;
                price = 0.5f;
                break;
            case 9:
                textureStr = "pastabolognese.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.25f;
                price = 3.0f;
                break;
            case 10:
                textureStr = "makaronilaatikko.png";
                energy = 0.2f;
                weight = 0.2f;
                healthiness = 0.2f;
                happiness = 0.1f;
                price = 1.0f;
                break;
            case 11:
                textureStr = "munakas.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.4f;
                happiness = 0.2f;
                price = 1.2f;
                break;
            case 12:
                textureStr = "noodlesoup.png";
                energy = 0.2f;
                weight = 0.2f;
                healthiness = 0.2f;
                happiness = 0.2f;
                price = 1.5f;
                break;
            case 13:
                textureStr = "leipa.png";
                energy = 0.1f;
                weight = 0.1f;
                healthiness = 0.3f;
                happiness = 0.3f;
                price = 1.5f;
                break;
            case 14:
                textureStr = "tortilla.png";
                energy = 0.1f;
                weight = 0.2f;
                healthiness = 0.1f;
                happiness = 0.2f;
                price = 1.5f;
                break;
            case 15:
                textureStr = "jogurttimyslillä.png";
                energy = 0.2f;
                weight = 0.1f;
                healthiness = 0.1f;
                happiness = 0.2f;
                price = 1.0f;
                break;
            case 16:
                textureStr = "coffee.png";
                energy = 0.4f;
                weight = 0f;
                healthiness = 0f;
                happiness = 0.2f;
                price = 2.0f;
                break;
            case 17:
                textureStr = "ratatouille.png";
                energy = 0.1f;
                weight = 0.1f;
                healthiness = 0.4f;
                happiness = 0.3f;
                price = 1.5f;
                break;
            case 18:
                textureStr = "chips.png";
                energy = 0.2f;
                weight = 0.4f;
                healthiness = 0f;
                happiness = 0.3f;
                price = 2.0f;
                break;
            case 19:
                textureStr = "kaalilaatikko.png";
                energy = 0.3f;
                weight = 0.1f;
                healthiness = 0.2f;
                happiness = 0.1f;
                price = 1.5f;
                break;
        }
        texture = new Texture(Gdx.files.internal(textureStr));
        setWidth(w);
        setHeight(h);
        setBounds(x, y, getWidth(), getHeight());

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

    public float getPrice(){
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
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
		this.time += dt * 9;
        this.minutes += dt * 9;

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
    BitmapFont font_white;
    FreeTypeFontGenerator generator;

	GameTime gt;

	Player player;
	ArrayList<Integer> foods;

	@Override
	public void create () {
	    // Alkusplash
	    // setScreen(new SplashScreen());
		batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        font = generator.generateFont(parameter);

        parameter.color = Color.WHITE;
        font_white = generator.generateFont(parameter);

		gt = new GameTime();

        player = new Player(0.5f, 0.5f, 0.5f, 0.5f, 50f);
		foods = new ArrayList<Integer>();

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
        font_white.dispose();
	}
}
