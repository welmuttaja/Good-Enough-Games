package fi.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

import jdk.nashorn.internal.runtime.Source;

//Kaupan pelinäkymä
class ShopScreen implements Screen {
    // Tausta
    private Stage foodStage;
    // Ruoat
    private Actor foodActorEggs;
    private Actor foodActorBeans;
    private Actor foodActorRice;

    // Ostoskori
    private Actor cartActor;
    // Ruokaryhmä
    private Group foodGroup;

    // Väliaikainen testi koko.
    private float width = 8f;
    private float height = 5f;

    SpriteBatch batch;
    final Main game;

    // Takaisinpäin nappula.
    MyActor backButton;

    OrthographicCamera camera;

    //Kauppanäkymän constructor
    public ShopScreen(final Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        // Luodaan näyttämö ja näyttelijät sekä tausta.
        foodStage = new Stage(new FitViewport(800, 600), game.batch);
        foodGroup = new Group();

        // Luodaan ruokia ja ostoskori.
        foodActorEggs = new MyActor("eggs.png", 200, 400, 100, 100);
        foodActorBeans = new MyActor("beans.png", 350, 400, 90, 90);
        foodActorRice = new MyActor("rice.png", 500, 400, 80, 80);
        cartActor = new MyActor("ostoskori.png", 350, 20, 120, 120);

        // Takaisin päin nappula
        backButton = new MyActor("koti.png", 0, 0, 80, 80);

        // Lisätään näytteljät.
        foodStage.addActor(foodActorEggs);
        foodStage.addActor(foodActorBeans);
        foodStage.addActor(foodActorRice);
        foodStage.addActor(cartActor);
        foodStage.addActor(backButton);

        //Lisää stageen inputprocessorin
        Gdx.input.setInputProcessor(foodStage);
        //Lisää takaisin nappiin kosketuksen tunnistamisen
        backButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Vaihtaa menu näkymään
                game.setScreen(new ApartmentScreen(game));
                return false;
            }
        });

        // Ruokien draggaus

        foodActorEggs.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                foodActorEggs.moveBy(x - foodActorEggs.getWidth() / 2, y - foodActorEggs.getHeight() / 2);
                foodActorEggs.toFront();
                System.out.println(foodActorEggs.getX());
                if(foodActorEggs.getX() > cartActor.getX() && foodActorEggs.getY() < cartActor.getY()) {
                    foodActorEggs.setX(200);
                    foodActorEggs.setY(400);
                }
            }
        });

        foodActorBeans.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                foodActorBeans.moveBy(x - foodActorBeans.getWidth() / 2, y - foodActorBeans.getHeight() / 2);
                foodActorBeans.toFront();
                System.out.println(foodActorBeans.getX());
                if(foodActorBeans.getX() > cartActor.getX() && foodActorBeans.getY() < cartActor.getY()) {
                    foodActorBeans.setX(350);
                    foodActorBeans.setY(400);
                }
            }
        });

        foodActorRice.addListener(new DragListener() {
            public void drag(InputEvent event, float x, float y, int pointer) {
                foodActorRice.moveBy(x - foodActorRice.getWidth() / 2, y - foodActorRice.getHeight() / 2);
                foodActorRice.toFront();
                System.out.println(foodActorRice.getX());
                if(foodActorRice.getX() > cartActor.getX() && foodActorRice.getY() < cartActor.getY()) {
                    foodActorRice.setX(500);
                    foodActorRice.setY(400);
                }
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Asettaa taustan värin
        Gdx.gl.glClearColor(1.7f, 1.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Päivittää kameran
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Kaupan tausta
        Texture background;
        background = new Texture(Gdx.files.internal("kauppa.jpg"));

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
