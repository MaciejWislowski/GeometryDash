package com.engine;

import com.component.*;
import com.dataStructure.AssetPool;
import com.dataStructure.Transform;
import com.file.Parser;
import com.ui.MainContainer;
import com.utility.Constants;
import com.utility.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LevelEditorScene extends Scene {

    public GameObject player;
    private Grid grid;
    private CameraControls cameraControls;
    public GameObject mouseCursor;
    private MainContainer editingButtons;

    public LevelEditorScene(String name) {
        super.Scene(name);

    }

    @Override
    public void init() {
        initAssetPool();
        editingButtons = new MainContainer();

        grid = new Grid();
        cameraControls = new CameraControls();
        editingButtons.start();

        mouseCursor = new GameObject("Mouse Cursor", new Transform(new Vector2()));
        mouseCursor.addComponent(new SnapToGrid(Constants.TILE_WIDTH,Constants.TILE_HEIGHT));

        player = new GameObject("Test",new Transform(new Vector2(300.0f,400.0f)));
        Spritesheet layerOne = AssetPool.getSpritesheet("assets/player/layerOne.png");
        Spritesheet layerTwo = AssetPool.getSpritesheet("assets/player/layerTwo.png");
        Spritesheet layerThree = AssetPool.getSpritesheet("assets/player/layerThree.png");
        Player playerComp = new Player(layerOne.sprites.get(0),layerTwo.sprites.get(0),layerThree.sprites.get(0), Color.RED, Color.GRAY);
        player.addComponent(playerComp);

        GameObject ground;
        ground = new GameObject("Ground",new Transform(new Vector2(0,Constants.GROUND_Y)));
        ground.addComponent(new Ground());

        ground.setNonserializable();
        player.setNonserializable();

        addGameObject(player);
        addGameObject(ground);

        Parser.openFile("Test");
        System.out.println(Parser.parseInt());
        System.out.println(Parser.parseInt());
        System.out.println(Parser.parseDouble());
        System.out.println(Parser.parseFloat());
        System.out.println(Parser.parseBoolean());
        System.out.println(Parser.parseBoolean());
        System.out.println(Parser.parseString());
        System.out.println(Parser.parseString());
    }

    public void initAssetPool() {
        AssetPool.addSpritesheet("assets/player/layerOne.png", 42,42,2,13,13*5);
        AssetPool.addSpritesheet("assets/player/layerTwo.png", 42,42,2,13,13*5);
        AssetPool.addSpritesheet("assets/player/layerThree.png", 42,42,2,13,13*5);
        AssetPool.addSpritesheet("assets/groundSprites.png", Constants.TILE_WIDTH, Constants.TILE_HEIGHT, 2, 6, 12);
        AssetPool.addSpritesheet("assets/ui/buttonSprites.png", 60, 60, 2, 2,2);
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
        editingButtons.update(dt);
        mouseCursor.update(dt);

        if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_F1)) {
            export("Test");
        }
    }

    private void export(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream("levels/" + fileName + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            zos.putNextEntry(new ZipEntry(fileName + ".json"));

            int i = 0;
            for (GameObject go: gameObjects) {
                String str = go.serialize(0);
                if(str.compareTo("") != 0) {
                    zos.write(str.getBytes());
                    if(i != gameObjects.size()-1) {
                        zos.write(",\n".getBytes());
                    }
                }
                i++;
            }
            zos.closeEntry();
            zos.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);

        renderer.render(g2);
        grid.draw(g2);
        editingButtons.draw(g2);

        // Should be drawn last
        mouseCursor.draw(g2);

    }
}
