package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.ActiveUnit;
import com.perceptron.TabletopRPG.Sprite;
import com.perceptron.TabletopRPG.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
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
 * Created By: Eric Brown
 * Date: 1/30/13
 */
public class PlayerInfoRenderer extends Renderer {
    private ActiveUnit player;
    private int x;
    private int y;
    private int width;
    private int height;

    public PlayerInfoRenderer(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Graphics2D g2d) {
        renderSelectedInfo(g2d, x, y, width / 2, height);

        renderPlayerInfo(g2d, x + (width / 2), y, width / 2, height);

        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.black);
        g2d.drawLine(x + (width / 2), y, x + (width / 2), y + height);
    }

    private void renderSelectedInfo(Graphics2D g2d, int ix, int iy, int iwidth, int iheight){

    }

    private void renderPlayerInfo(Graphics2D g2d, int ix, int iy, int iwidth, int iheight){
        // Draw a big version of the player sprite
        g2d.setColor(Color.black);
        int h = iheight - 30;
        g2d.fillRect(ix + 15, iy + 15, h, h);
        h = h - 20;
        g2d.drawImage(getSprite(), ix + 25, iy + 25, h, h, null);
    }

    private BufferedImage getSprite(){
        return SpriteManager.wizardSprite.getSprite(0);
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setPlayer(ActiveUnit player) {
        this.player = player;
    }
}
