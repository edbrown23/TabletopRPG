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
    private double maxFPS = 120;
    private double maxFrameTime = (double)((1d / maxFPS));
    public static boolean running = true;

    public MainForm(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1382, 806);
        this.setResizable(false);
        this.setLocation(50, 25);
        this.setVisible(true);

        gamePanel = new GamePanel();
        this.add(gamePanel);

        SpriteManager.initialize();
    }

    public void gameLoop(){
        double currentTime = System.currentTimeMillis();
        double t = 0.0;
        double fps = 0;
        double dT = 0.01;
        double accumulator = 0.0;
        double frameTime = 0;
        double oldTime = 0;

        while(running){
            //oldTime = currentTime;
            currentTime = System.currentTimeMillis();
            //frameTime = currentTime - oldTime;

            accumulator += (frameTime / 1000);
            gamePanel.processInput();
            // Technically we're updating the game a frame behind what is rendered, but it shouldn't matter in this turned based game
            while(accumulator >= dT){
                gamePanel.updateCurrentState(dT);
                accumulator -= dT;
                t += dT;
            }

            fps = (1d / (frameTime / 1000));
            gamePanel.setFPS(fps);
            gamePanel.renderCurrentState();
            frameTime = System.currentTimeMillis() - currentTime;
            limitFramerate(maxFrameTime - frameTime);
        }
        System.exit(0);
    }

    private void limitFramerate(double extraFrameTime){
        int sleepTime = (int)(extraFrameTime * 1000);
        //System.out.println(sleepTime);
        if(sleepTime > 0){
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
