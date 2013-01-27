package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.Models.PointLight;
import com.perceptron.TabletopRPG.Models.WorldLayer;
import com.perceptron.TabletopRPG.Sprite;
import com.perceptron.TabletopRPG.SpriteManager;
import com.perceptron.TabletopRPG.Vector2;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
    private AlphaComposite lightingComposite;
    private ArrayList<Polygon> shadowVolumes;
    private ArrayList<Vector2> litSquares;

    public LightingRenderer(WorldLayer layer, Camera camera){
        this.layer = layer;
        this.camera = camera;
        lightMask = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_INT_ARGB);
        lightMask = Sprite.toCompatibleImage(lightMask);
        lightGraphics = lightMask.createGraphics();
        ambientColor = new Color(0, 0, 0, 200);
        lightingComposite = AlphaComposite.getInstance(AlphaComposite.SRC, 0.5f);
        shadowVolumes = new ArrayList<Polygon>();
        litSquares = new ArrayList<Vector2>();
    }

    @Override
    public void render(Graphics2D g2d) {
        //g2d.setComposite(lightingComposite);
        resetImage();
        ArrayList<PointLight> lights = layer.getLights();
        int i = 0;
        for(PointLight light : lights){
            processLight(light);
        }
        // The shadow volumes for all lights have not been calculated
        for(PointLight light : lights){
            lightSquares(light);
        }
        renderShadowVolumes();
        reLightSquares();
        for(PointLight light : lights){
            Sprite lightSprite;
            if(i == 0){
                lightSprite = SpriteManager.whiteLight;
            }else{
                lightSprite = SpriteManager.redLight;
            }
            lightGraphics.setComposite(AlphaComposite.SrcOver);
            renderLight(light, lightSprite);
            i++;
        }
        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.drawImage(lightMask, 0, 0, camera.getWidth(), camera.getHeight(), null);
    }

    private void reLightSquares(){
        for(Vector2 square : litSquares){
            lightGraphics.setComposite(AlphaComposite.Clear);
            lightGraphics.fillRect((int)square.getX() * camera.getZoomLevel() - camera.getX(), (int)square.getY() * camera.getZoomLevel() - camera.getY(), camera.getZoomLevel(), camera.getZoomLevel());
        }
    }

    private void renderLight(PointLight light, Sprite sprite){
        lightGraphics.drawImage(sprite.getCurrentSprite(), calcLightX(light), calcLightY(light), calcDiameter(light), calcDiameter(light), null);
    }

    private void renderShadowVolumes(){
        lightGraphics.setComposite(AlphaComposite.Src);
        lightGraphics.setColor(ambientColor);
        for(Polygon shadow : shadowVolumes){
            lightGraphics.fillPolygon(shadow);
        }
    }

    private void lightSquares(PointLight light){
        int sx = (int)(light.getX() - light.getRadius() + 0.5);
        int sy = (int)(light.getY() - light.getRadius() + 0.5);
        int diam = (int)(light.getRadius() * 2 + 0.5);
        int width = sx + diam;
        int height = sy + diam;
        for(sx = (int)(light.getX() - light.getRadius() + 0.5); sx < width; sx++){
            for(sy = (int)(light.getY() - light.getRadius() + 0.5); sy < height; sy++){
                if(boundsCheck(sx, sy) && Point2D.distance(sx, sy, light.getX(), light.getY()) < (light.getRadius() - 2)){
                    lightGraphics.setComposite(AlphaComposite.Clear);
                    lightGraphics.fillRect(sx * camera.getZoomLevel() - camera.getX(), sy * camera.getZoomLevel() - camera.getY(), camera.getZoomLevel(), camera.getZoomLevel());
                }
            }
        }
    }

    private void resetImage(){
        lightGraphics.setComposite(AlphaComposite.Clear);
        lightGraphics.fillRect(0, 0, lightMask.getWidth(), lightMask.getHeight());
        lightGraphics.setComposite(AlphaComposite.SrcOver);
        lightGraphics.setColor(ambientColor);
        lightGraphics.fillRect(0, 0, lightMask.getWidth(), lightMask.getHeight());
        shadowVolumes.clear();
        litSquares.clear();
    }

    private void processLight(PointLight light){
        int sx = (int)(light.getX() - light.getRadius() + 0.5);
        int sy = (int)(light.getY() - light.getRadius() + 0.5);
        int diam = (int)(light.getRadius() * 2 + 0.5);
        int width = sx + diam;
        int height = sy + diam;
        for(sx = (int)(light.getX() - light.getRadius() + 0.5); sx < width; sx++){
            for(sy = (int)(light.getY() - light.getRadius() + 0.5); sy < height; sy++){
                if(boundsCheck(sx, sy) && layer.getCell(sx, sy).blocksLight() && Point2D.distance(sx, sy, light.getX(), light.getY()) < (light.getRadius())){
                    //if(!checkForBlockingNeighbors(sx, sy, light)){
                        shadowVolumes.add(calculateShadowPolygon(sx, sy, light));
                        litSquares.add(new Vector2(sx, sy));
                    //}
                }
            }
        }
    }

    /*
     * This method doesn't quite work yet, but the idea makes sense to me, so I'm keeping it around
     */
    private boolean checkForBlockingNeighbors(int x, int y, PointLight light){
        Vector2 lightDir = new Vector2(x - light.getX(), y - light.getY());
        //lightDir.convertToUnitVector();
        int nx = (int)(x + lightDir.getX() + 0.5f);
        int ny = (int)(y + lightDir.getY() + 0.5f);
        if(nx >= 0 && nx < layer.getWidth() && ny >= 0 && ny < layer.getHeight()){
            return layer.getCell(nx, ny).blocksLight();
        }
        return false;
    }


    private boolean boundsCheck(int x, int y){
        return x < layer.getWidth() && x >= 0 && y < layer.getHeight() && y >= 0;
    }

    private void drawNumber(int x, int y, int num){
        lightGraphics.setColor(Color.red);
        lightGraphics.drawString(Integer.toString(num), x, y);
    }

    private ArrayList<Vector2> findBlockingCorners(int x, int y, PointLight light){
        ArrayList<Vector2> output = new ArrayList<Vector2>();
        HashMap<Double, Vector2> cornerMap = new HashMap<Double, Vector2>();
        Vector2 tlCorner = new Vector2(x, y);
        Vector2 trCorner = new Vector2(x + 1, y);
        Vector2 blCorner = new Vector2(x, y + 1);
        Vector2 brCorner = new Vector2(x + 1, y + 1);

        double cornerDistances[] = new double[4];
        cornerDistances[0] = Point2D.distanceSq(tlCorner.getX(), tlCorner.getY(), light.getX(), light.getY());
        cornerMap.put(cornerDistances[0], tlCorner);
        cornerDistances[1] = Point2D.distanceSq(trCorner.getX(), trCorner.getY(), light.getX(), light.getY());
        cornerMap.put(cornerDistances[1], trCorner);
        cornerDistances[2] = Point2D.distanceSq(blCorner.getX(), blCorner.getY(), light.getX(), light.getY());
        cornerMap.put(cornerDistances[2], blCorner);
        cornerDistances[3] = Point2D.distanceSq(brCorner.getX(), brCorner.getY(), light.getX(), light.getY());
        cornerMap.put(cornerDistances[3], brCorner);
        // Picking the appropriate corners probably has something to do with the angle between the vectors
        Arrays.sort(cornerDistances);
        output.add(cornerMap.get(cornerDistances[1]));
        output.add(cornerMap.get(cornerDistances[2]));

        return output;
    }

    private Polygon calculateShadowPolygon(int x, int y, PointLight light){
        ArrayList<Integer> xCoords = new ArrayList<Integer>();
        ArrayList<Integer> yCoords = new ArrayList<Integer>();
        ArrayList<Vector2> corners = findBlockingCorners(x, y, light);
        xCoords.add((int)(corners.get(0).getX()));
        yCoords.add((int)(corners.get(0).getY()));
        for(Vector2 corner : corners){
            if(Point2D.distance(corner.getX(),  corner.getY(), light.getX(), light.getY()) < light.getRadius()){
                Vector2 diffVec = new Vector2(corner.getX() - light.getX(),  corner.getY() - light.getY());
                diffVec.convertToUnitVector();
                diffVec.scale(light.getRadius());
                xCoords.add((int)(diffVec.getX() + light.getX() + 0.5));
                yCoords.add((int)(diffVec.getY() + light.getY() + 0.5));
            }
        }
        xCoords.add((int)(corners.get(1).getX()));
        yCoords.add((int)(corners.get(1).getY()));
        int xC[] = new int[xCoords.size()];
        int yC[] = new int[yCoords.size()];
        for(int i = 0; i < xCoords.size(); i++){
            xC[i] = (xCoords.get(i) * camera.getZoomLevel()) - camera.getX();
            yC[i] = (yCoords.get(i) * camera.getZoomLevel()) - camera.getY();
        }
        return new Polygon(xC, yC, xCoords.size());
    }

    private int calcLightX(PointLight light){
        return (int)(light.getX() - light.getRadius() + 0.5) * camera.getZoomLevel() - camera.getX();
    }

    private int calcLightY(PointLight light){
        return (int)(light.getY() - light.getRadius() + 0.5) * camera.getZoomLevel() - camera.getY();
    }

    private int calcDiameter(PointLight light){
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

    public void setCamera(Camera camera){
        this.camera = camera;
    }
}
