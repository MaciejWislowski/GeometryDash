package com.ui;

import com.component.SnapToGrid;
import com.component.Sprite;
import com.engine.Component;
import com.engine.GameObject;
import com.engine.LevelEditorScene;
import com.engine.Window;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;


public class MenuItem extends Component {

    private int x, y, width, height;
    private Sprite buttonSprite, hoverSprite, myImage;

    public MainContainer mainContainer;
    List<GameObject> menuItemList;

    public boolean isSelected = false;

    private int bufferX, bufferY;

    public MenuItem(int x, int y, int width, int height, Sprite buttonSprite, Sprite hoverSprite, MainContainer mainContainer) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonSprite = buttonSprite;
        this.hoverSprite = hoverSprite;
        this.mainContainer = mainContainer;
    }

    public void start() {
        myImage = gameObject.getComponent(Sprite.class);

        this.bufferX = (int)((this.width/2.0) - (myImage.width / 2.0));
        this.bufferY = (int)((this.height/2.0) - (myImage.height / 2.0));
    }

    @Override
    public void update(double dt) {
        if(
                Window.getWindow().mouseListener.x > this.x && Window.getWindow().mouseListener.x <= this.x + this.width &&
                Window.getWindow().mouseListener.y > this.y && Window.getWindow().mouseListener.y <= this.y + this.height) {
            if(Window.getWindow().mouseListener.mousePressed && Window.getWindow().mouseListener.mouseButton == MouseEvent.BUTTON1) {
                // Clicked inside the button
                GameObject obj = gameObject.copy();

                menuItemList = mainContainer.getMenuItems();
                for(int i = 0; i <menuItemList.size()-1;i++) {
                    GameObject gO = menuItemList.get(i);
                    gO.getComponent(MenuItem.class);
                    System.out.println(gO.isSelected);
                    gO.isSelected = false;
                    mainContainer.menuItems.set(i, gO);
                }
                obj.removeComponent(MenuItem.class);
                LevelEditorScene scene = (LevelEditorScene) Window.getWindow().getCurrentScene();

                SnapToGrid snapToGrid = scene.mouseCursor.getComponent(SnapToGrid.class);
                obj.addComponent(snapToGrid);
                scene.mouseCursor = obj;
                isSelected = true;

            }
        }


    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(buttonSprite.image, this.x, this.y, this.width, this.height, null);
        g2.drawImage(myImage.image, this.x + bufferX, this.y + bufferY, myImage.width, myImage.height, null);



        if(isSelected) {
            g2.drawImage(hoverSprite.image, this.x, this.y, this.width, this.height,null);
        }
    }

    @Override
    public String serialize(int tabSize) {
        return "";
    }
}
