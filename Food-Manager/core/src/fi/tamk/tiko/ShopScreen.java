package fi.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

//Kaupan pelinäkymä
class ShopScreen implements Screen {
    // Tausta
    private Stage foodStage;
    // Ruoat
    private Actor foodActor;
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
        foodActor = new Food("kananmunat_1.png", 325, 380);
        cartActor = new Cart("kassakone.png", 350, 0);

        // Takaisin päin nappula
        backButton = new MyActor("exit.png", 0, 0, 200, 50);

        // Lisätään näytteljät.
        foodStage.addActor(foodActor);
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
        //Ruutua klikatessa vaihtaa kauppavalikkoon. Testausta varten, saa poistaa tarvittaessa.
        if (Gdx.input.justTouched()) {
            game.setScreen(new ShopScreen(game));
        }

        // Kaupan tausta
        Texture background;
        background = new Texture(Gdx.files.internal("kauppa.jpg"));

        foodStage.act(Gdx.graphics.getDeltaTime());
        foodStage.getBatch().begin();
        foodStage.getBatch().draw(background, 0, 0, 800, 600);
        foodStage.getBatch().end();
        foodStage.draw();

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
