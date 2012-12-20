package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.Keyboard;
import com.perceptron.TabletopRPG.SinglePlayerState;
import com.perceptron.TabletopRPG.StateChange;

import java.awt.*;
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
public class SinglePlayerController implements Controller {
    private SinglePlayerState singlePlayerState;
    private double controlDelayCounter = 0;
    private Random random;

    public SinglePlayerController(SinglePlayerState singlePlayerState){
        this.singlePlayerState = singlePlayerState;
        random = new Random();
    }

    @Override
    public StateChange update(double dT) {
        controlDelayCounter += dT;
        if(controlDelayCounter > 0.08){
            if(Keyboard.UP){
                singlePlayerState.getEntities().add(new Point2D.Float(random.nextInt(1366), random.nextInt(768)));
            }
            if(Keyboard.DOWN){
                if(singlePlayerState.getEntities().size() > 0){
                    singlePlayerState.getEntities().remove(singlePlayerState.getEntities().size() - 1);
                }
            }
            controlDelayCounter = 0;
        }
        return StateChange.linger;
    }
}
