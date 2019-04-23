package fi.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import static java.lang.String.valueOf;

class ApartmentScreen implements Screen {
    final Main game;
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

    boolean foodSelected = false;
    float selectedFoodX = 0;
    float selectedFoodY = 0;

    final GameTime gt;
    final Player player;
    final ArrayList<Integer> foods;

    ArrayList<FoodActor> foodActors=new ArrayList<FoodActor>();

    Boolean fridgeOpen = false;

    Sound sound = Gdx.audio.newSound(Gdx.files.internal("eating.mp3"));

    Sound click = Gdx.audio.newSound(Gdx.files.internal("klikkausaani.wav"));

    //Asuntonäkymän constructor
    public ApartmentScreen(final Main game, final GameTime gt, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
        this.gt = gt;
        this.player = player;
        this.foods = foods;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        apartmentbg = new Texture("apartmentbg.png");

        //Stagen määrittely
        apartmentStage = new Stage(new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT), game.batch);
        //Painikkeiden määrittely
        shopButton = new MyActor("kauppa.png", 10, 0, 110, 110);
        fridgeActor = new MyActor("test-transparent.png", 0, 50, 200, 540);
        //Jääkaapin valikon tausta
        fridgeMenuBg = new MyActor("fridgebg2.png", 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        fridgeMenuBg.setVisible(fridgeOpen);
        //Jääkaapin sulje painike
        exitButton = new MyActor("exit.png", 550, 10, 210, 70);
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

        //Lisää ruoat jääkaappiin
        float leftMargin = 50;
        float topMargin = 130;

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
                leftMargin += 70;
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

                            System.out.println("contains food");

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

                            System.out.println("contains " + output);

                            foods.add(Integer.valueOf(output));
                        }
                    }
                }

                //Siirtyy kauppa näkymään
                long id = click.play(1.0f);
                game.setScreen(new ShopScreen(game, gt, player, foods));

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

                        final MyActor eat = new MyActor("eatbutton.png", thisX + 10, thisY + 10, 90, 30);
                        final MyActor close = new MyActor("exitbutton.png", thisX + 110, thisY + 10, 90, 30);
                        final MyActor foodStatBg = new MyActor("menubg.png", thisX, thisY, 300, 150);
                        final MyActor blueBar = new MyActor("blue.png", thisX + 10, thisY + 125, foodActors.get(fIndex).getEnergy() * 280, 15);
                        final MyActor redBar = new MyActor("red.png", thisX + 10, thisY + 100, foodActors.get(fIndex).getWeight() * 280, 15);
                        final MyActor greenBar = new MyActor("green.png", thisX + 10, thisY + 75, foodActors.get(fIndex).getHealthiness() * 280, 15);
                        final MyActor yellowBar = new MyActor("yellow.png", thisX + 10, thisY + 50, foodActors.get(fIndex).getHappiness() * 280, 15);

                        apartmentStage.addActor(foodStatBg);
                        apartmentStage.addActor(blueBar);
                        apartmentStage.addActor(redBar);
                        apartmentStage.addActor(greenBar);
                        apartmentStage.addActor(yellowBar);

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
                                eat.remove();
                                close.remove();

                                foodSelected = false;

                                long id = sound.play(1.0f);

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


    @Override
    public void render(float delta) {

        if(player.getEnergy() > 0 && player.getWeight() > 0 && player.getHealthiness() > 0 && player.getHappiness() > 0){

            //päivittää peliaikaa
            gt.updateTime(Gdx.graphics.getDeltaTime());

            //päivittää pelaajan statsit ja statsi mittarit
            player.updateStats();
        } else{
            game.setScreen(new GameOverScreen(game, Math.round(gt.getTime())));
        }

        charEnergy.setWidth(player.getEnergy() * 280);
        charWeight.setWidth(player.getWeight() * 280);
        charHealthiness.setWidth(player.getHealthiness() * 280);
        charHappiness.setWidth(player.getHappiness() * 280);

        //Asettaa taustan värin
        Gdx.gl.glClearColor(0.5f, 0.7f, 0.5f, 1);
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
        game.font.draw(game.batch, "Time: " + timeString + "    " + dateString, 10, 580);
        game.font.draw(game.batch, "Money: " + player.getMoney(), 10, 550);

        game.batch.end();

        //tekee actorien toiminnot
        apartmentStage.act(Gdx.graphics.getDeltaTime());
        //piirtää stagen actorit
        apartmentStage.draw();
    }

    @Override
    public void resize(int width, int height) {
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