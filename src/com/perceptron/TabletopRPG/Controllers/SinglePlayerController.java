package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.*;
import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.Models.Cell;
import com.perceptron.TabletopRPG.Models.WorldLayer;
import com.perceptron.TabletopRPG.Views.InGameMenuRenderer;
import com.perceptron.TabletopRPG.Views.SinglePlayerRenderer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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
public class SinglePlayerController extends Controller {
    private SinglePlayerState singlePlayerState;
    private IntegerPoint2D selectorPosition;
    private DungeonMaster dungeonMaster;
    private Camera camera;
    
    public SinglePlayerController(){
        singlePlayerState = new SinglePlayerState();
        selectorPosition = singlePlayerState.getSelectorCoords();
        dungeonMaster = new NonCombatDungeonMaster();

        this.state = singlePlayerState;
        camera = new Camera(0, 0, Utilities.renderingPanelWidth, Utilities.renderingPanelHeight);
        singlePlayerState.setCamera(camera);
    }

    public StateChange processInput(){
        dungeonMaster.setLayer(singlePlayerState.getCurrentWorldLayer());
        MouseState mouseState = Mouse.dequeueState();
        dungeonMaster.setCamera(camera);
        dungeonMaster.updateStateMachine(Keyboard.dequeueKeyEvent(), mouseState);
        StateChange change = processKeyboard();
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
    public StateChange processKeyboard() {
        if(Keyboard.checkKey(KeyEvent.VK_UP)){
            camera.moveY(-2);
            //Keyboard.clearKey(KeyEvent.VK_UP);
        }
        if(Keyboard.checkKey(KeyEvent.VK_DOWN)){
            camera.moveY(2);
            //Keyboard.clearKey(KeyEvent.VK_DOWN);
        }
        if(Keyboard.checkKey(KeyEvent.VK_LEFT)){
            camera.moveX(-2);
            //Keyboard.clearKey(KeyEvent.VK_LEFT);
        }
        if(Keyboard.checkKey(KeyEvent.VK_RIGHT)){
            camera.moveX(2);
            //Keyboard.clearKey(KeyEvent.VK_RIGHT);
        }
        if(Keyboard.checkKey(KeyEvent.VK_PAGE_UP)){
            camera.increaseZoomLevel();
            Keyboard.clearKey(KeyEvent.VK_PAGE_UP);
        }
        if(Keyboard.checkKey(KeyEvent.VK_PAGE_DOWN)){
            camera.decreaseZoomLevel();
            Keyboard.clearKey(KeyEvent.VK_PAGE_DOWN);
        }
        if(Keyboard.checkKey(KeyEvent.VK_W)){
            singlePlayerState.getCurrentWorldLayer().getLights().get(0).addDY(-0.05f);
        }
        if(Keyboard.checkKey(KeyEvent.VK_S)){
            singlePlayerState.getCurrentWorldLayer().getLights().get(0).addDY(0.05f);
        }
        if(Keyboard.checkKey(KeyEvent.VK_A)){
            singlePlayerState.getCurrentWorldLayer().getLights().get(0).addDX(-0.05f);
        }
        if(Keyboard.checkKey(KeyEvent.VK_D)){
            singlePlayerState.getCurrentWorldLayer().getLights().get(0).addDX(0.05f);
        }
        if(Keyboard.checkKey(KeyEvent.VK_SPACE)){
            singlePlayerState.getCurrentWorldLayer().getLights().get(0).adjustRadius(0.5f);
            Keyboard.clearKey(KeyEvent.VK_SPACE);
        }
        if(Keyboard.checkKey(KeyEvent.VK_CONTROL)){
            singlePlayerState.getCurrentWorldLayer().getLights().get(0).adjustRadius(-0.5f);
            Keyboard.clearKey(KeyEvent.VK_CONTROL);
        }
        return StateChange.linger;
    }

    @Override
    public StateChange processMouse(MouseState nextState) {
        if(nextState != null && nextState.button == MouseEvent.BUTTON1 && nextState.down){
            // These are the mouse's x and y in screen coordinates
            int x = Mouse.X;
            int y = Mouse.Y;
            // Now we get layer coordinates
            int lx = convertXToLayerCoords(x);
            int ly = convertYToLayerCoords(y);
            selectorPosition.x = lx;
            selectorPosition.y = ly;
        }
        return StateChange.linger;
    }

    @Override
    public StateChange update(double dT) {
        return StateChange.linger;
    }

    private int convertXToLayerCoords(int screenX){
        WorldLayer layer = singlePlayerState.getCurrentWorldLayer();
        int layerX = (screenX + camera.getX()) / camera.getZoomLevel();
        //System.out.println(camera.getX() + " " + camera.getY());
        if(layerX < 0 || layerX >= layer.getWidth()){
            return -1;
        }else{
            return layerX;
        }
    }

    private int convertYToLayerCoords(int screenY){
        WorldLayer layer = singlePlayerState.getCurrentWorldLayer();
        int layerY = (screenY + camera.getY()) / camera.getZoomLevel();
        if(layerY < 0 || layerY >= layer.getHeight()){
            return -1;
        }else{
            return layerY;
        }
    }

    public SinglePlayerState getSinglePlayerState() {
        return singlePlayerState;
    }

    public Camera getCamera(){
        return camera;
    }
}
