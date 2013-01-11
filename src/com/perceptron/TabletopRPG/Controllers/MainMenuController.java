package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.*;
import com.perceptron.TabletopRPG.Models.MenuButton;
import com.perceptron.TabletopRPG.Models.MenuItem;
import com.perceptron.TabletopRPG.Models.MenuState;
import com.perceptron.TabletopRPG.Models.MenuTextbox;
import com.perceptron.TabletopRPG.Views.ButtonRenderer;
import com.perceptron.TabletopRPG.Views.MenuRenderer;
import com.perceptron.TabletopRPG.Views.TextboxRenderer;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

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
public class MainMenuController extends MenuController {
    private MenuState mainMenu;
    private MenuRenderer mainMenuRenderer;
    private MenuState singlePlayerMenu;
    private MenuRenderer singlePlayerMenuRenderer;
    private MenuState multiplayerMenu;
    private MenuRenderer multiplayerMenuRenderer;
    private MenuState levelCreationMenu;
    private MenuRenderer levelCreationMenuRenderer;
    private MenuState optionsMenu;
    private MenuRenderer optionsMenuRenderer;
    private HashMap<MenuItem, Controller> itemControllerMap;
    private double menuChangeElapsedTime = 0;

    public MainMenuController() {
        itemControllerMap = new HashMap<MenuItem, Controller>();
        initializeMenusAndRenderers();
    }

    private void initializeMenusAndRenderers(){
        initializeMainMenuItemsAndRenderers();
        initializeSinglePlayerItemsAndRenderers();
        initializeMultiplayerItemsAndRenderers();
        initializeLevelCreationItemsAndRenderers();
        initializeOptionsItemsAndRenderers();

        this.currentMenu = mainMenu;
        this.renderer = mainMenuRenderer;
    }

    private void initializeMainMenuItemsAndRenderers(){
        mainMenu = new MenuState("Main Menu");
        mainMenuRenderer = new MenuRenderer(mainMenu);

        MenuButton singlePlayerButton = new MenuButton(new Point2D.Float(400, 200), "Single Player");
        mainMenuRenderer.addRenderer(new ButtonRenderer(singlePlayerButton));
        mainMenu.addButton(singlePlayerButton);

        MenuButton multiplayerButton = new MenuButton(new Point2D.Float(400, 260), "Multiplayer");
        mainMenuRenderer.addRenderer(new ButtonRenderer(multiplayerButton));
        mainMenu.addButton(multiplayerButton);

        MenuButton levelCreationButton = new MenuButton(new Point2D.Float(400, 320), "Level Creation");
        mainMenuRenderer.addRenderer(new ButtonRenderer(levelCreationButton));
        mainMenu.addButton(levelCreationButton);

        MenuButton optionsButton = new MenuButton(new Point2D.Float(400, 380), "Options");
        mainMenuRenderer.addRenderer(new ButtonRenderer(optionsButton));
        mainMenu.addButton(optionsButton);

        mainMenu.getAllMenuItems().get(0).setSelected(true);
    }

    private void initializeSinglePlayerItemsAndRenderers(){
        singlePlayerMenu = new MenuState("Single Player");
        singlePlayerMenuRenderer = new MenuRenderer(singlePlayerMenu);

        MenuButton selectLevelButton = new MenuButton(new Point2D.Float(400, 200), "Select Level");
        singlePlayerMenuRenderer.addRenderer(new ButtonRenderer(selectLevelButton));
        singlePlayerMenu.addButton(selectLevelButton);

        MenuButton loadLevelButton = new MenuButton(new Point2D.Float(400, 260), "Load Level");
        singlePlayerMenuRenderer.addRenderer(new ButtonRenderer(loadLevelButton));
        singlePlayerMenu.addButton(loadLevelButton);

        MenuButton startLevelButton = new MenuButton(new Point2D.Float(400, 320), "Start Level");
        singlePlayerMenuRenderer.addRenderer(new ButtonRenderer(startLevelButton));
        singlePlayerMenu.addButton(startLevelButton);

        MenuTextbox levelPathTextbox = new MenuTextbox(new Point2D.Float(400, 380));
        levelPathTextbox.appendString("Text");
        singlePlayerMenuRenderer.addRenderer(new TextboxRenderer(levelPathTextbox));
        singlePlayerMenu.addTextBox(levelPathTextbox);
        itemControllerMap.put(levelPathTextbox, new TextboxController(levelPathTextbox));
    }

    private void initializeMultiplayerItemsAndRenderers(){
        multiplayerMenu = new MenuState("Multiplayer");
        multiplayerMenuRenderer = new MenuRenderer(multiplayerMenu);
    }

    private void initializeLevelCreationItemsAndRenderers(){
        levelCreationMenu = new MenuState("Level Creation");
        levelCreationMenuRenderer = new MenuRenderer(levelCreationMenu);
    }

    private void initializeOptionsItemsAndRenderers(){
        optionsMenu = new MenuState("Options");
        optionsMenuRenderer = new MenuRenderer(optionsMenu);
    }

    @Override
    public StateChange update(double dT){
        StateChange change = super.update(dT);
        if(Keyboard.checkKey(KeyEvent.VK_ENTER)){
            return handleEnterButton();
        }
        if(Keyboard.checkKey(KeyEvent.VK_ESCAPE)){
            return handleEscapeButton();
        }
        handleTextboxes(dT);
        menuChangeElapsedTime = 0;
        return StateChange.linger;
    }

    private void handleTextboxes(double dT){
        if(currentMenu.getAllMenuItems().size() > 0){
            TextboxController textboxController = (TextboxController)itemControllerMap.get(currentMenu.getAllMenuItems().get(selectedIndex));
            if(textboxController != null){
                textboxController.update(dT);
            }
        }
    }

    private StateChange handleEscapeButton(){
        if(currentMenu.getMenuName().equals("Main Menu")){
            System.exit(0);
        }else{
            currentMenu = mainMenu;
            renderer = mainMenuRenderer;
            selectedIndex = 0;
        }
        Keyboard.clearKey(KeyEvent.VK_ESCAPE);
        return new StateChange(this);
    }

    private StateChange handleEnterButton(){
        // Terrible
        if(currentMenu.getAllMenuItems().size() > 0){
            if(currentMenu.getAllMenuItems().get(selectedIndex).getClass() == MenuButton.class){
                String nextMenu = ((MenuButton)(currentMenu.getAllMenuItems().get(selectedIndex))).getDestination();
                if(nextMenu.equals("Single Player")){
                    currentMenu = singlePlayerMenu;
                    renderer = singlePlayerMenuRenderer;
                    return new StateChange(this);
                }else if(nextMenu.equals("Multiplayer")){
                    currentMenu = multiplayerMenu;
                    renderer = multiplayerMenuRenderer;
                    return new StateChange(this);
                }else if(nextMenu.equals("Level Creation")){
                    currentMenu = levelCreationMenu;
                    renderer = levelCreationMenuRenderer;
                    return new StateChange(this);
                }else if(nextMenu.equals("Options")){
                    currentMenu = optionsMenu;
                    renderer = optionsMenuRenderer;
                    return new StateChange(this);
                }else if(nextMenu.equals("Load Level")){
                    // TODO Load the level here
                }else if(nextMenu.equals("Start Level")){
                    return new StateChange(GameStateManager.singlePlayerController);
                }
                selectedIndex = 0;
            }
        }
        Keyboard.clearKey(KeyEvent.VK_ENTER);
        return StateChange.linger;
    }
}
