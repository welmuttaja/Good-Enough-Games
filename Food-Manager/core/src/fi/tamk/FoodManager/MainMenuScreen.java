package fi.tamk.FoodManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
    final GameTime gt;
    final Player player;
    final ArrayList<Integer> foods;

    Preferences lang_pref;
    Preferences prefs;

    OrthographicCamera camera;

    Texture tausta;

    //päävalikon stage ja napit
    Stage menuStage;
    MyActor playButton;
    MyActor settingButton;
    MyActor HTPButton;
    MyActor ENGButton;
    MyActor FINButton;

    MyActor mute;
    MyActor unmute;

    private final Sound click = Gdx.audio.newSound(Gdx.files.internal("klikkausaani.wav"));
    private final Music music = Gdx.audio.newMusic(Gdx.files.internal("intro.mp3"));
    private final Sound langSound = Gdx.audio.newSound(Gdx.files.internal("close.wav"));

    BitmapFont font;

    //Päävalikon constructor, täällä määritellään uudet elementit
    public MainMenuScreen(final Main game, final GameTime gt, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
        lang_pref = Gdx.app.getPreferences("preferences_lang");

        if(lang_pref.getString("lang") == null ? "preferences_en" != null : !lang_pref.getString("lang").equals("preferences_en")) {
            lang_pref.putString("lang", "preferences_fi");
        }

        String lang = lang_pref.getString("lang");

        this.foods = foods;
        this.prefs = Gdx.app.getPreferences(lang);
        this.gt = gt;
        this.player = player;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 14;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 0.2f;
        parameter.borderColor = Color.WHITE;
        font = generator.generateFont(parameter);

        //asettaa localisaation

        if(lang == null ? "preferences_fi" == null : lang.equals("preferences_fi")){

            prefs.putString("close", "fi_close");
            prefs.putString("eat", "fi_eat.png");
            prefs.putString("close", "fi_close.png");
            prefs.putString("time", "Aika: ");
            prefs.putString("money", "Rahat: ");
            prefs.putString("instructions", "fi_instructions.png");
            prefs.putString("startgame", "fi_startgame.png");
            prefs.putString("frozen", "fi_frozen.png");
            prefs.putString("sauces", "fi_sauces.png");
            prefs.putString("drinks", "fi_drinks.png");
            prefs.putString("fruits-vegetables", "fi_fruits-vegetables.png");
            prefs.putString("dairy", "fi_dairy.png");
            prefs.putString("sales", "fi_sales.png");
            prefs.putString("buy", "fi_buy.png");
            prefs.putString("exit", "fi_exit.png");
            prefs.putString("points", "Pisteet: ");
            prefs.putString("extras", "fi_extras.png");
            prefs.putString("meat", "fi_meat.png");

        } else if(lang == null ? "preferences_en" == null : lang.equals("preferences_en")) {

            prefs.putString("close", "en_close");
            prefs.putString("eat", "en_eat.png");
            prefs.putString("close", "en_close.png");
            prefs.putString("time", "Time: ");
            prefs.putString("money", "Money: ");
            prefs.putString("instructions", "en_instructions.png");
            prefs.putString("startgame", "en_startgame.png");;
            prefs.putString("frozen", "en_frozen.png");
            prefs.putString("sauces", "en_sauces.png");
            prefs.putString("drinks", "en_drinks.png");
            prefs.putString("fruits-vegetables", "en_fruits-vegetables.png");
            prefs.putString("dairy", "en_dairy.png");
            prefs.putString("sales", "en_sales.png");
            prefs.putString("buy", "en_buy.png");
            prefs.putString("exit", "en_exit.png");
            prefs.putString("points", "Points: ");
            prefs.putString("extras", "en_extras.png");
            prefs.putString("meat", "en_meat.png");
        }

        lang_pref.flush();
        prefs.flush();

        System.out.println("testi" + prefs.getString("buy"));

        mute = new MyActor("mute_button.png", 700, 0, 100, 100);
        unmute = new MyActor("unmute_button.png", 700, 0, 100, 100);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        tausta = new Texture("taustaFHD.png");

        music.setLooping(true);
        music.play();

        //Stagen määrittely
        menuStage = new Stage(new FitViewport(800, 600), game.batch);

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
        System.out.println(prefs + "preferenssit" + prefs.getString("time"));
        //Painikkeiden määrittely
        playButton = new MyActor(prefs.getString("startgame"), 300, 300, 200, 50);
        FINButton = new MyActor("fin.png", 670, 540, 100, 50);
        ENGButton = new MyActor("eng.png", 550, 540, 100, 50);
        //Lisää painikkeet stageen
        menuStage.addActor(playButton);
        menuStage.addActor(FINButton);
        menuStage.addActor(ENGButton);
        menuStage.addActor(mute);
        menuStage.addActor(unmute);

        FINButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                langSound.play();
                Preferences lang_pref = Gdx.app.getPreferences("preferences_lang");
                lang_pref.putString("lang", "preferences_fi");
                lang_pref.flush();

                music.stop();
                click.play(1.0f);

                game.setScreen(new MainMenuScreen(game, gt, player, foods));
                return false;
            }
        });

        ENGButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                langSound.play();
                Preferences lang_pref = Gdx.app.getPreferences("preferences_lang");
                lang_pref.putString("lang", "preferences_en");
                lang_pref.flush();

                music.stop();
                click.play(1.0f);

                game.setScreen(new MainMenuScreen(game, gt, player, foods));
                return false;
            }
        });

        //Lisää stageen inputprocessorin
        Gdx.input.setInputProcessor(menuStage);
        //Lisää play painikkeeseen kosketuksen tunnistamisen
        playButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                music.stop();
                click.play(1.0f);
                //Vaihtaa asunto näkymään
                game.setScreen(new ApartmentScreen(game, prefs, gt, player, foods));
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
        Gdx.gl.glClearColor(176f/255, 216f/255, 230f/255, 1);
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
        menuStage.getViewport().update(width,height);
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