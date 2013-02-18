package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.MainMenuState;
import com.perceptron.TabletopRPG.Models.MenuState;

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
 * Date: 2/17/13
 */
public class MainMenuRenderer extends Renderer {
    private MainMenuState mainMenuState;
    private MenuRenderer mainMenuRenderer;
    private MenuRenderer singlePlayerMenuRenderer;
    private MenuRenderer multiplayerMenuRenderer;
    private MenuRenderer levelCreationMenuRenderer;
    private MenuRenderer optionsMenuRenderer;

    public MainMenuRenderer(MainMenuState mainMenuState){
        this.mainMenuState = mainMenuState;
        initializeRenderers();
    }

    private void initializeRenderers(){
        mainMenuRenderer = new MenuRenderer(mainMenuState.getMainMenu());
        singlePlayerMenuRenderer = new MenuRenderer(mainMenuState.getSinglePlayerMenu());
        multiplayerMenuRenderer = new MenuRenderer(mainMenuState.getMultiplayerMenu());
        levelCreationMenuRenderer = new MenuRenderer(mainMenuState.getLevelCreationMenu());
        optionsMenuRenderer = new MenuRenderer(mainMenuState.getOptionsMenu());
    }

    @Override
    public void render(Graphics2D g2d) {
        MenuState currentMenu = mainMenuState.getCurrentMenu();
        if(currentMenu.getMenuName().equals("Main Menu")){
            mainMenuRenderer.render(g2d);
        }else if(currentMenu.getMenuName().equals("Single Player")){
            singlePlayerMenuRenderer.render(g2d);
        }else if(currentMenu.getMenuName().equals("Multiplayer")){
            multiplayerMenuRenderer.render(g2d);
        }else if(currentMenu.getMenuName().equals("Level Creation")){
            levelCreationMenuRenderer.render(g2d);
        }else if(currentMenu.getMenuName().equals("Options")){
            optionsMenuRenderer.render(g2d);
        }
    }
}
