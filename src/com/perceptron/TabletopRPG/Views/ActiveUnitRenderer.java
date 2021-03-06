package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.ActiveUnit;
import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.SpriteManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 2/1/13
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
public class ActiveUnitRenderer extends Renderer {
    private ActiveUnit unit;

    public ActiveUnitRenderer(ActiveUnit unit){
        this.unit = unit;
        this.renderingCamera = new Camera();
    }

    @Override
    public void render(Graphics2D g2d) {
        switch(unit.getTypeID()){
            case 0:
                g2d.drawImage(SpriteManager.wizardSprite.getSprite(0), renderingCamera.applyCameraX(unit.getX()), renderingCamera.applyCameraY(unit.getY()), renderingCamera.getZoomLevel(), renderingCamera.getZoomLevel(), null);
                break;
        }
    }
}
