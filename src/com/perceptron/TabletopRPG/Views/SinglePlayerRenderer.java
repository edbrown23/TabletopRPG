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
public class SinglePlayerRenderer implements Renderer {
    private SinglePlayerState singlePlayerState;
    private Camera camera;
    private Camera renderingCamera;
    private LightingRenderer lightingRenderer;
    private IntegerPoint2D selectorCoords;
    private InGameMenuRenderer menuRenderer;

    public SinglePlayerRenderer(SinglePlayerState singlePlayerState){
        menuRenderer = new InGameMenuRenderer(0, 0, 0, 0);

        this.singlePlayerState = singlePlayerState;
        camera = new Camera(0, 0, Utilities.renderingPanelWidth, Utilities.renderingPanelHeight);
        renderingCamera = new Camera(0, 0, Utilities.renderingPanelWidth, Utilities.renderingPanelHeight);
        lightingRenderer = new LightingRenderer(singlePlayerState.getCurrentWorldLayer(), renderingCamera);
    }

    @Override
    public void render(Graphics2D g2d) {
        clearScreen(g2d);

        copyCamera();
        lightingRenderer.setCamera(renderingCamera);

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, camera.getWidth(), camera.getHeight());

        WorldLayer layer = singlePlayerState.getCurrentWorldLayer();
        int height = renderingCamera.getZoomAdjustedY() + (renderingCamera.getZoomAdjustedHeight()) + 2;
        int width = renderingCamera.getZoomAdjustedX() + (renderingCamera.getZoomAdjustedWidth()) + 2;

        renderLayer(g2d, layer, width, height);

        renderPlayers(g2d, layer);

        renderLines(g2d, layer, width, height);

        lightingRenderer.setLayer(layer);
        lightingRenderer.render(g2d);

        renderSelector(g2d);

        menuRenderer.render(g2d);
    }

    private void renderPlayers(Graphics2D g2d, WorldLayer layer){
        ArrayList<ActiveUnit> players = layer.getPlayers();
        for(ActiveUnit player : players){
            g2d.drawImage(SpriteManager.wizardSprite.getCurrentSprite(), renderingCamera.applyCameraX(player.getX()), renderingCamera.applyCameraY(player.getY()), renderingCamera.getZoomLevel(), renderingCamera.getZoomLevel(), null);
        }
    }

    private void renderLayer(Graphics2D g2d, WorldLayer layer, int width, int height){
        for(int y = renderingCamera.getZoomAdjustedY(); y < height; y++){
            for(int x = renderingCamera.getZoomAdjustedX(); x < width; x++){
                if(boundsCheck(x, y, layer)){
                    Cell currentCell = layer.getCell(x, y);
                    switch(currentCell.getType()){
                        case Rock:
                            g2d.drawImage(SpriteManager.rockSprite.getCurrentSprite(), renderingCamera.applyCameraX(x), renderingCamera.applyCameraY(y), renderingCamera.getZoomLevel(), renderingCamera.getZoomLevel(), null);
                            break;
                        case Dirt:
                            g2d.drawImage(SpriteManager.dirtSprite.getCurrentSprite(), renderingCamera.applyCameraX(x), renderingCamera.applyCameraY(y), renderingCamera.getZoomLevel(), renderingCamera.getZoomLevel(), null);
                            break;
                    }
                }
            }
        }
    }

    private void renderLines(Graphics2D g2d, WorldLayer layer, int width, int height){
        g2d.setColor(Color.black);
        float zoomLevel = renderingCamera.getBareZoomLevel();
        if(renderingCamera.getBareZoomLevel() == 1) zoomLevel = 1.8f;
        g2d.setStroke(new BasicStroke(zoomLevel));
        for(int y = renderingCamera.getZoomAdjustedY(); y < height + 1; y++){
            g2d.drawLine(renderingCamera.applyCameraX(0), renderingCamera.applyCameraY(y), renderingCamera.applyCameraX(layer.getWidth()), renderingCamera.applyCameraY(y));
        }
        for(int x = renderingCamera.getZoomAdjustedX(); x < width + 1; x++){
            g2d.drawLine(renderingCamera.applyCameraX(x), renderingCamera.applyCameraY(0), renderingCamera.applyCameraX(x), renderingCamera.applyCameraY(layer.getHeight()));
        }
    }

    private void renderSelector(Graphics2D g2d){
        if(selectorCoords.x != -1 && selectorCoords.y != -1){
            //System.out.println(selectorCoords.x + " " + selectorCoords.y);
            g2d.drawImage(SpriteManager.selectorSprite.getCurrentSprite(), renderingCamera.applyCameraX(selectorCoords.x), renderingCamera.applyCameraY(selectorCoords.y), renderingCamera.getZoomLevel(), renderingCamera.getZoomLevel(), null);
        }
    }

    private void copyCamera(){
        renderingCamera.setWidth(camera.getWidth());
        renderingCamera.setHeight(camera.getHeight());
        renderingCamera.setX(camera.getX());
        renderingCamera.setY(camera.getY());
        renderingCamera.setBareZoomLevel(camera.getBareZoomLevel());

        menuRenderer.setPosition(0, camera.getHeight() - 200);
        menuRenderer.setDimensions(camera.getWidth(), 200);
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

    private boolean boundsCheck(int x, int y, WorldLayer layer){
        return x < layer.getWidth() && x >= 0 && y < layer.getHeight() && y >= 0;
    }

    public InGameMenuRenderer getMenuRenderer(){
        return menuRenderer;
    }
}
