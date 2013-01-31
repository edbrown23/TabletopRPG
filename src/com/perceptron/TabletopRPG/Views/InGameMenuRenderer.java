package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.ActiveUnit;
import com.perceptron.TabletopRPG.Models.Cell;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType;
import sun.security.krb5.internal.crypto.KeyUsage;

import javax.security.auth.Subject;
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
public class InGameMenuRenderer implements Renderer {
    private PlayerInfoRenderer playerInfoRenderer;
    private EnemyInfoRenderer enemyInfoRenderer;
    private TileInfoRenderer tileInfoRenderer;
    private Renderer currentRenderer;
    private int x;
    private int y;
    private int width;
    private int height;

    public InGameMenuRenderer(int x, int y, int width, int height){
        playerInfoRenderer = new PlayerInfoRenderer(x, y, width, height);
        enemyInfoRenderer = new EnemyInfoRenderer(x, y, width, height);
        tileInfoRenderer = new TileInfoRenderer(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Graphics2D g2d) {
        if(currentRenderer != null){
            currentRenderer.render(g2d);
        }else{
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(x, y, width, height);
        }
    }

    public void activatePlayerInfoRenderer(ActiveUnit player){
        playerInfoRenderer.setPosition(x, y);
        playerInfoRenderer.setDimensions(width, height);
        playerInfoRenderer.setPlayer(player);

        currentRenderer = playerInfoRenderer;
    }

    public void activateEnemyInfoRenderer(ActiveUnit enemy){
        enemyInfoRenderer.setPosition(x, y);
        enemyInfoRenderer.setDimensions(width, height);
        enemyInfoRenderer.setEnemy(enemy);

        currentRenderer = enemyInfoRenderer;
    }

    public void activateTileInfoRenderer(Cell tile){
        tileInfoRenderer.setPosition(x, y);
        tileInfoRenderer.setDimensions(width, height);
        tileInfoRenderer.setTile(tile);

        currentRenderer = tileInfoRenderer;
    }

    public void activateIdleRenderer(){
        currentRenderer = null;
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
