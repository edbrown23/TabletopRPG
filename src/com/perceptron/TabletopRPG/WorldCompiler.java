package com.perceptron.TabletopRPG;

import com.perceptron.TabletopRPG.Models.Cell;
import com.perceptron.TabletopRPG.Models.CellTypes;
import com.perceptron.TabletopRPG.Models.WorldLayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class WorldCompiler {
    public static WorldLayer combineLayerImages(String envFileName, String portalsFileName, String entitiesFileName){
        BufferedImage envImage = loadImage(envFileName);
        BufferedImage portalImage = loadImage(portalsFileName);
        BufferedImage entitiesImage = loadImage(entitiesFileName);

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
                int effectsValue = portalImage.getRGB(x, y);
                parsePortalCell(x, y, output, effectsValue);
                int entitiesValue = entitiesImage.getRGB(x, y);
                parseEntitiesCell(x, y, output, entitiesValue);
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
            output = new Cell(null, false, CellTypes.Dirt, false, true);
        }else if(r == 127 && g == 51 && b == 0){
            output = new Cell(null, false, CellTypes.Rock, false, false);
        }
        return output;
    }

    private static void parsePortalCell(int x, int y, WorldLayer layer, int portalsValue){
        int value = (portalsValue) & (0xffffff); // Mask off the alpha channel to get just the total RGB value

    }

    private static void parseEntitiesCell(int x, int y, WorldLayer layer, int entitiesValue){
        
    }

    public static String compileWorldLayers(String outputFileName, WorldLayer... layers){
        for(WorldLayer layer : layers){

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
}
