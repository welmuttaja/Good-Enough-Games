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
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

class ApartmentScreen implements Screen {
    final Main game;

    OrthographicCamera camera;

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

    final Player player;
    final ArrayList<Integer> foods;

    ArrayList<FoodActor> foodActors=new ArrayList<FoodActor>();

    Boolean fridgeOpen = false;

    Sound sound = Gdx.audio.newSound(Gdx.files.internal("eating.mp3"));

    //Asuntonäkymän constructor
    public ApartmentScreen(final Main game, final Player player, final ArrayList<Integer> foods) {
        this.game = game;
        this.player = player;
        this.foods = foods;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        apartmentbg = new Texture("apartmentbg.png");

        //Stagen määrittely
        apartmentStage = new Stage(new FitViewport(800, 600), game.batch);
        //Painikkeiden määrittely
        shopButton = new MyActor("kauppa.png", 0, 0, 80, 80);
        fridgeActor = new MyActor("test-transparent.png", 100, 45, 100, 260);
        //Jääkaapin valikon tausta
        fridgeMenuBg = new MyActor("menubg.png", 200, 100, 400, 400);
        fridgeMenuBg.setVisible(fridgeOpen);
        //Jääkaapin sulje painike
        exitButton = new MyActor("exit.png", fridgeMenuBg.getX() + 230, fridgeMenuBg.getY() + 10, 150, 50);
        exitButton.setVisible(fridgeOpen);

        statBg = new MyActor("menubg.png", 490, 460, 300, 130);
        charEnergy = new MyActor("blue.png", 500, 560, 280 * player.getEnergy(), 20);
        charWeight = new MyActor("red.png", 500, 530, 280 * player.getWeight(), 20);
        charHealthiness = new MyActor("green.png", 500, 500, 280 * player.getHealthiness(), 20);
        charHappiness = new MyActor("yellow.png", 500, 470, 280 * player.getHappiness(), 20);

        energyIcon = new MyActor("energia.png", 455, 555, 30, 30);
        weightIcon = new MyActor("paino.png", 455, 525, 30, 30);
        healthinessIcon = new MyActor("terveys.png", 455, 495, 30, 30);
        happinessIcon = new MyActor("onnellisuus.png", 455, 460, 30, 30);

        //Lisää ruoat jääkaappiin
        float leftMargin = 10;
        float topMargin = 10;

        for( int i = 0; i < foods.size(); i++ ){
            float x = fridgeMenuBg.getX() + leftMargin;
            float y = fridgeMenuBg.getTop() - 50 - topMargin;

            foodActors.add(new FoodActor(foods.get(i), x, y, 50, 50));
            foodActors.get(i).setName("food" + foods.get(i));
            foodActors.get(i).setVisible(fridgeOpen);

            if(leftMargin == 310){
                leftMargin = 10;
                topMargin += 60;
            } else{
                leftMargin += 60;
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
        apartmentStage.addActor(exitButton);
        apartmentStage.addActor(shopButton);

        //Lisää ruokatavarat stageen
        for( int i = 0; i < foodActors.size(); i++){
            apartmentStage.addActor(foodActors.get(i));
        }

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
                game.setScreen(new ShopScreen(game, player, foods));

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

                        float thisX = foodActors.get(fIndex).getX() + 50;
                        float thisY = foodActors.get(fIndex).getY() - 100;

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

        //päivittää pelaajan statsit ja statsi mittarit
        player.updateStats();
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

        game.batch.begin();
        //Piirtää taustan
        game.batch.draw(apartmentbg, 0, 0, 800, 600);
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