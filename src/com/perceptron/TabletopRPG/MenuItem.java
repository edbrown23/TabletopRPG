package com.perceptron.TabletopRPG;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;

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
public class MenuItem {
    private Point2D.Float drawingLocation;
    private String text;
    private boolean selected;
    private Font font;
    private String subMenuName;
    private GameState externalState;

    public MenuItem(Point2D.Float drawingLocation, String text){
        this(drawingLocation, text, text, null);
    }

    public MenuItem(Point2D.Float drawingLocation, String text, GameState externalState){
        this(drawingLocation, text, text, externalState);
    }

    public MenuItem(Point2D.Float drawingLocation, String text, String subMenuName, GameState externalState){
        this.drawingLocation = drawingLocation;
        this.text = text;
        selected = false;
        font = new Font("SansSerif", Font.BOLD, 50);
        this.subMenuName = subMenuName;
        this.externalState = externalState;
    }

    public Point2D.Float getDrawingLocation(){
        return drawingLocation;
    }

    public String getText(){
        return text;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public Font getFont(){
        return font;
    }

    public String getSubMenuName(){
        return subMenuName;
    }

    public GameState getExternalState(){
        return externalState;
    }
}
