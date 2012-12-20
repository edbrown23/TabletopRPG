package com.perceptron.TabletopRPG;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

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
public class MainMenuState implements GameState {
    private ArrayList<MenuItem> currentMenuItems;
    private ArrayList<MenuItem> mainMenuItems;
    private ArrayList<MenuItem> optionsMenuItems;
    private ArrayList<MenuItem> singlePlayerItems;
    private ArrayList<MenuItem> multiPlayerItems;
    private ArrayList<MenuItem> levelCreationItems;
    private HashMap<String, ArrayList<MenuItem>> menuConnections;

    public MainMenuState(){
        initializeMenuItems();

        setupMenuConnections();

        currentMenuItems = mainMenuItems;
    }

    private void initializeMenuItems(){
        mainMenuItems = new ArrayList<MenuItem>();
        mainMenuItems.add(new MenuItem(new Point2D.Float(400, 200), "Single Player"));
        mainMenuItems.add(new MenuItem(new Point2D.Float(400, 260), "Multiplayer"));
        mainMenuItems.add(new MenuItem(new Point2D.Float(400, 320), "Level Creation"));
        mainMenuItems.add(new MenuItem(new Point2D.Float(400, 380), "Options"));
        mainMenuItems.get(0).setSelected(true);

        singlePlayerItems = new ArrayList<MenuItem>();
        singlePlayerItems.add(new MenuItem(new Point2D.Float(400, 200), "Select Level"));
        singlePlayerItems.add(new MenuItem(new Point2D.Float(400, 260), "Load Level"));
        singlePlayerItems.add(new MenuItem(new Point2D.Float(400, 320), "Start Level", GameStateManager.singlePlayerState));

        multiPlayerItems = new ArrayList<MenuItem>();
        multiPlayerItems.add(new MenuItem(new Point2D.Float(400, 200), "Nothing"));

        levelCreationItems = new ArrayList<MenuItem>();
        levelCreationItems.add(new MenuItem(new Point2D.Float(400, 200), "Nothing"));

        optionsMenuItems = new ArrayList<MenuItem>();
        optionsMenuItems.add(new MenuItem(new Point2D.Float(400, 200), "Nothing"));
    }

    private void setupMenuConnections(){
        menuConnections = new HashMap<String, ArrayList<MenuItem>>();
        menuConnections.put(mainMenuItems.get(0).getSubMenuName(), singlePlayerItems);
        menuConnections.put(mainMenuItems.get(1).getSubMenuName(), multiPlayerItems);
        menuConnections.put(mainMenuItems.get(2).getSubMenuName(), levelCreationItems);
        menuConnections.put(mainMenuItems.get(3).getSubMenuName(), optionsMenuItems);
    }

    public ArrayList<MenuItem> getCurrentMenuItems() {
        return currentMenuItems;
    }

    public ArrayList<MenuItem> getMainMenuItems() {
        return mainMenuItems;
    }

    public ArrayList<MenuItem> getOptionsMenuItems() {
        return optionsMenuItems;
    }

    public ArrayList<MenuItem> getSinglePlayerItems() {
        return singlePlayerItems;
    }

    public ArrayList<MenuItem> getMultiPlayerItems() {
        return multiPlayerItems;
    }

    public ArrayList<MenuItem> getLevelCreationItems() {
        return levelCreationItems;
    }

    public HashMap<String, ArrayList<MenuItem>> getMenuConnections() {
        return menuConnections;
    }

    public void setCurrentMenuItems(ArrayList<MenuItem> currentMenuItems) {
        this.currentMenuItems = currentMenuItems;
    }
}
