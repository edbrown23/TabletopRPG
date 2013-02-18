package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.Models.WorldLayer;
import com.perceptron.TabletopRPG.MouseState;
import com.perceptron.TabletopRPG.StateChange;
import sun.reflect.generics.tree.VoidDescriptor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
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
 * Date: 2/16/13
 */
public class WorldController extends Controller {
    private HashMap<Integer, LayerController> layerMap;
    private LayerController currentController;

    public WorldController(){
        layerMap = new HashMap<Integer, LayerController>();
    }

    public void putLayer(int i, LayerController layer){
        layerMap.put(i, layer);
    }

    public void setCurrentController(int id){
        currentController = layerMap.get(id);
    }

    public LayerController getCurrentController() {
        return currentController;
    }

    public ArrayList<WorldLayer> getLayers(){
        Collection<LayerController> controllers = layerMap.values();
        ArrayList<WorldLayer> output = new ArrayList<WorldLayer>();
        for(LayerController controller : controllers){
            output.add(controller.getLayer());
        }
        return output;
    }

    @Override
    public StateChange update(double dT) {
        return null;
    }

    @Override
    public StateChange processInput() {
        return null;
    }

    @Override
    public StateChange processKeyboard() {
        return null;
    }

    @Override
    public StateChange processMouse(MouseState nextState) {
        return null;
    }
}
