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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

    private final Sound click = Gdx.audio.newSound(Gdx.files.internal("klikkausaani.wav"));
    private final Music music = Gdx.audio.newMusic(Gdx.files.internal("intro.mp3"));

    BitmapFont font;

    //Päävalikon constructor, täällä määritellään uudet elementit
    public MainMenuScreen(final Main game, final GameTime gt, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
        this.LANG = Gdx.app.getPreferences("my-preferences").getString("lang");
        this.gt = gt;
        this.player = player;
        this.foods = foods;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 14;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 0.2f;
        parameter.borderColor = Color.WHITE;
        font = generator.generateFont(parameter);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        tausta = new Texture("taustaFHD.png");

        music.setLooping(true);
        music.play();

        //Stagen määrittely
        menuStage = new Stage(new FitViewport(800, 600), game.batch);
        //Painikkeiden määrittely
        playButton = new MyActor("en_startgame.png", 300, 400, 200, 50);
        HTPButton = new MyActor("en_instructions.png", 300, 300, 200, 50);
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
                music.stop();
                click.play(1.0f);
                //Vaihtaa asunto näkymään
                game.setScreen(new ApartmentScreen(game, LANG, gt, player, foods));
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
        font.draw(game.batch, "Songs: Chibi Ninja - Resistor Anthems, HHavok-intro - Resistor Anthems, A night Of Dizzy Spells", 10, 40);
        font.draw(game.batch, "Music by Eric Skiff - Available at http://EricSkiff.com/music", 10, 20);
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