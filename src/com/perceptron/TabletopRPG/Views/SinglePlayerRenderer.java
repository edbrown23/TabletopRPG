package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.*;
import com.perceptron.TabletopRPG.Models.*;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 12/19/12
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
public class SinglePlayerRenderer implements Renderer {
    private SinglePlayerState singlePlayerState;
    private Camera camera;
    private LightingRenderer lightingRenderer;

    public SinglePlayerRenderer(SinglePlayerState singlePlayerState){
        this.singlePlayerState = singlePlayerState;
        camera = new Camera();
        lightingRenderer = new LightingRenderer(singlePlayerState.getCurrentWorldLayer(), camera);
    }

    @Override
    public void render(Graphics2D g2d) {
        WorldLayer layer = singlePlayerState.getCurrentWorldLayer();
        int height = camera.getZoomAdjustedY() + camera.getZoomAdjustedHeight();
        int width = camera.getZoomAdjustedX() + camera.getZoomAdjustedWidth();
        for(int y = camera.getZoomAdjustedY() - 5; y < height + 5; y++){
            for(int x = camera.getZoomAdjustedX() - 5; x < width + 5; x++){
                if(x >= layer.getWidth() || y >= layer.getHeight() || x < 0 || y < 0){
                    g2d.setColor(Color.black);
                    g2d.fillRect(x * camera.getZoomLevel() - camera.getX(), y * camera.getZoomLevel() - camera.getY(), camera.getZoomLevel(), camera.getZoomLevel());
                }else{
                    Cell currentCell = layer.getCell(x, y);
                    switch(currentCell.getType()){
                        case Rock:
                            g2d.drawImage(SpriteManager.rockSprite.getCurrentSprite(), x * camera.getZoomLevel()  - camera.getX(), y * camera.getZoomLevel() - camera.getY(), camera.getZoomLevel(), camera.getZoomLevel(), null);
                            break;
                        case Dirt:
                            g2d.drawImage(SpriteManager.dirtSprite.getCurrentSprite(), x * camera.getZoomLevel() - camera.getX(), y * camera.getZoomLevel() - camera.getY(), camera.getZoomLevel(), camera.getZoomLevel(), null);
                            break;
                    }
                }
            }
        }
        lightingRenderer.setLayer(layer);
        lightingRenderer.render(g2d);

        g2d.setColor(Color.black);
        for(int y = camera.getZoomAdjustedY() - 5; y < height + 5; y++){
            if(y < layer.getHeight() && y >= 0){
                g2d.drawLine(0, y * camera.getZoomLevel() - camera.getY(), camera.getWidth(), y * camera.getZoomLevel() - camera.getY());
            }
        }

        for(int x = camera.getZoomAdjustedX() - 5; x < width + 5; x++){
            if(x < layer.getWidth() && x >= 0){
                g2d.drawLine(x * camera.getZoomLevel() - camera.getX(), 0, x * camera.getZoomLevel() - camera.getX(), camera.getHeight());
            }
        }

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, camera.getHeight(), camera.getWidth(), camera.getHeight());
    }

    public Camera getCamera() {
        return camera;
    }
}
