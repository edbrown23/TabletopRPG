package com.perceptron.TabletopRPG.Models;

import com.perceptron.TabletopRPG.IntegerPoint2D;

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
 * Date: 12/20/12
 */
public class WorldLayer {
    private int ID;
    // The map
    private Cell[][] cells;
    // The enemies
    private ArrayList<ActiveUnit> enemies;
    // The players
    private ArrayList<ActiveUnit> players;
    private ArrayList<Entity> allEntities;
    private HashMap<IntegerPoint2D, WorldLayer> portalCells;

    public WorldLayer(int width, int height, int layerID){
        portalCells = new HashMap<IntegerPoint2D, WorldLayer>();
        cells = new Cell[width][];
        for(int x = 0; x < width; x++){
            cells[x] = new Cell[height];
        }
        enemies = new ArrayList<ActiveUnit>();
        players = new ArrayList<ActiveUnit>();
        allEntities = new ArrayList<Entity>();
        ID = layerID;
    }

    public void addPortalCell(int x, int y, WorldLayer destination){
        portalCells.put(new IntegerPoint2D(x, y), destination);
    }

    public void setCell(int x, int y, Cell cell){
        cells[x][y] = cell;
    }

    public int getID() {
        return ID;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void addPlayer(ActiveUnit player){
        players.add(player);
        allEntities.add(player);
    }

    public void addEnemy(ActiveUnit enemy){
        enemies.add(enemy);
        allEntities.add(enemy);
    }

    public ArrayList<ActiveUnit> getEnemies() {
        return enemies;
    }

    public ArrayList<ActiveUnit> getPlayers() {
        return players;
    }

    public ArrayList<Entity> getAllEntities() {
        return allEntities;
    }
}
