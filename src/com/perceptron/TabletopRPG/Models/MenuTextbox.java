package com.perceptron.TabletopRPG.Models;

import com.perceptron.TabletopRPG.GameState;
import com.perceptron.TabletopRPG.Models.MenuItem;

import java.awt.geom.Point2D;

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
 * Date: 1/7/13
 */
public class MenuTextbox extends MenuItem {

    private int cursorPosition;

    public MenuTextbox(Point2D.Float drawingLocation, String text) {
        super(drawingLocation, text);
        cursorPosition = 0;
    }

    public MenuTextbox(Point2D.Float drawingLocation, String text, GameState externalState) {
        super(drawingLocation, text, externalState);
        cursorPosition = 0;
    }

    public MenuTextbox(Point2D.Float drawingLocation, String text, String subMenuName, GameState externalState) {
        super(drawingLocation, text, subMenuName, externalState);
        cursorPosition = 0;
    }
}
