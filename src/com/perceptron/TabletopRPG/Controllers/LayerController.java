package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.Models.WorldLayer;
import com.perceptron.TabletopRPG.MouseState;
import com.perceptron.TabletopRPG.StateChange;
import com.perceptron.TabletopRPG.Views.LayerRenderer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 2/1/13
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
public class LayerController extends Controller {
    private WorldLayer layer;
    private LayerRenderer layerRenderer;
    private ArrayList<ActiveUnitController> allUnitControllers;
    private ArrayList<ActiveUnitController> playerControllers;
    private ArrayList<ActiveUnitController> enemyControllers;


    public LayerController(WorldLayer layer, LayerRenderer renderer){
        this.layer = layer;
        this.layerRenderer = renderer;
        this.renderer = renderer;

        allUnitControllers = new ArrayList<ActiveUnitController>();
        playerControllers = new ArrayList<ActiveUnitController>();
        enemyControllers = new ArrayList<ActiveUnitController>();
    }

    public void addEnemyController(ActiveUnitController enemyController){
        enemyControllers.add(enemyController);
        allUnitControllers.add(enemyController);
    }

    public void addPlayerController(ActiveUnitController playerController){
        playerControllers.add(playerController);
        allUnitControllers.add(playerController);
    }

    public WorldLayer getLayer() {
        return layer;
    }

    public void renderLayer(Graphics2D g2d, Camera renderingCamera){
        layerRenderer.setCamera(renderingCamera);
        layerRenderer.render(g2d);

        for(ActiveUnitController player : playerControllers){
            player.renderActiveUnit(g2d, renderingCamera);
        }
    }

    @Override
    public StateChange update(double dT) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateChange processInput() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateChange processKeyboard() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StateChange processMouse(MouseState nextState) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
