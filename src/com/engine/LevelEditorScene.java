package com.engine;

import com.component.BoxBounds;
import com.component.Spritesheet;
import com.dataStructure.AssetPool;
import com.dataStructure.Transform;
import com.utility.Constants;
import com.utility.Vector2;

import java.awt.*;

public class LevelEditorScene extends Scene {

    GameObject testObj;

    public LevelEditorScene(String name) {
        super.Scene(name);
    }

    @Override
    public void init() {
        testObj = new GameObject("Test",new Transform(new Vector2(100.0f,300.0f)));
        Spritesheet spritesheet = new Spritesheet("assets/player/layerOne.png", 42,42,2,13,13*5);
        testObj.addComponent(spritesheet.sprites.get(43));
    }

    @Override
    public void update(double dt) {
        testObj.update(dt);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);

        testObj.draw(g2);

    }
}
