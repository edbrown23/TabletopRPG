package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.ActiveUnit;

import java.awt.*;

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
public class EnemyInfoRenderer extends Renderer {
    private ActiveUnit enemy;
    private int x;
    private int y;
    private int width;
    private int height;

    public EnemyInfoRenderer(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillRect(x, y, width, height);
    }

    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setEnemy(ActiveUnit enemy) {
        this.enemy = enemy;
    }
}
