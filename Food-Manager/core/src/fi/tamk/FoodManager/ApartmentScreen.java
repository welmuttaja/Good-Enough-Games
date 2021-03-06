package fi.tamk.FoodManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import static java.lang.String.valueOf;

class ApartmentScreen implements Screen {
    final Main game;
    final Preferences prefs;

    final int SCREEN_WIDTH = 800;
    final int SCREEN_HEIGHT = 600;

    OrthographicCamera camera;

    Boolean gameOver;

    Texture apartmentbg;

    Stage apartmentStage;

    MyActor fridgeActor;
    MyActor fridgeMenuBg;
    MyActor shopButton;
    MyActor exitButton;

    MyActor statBg;
    MyActor charEnergy;
    MyActor charWeight;
    MyActor charHealthiness;
    MyActor charHappiness;

    MyActor energyIcon;
    MyActor weightIcon;
    MyActor healthinessIcon;
    MyActor happinessIcon;

    MyActor energyIconFridge;
    MyActor weightIconFridge;
    MyActor healthinessIconFridge;
    MyActor happinessIconFridge;

    Texture happyHuman;
    Texture sadHuman;
    Texture fatHuman;
    Texture tiredHuman;

    boolean foodSelected = false;
    float selectedFoodX = 0;
    float selectedFoodY = 0;

    final GameTime gt;
    final Player player;
    final ArrayList<Integer> foods;

    ArrayList<FoodActor> foodActors=new ArrayList<FoodActor>();

    Boolean fridgeOpen = false;

    private final Sound eatSound = Gdx.audio.newSound(Gdx.files.internal("eating.mp3"));
    private final Sound click = Gdx.audio.newSound(Gdx.files.internal("klikkausaani.wav"));
    private final Sound closeSound = Gdx.audio.newSound(Gdx.files.internal("close.wav"));
    Music music = Gdx.audio.newMusic(Gdx.files.internal("apartmentMusic.mp3"));

    MyActor mute;
    MyActor unmute;

    public boolean musicON = true;

