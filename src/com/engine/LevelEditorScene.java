package com.engine;

import com.component.*;
import com.dataStructure.Transform;
import com.utility.Constants;
import com.utility.Vector2;

import java.awt.*;

public class LevelEditorScene extends Scene {

    public GameObject player;
    Grid grid;
    CameraControls cameraControls;
    GameObject mouseCursor;

    public LevelEditorScene(String name) {
        super.Scene(name);

    }

    @Override
    public void init() {
        grid = new Grid();
        cameraControls = new CameraControls();

        Spritesheet objects = new Spritesheet("assets/spritesheet.png", 42,42,2,6,12);
        Sprite mouseSprite = objects.sprites.get(0);
        mouseCursor = new GameObject("Mouse Cursor", new Transform(new Vector2()));
        mouseCursor.addComponent(new SnapToGrid(Constants.TILE_WIDTH,Constants.TILE_HEIGHT));
        mouseCursor.addComponent(mouseSprite);

        player = new GameObject("Test",new Transform(new Vector2(300.0f,400.0f)));
        Spritesheet layerOne = new Spritesheet("assets/player/layerOne.png", 42,42,2,13,13*5);
        Spritesheet layerTwo = new Spritesheet("assets/player/layerTwo.png", 42,42,2,13,13*5);
        Spritesheet layerThree = new Spritesheet("assets/player/layerThree.png", 42,42,2,13,13*5);
        Player playerComp = new Player(layerOne.sprites.get(0),layerTwo.sprites.get(0),layerThree.sprites.get(0), Color.RED, Color.GRAY);
        player.addComponent(playerComp);

        GameObject ground;
        ground = new GameObject("Ground",new Transform(new Vector2(0,Constants.GROUND_Y)));
        ground.addComponent(new Ground());

        addGameObject(player);
        addGameObject(ground);

    }

    @Override
    public void update(double dt) {

        if(camera.position.y > Constants.CAMERA_OFFSET_GROUND_Y) {
            camera.position.y = Constants.CAMERA_OFFSET_GROUND_Y;
        }

        for(GameObject g: gameObjects) {
            g.update(dt);
        }
//        if(keyListener.isKeyPressed(KeyEvent.VK_Q)) player.transform.rotation += dt * 5f;
//        if(keyListener.isKeyPressed(KeyEvent.VK_E)) player.transform.rotation += dt * -5f;
//        if(keyListener.isKeyPressed(KeyEvent.VK_NUMPAD8)) {
//            player.transform.scale.x += dt * 0.9f;
//            player.transform.scale.y += dt * 0.9f;
//        }
//        if(keyListener.isKeyPressed(KeyEvent.VK_NUMPAD2)) {
//            player.transform.scale.x += dt * -0.9f;
//            player.transform.scale.y += dt * -0.9f;
//        }

        cameraControls.update(dt);
        grid.update(dt);
        mouseCursor.update(dt);


    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);

        renderer.render(g2);
        grid.draw(g2);
        mouseCursor.draw(g2);

    }
}
