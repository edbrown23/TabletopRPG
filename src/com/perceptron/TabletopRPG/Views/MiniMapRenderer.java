package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.Cell;
import com.perceptron.TabletopRPG.Models.WorldLayer;
import com.perceptron.TabletopRPG.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 1/31/13
 * This software falls under the MIT license, as follows:
 * Copyright (C) 2012
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * <p/>
 */
public class MiniMapRenderer implements Renderer {
    private WorldLayer layer;
    private BufferedImage cachedMap;
    private int x;
    private int y;
    private int width;
    private int height;

    public MiniMapRenderer(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Graphics2D g2d) {
        int drawX = x;
        int drawY = y;
        int drawWidth = cachedMap.getWidth();
        int drawHeight = cachedMap.getHeight();
        float widthRatio = (float)width / (float)cachedMap.getWidth();
        float heightRatio = (float)height / (float)cachedMap.getHeight();
        if(widthRatio < heightRatio){
            drawWidth = (int)(drawWidth * widthRatio + 0.5f);
            drawHeight = (int)(drawHeight * widthRatio + 0.5f);
            drawY = ((height - drawHeight) / 2) + y;
        }else if(heightRatio < widthRatio){
            drawWidth = (int)(drawWidth * heightRatio + 0.5f);
            drawHeight = (int)(drawHeight * heightRatio + 0.5f);
            drawX = ((width - drawWidth) / 2) + x;
        }
        g2d.drawImage(cachedMap, drawX, drawY, drawWidth, drawHeight, null);
    }

    public void setLayer(WorldLayer layer){
        this.layer = layer;
        updateCachedMap();
    }

    private void updateCachedMap(){
        int detailFactor = 10;
        cachedMap = new BufferedImage(layer.getWidth() * detailFactor, layer.getHeight() * detailFactor, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = cachedMap.createGraphics();
        for(int y = 0; y < layer.getHeight(); y++){
            for(int x = 0; x < layer.getWidth(); x++){
                Cell cell = layer.getCell(x, y);
                switch(cell.getType()){
                    case Dirt:
                        g2d.drawImage(SpriteManager.dirtSprite.getCurrentSprite(), x * detailFactor, y * detailFactor, detailFactor, detailFactor, null);
                        break;
                    case Rock:
                        g2d.drawImage(SpriteManager.rockSprite.getCurrentSprite(), x * detailFactor, y * detailFactor, detailFactor, detailFactor, null);
                        break;
                }
            }
        }
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
}
