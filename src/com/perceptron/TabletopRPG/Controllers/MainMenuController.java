package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.*;
import com.perceptron.TabletopRPG.Models.*;
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

        MenuButton startLevelButton = new MenuButton(new Point2D.Float(400, 200), "Start Level");
        singlePlayerMenuRenderer.addRenderer(new ButtonRenderer(startLevelButton));
        singlePlayerMenu.addButton(startLevelButton);

        MenuTextbox savePathTextbox = new MenuTextbox(new Point2D.Float(400, 260));
        savePathTextbox.appendString("");
        singlePlayerMenuRenderer.addRenderer(new TextboxRenderer(savePathTextbox));
        singlePlayerMenu.addTextBox(savePathTextbox);
        itemControllerMap.put(savePathTextbox, new TextboxController(savePathTextbox));
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
    public StateChange processInput(){
        MouseState mouseState = Mouse.dequeueState();
        StateChange change = super.processInput();
        if(change != StateChange.linger){
            return change;
        }
        change = processKeyboard();
        if(change != StateChange.linger){
            return change;
        }
        change = processMouse(mouseState);
        if(change != StateChange.linger){
            return change;
        }
        return StateChange.linger;
    }

    @Override
    public StateChange processKeyboard(){
        super.processKeyboard();
        if(Keyboard.checkKey(KeyEvent.VK_ENTER)){
            return handleEnterButton();
        }
        if(Keyboard.checkKey(KeyEvent.VK_ESCAPE)){
            return handleEscapeButton();
        }
        handleTextboxes();
        menuChangeElapsedTime = 0;
        return StateChange.linger;
    }

    @Override
    public StateChange processMouse(MouseState nextState){
        super.processMouse(nextState);
        return StateChange.linger;
    }

    @Override
    public StateChange update(double dT){
        return StateChange.linger;
    }

    private void handleTextboxes(){
        if(currentMenu.getAllMenuItems().size() > 0){
            TextboxController textboxController = (TextboxController)itemControllerMap.get(currentMenu.getAllMenuItems().get(selectedIndex));
            if(textboxController != null){
                Keyboard.startCollectingASCIIKeys();
                textboxController.processInput();
            }else{
                Keyboard.stopCollectingASCIIKeys();
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
        Keyboard.clearKey(KeyEvent.VK_ENTER);
        // Terrible
        if(currentMenu.getAllMenuItems().size() > 0){
            if(currentMenu.getAllMenuItems().get(selectedIndex).getClass() == MenuButton.class){
                String nextMenu = ((MenuButton)(currentMenu.getAllMenuItems().get(selectedIndex))).getDestination();
                if(nextMenu.equals("Single Player")){
                    currentMenu.getAllMenuItems().get(selectedIndex).setSelected(false);
                    currentMenu = singlePlayerMenu;
                    renderer = singlePlayerMenuRenderer;
                    return new StateChange(this);
                }else if(nextMenu.equals("Multiplayer")){
                    currentMenu.getAllMenuItems().get(selectedIndex).setSelected(false);
                    currentMenu = multiplayerMenu;
                    renderer = multiplayerMenuRenderer;
                    return new StateChange(this);
                }else if(nextMenu.equals("Options")){
                    currentMenu.getAllMenuItems().get(selectedIndex).setSelected(false);
                    currentMenu = optionsMenu;
                    renderer = optionsMenuRenderer;
                    return new StateChange(this);
                }else if(nextMenu.equals("Start Level")){
                    String path = currentMenu.getAllMenuItems().get(1).getText();
                    loadSave(path);
                    currentMenu.getAllMenuItems().get(selectedIndex).setSelected(false);
                    return new StateChange(GameStateManager.singlePlayerController);
                }
                selectedIndex = 0;
                currentMenu.getAllMenuItems().get(selectedIndex).setSelected(true);
            }
        }
        return StateChange.linger;
    }

    private void loadSave(String path){
        WorldLayer layer1 = WorldCompiler.combineLayerImages("WorldFiles/World0Environment.png", "WorldFiles/World0Portals.png", "WorldFiles/World0Entities.png", "WorldFiles/World0Lights.png");
        WorldLayer layer2 = WorldCompiler.combineLayerImages("WorldFiles/World2Environment.png", "WorldFiles/World2Portals.png", "WorldFiles/World0Entities.png", "WorldFiles/World2Lights.png");
        try {
            WorldCompiler.compileWorldLayersToFile("testies", layer1, layer2);
            layer1 = WorldFileReader.readSaveFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((SinglePlayerState)(GameStateManager.singlePlayerController.state)).setCurrentWorldLayer(layer1);
        GameStateManager.singlePlayerController.informRenderersOfLayerChange(layer1);
    }
}
