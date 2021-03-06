package com.perceptron.TabletopRPG;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 12/17/12
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
public class Mouse {
    public static int X = 0;
    public static int Y = 0;
    public static int lastX = 0;
    public static int lastY = 0;
    public static int dX = 0;
    public static int dY = 0;
    private static Queue<MouseState> eventQueue;

    static{
        eventQueue = new LinkedList<MouseState>();
    }

    public static void setXY(int x, int y){
        lastX = X;
        lastY = Y;
        X = x;
        Y = y;
        dX = X - lastX;
        dY = Y - lastY;
    }

    public static void enqueueState(MouseState state){
        eventQueue.offer(state);
    }

    public static MouseState dequeueState(){
        if(!eventQueue.isEmpty()){
            return eventQueue.remove();
        }
        return null;
    }
}
