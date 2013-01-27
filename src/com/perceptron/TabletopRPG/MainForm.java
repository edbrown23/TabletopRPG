package com.perceptron.TabletopRPG;

import javax.swing.*;

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
public class MainForm extends JFrame{
    private GamePanel gamePanel;
    private long maxFPS = 120;
    private long maxFrameTime = (long)((1D / maxFPS) * 1E9);
    public static boolean running = true;

    public MainForm(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1365, 768);
        this.setResizable(false);
        this.setLocation(100, 56);
        this.setVisible(true);

        gamePanel = new GamePanel();
        this.add(gamePanel);

        SpriteManager.initialize();
    }

    public void gameLoop(){
        double currentTime = System.nanoTime();
        double t = 0.0;
        long fps = 0;
        double dT = 0.01;
        double accumulator = 0.0;
        double frameTime = 0;

        while(running){
            double oldTime = currentTime;
            currentTime = System.nanoTime();
            frameTime = currentTime - oldTime;

            accumulator += (frameTime / 1E9);
            gamePanel.processInput();
            // Technically we're updating the game a frame behind what is rendered, but it shouldn't matter in this turned based game
            while(accumulator >= dT){
                gamePanel.updateCurrentState(dT);
                accumulator -= dT;
                t += dT;
            }

            fps = (long)(1f / ((frameTime) / 1E9));
            //System.out.println(1d / (frameTime / 1E9));
            gamePanel.setFPS(fps);
            gamePanel.renderCurrentState();
            limitFramerate(maxFrameTime - frameTime);
        }
        System.exit(0);
    }

    private void limitFramerate(double extraFrameTime){
        int sleepTime = (int)(extraFrameTime / 1E6);
        if(sleepTime > 0){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
