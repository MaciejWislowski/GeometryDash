package com.component;

import com.engine.Component;
import com.engine.GameObject;
import com.engine.LevelEditorScene;
import com.utility.Constants;

import javax.swing.*;
import java.awt.*;

public class Ground extends Component {



    public void update(double dt) {
        GameObject player = LevelEditorScene.getScene().player;
        if(player.transform.position.y + player.getComponent(BoxBounds.class).height > gameObject.transform.position.y)
            player.transform.position.y = gameObject.transform.position.y - player.getComponent(BoxBounds.class).height;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawRect((int)gameObject.transform.position.x - 10, (int)gameObject.transform.position.y, Constants.SCREEN_WIDTH + 20, Constants.SCREEN_HEIGHT);
    }
}
