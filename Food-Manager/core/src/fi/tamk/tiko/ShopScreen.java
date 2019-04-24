package fi.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import jdk.nashorn.internal.runtime.Source;

import static com.badlogic.gdx.math.MathUtils.random;

//Kaupan pelinäkymä
class ShopScreen implements Screen {
    // Tausta
    private Stage foodStage;
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
    private Actor ChocolateCereal;
    private Actor Pasta;
    private Actor Yogurt;
    private Actor MbuyBalls;
    private Actor YogurtMysli;
    private Actor Lohi;
    private Actor Pizza;
    private Actor Porridge;
    private Actor Cereals;
    private Actor Coffee;
    private Actor Ratatouille;
    private Actor Chips;
    private Actor Kaalilaatikko;

    // Random alennukset
    private Actor Random1;
    private Actor Random2;
    private Actor Random3;
    private Actor Random4;
    private Actor Random5;
    private Actor Random6;

    Sound sound = Gdx.audio.newSound(Gdx.files.internal("cha-ching.wav"));

    Sound click = Gdx.audio.newSound(Gdx.files.internal("klikkausaani.wav"));

    // Ostoskori
    private Actor cartActor;
    // Ruokaryhmä
    ArrayList<FoodActor> foodActors=new ArrayList<FoodActor>();

    // Väliaikainen testi koko.
    private float width = 8f;
    private float height = 5f;

    SpriteBatch batch;
    final Main game;
    final String LANG;
    final GameTime gt;
    final Player player;
    final ArrayList<Integer> foods;

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
    MyActor backButton;

    // Kategoriat.
    MyActor Pakasteet;
    MyActor Kastikkeet;
    MyActor Juomat;
    MyActor HeVi;
    MyActor Maitotuotteet;
    MyActor LihaKala;
    MyActor Herkut;
    MyActor Alennukset;

    MyActor PakasteetTop;
    MyActor KastikkeetTop;
    MyActor JuomatTop;
    MyActor HeViTop;
    MyActor MaitotuotteetTop;
    MyActor LihaKalaTop;
    MyActor HerkutTop;

    MyActor energyIcon;
    MyActor weightIcon;
    MyActor healthinessIcon;
    MyActor happinessIcon;

    FoodActor FoodFont;

    OrthographicCamera camera;

    boolean foodSelected = false;

    BitmapFont font;
    BitmapFont font_white;
    FreeTypeFontGenerator generator;

    boolean infoBox = false;
    float xFont;
    float yFont;

