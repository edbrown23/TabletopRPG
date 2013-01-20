package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.Models.PointLight;
import com.perceptron.TabletopRPG.Models.WorldLayer;
import com.perceptron.TabletopRPG.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
 * Date: 1/17/13
 */
public class LightingRenderer implements Renderer {
    private WorldLayer layer;
    private Camera camera;
    private BufferedImage lightMask;
    private Graphics2D lightGraphics;
    private Color ambientColor;

    public LightingRenderer(WorldLayer layer, Camera camera){
        this.layer = layer;
        this.camera = camera;
        lightMask = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_INT_ARGB);
        lightGraphics = lightMask.createGraphics();
        ambientColor = new Color(0, 0, 0, 150);
    }

    @Override
    public void render(Graphics2D g2d) {
        resetImage();
        ArrayList<PointLight> lights = layer.getLights();
        for(PointLight light : lights){
            processLight(light);
        }
        g2d.drawImage(lightMask, 0, 0, camera.getWidth(), camera.getHeight(), null);
    }

    private void resetImage(){
        lightGraphics.setComposite(AlphaComposite.Clear);
        lightGraphics.fillRect(0, 0, lightMask.getWidth(), lightMask.getHeight());
        lightGraphics.setComposite(AlphaComposite.SrcOver);
        lightGraphics.setColor(ambientColor);
        lightGraphics.fillRect(0, 0, lightMask.getWidth(), lightMask.getHeight());
    }

    private void processLight(PointLight light){
        // Draw the light on the light mask
        lightGraphics.drawImage(SpriteManager.whiteLight.getCurrentSprite(), calcLightX(light), calcLightY(light), calcRadius(light), calcRadius(light), null);
        // Black out all squares which block light within the radius of this light
        int sx = (int)(light.getX() - light.getRadius() + 0.5);
        int sy = (int)(light.getY() - light.getRadius() + 0.5);
        int diam = (int)(light.getRadius() * 2 + 0.5);
        int width = sx + diam;
        int height = sy + diam;
        for(sx = (int)(light.getX() - light.getRadius() + 0.5); sx < width; sx++){
            for(sy = (int)(light.getY() - light.getRadius() + 0.5); sy < height; sy++){
                if(layer.getCell(sx, sy).blocksLight()){
                    lightGraphics.setColor(ambientColor);
                    lightGraphics.fillRect((sx * camera.getZoomLevel() - camera.getX()), (sy * camera.getZoomLevel() - camera.getY()), camera.getZoomLevel(), camera.getZoomLevel());
                    lightGraphics.setColor(Color.red);
                    lightGraphics.fillRect((sx * camera.getZoomLevel() - camera.getX()), (sy * camera.getZoomLevel() - camera.getY()), 5, 5);
                    lightGraphics.fillRect((sx * camera.getZoomLevel() - camera.getX()), ((sy + 1) * camera.getZoomLevel() - camera.getY()), 5, 5);
                    lightGraphics.fillRect(((sx + 1) * camera.getZoomLevel() - camera.getX()), (sy * camera.getZoomLevel() - camera.getY()), 5, 5);
                    lightGraphics.fillRect(((sx + 1) * camera.getZoomLevel() - camera.getX()), ((sy + 1) * camera.getZoomLevel() - camera.getY()), 5, 5);
                }
            }
        }

    }

    private int calcLightX(PointLight light){
        return (int)(light.getX() - light.getRadius() + 0.5) * camera.getZoomLevel() - camera.getX();
    }

    private int calcLightY(PointLight light){
        return (int)(light.getY() - light.getRadius() + 0.5) * camera.getZoomLevel() - camera.getY();
    }

    private int calcRadius(PointLight light){
        return (int)(light.getRadius() * camera.getZoomLevel() * 2);
    }

    private int convertLayerIndexToX(int ix){
        return (ix - camera.getZoomAdjustedX()) * camera.getZoomLevel();
    }

    private int convertLayerIndexToY(int iy){
        return (iy - camera.getZoomAdjustedY()) * camera.getZoomLevel();
    }

    private int convertXToLayerIndex(int x){
        return (x + camera.getX()) / camera.getZoomLevel();
    }

    private int convertYToLayerIndex(int y){
        return (y + camera.getY()) / camera.getZoomLevel();
    }

    public void setLayer(WorldLayer layer) {
        this.layer = layer;
    }
}
