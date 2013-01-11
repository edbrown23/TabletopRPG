package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.Keyboard;
import com.perceptron.TabletopRPG.Models.MenuTextbox;
import com.perceptron.TabletopRPG.StateChange;

import java.awt.event.KeyEvent;

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
 * Date: 1/8/13
 */
public class TextboxController extends Controller {
    private MenuTextbox textbox;

    public TextboxController(MenuTextbox textbox){
        this.textbox = textbox;
    }

    @Override
    public StateChange update(double dT) {
        if(Keyboard.checkKey(KeyEvent.VK_BACK_SPACE)){
            handleBackspace();
            Keyboard.clearKey(KeyEvent.VK_BACK_SPACE);
        }
        testAndHandleChar();
        return null;
    }

    private void testAndHandleChar(){
        String text = Keyboard.convertCodeToChar(Keyboard.dequeueKey());
        textbox.appendString(text);
    }

    private void handleBackspace(){
        textbox.removeLastChar();
    }
}
