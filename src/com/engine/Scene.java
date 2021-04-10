package com.engine;

import java.awt.*;

public abstract class Scene {
    String name;

    public void Scene(String name) {
        this.name = name;
        init();
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2);
}
