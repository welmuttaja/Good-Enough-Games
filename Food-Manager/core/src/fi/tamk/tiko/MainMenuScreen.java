package fi.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

class MainMenuScreen implements Screen {

    final Main game;
    final String LANG;
    final GameTime gt;
    final Player player;
    final ArrayList<Integer> foods;

    OrthographicCamera camera;

    Texture tausta;

    //päävalikon stage ja napit
    Stage menuStage;
    MyActor playButton;
    MyActor settingButton;
    MyActor HTPButton;
    MyActor ENGButton;
    MyActor FINButton;

    //Päävalikon constructor, täällä määritellään uudet elementit
    public MainMenuScreen(final Main game, final GameTime gt, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
        this.LANG = Gdx.app.getPreferences("my-preferences").getString("lang");
        this.gt = gt;
        this.player = player;
        this.foods = foods;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        tausta = new Texture("taustaFHD.png");

        //Stagen määrittely
        menuStage = new Stage(new FitViewport(800, 600), game.batch);
        //Painikkeiden määrittely
        if(LANG == "fi"){
            playButton = new MyActor("fi_startgame.png", 300, 400, 200, 50);
            HTPButton = new MyActor("fi_instructions.png", 300, 300, 200, 50);
        } else{
            playButton = new MyActor("en_startgame.png", 300, 400, 200, 50);
            HTPButton = new MyActor("en_instructions.png", 300, 300, 200, 50);
        }

        FINButton = new MyActor("fin.png", 670, 540, 100, 50);
        ENGButton = new MyActor("eng.png", 550, 540, 100, 50);
        //Lisää painikkeet stageen
        menuStage.addActor(playButton);
        menuStage.addActor(HTPButton);
        menuStage.addActor(FINButton);
        menuStage.addActor(ENGButton);

        //Lisää stageen inputprocessorin
        Gdx.input.setInputProcessor(menuStage);
        //Lisää play painikkeeseen kosketuksen tunnistamisen
        playButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Vaihtaa asunto näkymään
                game.setScreen(new ApartmentScreen(game, LANG, gt, player, foods));
                return false;
            }
        });

        FINButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.getPreferences("my-preferences").putString("lang", "fi");
                Gdx.app.getPreferences("my-preferences").flush();
                game.setScreen(new MainMenuScreen(game, gt, player, foods));
                return false;
            }
        });

        ENGButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.getPreferences("my-preferences").putString("lang", "en");
                Gdx.app.getPreferences("my-preferences").flush();
                game.setScreen(new MainMenuScreen(game, gt, player, foods));
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

        game.batch.begin();
        game.batch.draw(tausta, 0, 0, 800, 600);
        game.batch.end();

        //tekee actorien toiminnot
        menuStage.act(Gdx.graphics.getDeltaTime());
        //piirtää stagen actorit
        menuStage.draw();
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