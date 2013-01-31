package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.*;
import com.perceptron.TabletopRPG.Models.MenuItem;
import com.perceptron.TabletopRPG.Models.MenuState;

import javax.naming.directory.NoSuchAttributeException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
public class MenuController extends Controller {
    protected int selectedIndex = 0;
    protected MenuState currentMenu;

    public StateChange processInput(){
        MouseState mouseState = Mouse.dequeueState();
        StateChange change = this.processKeyboard();
        if(change != StateChange.linger){
            return change;
        }
        change = this.processMouse(mouseState);
        if(change != StateChange.linger){
            return change;
        }
        return StateChange.linger;
    }

    @Override
    public StateChange processKeyboard() {
        if(Keyboard.checkKey(KeyEvent.VK_UP)){
            currentMenu.getAllMenuItems().get(selectedIndex).setSelected(false);
            selectedIndex--;
            if(selectedIndex < 0){
                selectedIndex = currentMenu.getAllMenuItems().size() - 1;
            }
            currentMenu.getAllMenuItems().get(selectedIndex).setSelected(true);
            Keyboard.clearKey(KeyEvent.VK_UP);
        }
        if(Keyboard.checkKey(KeyEvent.VK_DOWN)){
            currentMenu.getAllMenuItems().get(selectedIndex).setSelected(false);
            selectedIndex++;
            if(selectedIndex >= currentMenu.getAllMenuItems().size()){
                selectedIndex = 0;
            }
            currentMenu.getAllMenuItems().get(selectedIndex).setSelected(true);
            Keyboard.clearKey(KeyEvent.VK_DOWN);
        }
        return StateChange.linger;
    }

    @Override
    public StateChange processMouse(MouseState nextState) {
        return null;
    }


    @Override
    public StateChange update(double dT) {
        return StateChange.linger;
    }
}
