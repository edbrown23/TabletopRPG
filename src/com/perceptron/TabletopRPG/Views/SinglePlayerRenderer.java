package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.*;
import com.perceptron.TabletopRPG.Models.*;

import java.awt.*;
import java.util.ArrayList;

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
public class SinglePlayerRenderer extends Renderer {
    private SinglePlayerState singlePlayerState;
    private IntegerPoint2D selectorCoords;
    private InGameMenuRenderer menuRenderer;

    public SinglePlayerRenderer(SinglePlayerState singlePlayerState){
        menuRenderer = new InGameMenuRenderer(0, 0, 0, 0);

        this.singlePlayerState = singlePlayerState;
        camera = new Camera(0, 0, Utilities.renderingPanelWidth, Utilities.renderingPanelHeight);
        renderingCamera = new Camera(0, 0, Utilities.renderingPanelWidth, Utilities.renderingPanelHeight);
    }

    @Override
    public void render(Graphics2D g2d) {
        clearScreen(g2d);

        copyCamera();
        updateMenuDimensions();

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, camera.getWidth(), camera.getHeight());

        singlePlayerState.render(g2d, renderingCamera);

        renderSelector(g2d);

        menuRenderer.render(g2d);
    }

    private void renderSelector(Graphics2D g2d){
        if(selectorCoords.x != -1 && selectorCoords.y != -1){
            //System.out.println(selectorCoords.x + " " + selectorCoords.y);
            g2d.drawImage(SpriteManager.selectorSprite.getSprite(0), renderingCamera.applyCameraX(selectorCoords.x), renderingCamera.applyCameraY(selectorCoords.y), renderingCamera.getZoomLevel(), renderingCamera.getZoomLevel(), null);
        }
    }

    public void updateMenuDimensions(){
        menuRenderer.setPosition(0, renderingCamera.getHeight() - 200);
        menuRenderer.setDimensions(renderingCamera.getWidth(), 200);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setSelectorCoords(IntegerPoint2D coords){
        selectorCoords = coords;
    }

    private void clearScreen(Graphics2D g2d){
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, Utilities.renderingPanelWidth, Utilities.renderingPanelHeight);
    }

    public InGameMenuRenderer getMenuRenderer(){
        return menuRenderer;
    }
}
