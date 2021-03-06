package com.perceptron.TabletopRPG.Models;

import java.awt.image.BufferedImage;

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
 * Date: 12/20/12
 */
public class Cell {
    // Another layer, to move between world layers via a staircase or something
    private WorldLayer layerPortal;
    // The ID of the destination layer if the cell is a portal
    private int portalID;
    // Fog of War
    private boolean discovered;
    // cell type
    private CellTypes type;
    // whether the cell is destructable
    private boolean destructable;
    // whether the cell can be stood on by a player or an enemy or other entity
    private boolean passable;
    private boolean blocksLight;

    public Cell(){
        layerPortal = null;
        discovered = false;
        destructable = false;
        passable = true;
    }

    public Cell(WorldLayer layerPortal, boolean discovered, CellTypes type, boolean destructable, boolean passable, boolean blocksLight) {
        this.layerPortal = layerPortal;
        this.discovered = discovered;
        this.type = type;
        this.destructable = destructable;
        this.passable = passable;
        this.blocksLight = blocksLight;
        portalID = -1;
    }

    public WorldLayer getLayerPortal() {
        return layerPortal;
    }

    public void setLayerPortal(WorldLayer layerPortal) {
        this.layerPortal = layerPortal;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public CellTypes getType() {
        return type;
    }

    public void setType(CellTypes type) {
        this.type = type;
    }

    public boolean isDestructable() {
        return destructable;
    }

    public void setDestructable(boolean destructable) {
        this.destructable = destructable;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public int getPortalID() {
        return portalID;
    }

    public void setPortalID(int portalID) {
        this.portalID = portalID;
    }

    public boolean blocksLight() {
        return blocksLight;
    }

    public void setBlocksLight(boolean b){
        blocksLight = b;
    }

    public boolean isPortal(){
        return portalID != 0;
    }
}