    //Asuntonäkymän constructor
    public ApartmentScreen(final Main game, final Preferences prefs, final GameTime gt, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
        this.prefs = prefs;
        this.gt = gt;
        this.player = player;
        this.foods = foods;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        apartmentbg = new Texture("apartmentbg.png");

        // Hahmot
        happyHuman = new Texture("happyhuman.png");
        sadHuman = new Texture("sadhuman.png");
        tiredHuman = new Texture("tiredhuman.png");
        fatHuman = new Texture("fathuman.png");

        //Stagen määrittely
        apartmentStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), game.batch);
        //Painikkeiden määrittely
        shopButton = new MyActor("kauppa.png", 10, 0, 110, 110);
        fridgeActor = new MyActor("test-transparent.png", 0, 50, 200, 540);
        //Jääkaapin valikon tausta
        fridgeMenuBg = new MyActor("fridgebg2.png", 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        fridgeMenuBg.setVisible(fridgeOpen);
        //Jääkaapin sulje painike
        exitButton = new MyActor(prefs.getString("close"), 550, 10, 210, 70);
        exitButton.setVisible(fridgeOpen);

        statBg = new MyActor("menubg.png", 450, 460, 340, 130);
        charEnergy = new MyActor("blue.png", 500, 560, 280 * player.getEnergy(), 20);
        charWeight = new MyActor("red.png", 500, 530, 280 * player.getWeight(), 20);
        charHealthiness = new MyActor("green.png", 500, 500, 280 * player.getHealthiness(), 20);
        charHappiness = new MyActor("yellow.png", 500, 470, 280 * player.getHappiness(), 20);

        energyIcon = new MyActor("energia.png", 460, 555, 25, 25);
        weightIcon = new MyActor("paino.png", 460, 527, 25, 25);
        healthinessIcon = new MyActor("terveys.png", 460, 498, 25, 25);
        happinessIcon = new MyActor("onnellisuus.png", 460, 465, 25, 25);

        mute = new MyActor("mute_button.png", 700, 0, 100, 100);
        unmute = new MyActor("unmute_button.png", 700, 0, 100, 100);

        apartmentStage.addActor(mute);
        apartmentStage.addActor(unmute);
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



        //Lisää ruoat jääkaappiin
        float leftMargin = 50;
        float topMargin = 140;

        for( int i = 0; i < foods.size(); i++ ){
            float x = fridgeMenuBg.getX() + leftMargin;
            float y = fridgeMenuBg.getTop() - topMargin;

            foodActors.add(new FoodActor(foods.get(i), x, y, 80, 80));
            foodActors.get(i).setName("food" + foods.get(i));
            foodActors.get(i).setVisible(fridgeOpen);
            foodActors.get(i).clearListeners();

            if(leftMargin > 600){
                leftMargin = 50;
                topMargin += 210;
            } else{
                leftMargin += 100;
            }

        }

        //Lisää actorit stageen
        apartmentStage.addActor(statBg);
        apartmentStage.addActor(charEnergy);
        apartmentStage.addActor(charWeight);
        apartmentStage.addActor(charHealthiness);
        apartmentStage.addActor(charHappiness);

        apartmentStage.addActor(happinessIcon);
        apartmentStage.addActor(weightIcon);
        apartmentStage.addActor(healthinessIcon);
        apartmentStage.addActor(energyIcon);

        apartmentStage.addActor(fridgeActor);
        apartmentStage.addActor(fridgeMenuBg);

        //Lisää ruokatavarat stageen
        for( int i = 0; i < foodActors.size(); i++){
            apartmentStage.addActor(foodActors.get(i));
        }

        apartmentStage.addActor(shopButton);
        apartmentStage.addActor(exitButton);

        //Lisää stageen inputprocessorin
        Gdx.input.setInputProcessor(apartmentStage);

        //Lisää jääkaappiin kosketuksen tunnistamisen
        fridgeActor.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Avaa fridge näkymän
                if(fridgeOpen) {
                    fridgeOpen = false;
                } else {
                    fridgeOpen = true;
                }

                fridgeMenuBg.setVisible(fridgeOpen);
                exitButton.setVisible(fridgeOpen);
                shopButton.setVisible(false);
                for(int i = 0; i < foodActors.size(); i++){
                    foodActors.get(i).setVisible(fridgeOpen);
                }

                return false;
            }
        });
        //Lisää jääkaapin sulje nappiin kosketuksen tunnistamisen
        exitButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                //Sulkee fridge näkymän
                if(fridgeOpen) {
                    fridgeOpen = false;
                } else {
                    fridgeOpen = true;
                }

                fridgeMenuBg.setVisible(fridgeOpen);
                exitButton.setVisible(fridgeOpen);
                shopButton.setVisible(true);

                for(int i = 0; i < foodActors.size(); i++){
                    foodActors.get(i).setVisible(fridgeOpen);
                }
                closeSound.play(1.0f);
                return false;
            }
        });

        //Lisää kauppa painikkeeseen kosketuksen tunnistamisen
        shopButton.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                foods.clear();

                for(int i = 0; i < apartmentStage.getActors().size; i++) {

                    String str = apartmentStage.getActors().get(i).getName();

                    if (str != null) {
                        if (str.contains("food")) {

                            StringBuilder sb = new StringBuilder();
                            boolean found = false;
                            for (char c : str.toCharArray()) {
                                if (Character.isDigit(c)) {
                                    sb.append(c);
                                    found = true;
                                } else if (found) {
                                    // If we already found a digit before and this char is not a digit, stop looping
                                    break;
                                }
                            }

                            String output = sb.toString();

                            foods.add(Integer.valueOf(output));
                        }
                    }
                }

                //Siirtyy kauppa näkymään
                music.stop();
                click.play(1.0f);
                game.setScreen(new ShopScreen(game, prefs, gt, player, foods));

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

                        float thisX = 490;
                        float thisY = 50;

                        if(foodActors.get(fIndex).getX() < 490) {

                            thisX = foodActors.get(fIndex).getX() + 50;
                        }
                        if(foodActors.get(fIndex).getY() > 50) {

                            thisY = foodActors.get(fIndex).getY() - 100;
                        }

                        final MyActor eat = new MyActor(prefs.getString("eat"), thisX + 10, thisY + 10, 90, 30);
                        final MyActor close = new MyActor(prefs.getString("exit"), thisX + 110, thisY + 10, 90, 30);
                        final MyActor foodStatBg = new MyActor("menubg.png", thisX, thisY, 300, 150);
                        final MyActor blueBar = new MyActor("blue.png", thisX + 10, thisY + 125, foodActors.get(fIndex).getEnergy() * 280, 15);
                        final MyActor redBar = new MyActor("red.png", thisX + 10, thisY + 100, foodActors.get(fIndex).getWeight() * 280, 15);
                        final MyActor greenBar = new MyActor("green.png", thisX + 10, thisY + 75, foodActors.get(fIndex).getHealthiness() * 280, 15);
                        final MyActor yellowBar = new MyActor("yellow.png", thisX + 10, thisY + 50, foodActors.get(fIndex).getHappiness() * 280, 15);
                        energyIconFridge = new MyActor("energia.png", thisX + 10, thisY + 125, 15, 15);
                        weightIconFridge = new MyActor("paino.png", thisX + 10, thisY + 100, 15, 15);
                        healthinessIconFridge = new MyActor("terveys.png", thisX + 10, thisY + 75, 15, 15);
                        happinessIconFridge = new MyActor("onnellisuus.png", thisX + 10, thisY + 50, 15, 15);

                        apartmentStage.addActor(foodStatBg);
                        apartmentStage.addActor(blueBar);
                        apartmentStage.addActor(redBar);
                        apartmentStage.addActor(greenBar);
                        apartmentStage.addActor(yellowBar);
                        apartmentStage.addActor(energyIconFridge);
                        apartmentStage.addActor(weightIconFridge);
                        apartmentStage.addActor(healthinessIconFridge);
                        apartmentStage.addActor(happinessIconFridge);

                        apartmentStage.addActor(eat);
                        apartmentStage.addActor(close);

                        eat.addListener(new InputListener() {
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                                player.setEnergy(player.getEnergy() + foodActors.get(fIndex).getEnergy());
                                player.setWeight(player.getWeight() + foodActors.get(fIndex).getWeight());
                                player.setHealthiness(player.getHealthiness() + foodActors.get(fIndex).getHealthiness());
                                player.setHappiness(player.getHappiness() + foodActors.get(fIndex).getHappiness());

                                foodActors.get(fIndex).remove();
                                foodStatBg.remove();
                                blueBar.remove();
                                redBar.remove();
                                greenBar.remove();
                                yellowBar.remove();
                                energyIconFridge.remove();
                                weightIconFridge.remove();
                                healthinessIconFridge.remove();
                                happinessIconFridge.remove();
                                eat.remove();
                                close.remove();

                                foodSelected = false;

                                eatSound.play(1.0f);

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
                                energyIconFridge.remove();
                                weightIconFridge.remove();
                                healthinessIconFridge.remove();
                                happinessIconFridge.remove();
                                eat.remove();
                                close.remove();

                                closeSound.play(1.0f);

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


    @Override
    public void render(float delta) {

        if(player.getEnergy() > 0 && player.getWeight() > 0 && player.getHealthiness() > 0 && player.getHappiness() > 0){

            //päivittää peliaikaa
            gt.updateTime(Gdx.graphics.getDeltaTime());

            //päivittää pelaajan statsit ja statsi mittarit
            player.updateStats();
        } else{
            music.stop();
            musicON = false;
            game.setScreen(new GameOverScreen(game, Math.round(gt.getTime())));
        }

        charEnergy.setWidth(player.getEnergy() * 280);
        charWeight.setWidth(player.getWeight() * 280);
        charHealthiness.setWidth(player.getHealthiness() * 280);
        charHappiness.setWidth(player.getHappiness() * 280);

        //Asettaa taustan värin
        Gdx.gl.glClearColor(176f/255, 216f/255, 230f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Päivittää kameran
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        String timeString = "";

        if(gt.getHours() <= 9){
            timeString += "0";
        }

        timeString += gt.getHours() + ":";

        if(Math.round(gt.getMinutes()) < 10){
            timeString += "0";
        }

        timeString += Math.round(gt.getMinutes());

        String dateString = String.valueOf(gt.getDays());

        switch(gt.getDays()){
            case 1:
                dateString += "st";
                break;
            case 2:
                dateString += "nd";
                break;
            case 3:
                dateString += "rd";
                break;
            default:
                dateString += "th";
                break;
        }

        dateString += " of ";

        switch(gt.getMonths()){
            case 1:
                dateString += "January";
                break;
            case 2:
                dateString += "February";
                break;
            case 3:
                dateString += "March";
                break;
            case 4:
                dateString += "April";
                break;
            case 5:
                dateString += "May";
                break;
            case 6:
                dateString += "June";
                break;
            case 7:
                dateString += "July";
                break;
            case 8:
                dateString += "August";
                break;
            case 9:
                dateString += "September";
                break;
            case 10:
                dateString += "October";
                break;
            case 11:
                dateString += "November";
                break;
            case 12:
                dateString += "December";
                break;
        }

        game.batch.begin();
        //Piirtää taustan
        game.batch.draw(apartmentbg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        game.font.draw(game.batch, prefs.getString("time") + timeString + "    " + dateString, 10, 580);
        game.font.draw(game.batch, prefs.getString("money") + player.getMoney() + "€", 10, 550);

        // Piirtää hahmon
        if (player.getHappiness() <= 0.3f || player.getEnergy() <= 0.3f
                || player.getHealthiness() <= 0.3f) {
            game.batch.draw(sadHuman, 460, 0, 400, 400);
        }
        if (player.getWeight() >= 0.8f) {
            game.batch.draw(fatHuman, 460, 0, 400, 400);
        }
        if (player.getEnergy() <= 0.3f) {
            game.batch.draw(tiredHuman, 460, 0, 400, 400);
        }
        else {
            game.batch.draw(happyHuman, 460, 0, 400, 400);
        }
        game.batch.end();

        //tekee actorien toiminnot
        apartmentStage.act(Gdx.graphics.getDeltaTime());
        //piirtää stagen actorit
        apartmentStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        apartmentStage.getViewport().update(width,height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}