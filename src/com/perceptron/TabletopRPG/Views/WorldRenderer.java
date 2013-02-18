package com.perceptron.TabletopRPG.Views;

import com.perceptron.TabletopRPG.Models.Camera;
import com.perceptron.TabletopRPG.Models.WorldLayer;

import java.awt.*;
import java.util.ArrayList;
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
public class WorldRenderer extends Renderer {
    private ArrayList<LayerRenderer> layerRenderers;
    private HashMap<WorldLayer, LayerRenderer> rendererMap;
    private WorldLayer currentLayer;
    private LayerRenderer currentRenderer;

    public WorldRenderer(){
        layerRenderers = new ArrayList<LayerRenderer>();
        rendererMap = new HashMap<WorldLayer, LayerRenderer>();
        renderingCamera = new Camera();
    }

    public void addLayerRenderer(WorldLayer layer, LayerRenderer layerRenderer){
        layerRenderers.add(layerRenderer);
        rendererMap.put(layer, layerRenderer);
    }

    public void updateCurrentLayer(WorldLayer layer){
        currentLayer = layer;
        currentRenderer = rendererMap.get(layer);
    }

    @Override
    public void render(Graphics2D g2d) {
        currentRenderer.setCamera(renderingCamera);
        currentRenderer.render(g2d);
    }
}
