package fi.tamk.tiko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

// Ruoan luonti luokka.
class Food extends Actor {
    private Texture texture;

    private float cartX = 350;
    private float cartY = 20;

    Sound sound = Gdx.audio.newSound(Gdx.files.internal("cha-ching.wav"));

    //Ruoan constructor
    public Food(String textureStr, final float x, final float y, float w, float h, int p, float en,
                float we, float he, float ha) {
        texture = new Texture(Gdx.files.internal(textureStr));
        setWidth(w);
        setHeight(h);
        setBounds(x, y, getWidth(), getHeight());

        // Ruoan draggaus kauppanäkymässä.
        addListener(new DragListener() {
            public void drag(InputEvent event, float xx, float yy, int pointer) {
                moveBy(xx - getWidth() / 2, yy - getHeight() / 2);
                toFront();
                System.out.println(getX());
                System.out.println(getY());
                if(getX() > cartX && getY() < cartY && getX() < cartX + 30) {
                    setX(x);
                    setY(y);
                    long id = sound.play(1.0f);
                    cancel();
                }
                if(getX() < 0 || getY() < 0 || getX() > 800 || getY() > 600) {
                    setX(x);
                    setY(y);
                    cancel();
                }
            }
        });

        // Long-tap info
        addListener(new ActorGestureListener()  {
            public void longPress(InputEvent event, float xxx, float yyy) {
            setX(200);
            setY(200);
            }
        });
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


