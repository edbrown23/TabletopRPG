package com.perceptron.TabletopRPG;

import com.sun.deploy.panel.ITreeNode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
 * Date: 1/16/13
 */
public class Sprite {
    protected BufferedImage[] sprites;
    protected int currentAnimationFrame;
    protected int singleFrameWidth;
    protected int singleFrameHeight;

    /**
     * A generic constructor the sub classes
     * @param singleFrameWidth
     * @param singleFrameHeight
     * @param numSprites
     */
    public Sprite(int singleFrameWidth, int singleFrameHeight, int numSprites){
        this.singleFrameHeight = singleFrameHeight;
        this.singleFrameWidth = singleFrameWidth;
        sprites = new BufferedImage[numSprites];
    }

    /**
     * Constructs a Sprite from a sprite sheet containing only the animation frames for one sprite
     * @param sourceFile The path to the individual sprite's sheet
     * @param singleFrameWidth The width of a single frame from the sprite sheet
     * @param singleFrameHeight The height of a single frame from the sprite sheet
     */
    public Sprite(String sourceFile, int singleFrameWidth, int singleFrameHeight, int numSprites){
        sprites = new BufferedImage[numSprites];
        BufferedImage fullSpriteImage;
        this.singleFrameHeight = singleFrameHeight;
        this.singleFrameWidth = singleFrameWidth;
        currentAnimationFrame = 0;
        try {
            fullSpriteImage = ImageIO.read(new File(sourceFile));
            cutSpriteFrames(fullSpriteImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentAnimationFrame = 0;
    }

    /**
     * Constructs a Sprite from a monolithic sprite sheet, with this sprites location in the sheet specified
     * @param sourceFile The path to the individual sprite's sheet
     * @param singleFrameWidth The width of a single frame from the sprite sheet
     * @param singleFrameHeight The height of a single frame from the sprite sheet
     * @param sheetX The x position of the start of this sprite's area in the sprite sheet
     * @param sheetY The y position of the start of this sprite's area in the sprite sheet
     * @param sheetWidth The width of this sprite's area in the sprite sheet
     * @param sheetHeight The width of this sprite's area in the sprite sheet
     */
    public Sprite(String sourceFile, int singleFrameWidth, int singleFrameHeight, int sheetX, int sheetY, int sheetWidth, int sheetHeight, int numSprites){
        sprites = new BufferedImage[numSprites];
        BufferedImage spriteSheet;
        this.singleFrameHeight = singleFrameHeight;
        this.singleFrameWidth = singleFrameWidth;
        try {
            spriteSheet = ImageIO.read(new File(sourceFile));
            cutSpriteFrames(spriteSheet.getSubimage(sheetX, sheetY, sheetX + sheetWidth, sheetY + sheetHeight));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentAnimationFrame = 0;
    }

    private void cutSpriteFrames(BufferedImage fullImage){
        int i = 0;
        for(int y = 0; y < fullImage.getHeight(); y += singleFrameHeight){
            for(int x = 0; x < fullImage.getWidth(); x += singleFrameWidth){
                sprites[i++] = Utilities.deepCopy(fullImage.getSubimage(x, y, x + singleFrameWidth, y + singleFrameHeight));
            }
        }
    }

    public BufferedImage getCurrentSprite(){
        return sprites[currentAnimationFrame];
    }
}
