package com.engine;

import com.component.*;
import com.dataStructure.AssetPool;
import com.dataStructure.Transform;
import com.file.Parser;
import com.utility.Constants;
import com.utility.Vector2;

import java.awt.*;

public class LevelScene extends Scene {

    public GameObject player;
    public BoxBounds playerBounds;

    KL keyListener;

    public LevelScene(String name) {
        super.Scene(name);
    }


    @Override
    public void init() {
        initAssetPool();

        player = new GameObject("Test",new Transform(new Vector2(300.0f,400.0f)));
        Spritesheet layerOne = AssetPool.getSpritesheet("assets/player/layerOne.png");
        Spritesheet layerTwo = AssetPool.getSpritesheet("assets/player/layerTwo.png");
        Spritesheet layerThree = AssetPool.getSpritesheet("assets/player/layerThree.png");
        Player playerComp = new Player(layerOne.sprites.get(0),layerTwo.sprites.get(0),layerThree.sprites.get(0), Color.RED, Color.GRAY);
        player.addComponent(playerComp);
        player.addComponent(new Rigidbody(new Vector2(395,0)));
        player.addComponent(new BoxBounds(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT));
        playerBounds = new BoxBounds(Constants.TILE_WIDTH,Constants.TILE_HEIGHT);
        player.addComponent(playerBounds);

        GameObject ground;
        ground = new GameObject("Ground",new Transform(new Vector2(0,Constants.GROUND_Y)));
        ground.addComponent(new Ground());

        addGameObject(player);
        addGameObject(ground);

        importLevel("Test");

    }

    public void initAssetPool() {
        AssetPool.addSpritesheet("assets/player/layerOne.png", 42,42,2,13,13*5);
        AssetPool.addSpritesheet("assets/player/layerTwo.png", 42,42,2,13,13*5);
        AssetPool.addSpritesheet("assets/player/layerThree.png", 42,42,2,13,13*5);
        AssetPool.addSpritesheet("assets/groundSprites.png", Constants.TILE_WIDTH, Constants.TILE_HEIGHT, 2, 6, 12);
    }

    private void importLevel(String filename) {
        Parser.openFile(filename);

        GameObject go = Parser.parseGameObject();
        while (go != null) {
            addGameObject(go);
            go = Parser.parseGameObject();
        }
    }

    @Override
    public void update(double dt) {

        if(player.transform.position.x - camera.position.x > Constants.CAMERA_OFFSET_X) {
            camera.position.x = player.transform.position.x - Constants.CAMERA_OFFSET_X;
        }
        if(player.transform.position.y - camera.position.y > Constants.CAMERA_OFFSET_Y) {
            camera.position.y = player.transform.position.y - Constants.CAMERA_OFFSET_Y;
        }

        if(camera.position.y > Constants.CAMERA_OFFSET_GROUND_Y) {
            camera.position.y = Constants.CAMERA_OFFSET_GROUND_Y;
        }

        for(GameObject g: gameObjects) {
            g.update(dt);

            Bounds b = g.getComponent(Bounds.class);
            if(g != player && b != null) {
                if(Bounds.checkCollision(playerBounds, b)) {
                    System.out.println("Colliding!");
                }
            }
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


    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);

        renderer.render(g2);

    }
}
