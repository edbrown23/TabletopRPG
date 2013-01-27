package com.perceptron.TabletopRPG;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 12/16/12
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
public class GamePanel extends JPanel {
    private long fps = 0;
    private Font font;
    private GameStateManager stateManager;

    public GamePanel(){
        this.setSize(1366, 768);
        this.setDoubleBuffered(true);

        Utilities.renderingPanelWidth = this.getWidth();
        Utilities.renderingPanelHeight = this.getHeight();

        this.addKeyListener(new CustomKeyboardListener());
        this.addMouseListener(new CustomMouseListener());

        stateManager = new GameStateManager();
        font = new Font("SansSerif", Font.BOLD, 13);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        stateManager.renderCurrentState(g2d);

        g2d.setColor(Color.white);
        g2d.setFont(font);
        g2d.drawString("FPS: " + Long.toString(fps), 5, 15);
    }

    public void updateCurrentState(double dT){
        stateManager.updateCurrentState(dT);
    }

    public void processInput(){
        this.grabFocus();
        stateManager.processInput();
    }

    public void renderCurrentState(){
        repaint();
    }

    public void setFPS(long fps){
        this.fps = fps;
    }
}
