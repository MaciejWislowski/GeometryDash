package com.engine;

import com.component.BoxBounds;
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
        testObj = new GameObject("Test",new Transform(new Vector2(0.0f,0.0f)));
        testObj.addComponent(new BoxBounds("Box"));
    }

    @Override
    public void update(double dt) {
        System.out.println(testObj.getComponent(BoxBounds.class).name);;

        testObj.update(dt);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.fillRect(0,0, Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);

    }
}
