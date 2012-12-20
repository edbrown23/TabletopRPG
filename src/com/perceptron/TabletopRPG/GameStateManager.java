package com.perceptron.TabletopRPG;

import com.perceptron.TabletopRPG.Controllers.Controller;
import com.perceptron.TabletopRPG.Controllers.MainMenuController;
import com.perceptron.TabletopRPG.Controllers.SinglePlayerController;
import com.perceptron.TabletopRPG.Views.MenuRenderer;
import com.perceptron.TabletopRPG.Views.Renderer;
import com.perceptron.TabletopRPG.Views.SinglePlayerRenderer;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 12/17/12
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
public class GameStateManager {
    private GameState currentState;
    private Controller currentController;
    private Renderer currentRenderer;
    public static MainMenuState mainMenuState;
    public static SinglePlayerState singlePlayerState;
    private MainMenuController mainMenuController;
    private SinglePlayerController singlePlayerController;
    private MenuRenderer menuRenderer;
    private SinglePlayerRenderer singlePlayerRenderer;

    public GameStateManager(){
        initializeStates();
        initializeControllers();
        initializeRenderers();

        currentState = mainMenuState;
        currentController = mainMenuController;
        currentRenderer = menuRenderer;
    }

    private void initializeStates(){
        singlePlayerState = new SinglePlayerState();
        mainMenuState = new MainMenuState();
    }

    private void initializeControllers(){
        mainMenuController = new MainMenuController(mainMenuState);
        mainMenuState.setController(mainMenuController);
        singlePlayerController = new SinglePlayerController(singlePlayerState);
        singlePlayerState.setController(singlePlayerController);
    }

    private void initializeRenderers(){
        menuRenderer = new MenuRenderer(mainMenuState);
        mainMenuState.setRenderer(menuRenderer);
        singlePlayerRenderer = new SinglePlayerRenderer(singlePlayerState);
        singlePlayerState.setRenderer(singlePlayerRenderer);
    }

    public void renderCurrentState(Graphics2D g2d){
        currentRenderer.render(g2d);
    }

    public void updateCurrentState(double dT){
        StateChange change = currentController.update(dT);
        if(change.getNextState() != null){
            currentState = change.getNextState();
            currentController = currentState.getController();
            currentRenderer = currentState.getRenderer();
        }
    }
}
