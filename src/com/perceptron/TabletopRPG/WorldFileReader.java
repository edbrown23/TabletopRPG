package com.perceptron.TabletopRPG;

import com.perceptron.TabletopRPG.Models.*;
import com.sun.javaws.ui.SecureStaticVersioning;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 12/23/12
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
public class WorldFileReader {

    public static WorldLayer readSaveFile(String fileName) throws Exception{
        Scanner input = new Scanner(new File(fileName));
        ArrayList<WorldLayer> layers = new ArrayList<WorldLayer>();
        while(input.hasNext()){
            String line = input.nextLine();
            layers.add(readWorldFile(line));
        }
        WorldCompiler.connectLayerPortals(layers);
        return layers.get(0);
    }

    public static WorldLayer readWorldFile(String fileName) throws Exception {
        Scanner input = new Scanner(new File(fileName));
        ReadStates readState = ReadStates.ID;
        // Setting up some defaults to detect if a file does not have enough information
        int id = -1;
        int width = -1;
        int height = -1;
        int x = 0;
        int y = 0;
        int numPlayers = 0;
        int numEnemies = 0;
        int numLights = 0;
        int entitityCounter = 0;
        WorldLayer layer = null;
        while(input.hasNext()){
            String line = stripComments(input.nextLine());
            if(!line.equals("")){
                switch(readState){
                    case ID:
                        id = Integer.parseInt(line);
                        readState = ReadStates.WORLDSIZE;
                        break;
                    case WORLDSIZE:
                        String wh[] = line.split(" ");
                        width = Integer.parseInt(wh[0]);
                        height = Integer.parseInt(wh[1]);
                        if(validateBasics(id, width, height)){
                            layer = new WorldLayer(width, height, id);
                        }else{
                            throw new Exception("Missing the basics: width = " + width + " height = " + height + " id = " + id);
                        }
                        readState = ReadStates.WORLD;
                        break;
                    case WORLD:
                        readWorldLine(line, x, y, layer);
                        x++;
                        if(x >= width){
                            y++;
                            x = 0;
                        }
                        if(y >= height){
                            readState = ReadStates.NUMPLAYERS;
                        }
                        break;
                    case NUMPLAYERS:
                        numPlayers = Integer.parseInt(line);
                        readState = ReadStates.PLAYERS;
                        if(numPlayers != 0){
                            break;
                        }
                    case PLAYERS:
                        if(numPlayers > 0){
                            readPlayersLine(line, layer);
                        }
                        entitityCounter++;
                        if(entitityCounter >= numPlayers){
                            readState = ReadStates.NUMENEMIES;
                            entitityCounter = 0;
                        }
                        break;
                    case NUMENEMIES:
                        numEnemies = Integer.parseInt(line);
                        readState = ReadStates.ENEMIES;
                        if(numEnemies != 0){
                            break;
                        }
                    case ENEMIES:
                        if(numEnemies > 0){
                            readEnemiesLine(line, layer);
                        }
                        entitityCounter++;
                        if(entitityCounter >= numEnemies){
                            readState = ReadStates.NUMLIGHTS;
                            entitityCounter = 0;
                        }
                        break;
                    case NUMLIGHTS:
                        numLights = Integer.parseInt(line);
                        readState = ReadStates.LIGHTS;
                        break;
                    case LIGHTS:
                        if(numLights > 0){
                            readLightsLine(line, layer);
                        }
                        entitityCounter++;
                        if(entitityCounter >= numLights){
                            readState = ReadStates.DONE;
                            entitityCounter = 0;
                        }
                        break;
                    case DONE:
                        break;
                }
            }
        }

        return layer;
    }

    private static void readPlayersLine(String line, WorldLayer layer){
        String parms[] = line.split(" ");
        int id = Integer.parseInt(parms[0]);
        int x = Integer.parseInt(parms[1]);
        int y = Integer.parseInt(parms[2]);
        int visionRange = Integer.parseInt(parms[3]);
        int health = Integer.parseInt(parms[4]);
        int attackPower = Integer.parseInt(parms[5]);
        int attackRange = Integer.parseInt(parms[6]);
        layer.addPlayer(new ActiveUnit(id, x, y, visionRange, health, attackPower, attackRange));
    }

    private static void readEnemiesLine(String line, WorldLayer layer){
        String parms[] = line.split(" ");
        int id = Integer.parseInt(parms[0]);
        int x = Integer.parseInt(parms[1]);
        int y = Integer.parseInt(parms[2]);
        int visionRange = Integer.parseInt(parms[3]);
        int health = Integer.parseInt(parms[4]);
        int attackPower = Integer.parseInt(parms[5]);
        int attackRange = Integer.parseInt(parms[6]);
        layer.addEnemy(new ActiveUnit(id, x, y, visionRange, health, attackPower, attackRange));
    }

    private static void readLightsLine(String line, WorldLayer layer){
        String parms[] = line.split(" ");
        int rgba = Integer.parseInt(parms[0]);
        Color lcolor = new Color(rgba);
        float x = Float.parseFloat(parms[1]);
        float y = Float.parseFloat(parms[2]);
        float radius = Float.parseFloat(parms[3]);
        layer.addLight(new PointLight(lcolor, x, y, radius));
    }

    private static void readWorldLine(String line, int x, int y, WorldLayer layer){
        String parms[] = line.split(" ");
        int id = Integer.parseInt(parms[0]);
        boolean discovered = Boolean.parseBoolean(parms[1]);
        CellTypes type = CellTypes.valueOf(parms[2]);
        boolean destructable = Boolean.parseBoolean(parms[3]);
        boolean passable = Boolean.parseBoolean(parms[4]);
        boolean blocksLight = Boolean.parseBoolean(parms[5]);
        Cell newCell = new Cell(null, discovered, type, destructable, passable, blocksLight);
        newCell.setPortalID(id);
        layer.setCell(x, y, newCell);
        handlePortal(x, y, layer, id);
    }

    private static void handlePortal(int x, int y, WorldLayer layer, int portalID){
        if(portalID != 0 && portalID != layer.getID()){
            layer.addPortalCell(x, y, null);
        }
    }

    private static String stripComments(String inputLine){
        int commentLocation = inputLine.indexOf(";");
        if(commentLocation != -1){
            return inputLine.substring(0, commentLocation).trim();
        }else{
            return inputLine.trim(); // No comment
        }
    }

    private static boolean validateBasics(int id, int width, int height){
        return id != -1 && id != 0 && width != -1 && height != -1;
    }

    enum ReadStates{
        ID, WORLDSIZE, WORLD, NUMPLAYERS, PLAYERS, NUMENEMIES, ENEMIES, NUMLIGHTS, LIGHTS, DONE
    }
}
