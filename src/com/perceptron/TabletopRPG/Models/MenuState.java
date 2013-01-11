package com.perceptron.TabletopRPG.Models;

import java.util.ArrayList;

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
public class MenuState {
    private ArrayList<MenuItem> allMenuItems;
    private ArrayList<MenuButton> buttons;
    private ArrayList<MenuTextbox> textboxes;
    private String menuName;

    public MenuState(String menuName){
        this.menuName = menuName;
        allMenuItems = new ArrayList<MenuItem>();
        buttons = new ArrayList<MenuButton>();
        textboxes = new ArrayList<MenuTextbox>();
    }

    public ArrayList<MenuItem> getAllMenuItems() {
        return allMenuItems;
    }

    public void setAllMenuItems(ArrayList<MenuItem> allMenuItems) {
        this.allMenuItems = allMenuItems;
    }

    public ArrayList<MenuButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<MenuButton> buttons) {
        this.buttons = buttons;
    }

    public ArrayList<MenuTextbox> getTextboxes() {
        return textboxes;
    }

    public void setTextboxes(ArrayList<MenuTextbox> textboxes) {
        this.textboxes = textboxes;
    }

    public void addButton(MenuButton button){
        buttons.add(button);
        allMenuItems.add(button);
    }

    public void addTextBox(MenuTextbox textbox){
        textboxes.add(textbox);
        allMenuItems.add(textbox);
    }

    public String getMenuName() {
        return menuName;
    }
}
