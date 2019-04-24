package fi.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;

//Kaupan pelinäkymä
class ShopScreen implements Screen {
    // Tausta
    private final Stage foodStage;
    // Ruoat
    private Actor Eggs;
    private Actor Beans;
    private Actor Rice;
    private Actor Tuna;
    private Actor Macaroni;
    private Actor Leipa;
    private Actor Mikropizza;
    private Actor SalmonSoup;
    private Actor PastaBolognese;
    private Actor MakaroniLaatikko;
    private Actor Munakas;
    private Actor NoodleSoup;
    private Actor Tortilla;
    private Actor MeatBalls;
    private Actor YogurtMysli;
    private Actor Porridge;
    private Actor Coffee;
    private Actor Ratatouille;
    private Actor Chips;
    private Actor Kaalilaatikko;

    // Äänet
    private final Sound buySound = Gdx.audio.newSound(Gdx.files.internal("cha-ching.wav"));
    private final Sound click = Gdx.audio.newSound(Gdx.files.internal("klikkausaani.wav"));
    private final Sound closeSound = Gdx.audio.newSound(Gdx.files.internal("close.wav"));
    private final Sound changeFoodSound = Gdx.audio.newSound(Gdx.files.internal("close.wav"));
    Music music = Gdx.audio.newMusic(Gdx.files.internal("shopMusic.mp3"));

    // Ruokaryhmä
    private final ArrayList<FoodActor> foodActors=new ArrayList<FoodActor>();

    // Väliaikainen testi koko.
    private float width = 8f;
    private float height = 5f;

    private SpriteBatch batch;
    private final Main game;
    private final GameTime gt;
    private final Player player;
    private final ArrayList<Integer> foods;
    private final String LANG;

    // Ruokien sijainti.
    private float x1 = 200;
    private float x2 = 350;
    private float x3 = 500;
    private float y1 = 400;
    private float y2 = 220;

    // Ruokien koot:
    private float w = 100;
    private float h = 100;


    // Takaisinpäin nappula.
    private final MyActor backButton;

    // Kategoriat.
    private final MyActor Vegetables;
    private final MyActor Extras;
    private final MyActor Meat;
    private final MyActor Alennukset;
    private MyActor VegetablesTop;
    private MyActor ExtrasTop;
    private MyActor MeatTop;

    // Ikonit
    private MyActor energyIcon;
    private MyActor weightIcon;
    private MyActor healthinessIcon;
    private MyActor happinessIcon;

    private FoodActor FoodFont;

    private final OrthographicCamera camera;

    private boolean foodSelected = false;

    private BitmapFont fontZ;

    // Infoboksin tietojen keruu.
    private boolean infoBox = false;
    private float xFont;
    private float yFont;

    // Näyttö.
    final int SCREEN_WIDTH = 800;
    final int SCREEN_HEIGHT = 600;

    MyActor mute;
    MyActor unmute;

