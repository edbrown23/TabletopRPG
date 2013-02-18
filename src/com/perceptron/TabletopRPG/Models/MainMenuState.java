package com.perceptron.TabletopRPG.Models;

import com.perceptron.TabletopRPG.Controllers.Controller;
import com.perceptron.TabletopRPG.Controllers.TextboxController;
import com.perceptron.TabletopRPG.GameState;
import com.perceptron.TabletopRPG.Views.ButtonRenderer;
import com.perceptron.TabletopRPG.Views.MenuRenderer;
import com.perceptron.TabletopRPG.Views.TextboxRenderer;

import java.awt.geom.Point2D;
import java.util.HashMap;

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
 * Date: 2/17/13
 */
public class MainMenuState extends MenuState {
    private MenuState mainMenu;
    private MenuState singlePlayerMenu;
    private MenuState multiplayerMenu;
    private MenuState levelCreationMenu;
    private MenuState optionsMenu;
    private MenuState currentMenu;

    public MainMenuState(){
        this("Main Menu");
        initializeMenusAndRenderers();
    }

    private MainMenuState(String menuName) {
        super(menuName);
    }

    private void initializeMenusAndRenderers(){
        initializeMainMenuItemsAndRenderers();
        initializeSinglePlayerItemsAndRenderers();
        initializeMultiplayerItemsAndRenderers();
        initializeLevelCreationItemsAndRenderers();
        initializeOptionsItemsAndRenderers();
    }

    private void initializeMainMenuItemsAndRenderers(){
        mainMenu = new MenuState("Main Menu");

        MenuButton singlePlayerButton = new MenuButton(new Point2D.Float(400, 200), "Single Player");
        mainMenu.addButton(singlePlayerButton);

        MenuButton multiplayerButton = new MenuButton(new Point2D.Float(400, 260), "Multiplayer");
        mainMenu.addButton(multiplayerButton);

        MenuButton levelCreationButton = new MenuButton(new Point2D.Float(400, 320), "Level Creation");
        mainMenu.addButton(levelCreationButton);

        MenuButton optionsButton = new MenuButton(new Point2D.Float(400, 380), "Options");
        mainMenu.addButton(optionsButton);

        mainMenu.getAllMenuItems().get(0).setSelected(true);
    }

    private void initializeSinglePlayerItemsAndRenderers(){
        singlePlayerMenu = new MenuState("Single Player");

        MenuButton startLevelButton = new MenuButton(new Point2D.Float(400, 200), "Start Level");
        singlePlayerMenu.addButton(startLevelButton);

        MenuTextbox savePathTextbox = new MenuTextbox(new Point2D.Float(400, 260));
        savePathTextbox.appendString("");
        singlePlayerMenu.addTextBox(savePathTextbox);

    }

    private void initializeMultiplayerItemsAndRenderers(){
        multiplayerMenu = new MenuState("Multiplayer");
    }

    private void initializeLevelCreationItemsAndRenderers(){
        levelCreationMenu = new MenuState("Level Creation");
    }

    private void initializeOptionsItemsAndRenderers(){
        optionsMenu = new MenuState("Options");
    }

    public MenuState getMainMenu() {
        return mainMenu;
    }

    public MenuState getSinglePlayerMenu() {
        return singlePlayerMenu;
    }

    public MenuState getMultiplayerMenu() {
        return multiplayerMenu;
    }

    public MenuState getLevelCreationMenu() {
        return levelCreationMenu;
    }

    public MenuState getOptionsMenu() {
        return optionsMenu;
    }

    public MenuState getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(MenuState currentMenu) {
        this.currentMenu = currentMenu;
    }
}
