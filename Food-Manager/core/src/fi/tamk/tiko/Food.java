package fi.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

// Ruoan luonti luokka.
public class Food extends Actor {
    private Texture texture;

    //Ruoan constructor
    public Food(String textureStr, float x, float y) {
        texture = new Texture(Gdx.files.internal(textureStr));
        setWidth(150);
        setHeight(150);
        setBounds(x, y, getWidth(), getHeight());

    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}