    //Kauppanäkymän constructori.
    public ShopScreen(final Main game, final String LANG, final GameTime gt, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
        this.gt = gt;
        this.player = player;
        this.foods = foods;
        this.LANG = LANG;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        // Luodaan näyttämö ja näyttelijät sekä tausta.
        foodStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera), game.batch);

        // Luodaan fontti.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 0.5f;
        parameter.borderColor = Color.WHITE;
        fontZ = generator.generateFont(parameter);

        mute = new MyActor("mute_button.png", 700, 505, 100, 100);
        unmute = new MyActor("unmute_button.png", 700, 505, 100, 100);

        foodStage.addActor(mute);
        foodStage.addActor(unmute);
        unmute.setVisible(false);

        //Ääninappula
        mute.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                music.stop();
                mute.setVisible(false);
                unmute.setVisible(true);
                return false;
            }
        });

        unmute.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                music.setLooping(true);
                music.play();
                mute.setVisible(true);
                unmute.setVisible(false);
                return false;
            }
        });

        // Luodaan ruokia ja ostoskori.
        Eggs = new FoodActor(0, x2, y2, w, h);
        Beans = new FoodActor(1, x2, y1, w, h);
        Rice = new FoodActor(2, x1, y2, w, h);
        Tuna = new FoodActor(3, x1, y1, w, h);
        Macaroni = new FoodActor(4, x3, y2, w, h);
        Mikropizza = new FoodActor(5, x2, y1, w, h);
        MeatBalls = new FoodActor(6, x2, y1, w, h);
        SalmonSoup = new FoodActor(7, x3, y1, w, h);
        Porridge = new FoodActor(8, x1, y1, w, h);
        PastaBolognese = new FoodActor(9, x1, y2, w, h);
        MakaroniLaatikko = new FoodActor(10, x2, y2, w, h);
        Munakas = new FoodActor(11, x2, y1, w, h);
        NoodleSoup = new FoodActor(12, x3, y1, w, h);
        Leipa = new FoodActor(13, x3, y1, w, h);
        Tortilla = new FoodActor(14, x3, y2, w, h);
        YogurtMysli = new FoodActor(15, x2, y2, w, h);
        Coffee = new FoodActor(16, x3, y1, w, h);
        Ratatouille = new FoodActor(17, x3, y2, w, h);
        Chips = new FoodActor(18, x1, y1, w, h);
        Kaalilaatikko = new FoodActor(19, x1, y2, w, h);

        // Lisätään ruoat arraylistaan.
        foodActors.add((FoodActor) Eggs);
        foodActors.add((FoodActor) Rice);
        foodActors.add((FoodActor) Tuna);
        foodActors.add((FoodActor) Macaroni);
        foodActors.add((FoodActor) Mikropizza);
        foodActors.add((FoodActor) MeatBalls);
        foodActors.add((FoodActor) SalmonSoup);
        foodActors.add((FoodActor) Porridge);
        foodActors.add((FoodActor) PastaBolognese);
        foodActors.add((FoodActor) MakaroniLaatikko);
        foodActors.add((FoodActor) Munakas);
        foodActors.add((FoodActor) NoodleSoup);
        foodActors.add((FoodActor) Leipa);
        foodActors.add((FoodActor) Tortilla);
        foodActors.add((FoodActor) YogurtMysli);
        foodActors.add((FoodActor) Coffee);
        foodActors.add((FoodActor) Ratatouille);
        foodActors.add((FoodActor) Chips);
        foodActors.add((FoodActor) Kaalilaatikko);

        // Random etusivu
        // Random alennukset
        Actor random1 = new FoodActor(random(0, 3), x1, y1, w, h);
        Actor random2 = new FoodActor(random(4, 7), x2, y1, w, h);
        Actor random3 = new FoodActor(random(8, 11), x3, y1, w, h);
        Actor random4 = new FoodActor(random(12, 15), x1, y2, w, h);
        Actor random5 = new FoodActor(random(16, 19), x2, y2, w, h);

        foodActors.add((FoodActor) random1);
        foodActors.add((FoodActor) random2);
        foodActors.add((FoodActor) random3);
        foodActors.add((FoodActor) random4);
        foodActors.add((FoodActor) random5);

        // Takaisin päin nappula.
        backButton = new MyActor("koti.png", 0, 0, 80, 80);

        // Kategoria nappulat.
        Vegetables = new MyActor("Vegetables.png", 600, 0, 160, 50);
        Extras = new MyActor("Extras.png", 400, 0, 160, 50);
        Meat = new MyActor("Meat.png", 200, 0, 160, 50);
        Alennukset = new MyActor("en_sales.png", 317, 530, 200, 50);

        // Alkunäkymä, sisältää random alennukset.
        addUi();
        foodStage.addActor(Alennukset);
        foodStage.addActor(random1);
        foodStage.addActor(random2);
        foodStage.addActor(random3);
        foodStage.addActor(random4);
        foodStage.addActor(random5);

        //Lisää stageen inputprocessorin
        Gdx.input.setInputProcessor(foodStage);
        //Lisää takaisin nappiin kosketuksen tunnistamisen
        backButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Vaihtaa menu näkymään
                music.stop();
                click.play(1.0f);
                game.setScreen(new ApartmentScreen(game, LANG, gt, player, foods));
                return false;
            }
        });

        // Vegetables
        Vegetables.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                changeFoodSound.play(1.0f);
                foodStage.clear();
                addUi();
                VegetablesTop = new MyActor("Vegetables.png", 317, 530, 200, 50);
                foodStage.addActor(VegetablesTop);
                foodStage.addActor(Porridge);
                foodStage.addActor(Munakas);
                foodStage.addActor(Leipa);
                foodStage.addActor(Kaalilaatikko);
                foodStage.addActor(YogurtMysli);
                foodStage.addActor(Tortilla);
                return false;
            }
        });

        // Hedelmät ja Vihannekset
        Meat.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                changeFoodSound.play(1.0f);
                foodStage.clear();
                addUi();
                MeatTop = new MyActor("Meat.png", 317, 530, 200, 50);
                foodStage.addActor(MeatTop);
                foodStage.addActor(Tuna);
                foodStage.addActor(MeatBalls);
                foodStage.addActor(SalmonSoup);
                foodStage.addActor(PastaBolognese);
                foodStage.addActor(MakaroniLaatikko);
                foodStage.addActor(Ratatouille);
                return false;
            }
        });

        // Extra ruoat.
        Extras.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                changeFoodSound.play(1.0f);
                foodStage.clear();
                addUi();
                ExtrasTop = new MyActor("Extras.png", 317, 530, 200, 50);
                foodStage.addActor(ExtrasTop);
                foodStage.addActor(Chips);
                foodStage.addActor(Coffee);
                foodStage.addActor(Mikropizza);
                foodStage.addActor(Rice);
                foodStage.addActor(Eggs);
                foodStage.addActor(Macaroni);
                return false;
            }
        });

        //Lisää ruokiin kosketuksen tunnistamisen
        for(int i = 0; i < foodActors.size(); i++) {

            final int fIndex = i;

            foodActors.get(i).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    if (foodSelected == false) {

                        foodSelected = true;
                        infoBox = true;

                        float thisX = 490;
                        float thisY = 50;

                        if(foodActors.get(fIndex).getX() < 490) {

                            thisX = foodActors.get(fIndex).getX() + 50;
                        }
                        if(foodActors.get(fIndex).getY() > 50) {

                            thisY = foodActors.get(fIndex).getY() - 100;
                        }

                        final MyActor buy = new MyActor("en_buy.png", thisX + 10, thisY + 10, 90, 30);
                        final MyActor close = new MyActor("en_close.png", thisX + 110, thisY + 10, 90, 30);
                        final MyActor foodStatBg = new MyActor("menubg.png", thisX, thisY, 300, 180);
                        final MyActor blueBar = new MyActor("blue.png", thisX + 10, thisY + 125, foodActors.get(fIndex).getEnergy() * 280, 15);
                        final MyActor redBar = new MyActor("red.png", thisX + 10, thisY + 100, foodActors.get(fIndex).getWeight() * 280, 15);
                        final MyActor greenBar = new MyActor("green.png", thisX + 10, thisY + 75, foodActors.get(fIndex).getHealthiness() * 280, 15);
                        final MyActor yellowBar = new MyActor("yellow.png", thisX + 10, thisY + 50, foodActors.get(fIndex).getHappiness() * 280, 15);
                        final MyActor moneyBar = new MyActor("raha.png", thisX + 10, thisY + 150, 15, 15);

                        energyIcon = new MyActor("energia.png", thisX + 10, thisY + 125, 15, 15);
                        weightIcon = new MyActor("paino.png", thisX + 10, thisY + 100, 15, 15);
                        healthinessIcon = new MyActor("terveys.png", thisX + 10, thisY + 75, 15, 15);
                        happinessIcon = new MyActor("onnellisuus.png", thisX + 10, thisY + 50, 15, 15);

                        FoodFont = foodActors.get(fIndex);
                        xFont = thisX + 29;
                        yFont = thisY + 163;

                        foodStage.addActor(foodStatBg);
                        foodStage.addActor(blueBar);
                        foodStage.addActor(redBar);
                        foodStage.addActor(greenBar);
                        foodStage.addActor(yellowBar);
                        foodStage.addActor(moneyBar);
                        foodStage.addActor(energyIcon);
                        foodStage.addActor(weightIcon);
                        foodStage.addActor(healthinessIcon);
                        foodStage.addActor(happinessIcon);
                        foodStage.addActor(buy);
                        foodStage.addActor(close);

                        buy.addListener(new InputListener() {
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                                if(player.getMoney() - foodActors.get(fIndex).getPrice() > 0 &&
                                foods.size() < 25){
                                    buySound.play(1.0f);
                                    foods.add(foodActors.get(fIndex).getType());
                                    player.setMoney(player.getMoney() - foodActors.get(fIndex).getPrice());
                                }

                                foodStatBg.remove();
                                blueBar.remove();
                                redBar.remove();
                                greenBar.remove();
                                yellowBar.remove();
                                moneyBar.remove();
                                energyIcon.remove();
                                weightIcon.remove();
                                healthinessIcon.remove();
                                happinessIcon.remove();
                                buy.remove();
                                close.remove();

                                foodSelected = false;
                                infoBox = false;

                                return false;
                            }
                        });

                        close.addListener(new InputListener() {
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                                foodStatBg.remove();
                                blueBar.remove();
                                redBar.remove();
                                greenBar.remove();
                                yellowBar.remove();
                                moneyBar.remove();
                                energyIcon.remove();
                                weightIcon.remove();
                                healthinessIcon.remove();
                                happinessIcon.remove();
                                buy.remove();
                                close.remove();

                                closeSound.play(1.0f);

                                foodSelected = false;
                                infoBox = false;

                                return false;
                            }
                        });
                    }
                    return false;
                }
            });
        }
    }

    // Kauppanäkymän vakio elementit.
    private void addUi() {
        foodStage.addActor(backButton);
        foodStage.addActor(Vegetables);
        foodStage.addActor(Extras);
        foodStage.addActor(Meat);
        foodStage.addActor(mute);
        foodStage.addActor(unmute);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //päivittää peliaikaa
        gt.updateTime(Gdx.graphics.getDeltaTime());

        //Asettaa taustan värin
        Gdx.gl.glClearColor(139f/255, 69f/255, 19f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Päivittää kameran
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Jatkaa pelaajan statsien päivittämistä
        if(player.getEnergy() > 0 && player.getWeight() > 0 && player.getHealthiness() > 0 && player.getHappiness() > 0){

            //päivittää peliaikaa
            gt.updateTime(Gdx.graphics.getDeltaTime());

            //päivittää pelaajan statsit ja statsi mittarit
            player.updateStats();
        } else{
            music.stop();
            game.setScreen(new ApartmentScreen(game, LANG, gt, player, foods));
        }

        // Kaupan tausta
        Texture background;
        background = new Texture(Gdx.files.internal("hylly.png"));

        foodStage.act(Gdx.graphics.getDeltaTime());
        foodStage.getBatch().begin();
        foodStage.getBatch().draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        foodStage.getBatch().end();
        foodStage.draw();

        // Infoboksin ruoan hinta fontti.
        foodStage.getBatch().begin();
        if (infoBox == true) {
            fontZ.draw(game.batch, FoodFont.getPrice() + "€", xFont, yFont);
        }
        foodStage.getBatch().end();

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
        batch.dispose();
        fontZ.dispose();
        music.dispose();
    }
}
