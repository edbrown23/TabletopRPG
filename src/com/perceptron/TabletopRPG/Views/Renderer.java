package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.Camera;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 12/18/12
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
public abstract class Renderer {
    protected Camera camera;
    protected Camera renderingCamera;

    public abstract void render(Graphics2D g2d);

    protected void copyCamera(){
        renderingCamera.setWidth(camera.getWidth());
        renderingCamera.setHeight(camera.getHeight());
        renderingCamera.setX(camera.getX());
        renderingCamera.setY(camera.getY());
        renderingCamera.setBareZoomLevel(camera.getBareZoomLevel());
    }

    public void setCamera(Camera camera){
        this.camera = camera;
        copyCamera();
    }
}
