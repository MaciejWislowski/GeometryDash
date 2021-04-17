package com.ui;

import com.component.Sprite;
import com.component.Spritesheet;
import com.dataStructure.Transform;
import com.engine.Component;
import com.engine.GameObject;
import com.utility.Constants;
import com.utility.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainContainer extends Component {

    public List<GameObject> menuItems;

    public MainContainer() {
        menuItems = new ArrayList<>();
        init();
    }

    public void init() {
        Spritesheet groundSprites = new Spritesheet("assets/groundSprites.png", Constants.TILE_WIDTH, Constants.TILE_HEIGHT, 2, 6, 12);
        Spritesheet buttonSprites = new Spritesheet("assets/ui/buttonSprites.png", 60, 60, 2, 2,2);

        for(int i=0; i<groundSprites.sprites.size();i++) {
            Sprite currentSprite = groundSprites.sprites.get(i);
            int x = Constants.BUTTON_OFFSET_X + (currentSprite.column * Constants.BUTTON_WIDTH) + (currentSprite.column * Constants.BUTTON_SPACING_HZ);
            int y = Constants.BUTTON_OFFSET_Y + (currentSprite.row * Constants.BUTTON_HEIGHT) + (currentSprite.row * Constants.BUTTON_SPACING_VT);

            GameObject obj = new GameObject("Generated", new Transform(new Vector2(x,y)));
            obj.addComponent(currentSprite.copy());

            MenuItem menuItem = new MenuItem(x,y,Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, buttonSprites.sprites.get(0), buttonSprites.sprites.get(1));
            obj.addComponent(menuItem);
            menuItems.add(obj);
        }
    }

    public void start() {
        for(GameObject g: menuItems) {
            for (Component c: g.getAllComponents()) {
                c.start();
            }
        }
    }


    @Override
    public void update(double dt) {
        for (GameObject g: this.menuItems) {
            g.update(dt);
        }
    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void draw(Graphics2D g2) {
        for (GameObject g: this.menuItems) {
            g.draw(g2);
        }
    }

    public List<GameObject> getMenuItems() {
        return menuItems;
    }

}
