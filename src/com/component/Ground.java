package com.component;

import com.engine.*;
import com.engine.Component;
import com.engine.Window;
import com.utility.Constants;

import java.awt.*;

public class Ground extends Component {



    public void update(double dt) {
        if (!Window.getWindow().isInEditor) {

            LevelScene scene = (LevelScene) Window.getWindow().getCurrentScene();
            GameObject player = scene.player;

            if (player.transform.position.y + player.getComponent(BoxBounds.class).height > gameObject.transform.position.y) {
                player.transform.position.y = gameObject.transform.position.y - player.getComponent(BoxBounds.class).height;

                player.getComponent(Player.class).onGround = true;
            }
            gameObject.transform.position.x = scene.camera.position.x;
        } else {
            gameObject.transform.position.x = Window.getWindow().getCurrentScene().camera.position.x - 10;
        }
    }


    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawRect((int)gameObject.transform.position.x - 10, (int)gameObject.transform.position.y, Constants.SCREEN_WIDTH + 20, Constants.SCREEN_HEIGHT);
    }

    public Component copy() {
        return null;
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
