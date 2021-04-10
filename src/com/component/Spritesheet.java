package com.component;

import com.dataStructure.AssetPool;

import java.util.ArrayList;
import java.util.List;

public class Spritesheet {

    public List<Sprite> sprites;
    public int tileWidth, tileHeight, spacing;

    public Spritesheet ( String pictureFile,int tileWidth, int tileHeight,int spacing, int columns, int size) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.spacing = spacing;

        Sprite parent = AssetPool.getSprite(pictureFile);
        sprites = new ArrayList<>();
        int row = 0;
        int count = 0;
        while (count < size) {
            for(int column = 0; column < columns; column++) {
                int imgX = (column * tileWidth) + (column * spacing);
                int imgY = (row*tileHeight) + (row * spacing);

                sprites.add(new Sprite(parent.image.getSubimage(imgX,imgY,tileWidth,tileHeight)));
                count++;
                if(count > size - 1) {
                    break;
                }
            }
            row++;
        }

    }
}
