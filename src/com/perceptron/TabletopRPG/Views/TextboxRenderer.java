package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.MenuTextbox;

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
 * Date: 1/7/13
 */
public class TextboxRenderer extends Renderer {
    private MenuTextbox textbox;

    public TextboxRenderer(MenuTextbox textbox){
        this.textbox = textbox;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        g2d.fillRect((int)textbox.getDrawingLocation().x - 5, (int)textbox.getDrawingLocation().y - textbox.getFont().getSize(), 405, (textbox.getFont().getSize() + 5));
        g2d.setColor(Color.white);
        g2d.drawString(textbox.getText(), (int)textbox.getDrawingLocation().x, (int)textbox.getDrawingLocation().y);

    }
}
