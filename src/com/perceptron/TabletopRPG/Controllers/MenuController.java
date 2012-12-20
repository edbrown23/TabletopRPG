package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.*;

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
public class MenuController implements Controller {
    protected int selectedIndex = 0;
    protected double menuChangeElapsedTime = 0;
    protected MainMenuState menuState;

    public MenuController(MainMenuState menuState){
        this.menuState = menuState;
    }

    @Override
    public StateChange update(double dT) {
        menuChangeElapsedTime += dT;
        if(menuChangeElapsedTime > 0.08){
            if(Keyboard.UP){
                menuState.getCurrentMenuItems().get(selectedIndex).setSelected(false);
                selectedIndex--;
                if(selectedIndex < 0){
                    selectedIndex = menuState.getCurrentMenuItems().size() - 1;
                }
                menuState.getCurrentMenuItems().get(selectedIndex).setSelected(true);
            }
            if(Keyboard.DOWN){
                menuState.getCurrentMenuItems().get(selectedIndex).setSelected(false);
                selectedIndex++;
                if(selectedIndex >= menuState.getCurrentMenuItems().size()){
                    selectedIndex = 0;
                }
                menuState.getCurrentMenuItems().get(selectedIndex).setSelected(true);
            }
            if(Keyboard.ENTER){
                menuState.getCurrentMenuItems().get(selectedIndex).setSelected(false);
                ArrayList<MenuItem> newMenu = menuState.getMenuConnections().get(menuState.getCurrentMenuItems().get(selectedIndex).getSubMenuName());
                if(newMenu != null){
                    menuState.setCurrentMenuItems(newMenu);
                }else{
                    GameState nextState = menuState.getCurrentMenuItems().get(selectedIndex).getExternalState();
                    if(nextState != null){
                        return new StateChange(nextState);
                    }
                }
                selectedIndex = 0;
                menuState.getCurrentMenuItems().get(selectedIndex).setSelected(true);
            }
            menuChangeElapsedTime = 0;
        }

        return StateChange.linger;
    }
}
