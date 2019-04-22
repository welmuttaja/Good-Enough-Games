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
    private Actor Noodles;
    private Actor Mikropizza;
    private Actor SalmonSoup;
    private Actor PastaBolognese;
    private Actor MakaroniLaatikko;
    private Actor Munakas;
    private Actor NoodleSoup;
    private Actor ChocolateCereal;
    private Actor Pasta;
    private Actor Yogurt;
    private Actor MeatBalls;
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

    // Ostoskori
    private Actor cartActor;
    // Ruokaryhmä
    ArrayList<FoodActor> foodActors=new ArrayList<FoodActor>();

    // Väliaikainen testi koko.
    private float width = 8f;
    private float height = 5f;

    SpriteBatch batch;
    final Main game;
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

    OrthographicCamera camera;

    boolean foodSelected = false;

    int j = 0;

    //Kauppanäkymän constructor
    public ShopScreen(final Main game, final GameTime gt, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
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
        MeatBalls = new FoodActor(6, x1, y1, w, h);
        SalmonSoup = new FoodActor(7, x2, y1, w, h);
        Porridge = new FoodActor(8, x3, y1, w, h);
        PastaBolognese = new FoodActor(9, x1, y2, w, h);
        MakaroniLaatikko = new FoodActor(10, x2, y2, w, h);
        Munakas = new FoodActor(11, x3, y2, w, h);
        NoodleSoup = new FoodActor(12, x1, y2, w, h);
        Noodles = new FoodActor(13, x3, y2, w, h);
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
        foodActors.add((FoodActor) MeatBalls);
        foodActors.add((FoodActor) SalmonSoup);
        foodActors.add((FoodActor) Porridge);
        foodActors.add((FoodActor) PastaBolognese);
        foodActors.add((FoodActor) MakaroniLaatikko);
        foodActors.add((FoodActor) Munakas);
        foodActors.add((FoodActor) NoodleSoup);
        foodActors.add((FoodActor) Noodles);
        foodActors.add((FoodActor) ChocolateCereal);
        foodActors.add((FoodActor) YogurtMysli);
        foodActors.add((FoodActor) Coffee);
        foodActors.add((FoodActor) Ratatouille);
        foodActors.add((FoodActor) Chips);
        foodActors.add((FoodActor) Kaalilaatikko);

        // Random etusivu
        Random1 = new FoodActor(random(19), x1, y1, w, h);
        Random2 = new FoodActor(random(19), x2, y1, w, h);
        Random3 = new FoodActor(random(19), x3, y1, w, h);
        Random4 = new FoodActor(random(19), x1, y2, w, h);
        Random5 = new FoodActor(random(19), x2, y2, w, h);
        Random6 = new FoodActor(random(19), x3, y2, w, h);

        foodActors.add((FoodActor) Random1);
        foodActors.add((FoodActor) Random2);
        foodActors.add((FoodActor) Random3);
        foodActors.add((FoodActor) Random4);
        foodActors.add((FoodActor) Random5);
        foodActors.add((FoodActor) Random6);

        cartActor = new MyActor("ostoskori.png", 350, 20, 120, 120);

        // Takaisin päin nappula.
        backButton = new MyActor("koti.png", 0, 0, 80, 80);

        // Kategoria nappulat.
        Pakasteet = new MyActor("Pakasteet.png", 635, 400, 160, 80);
        Kastikkeet = new MyActor("Kastikkeet.png", 0, 400, 160, 80);
        Juomat = new MyActor("Juomat.png", 0, 200, 160, 80);
        HeVi = new MyActor("HeVi.png", 635, 200, 160, 80);
        Maitotuotteet = new MyActor("Maitotuotteet.png", 0, 300, 160, 80);
        Alennukset = new MyActor("alennukset.png", 317, 500, 160, 80);

        // Alkunäkymä, sisältää random alennukset.
        addUi();
        foodStage.addActor(Alennukset);
        foodStage.addActor(Random1);
        foodStage.addActor(Random2);
        foodStage.addActor(Random3);
        foodStage.addActor(Random4);
        foodStage.addActor(Random5);
        foodStage.addActor(Random6);

        //Lisää stageen inputprocessorin
        Gdx.input.setInputProcessor(foodStage);
        //Lisää takaisin nappiin kosketuksen tunnistamisen
        backButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Vaihtaa menu näkymään
                game.setScreen(new ApartmentScreen(game, gt, player, foods));
                return false;
            }
        });

        // Pakasteet
        Pakasteet.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                foodStage.clear();
                addUi();
                foodStage.addActor(Eggs);
                foodStage.addActor(Beans);
                foodStage.addActor(Rice);
                foodStage.addActor(Tuna);
                foodStage.addActor(Macaroni);
                foodStage.addActor(Noodles);
                return false;
            }
        });

        // Hedelmät ja Vihannekset
        HeVi.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                foodStage.clear();
                addUi();
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

            // Ruoan draggaus kauppanäkymässä.
            foodActors.get(i).addListener(new DragListener() {
                public void drag(InputEvent event, float xx, float yy, int pointer) {
                    float cartX = 315;
                    float cartY = 49;
                    foodActors.get(fIndex).moveBy(xx - foodActors.get(fIndex).getWidth() / 2, yy - foodActors.get(fIndex).getHeight() / 2);
                    foodActors.get(fIndex).toFront();
                    System.out.println(foodActors.get(fIndex).getX());
                    System.out.println(foodActors.get(fIndex).getY());
                    if(foodActors.get(fIndex).getX() > cartX && foodActors.get(fIndex).getY() < cartY && foodActors.get(fIndex).getX() < cartX + 100) {
                        foodActors.get(fIndex).setX(x1);
                        foodActors.get(fIndex).setY(y1);
                        if(player.getMoney() - foodActors.get(j).getPrice() > 0){
                            long id = sound.play(1.0f);
                            cancel();
                            foods.add(foodActors.get(fIndex).getType());
                            System.out.println(foods);
                            player.setMoney(player.getMoney() - foodActors.get(fIndex).getPrice());
                        }

                    }
                    if(foodActors.get(fIndex).getX() < -50 || foodActors.get(fIndex).getY() < -50 || foodActors.get(fIndex).getX() > 750 || foodActors.get(fIndex).getY() > 550) {
                        foodActors.get(fIndex).setX(x1);
                        foodActors.get(fIndex).setY(y1);
                        cancel();
                    }
                    while(foodSelected == true) {
                        // dragStop(event, xx, yy, pointer);
                    }
                }
            });

            foodActors.get(i).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    if (foodSelected == false) {

                        foodSelected = true;

                        float thisX = 490;
                        float thisY = 50;

                        if(foodActors.get(fIndex).getX() < 490) {

                            thisX = foodActors.get(fIndex).getX() + 50;
                        }
                        if(foodActors.get(fIndex).getY() > 50) {

                            thisY = foodActors.get(fIndex).getY() - 100;
                        }

                        final MyActor eat = new MyActor("eatbutton.png", thisX + 10, thisY + 10, 90, 30);
                        final MyActor close = new MyActor("exitbutton.png", thisX + 110, thisY + 10, 90, 30);
                        final MyActor foodStatBg = new MyActor("menubg.png", thisX, thisY, 300, 150);
                        final MyActor blueBar = new MyActor("blue.png", thisX + 10, thisY + 125, foodActors.get(fIndex).getEnergy() * 280, 15);
                        final MyActor redBar = new MyActor("red.png", thisX + 10, thisY + 100, foodActors.get(fIndex).getWeight() * 280, 15);
                        final MyActor greenBar = new MyActor("green.png", thisX + 10, thisY + 75, foodActors.get(fIndex).getHealthiness() * 280, 15);
                        final MyActor yellowBar = new MyActor("yellow.png", thisX + 10, thisY + 50, foodActors.get(fIndex).getHappiness() * 280, 15);

                        foodStage.addActor(foodStatBg);
                        foodStage.addActor(blueBar);
                        foodStage.addActor(redBar);
                        foodStage.addActor(greenBar);
                        foodStage.addActor(yellowBar);

                        foodStage.addActor(close);

                        close.addListener(new InputListener() {
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                                foodStatBg.remove();
                                blueBar.remove();
                                redBar.remove();
                                greenBar.remove();
                                yellowBar.remove();
                                eat.remove();
                                close.remove();

                                foodSelected = false;

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
        foodStage.addActor(cartActor);
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
