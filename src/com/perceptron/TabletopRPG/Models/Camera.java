package com.perceptron.TabletopRPG.Models;

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
 * Date: 12/20/12
 */
public class Camera {
    private int x;
    private int y;
    private int width;
    private int height;
    private int zoomLevel;

    public Camera(){
        this(0, 0, 1366, 768);
    }

    public Camera(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        zoomLevel = 4;
    }

    public int getZoomAdjustedX(){
        int ox = Math.round(x / (zoomLevel * 16));
        if(ox < 0){
            ox = 0;
        }
        return ox;
    }

    public int getZoomAdjustedY(){
        int oy = Math.round(y / (zoomLevel * 16));
        if(oy < 0){
            oy = 0;
        }
        return oy;
    }

    public int getZoomAdjustedWidth(){
        return Math.round(width / (zoomLevel * 16));
    }

    public int getZoomAdjustedHeight(){
        return Math.round(height / (zoomLevel * 16));
    }

    public int getZoomLevel(){
        return zoomLevel << 4;
    }

    public void increaseZoomLevel(){
        zoomLevel++;
        if(zoomLevel >= 10){
            zoomLevel = 10;
        }
    }

    public void decreaseZoomLevel(){
        zoomLevel--;
        if(zoomLevel <= 1){
            zoomLevel = 1;
        }
    }

    public void moveX(int dX){
        x += dX;
    }

    public void moveY(int dY){
        y += dY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int applyCameraX(int x){
        return x * getZoomLevel() - this.x;
    }

    public int applyCameraY(int y){
        return y * getZoomLevel() - this.y;
    }

    public void setBareZoomLevel(int z){
        this.zoomLevel = z;
    }

    public int getBareZoomLevel(){
        return zoomLevel;
    }
}
