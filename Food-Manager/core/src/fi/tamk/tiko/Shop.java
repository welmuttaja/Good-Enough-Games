package fi.tamk.tiko;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;

// Kauppa näkymä pelissä
public class Shop extends Game {
    // Tausta
    private Stage stage;
    // Ruoat (myöhemmin erikseen eri ruoat.)
    private Actor foodActor;
    // Ostoskori
    private Actor cartActor;
    // Ruokaryhmä
    private Group foodGroup;

    // Väliaikainen testi koko.
    private float width = 8f;
    private float height= 5f;

    SpriteBatch batch;

    @Override
    public void create() {
        //Asettaa ruokavalikon näkymäksi.
        //this.setScreen(new ShopScreen(this));

        // Luodaan näyttämö ja näyttelijät.
        stage = new Stage(new FitViewport(width, height), batch);
        foodGroup = new Group();
        foodActor = new Actor();
        cartActor = new Actor();

        foodGroup.addActor(foodActor);
    }

    // Perus osuma detection.
    //public Actor hit (float x, float y, boolean touchable) {
        //if (touchable && getTouchable() != Touchable.enabled) return null;
        //return x >= 0 && x < width && y >= 0 && y < height ? this : null;
        //}

}
