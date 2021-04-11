package com.engine;

import com.component.BoxBounds;
import com.component.Player;
import com.component.Spritesheet;
import com.dataStructure.AssetPool;
import com.dataStructure.Transform;
import com.utility.Constants;
import com.utility.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {

    GameObject player;
    KL keyListener;

    public LevelEditorScene(String name, KL keyListener) {
        super.Scene(name);
        this.keyListener = keyListener;
    }

    @Override
    public void init() {
        player = new GameObject("Test",new Transform(new Vector2(100.0f,300.0f)));
        Spritesheet layerOne = new Spritesheet("assets/player/layerOne.png", 42,42,2,13,13*5);
        Spritesheet layerTwo = new Spritesheet("assets/player/layerTwo.png", 42,42,2,13,13*5);
        Spritesheet layerThree = new Spritesheet("assets/player/layerThree.png", 42,42,2,13,13*5);
        Player playerComp = new Player(layerOne.sprites.get(0),layerTwo.sprites.get(0),layerThree.sprites.get(0), Color.RED, Color.GRAY);
        player.addComponent(playerComp);

        renderer.submit(player);

    }

    @Override
    public void update(double dt) {
        player.update(dt);
        if(keyListener.isKeyPressed(KeyEvent.VK_Q)) player.transform.rotation += dt * 5f;
        if(keyListener.isKeyPressed(KeyEvent.VK_E)) player.transform.rotation += dt * -5f;
        if(keyListener.isKeyPressed(KeyEvent.VK_NUMPAD8)) {
            player.transform.scale.x += dt * 0.9f;
            player.transform.scale.y += dt * 0.9f;
        }
        if(keyListener.isKeyPressed(KeyEvent.VK_NUMPAD2)) {
            player.transform.scale.x += dt * -0.9f;
            player.transform.scale.y += dt * -0.9f;
        }

        camera.position.x += dt * 30f;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);

        renderer.render(g2);

    }
}
