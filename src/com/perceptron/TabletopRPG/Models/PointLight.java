package com.perceptron.TabletopRPG.Models;

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
 * Date: 1/17/13
 */
public class PointLight {
    private Color lightColor;
    private float x;
    private float y;
    private float radius;
    private float radiusSquared;

    public PointLight(Color lightColor, float x, float y, float radius) {
        this.lightColor = lightColor;
        this.x = x;
        this.y = y;
        this.radius = radius;
        radiusSquared = radius * radius;
    }

    public boolean pixelInRange(int pX, int pY){
        float xSquared = (pX - x) * (pX - x);
        float ySquared = (pY - y) * (pY - y);
        return (xSquared + ySquared) < radiusSquared;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public float getRadiusSquared() {
        return radiusSquared;
    }

    public void addDX(float dx){
        x += dx;
    }

    public void addDY(float dy){
        y += dy;
    }

    public void adjustRadius(float dr){
        radius += dr;
    }
}
