package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.Keyboard;
import com.perceptron.TabletopRPG.SinglePlayerState;
import com.perceptron.TabletopRPG.StateChange;
import com.perceptron.TabletopRPG.Views.SinglePlayerRenderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Random;

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
public class SinglePlayerController extends Controller {
    private SinglePlayerState singlePlayerState;
    private SinglePlayerRenderer singlePlayerRenderer;

    public SinglePlayerController(){
        singlePlayerState = new SinglePlayerState();
        this.state = singlePlayerState;
        singlePlayerRenderer = new SinglePlayerRenderer(singlePlayerState);
        this.renderer = singlePlayerRenderer;
    }

    @Override
    public StateChange update(double dT) {
        if(Keyboard.checkKey(KeyEvent.VK_UP)){
            singlePlayerRenderer.getCamera().setY(singlePlayerRenderer.getCamera().getY() - 5);
            //Keyboard.clearKey(KeyEvent.VK_UP);
        }
        if(Keyboard.checkKey(KeyEvent.VK_DOWN)){
            singlePlayerRenderer.getCamera().setY(singlePlayerRenderer.getCamera().getY() + 5);
            //Keyboard.clearKey(KeyEvent.VK_DOWN);
        }
        if(Keyboard.checkKey(KeyEvent.VK_LEFT)){
            singlePlayerRenderer.getCamera().setX(singlePlayerRenderer.getCamera().getX() - 5);
            //Keyboard.clearKey(KeyEvent.VK_LEFT);
        }
        if(Keyboard.checkKey(KeyEvent.VK_RIGHT)){
            singlePlayerRenderer.getCamera().setX(singlePlayerRenderer.getCamera().getX() + 5);
            //Keyboard.clearKey(KeyEvent.VK_RIGHT);
        }
        if(Keyboard.checkKey(KeyEvent.VK_PAGE_UP)){
            singlePlayerRenderer.getCamera().increaseZoomLevel();
            Keyboard.clearKey(KeyEvent.VK_PAGE_UP);
        }
        if(Keyboard.checkKey(KeyEvent.VK_PAGE_DOWN)){
            singlePlayerRenderer.getCamera().decreaseZoomLevel();
            Keyboard.clearKey(KeyEvent.VK_PAGE_DOWN);
        }
        return StateChange.linger;
    }
}