    //Kauppanäkymän constructor
    public ShopScreen(final Main game, final String LANG, final GameTime gt, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
        this.LANG = LANG;
        this.gt = gt;
        this.player = player;
        this.foods = foods;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        // Luodaan näyttämö ja näyttelijät sekä tausta.
        foodStage = new Stage(new FitViewport(800, 600), game.batch);

        // Luodaan ruokia ja ostoskori.
        Eggs = new FoodActor(0, x1, y1, w, h);
        Beans = new FoodActor(1, x2, y1, w, h);
        Rice = new FoodActor(2, x3, y1, w, h);
        Tuna = new FoodActor(3, x1, y2, w, h);
        Macaroni = new FoodActor(4, x2, y2, w, h);
        Mikropizza = new FoodActor(5, x3, y2, w, h);
        MbuyBalls = new FoodActor(6, x1, y1, w, h);
        SalmonSoup = new FoodActor(7, x2, y1, w, h);
        Porridge = new FoodActor(8, x3, y1, w, h);
        PastaBolognese = new FoodActor(9, x1, y2, w, h);
        MakaroniLaatikko = new FoodActor(10, x2, y2, w, h);
        Munakas = new FoodActor(11, x3, y2, w, h);
        NoodleSoup = new FoodActor(12, x1, y2, w, h);
        Leipa = new FoodActor(13, x3, y2, w, h);
        ChocolateCereal = new FoodActor(14, x3, y1, w, h);
        YogurtMysli = new FoodActor(15, x1, y2, w, h);
        Coffee = new FoodActor(16, x2, y2, w, h);
        Ratatouille = new FoodActor(17, x3, y2, w, h);
        Chips = new FoodActor(18, x1, y1, w, h);
        Kaalilaatikko = new FoodActor(19, x2, y1, w, h);

        foodActors.add((FoodActor) Eggs);
        foodActors.add((FoodActor) Rice);
        foodActors.add((FoodActor) Tuna);
        foodActors.add((FoodActor) Macaroni);
        foodActors.add((FoodActor) Mikropizza);
        foodActors.add((FoodActor) MbuyBalls);
        foodActors.add((FoodActor) SalmonSoup);
        foodActors.add((FoodActor) Porridge);
        foodActors.add((FoodActor) PastaBolognese);
        foodActors.add((FoodActor) MakaroniLaatikko);
        foodActors.add((FoodActor) Munakas);
        foodActors.add((FoodActor) NoodleSoup);
        foodActors.add((FoodActor) Leipa);
        foodActors.add((FoodActor) ChocolateCereal);
        foodActors.add((FoodActor) YogurtMysli);
        foodActors.add((FoodActor) Coffee);
        foodActors.add((FoodActor) Ratatouille);
        foodActors.add((FoodActor) Chips);
        foodActors.add((FoodActor) Kaalilaatikko);

        // Random etusivu
        Random1 = new FoodActor(random(0,3), x1, y1, w, h);
        Random2 = new FoodActor(random(4,7), x2, y1, w, h);
        Random3 = new FoodActor(random(8,11), x3, y1, w, h);
        Random4 = new FoodActor(random(12,15), x1, y2, w, h);
        Random5 = new FoodActor(random(16,19), x2, y2, w, h);

        foodActors.add((FoodActor) Random1);
        foodActors.add((FoodActor) Random2);
        foodActors.add((FoodActor) Random3);
        foodActors.add((FoodActor) Random4);
        foodActors.add((FoodActor) Random5);

        // Takaisin päin nappula.
        backButton = new MyActor("koti.png", 0, 0, 80, 80);

        // Kategoria nappulat.
        Pakasteet = new MyActor("en_frozen.png", 645, 400, 160, 50);
        Kastikkeet = new MyActor("en_sauces.png", 645, 350, 160, 50);
        Juomat = new MyActor("en_drinks.png", 645, 250, 160, 50);
        HeVi = new MyActor("en_fruits-vegetables.png", 645, 200, 160, 50);
        Maitotuotteet = new MyActor("en_dairy.png", 645, 300, 160, 50);
        Alennukset = new MyActor("en_sales.png", 317, 530, 200, 50);

        // Alkunäkymä, sisältää random alennukset.
        addUi();
        foodStage.addActor(Alennukset);
        foodStage.addActor(Random1);
        foodStage.addActor(Random2);
        foodStage.addActor(Random3);
        foodStage.addActor(Random4);
        foodStage.addActor(Random5);

        //Lisää stageen inputprocessorin
        Gdx.input.setInputProcessor(foodStage);
        //Lisää takaisin nappiin kosketuksen tunnistamisen
        backButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Vaihtaa menu näkymään
                long id = click.play(1.0f);
                game.setScreen(new ApartmentScreen(game, LANG, gt, player, foods));
                return false;
            }
        });

        // Pakasteet
        Pakasteet.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                foodStage.clear();
                addUi();
                PakasteetTop = new MyActor("en_frozen.png", 317, 530, 200, 50);
                foodStage.addActor(PakasteetTop);
                foodStage.addActor(Eggs);
                foodStage.addActor(Beans);
                foodStage.addActor(Rice);
                foodStage.addActor(Tuna);
                foodStage.addActor(Macaroni);
                foodStage.addActor(Leipa);
                return false;
            }
        });

        // Hedelmät ja Vihannekset
        HeVi.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                foodStage.clear();
                addUi();
                HeViTop = new MyActor("en_fruits-vegetables.png", 317, 530, 200, 50);
                foodStage.addActor(HeViTop);
                foodStage.addActor(Chips);
                foodStage.addActor(Kaalilaatikko);
                foodStage.addActor(Porridge);
                foodStage.addActor(NoodleSoup);
                foodStage.addActor(Coffee);
                foodStage.addActor(Ratatouille);
                return false;
            }
        });

        // Hedelmät ja Vihannekset
        Juomat.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                foodStage.clear();
                addUi();
                JuomatTop = new MyActor("en_drinks.png", 317, 530, 200, 50);
                foodStage.addActor(JuomatTop);
                foodStage.addActor(Chips);
                foodStage.addActor(Kaalilaatikko);
                foodStage.addActor(Porridge);
                foodStage.addActor(NoodleSoup);
                foodStage.addActor(Coffee);
                foodStage.addActor(Ratatouille);
                return false;
            }
        });

        // Hedelmät ja Vihannekset
        Kastikkeet.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                foodStage.clear();
                addUi();
                KastikkeetTop = new MyActor("en_sauces.png", 317, 530, 200, 50);
                foodStage.addActor(KastikkeetTop);
                foodStage.addActor(Chips);
                foodStage.addActor(Kaalilaatikko);
                foodStage.addActor(Porridge);
                foodStage.addActor(NoodleSoup);
                foodStage.addActor(Coffee);
                foodStage.addActor(Ratatouille);
                return false;
            }
        });

        // Hedelmät ja Vihannekset
        Maitotuotteet.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                foodStage.clear();
                addUi();
                MaitotuotteetTop = new MyActor("en_dairy.png", 317, 530, 200, 50);
                foodStage.addActor(MaitotuotteetTop);
                foodStage.addActor(Chips);
                foodStage.addActor(Kaalilaatikko);
                foodStage.addActor(Porridge);
                foodStage.addActor(NoodleSoup);
                foodStage.addActor(Coffee);
                foodStage.addActor(Ratatouille);
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
                        final MyActor orangeBar = new MyActor("orange.png", thisX + 10, thisY + 150, foodActors.get(fIndex).getPrice() * 280, 15);
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
                                    long id = sound.play(1.0f);
                                    foods.add(foodActors.get(fIndex).getType());
                                    System.out.println(foods);
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
        foodStage.addActor(Pakasteet);
        foodStage.addActor(Kastikkeet);
        foodStage.addActor(Juomat);
        foodStage.addActor(HeVi);
        foodStage.addActor(Maitotuotteet);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //päivittää peliaikaa
        gt.updateTime(Gdx.graphics.getDeltaTime());

        //Asettaa taustan värin
        Gdx.gl.glClearColor(1.7f, 1.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Päivittää kameran
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Jatkaa pelaajan statsien päivittämistä
        player.updateStats();

        // Kaupan tausta
        Texture background;
        background = new Texture(Gdx.files.internal("hylly.png"));

        foodStage.act(Gdx.graphics.getDeltaTime());
        foodStage.getBatch().begin();
        foodStage.getBatch().draw(background, 0, 0, 800, 600);

        foodStage.getBatch().end();
        foodStage.draw();

        // Infoboksin ruoan hinta fontti.
        foodStage.getBatch().begin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 0.5f;
        parameter.borderColor = Color.WHITE;
        font = generator.generateFont(parameter);

        if (infoBox == true) {
            font.draw(game.batch, FoodFont.getPrice() + "€", xFont, yFont);
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

    }
}
