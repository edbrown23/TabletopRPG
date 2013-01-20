package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.Models.PointLight;
import com.perceptron.TabletopRPG.Models.WorldLayer;
import com.perceptron.TabletopRPG.SpriteManager;
import com.perceptron.TabletopRPG.Vector2;

import java.awt.*;
import java.awt.geom.Point2D;
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
        lightGraphics.setComposite(AlphaComposite.Clear);
        lightGraphics.fillOval(calcLightX(light), calcLightY(light), calcRadius(light), calcRadius(light));
        lightGraphics.setComposite(AlphaComposite.SrcOver);
        lightGraphics.drawImage(SpriteManager.whiteLight.getCurrentSprite(), calcLightX(light), calcLightY(light), calcRadius(light), calcRadius(light), null);
        // Black out all squares which block light within the radius of this light
        int sx = (int)(light.getX() - light.getRadius() + 0.5);
        int sy = (int)(light.getY() - light.getRadius() + 0.5);
        int diam = (int)(light.getRadius() * 2 + 0.5);
        int width = sx + diam;
        int height = sy + diam;
        for(sx = (int)(light.getX() - light.getRadius() + 0.5); sx < width; sx++){
            for(sy = (int)(light.getY() - light.getRadius() + 0.5); sy < height; sy++){
                if(boundsCheck(sx, sy) && layer.getCell(sx, sy).blocksLight()){
                    lightGraphics.setColor(ambientColor);
                    lightGraphics.fillRect((sx * camera.getZoomLevel() - camera.getX()), (sy * camera.getZoomLevel() - camera.getY()), camera.getZoomLevel(), camera.getZoomLevel());
                    calculateShadowPolygon(sx, sy, light);
                }
            }
        }

    }

    private boolean boundsCheck(int x, int y){
        return x < layer.getWidth() && x >= 0 && y < layer.getHeight() && y >= 0;
    }

    private void calculateShadowPolygon(int x, int y, PointLight light){
        Vector2 tlCorner = new Vector2(x, y);
        Vector2 trCorner = new Vector2(x + 1, y);
        Vector2 blCorner = new Vector2(x, y + 1);
        Vector2 brCorner = new Vector2(x + 1, y + 1);
        ArrayList<Integer> xCoords = new ArrayList<Integer>();
        ArrayList<Integer> yCoords = new ArrayList<Integer>();
        // Process the top left corner
        if(Point2D.distance(tlCorner.getX(), tlCorner.getY(), light.getX(), light.getY()) < light.getRadius()){
            Vector2 diffVec = new Vector2(light.getX() - tlCorner.getX(), light.getY() - tlCorner.getY());
            diffVec.convertToUnitVector();
            diffVec.scale(light.getRadius());
            xCoords.add((int)(tlCorner.getX() + 0.5));
            yCoords.add((int)(tlCorner.getY() + 0.5));
            xCoords.add((int)(diffVec.getX() + light.getX() + 0.5));
            yCoords.add((int)(diffVec.getY() + light.getY() + 0.5));
        }
        if(Point2D.distance(trCorner.getX(), trCorner.getY(), light.getX(), light.getY()) < light.getRadius()){
            Vector2 diffVec = new Vector2(light.getX() - trCorner.getX(), light.getY() - trCorner.getY());
            diffVec.convertToUnitVector();
            diffVec.scale(light.getRadius());
            xCoords.add((int)(diffVec.getX() + light.getX() + 0.5));
            yCoords.add((int)(diffVec.getY() + light.getY() + 0.5));
        }
        if(Point2D.distance(blCorner.getX(), blCorner.getY(), light.getX(), light.getY()) < light.getRadius()){
            Vector2 diffVec = new Vector2(light.getX() - blCorner.getX(), light.getY() - blCorner.getY());
            diffVec.convertToUnitVector();
            diffVec.scale(light.getRadius());
            xCoords.add((int)(diffVec.getX() + light.getX() + 0.5));
            yCoords.add((int)(diffVec.getY() + light.getY() + 0.5));
        }
        if(Point2D.distance(brCorner.getX(), brCorner.getY(), light.getX(), light.getY()) < light.getRadius()){
            Vector2 diffVec = new Vector2(light.getX() - brCorner.getX(), light.getY() - brCorner.getY());
            diffVec.convertToUnitVector();
            diffVec.scale(light.getRadius());
            xCoords.add((int)(diffVec.getX() + light.getX() + 0.5));
            yCoords.add((int)(diffVec.getY() + light.getY() + 0.5));
        }
        xCoords.add((int)(trCorner.getX() + 0.5));
        yCoords.add((int)(trCorner.getY() + 0.5));
        xCoords.add((int)(blCorner.getX() + 0.5));
        yCoords.add((int)(blCorner.getY() + 0.5));
        xCoords.add((int)(brCorner.getX() + 0.5));
        yCoords.add((int)(brCorner.getY() + 0.5));
        int xC[] = new int[xCoords.size()];
        int yC[] = new int[yCoords.size()];
        for(int i = 0; i < xCoords.size(); i++){
            if(xCoords.get(i) == 0 || yCoords.get(i) == 0){
                System.out.println(" ");
            }
            xC[i] = (xCoords.get(i) * camera.getZoomLevel()) - camera.getX();
            yC[i] = (yCoords.get(i) * camera.getZoomLevel()) - camera.getY();
        }
        Polygon shadow = new Polygon(xC, yC, xCoords.size());
        lightGraphics.setComposite(AlphaComposite.Clear);
        lightGraphics.fill(shadow);
        lightGraphics.setComposite(AlphaComposite.SrcOver);
        lightGraphics.setColor(ambientColor);
        lightGraphics.fill(shadow);
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
