package com.perceptron.TabletopRPG;

import java.awt.*;
import java.awt.geom.Point2D;
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
 * Date: 1/19/13
 */
public class LightSprite extends Sprite {
    public LightSprite(Color color, int singleFrameWidth, int singleFrameHeight, int numSprites) {
        super(singleFrameWidth, singleFrameHeight, numSprites);
        sprites[0] = new BufferedImage(singleFrameWidth, singleFrameHeight, BufferedImage.TYPE_INT_ARGB);
        initializeLightSprite(color);
    }

    private void initializeLightSprite(Color color){
        int centerX = singleFrameWidth / 2;
        int centerY = singleFrameHeight / 2;
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        for(int y = 0; y < singleFrameHeight; y++){
            for(int x = 0; x < singleFrameWidth; x++){
                double distance = Point2D.distance(x, y, centerX, centerY);
                if(distance < singleFrameWidth / 2){
                    double distanceRatio = 1 - distance / (singleFrameWidth / 2);
                    int na = (int)(150 * (distanceRatio) + 0.5);
                    int nr = (int)(r * distanceRatio + 0.5);
                    int ng = (int)(g * distanceRatio + 0.5);
                    int nb = (int)(b * distanceRatio + 0.5);
                    int rgb = (na << 24) | (nr << 16) | (ng << 8) | (nb);
                    sprites[0].setRGB(x, y, rgb);
                }else{
                    sprites[0].setRGB(x, y, 0x00000000);
                }
            }
        }
    }
}
