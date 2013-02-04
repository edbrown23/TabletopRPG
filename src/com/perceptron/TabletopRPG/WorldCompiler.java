package com.perceptron.TabletopRPG;

import com.perceptron.TabletopRPG.Models.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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
public class WorldCompiler {
    public static WorldLayer combineLayerImages(String envFileName, String portalsFileName, String entitiesFileName, String lightsFileName){
        BufferedImage envImage = loadImage(envFileName);
        BufferedImage portalImage = loadImage(portalsFileName);
        BufferedImage entitiesImage = loadImage(entitiesFileName);
        BufferedImage lightsImage = loadImage(lightsFileName);

        // In order to save on files, the layer's ID is defined as the value of the top left portalImage pixel value
        int ID = portalImage.getRGB(0, 0) & 0xff; // Have to mask off the alpha value
        int width = envImage.getWidth();
        int height = envImage.getHeight();
        WorldLayer output = new WorldLayer(width, height, ID);
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int envValue = envImage.getRGB(x, y);
                Cell newCell = parseEnvironmentCell(envValue);
                output.setCell(x, y, newCell);
                int lightsValue = lightsImage.getRGB(x, y);
                parseLightCell(x, y, output, lightsValue);
                int entitiesValue = entitiesImage.getRGB(x, y);
                parseEntitiesCell(x, y, output, entitiesValue);
                int portalsValue = portalImage.getRGB(x, y);
                parsePortalsCell(x, y, output, portalsValue);
            }
        }
        return output;
    }

    private static Cell parseEnvironmentCell(int envValue){
        int r = (envValue & 0xff0000) >> 16;
        int g = (envValue & 0xff00) >> 8;
        int b = (envValue & 0xff);

        Cell output = null;
        // Determine the cell type depending on the pixel value
        if(r == 192 && g == 192 && b == 192){
            output = new Cell(null, false, CellTypes.Dirt, false, true, false);
        }else if(r == 127 && g == 51 && b == 0){
            output = new Cell(null, false, CellTypes.Rock, false, false, true);
        }
        return output;
    }

    private static void parsePortalsCell(int x, int y, WorldLayer layer, int portalsValue){
        int value = (portalsValue) & (0xffffff); // Mask off the alpha channel to get just the total RGB value
        layer.getCell(x, y).setPortalID(value);
        if(value != 0 && value != layer.getID()){
            layer.addPortalCell(x, y, null);
        }
    }

    private static void parseLightCell(int x, int y, WorldLayer layer, int lightsValue){
        int r = (lightsValue & 0xff0000) >> 16;
        int g = (lightsValue & 0xff00) >> 8;
        int b = (lightsValue & 0xff);
        if(r == 255 && g == 255 & b == 254){
            layer.addLight(new PointLight(Color.green, x + 0.5f, y + 0.5f, 5));
        }
    }

    private static void parseEntitiesCell(int x, int y, WorldLayer layer, int entitiesValue){
        int r = (entitiesValue & 0xff0000) >> 16;
        int g = (entitiesValue & 0xff00) >> 8;
        int b = (entitiesValue & 0xff);

        // TODO actually add some entities
        if(r == 0 && g == 0 && b == 255){
            layer.addPlayer(new ActiveUnit(x, y, 0, 0, 0));
        }
    }

    public static void compileWorldLayersToFile(String outputFileName, WorldLayer... layers) throws Exception {
        if(!connectLayerPortals(layers)){
            throw new Exception("Connecting world layers failed! Most likely a layer is missing");
        }
        if(!encodeWorldLayers(outputFileName, layers)){
            throw new Exception("Encoding world layers failed! Who knows why?!?!");
        }
    }

    public static boolean connectLayerPortals(ArrayList<WorldLayer> layers){
        for(WorldLayer layer : layers){
            HashMap<IntegerPoint2D, WorldLayer> portals = layer.getPortalCells();
            for(IntegerPoint2D point : portals.keySet()){
                Cell portal = layer.getCell(point.x, point.y);
                boolean foundDestination = false;
                for(WorldLayer destLayer : layers){
                    if(destLayer.getID() == portal.getPortalID()){
                        portal.setLayerPortal(destLayer);
                        // This should simply replace the previous value in the table
                        layer.addPortalCell(point.x, point.y, destLayer);
                        foundDestination = true;
                    }
                }
                if(!foundDestination) return false;
            }
        }
        return true;
    }

    public static boolean connectLayerPortals(WorldLayer... layers){
        for(WorldLayer layer : layers){
            HashMap<IntegerPoint2D, WorldLayer> portals = layer.getPortalCells();
            for(IntegerPoint2D point : portals.keySet()){
                Cell portal = layer.getCell(point.x, point.y);
                boolean foundDestination = false;
                for(WorldLayer destLayer : layers){
                    if(destLayer.getID() == portal.getPortalID()){
                        portal.setLayerPortal(destLayer);
                        // This should simply replace the previous value in the table
                        layer.addPortalCell(point.x, point.y, destLayer);
                        foundDestination = true;
                    }
                }
                if(!foundDestination) return false;
            }
        }
        return true;
    }

    private static boolean encodeWorldLayers(String outputFileName, WorldLayer... layers){
        PrintWriter output;
        int i = 0;
        for(WorldLayer currentLayer : layers){
            try {
                output = new PrintWriter(new FileWriter(outputFileName + i));
            } catch (IOException e) {
                return false;
            }
            writeComment(output, "Layer ID");
            // First write out the layer ID
            output.println(currentLayer.getID());
            writeComment(output, "Width and Height");
            // First write out the width and height of the layer for decoding later
            output.println(currentLayer.getWidth() + " " + currentLayer.getHeight());
            // Now write out all the cells
            writeComment(output, "Layer cells");
            writeCells(output, currentLayer.getCells());
            // Now write out all the entities
            // Starting with players
            writeComment(output, "Players");
            output.println(currentLayer.getPlayers().size());
            writeActiveUnits(output, currentLayer.getPlayers());
            // Then enemies
            writeComment(output, "Enemies");
            output.println(currentLayer.getEnemies().size());
            writeActiveUnits(output, currentLayer.getEnemies());
            // lights
            writeComment(output, "Lights");
            output.println(currentLayer.getLights().size());
            writeLights(output, currentLayer.getLights());
            output.close();
            i++;
        }
        return true;
    }

    private static void writeCells(PrintWriter output, Cell[][] cells){
        writeComment(output, "PortalID Discovered Type Destructable Passable BlocksLight");
        // TODO is this terrible? It will null pointer if cells is empty, but do we care? Is y first faster?
        for(int y = 0; y < cells[0].length; y++){
            for(int x = 0; x < cells.length; x++){
                output.println(cells[x][y].getPortalID() + " " + cells[x][y].isDiscovered() + " " + cells[x][y].getType()
                        + " " + cells[x][y].isDestructable() + " " + cells[x][y].isPassable() + " " +
                        cells[x][y].blocksLight());
            }
        }
    }

    private static void writeActiveUnits(PrintWriter output, ArrayList<ActiveUnit> entities){
        writeComment(output, "ID X Y VisionRange Health AttackPower AttackRange");
        for(ActiveUnit currentUnit : entities){
            output.println(currentUnit.getTypeID() + " " + currentUnit.getX() + " " + currentUnit.getY() + " " +
                    currentUnit.getVisionRange() + " " + currentUnit.getCurrentHealth());
        }
    }

    private static void writeLights(PrintWriter output, ArrayList<PointLight> lights){
        writeComment(output, "ARGB X Y Radius");
        for(PointLight light : lights){
            output.println(light.getLightColor().getRGB() + " " + light.getX() + " " + light.getY() + " " +
                    light.getRadius());
        }
    }

    private static BufferedImage loadImage(String fileName){
        BufferedImage output = null;
        try {
            output = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    private static void writeComment(PrintWriter output, String comment){
        output.println("; " + comment);
    }


}
