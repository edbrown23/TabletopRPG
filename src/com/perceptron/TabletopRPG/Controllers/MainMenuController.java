package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.*;
import com.perceptron.TabletopRPG.Models.*;

import java.awt.event.KeyEvent;
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
    private MainMenuState mainMenuState;
    private HashMap<MenuItem, Controller> itemControllerMap;

    public MainMenuController(){
        this.mainMenuState = new MainMenuState();
        itemControllerMap = new HashMap<MenuItem, Controller>();
        // TODO This is a shim to get the menu working for now
        MenuTextbox t = mainMenuState.getSinglePlayerMenu().getTextboxes().get(0);
        itemControllerMap.put(t, new TextboxController(t));
        menu = mainMenuState.getMainMenu();
        mainMenuState.setCurrentMenu(menu);
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
        if(menu.getAllMenuItems().size() > 0){
            TextboxController textboxController = (TextboxController)itemControllerMap.get(menu.getAllMenuItems().get(selectedIndex));
            if(textboxController != null){
                Keyboard.startCollectingASCIIKeys();
                textboxController.processInput();
            }else{
                Keyboard.stopCollectingASCIIKeys();
            }
        }
    }

    private StateChange handleEscapeButton(){
        if(menu.getMenuName().equals("Main Menu")){
            System.exit(0);
        }else{
            menu = mainMenuState.getMainMenu();
            mainMenuState.setCurrentMenu(menu);
            selectedIndex = 0;
        }
        Keyboard.clearKey(KeyEvent.VK_ESCAPE);
        return StateChange.linger;
    }

    private StateChange handleEnterButton(){
        Keyboard.clearKey(KeyEvent.VK_ENTER);
        // Terrible
        if(menu.getAllMenuItems().size() > 0){
            if(menu.getAllMenuItems().get(selectedIndex).getClass() == MenuButton.class){
                String nextMenu = ((MenuButton)(menu.getAllMenuItems().get(selectedIndex))).getDestination();
                if(nextMenu.equals("Single Player")){
                    menu.getAllMenuItems().get(selectedIndex).setSelected(false);
                    menu = mainMenuState.getSinglePlayerMenu();
                    mainMenuState.setCurrentMenu(menu);
                    return StateChange.linger;
                }else if(nextMenu.equals("Multiplayer")){
                    menu.getAllMenuItems().get(selectedIndex).setSelected(false);
                    menu = mainMenuState.getMultiplayerMenu();
                    mainMenuState.setCurrentMenu(menu);
                    return StateChange.linger;
                }else if(nextMenu.equals("Options")){
                    menu.getAllMenuItems().get(selectedIndex).setSelected(false);
                    menu = mainMenuState.getOptionsMenu();
                    mainMenuState.setCurrentMenu(menu);
                    return StateChange.linger;
                }else if(nextMenu.equals("Start Level")){
                    String path = menu.getAllMenuItems().get(1).getText();
                    StateChange change = loadSave(path);
                    menu.getAllMenuItems().get(selectedIndex).setSelected(false);
                    mainMenuState.setCurrentMenu(menu);
                    if(change != StateChange.linger){
                        return change;
                    }
                }
                selectedIndex = 0;
                menu.getAllMenuItems().get(selectedIndex).setSelected(true);
            }
        }
        return StateChange.linger;
    }

    private StateChange loadSave(String path){
        WorldLayer layer1 = WorldCompiler.combineLayerImages("WorldFiles/World0Environment.png", "WorldFiles/World0Portals.png", "WorldFiles/World0Entities.png", "WorldFiles/World0Lights.png");
        WorldLayer layer2 = WorldCompiler.combineLayerImages("WorldFiles/World2Environment.png", "WorldFiles/World2Portals.png", "WorldFiles/World0Entities.png", "WorldFiles/World2Lights.png");
        WorldController worldController = null;
        try {
            WorldCompiler.compileWorldLayersToFile("testies", layer1, layer2);
            worldController = WorldFileReader.readSaveFile(path);
            worldController.setCurrentController(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((SinglePlayerState)(GameStateManager.singlePlayerController.state)).setCurrentWorldLayer(0);
        GameStateManager.singlePlayerRenderer.createRenderers();
        return new StateChange(GameStateManager.singlePlayerController, GameStateManager.singlePlayerRenderer);
    }

    public MainMenuState getMainMenuState(){
        return mainMenuState;
    }
    public MenuState getCurrentMenu(){
        return menu;
    }
}
