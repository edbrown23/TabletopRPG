package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.Models.Cell;
import com.perceptron.TabletopRPG.Models.PointLight;
import com.perceptron.TabletopRPG.Models.WorldLayer;
import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocationPerORB;
import com.sun.deploy.panel.ITreeNode;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.IntType;

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
    private WorldLayer currentLayer;
    private Camera camera;
    private BufferedImage lightMask;
    private Graphics2D lightGraphics;

    public LightingRenderer(WorldLayer layer, Camera camera){
        currentLayer = layer;
        this.camera = camera;
        lightMask = new BufferedImage(camera.getWidth(), camera.getHeight(), BufferedImage.TYPE_INT_ARGB);
        lightGraphics = lightMask.createGraphics();
    }

    @Override
    public void render(Graphics2D g2d) {
        resetImage();
        ArrayList<PointLight> lights = currentLayer.getLights();
        for(int y = 0; y < camera.getHeight(); y++){
            for(int x = 0; x < camera.getWidth(); x++){
                for(PointLight light : lights){
                    if(Point2D.distanceSq(x, y, convertLayerIndexToX(light.getX()), convertLayerIndexToY(light.getY())) < light.getRadiusSquared()){
                        processLight(x, y, light);
                    }
                }
            }
        }
        g2d.drawImage(lightMask, 0, 0, camera.getWidth(), camera.getHeight(), null);
    }

    private void resetImage(){
        for(int y = 0; y < lightMask.getHeight(); y++){
            for(int x = 0; x < lightMask.getWidth(); x++){
                lightMask.setRGB(x, y, 0xC8000000);
            }
        }
    }

    private void processLight(int x, int y, PointLight light){
        // Determine if the given light has any effect on this pixel
        int ix = convertXToLayerIndex(x);
        int iy = convertYToLayerIndex(y);
//        int slope;
//        if(ix == light.getX()){
//            for(int nY = iy; nY < light.getY(); nY++){
//                Cell cell = currentLayer.getCell(ix, nY);
//                if(cell.blocksLight()){
//                    return;
//                }
//            }
//        }else{
//            slope = (iy - light.getY()) / (ix - light.getX());
//            int count = 0;
//            for(int nX = ix; nX < light.getX(); nX++){
//                count++;
//                Cell cell = currentLayer.getCell(nX, iy + count * slope);
//                if(cell.blocksLight()){
//                    return;
//                }
//            }
//        }

        // If we get to here, the pixel is affected by the light
        int lx = (light.getX() - camera.getZoomAdjustedX()) * camera.getZoomLevel();
        int ly = (light.getY() - camera.getZoomAdjustedY()) * camera.getZoomLevel();
        double distance = Point2D.distance(x, y, lx, ly);
        double distanceProportion = 1 - distance / (light.getRadius() * camera.getZoomLevel());
        int argb = lightMask.getRGB(x, y);
        int a = (argb & 0xff000000) >> 24;
        int r = (argb & 0xff0000) >> 16;
        int g = (argb & 0xff00) >> 8;
        int b = (argb & 0xff);
        r = (r + (int)(light.getLightColor().getRed() * distanceProportion + 0.5)) / 2;
        g = (g + (int)(light.getLightColor().getGreen() * distanceProportion + 0.5)) / 2;
        b = (b + (int)(light.getLightColor().getBlue() * distanceProportion + 0.5)) / 2;
        argb = ((a << 24) | (r << 16) | (g << 8) | (b));
        lightMask.setRGB(x, y, argb);

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

    public void setCurrentLayer(WorldLayer currentLayer) {
        this.currentLayer = currentLayer;
    }
}
