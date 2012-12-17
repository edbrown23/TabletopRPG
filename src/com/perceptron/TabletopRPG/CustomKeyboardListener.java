package com.perceptron.TabletopRPG;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 12/17/12
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
public class CustomKeyboardListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        switch(e.getKeyCode()){
            case KeyEvent.VK_Q:
                Keyboard.Q = true;
                break;
            case KeyEvent.VK_W:
                Keyboard.W = true;
                break;
            case KeyEvent.VK_E:
                Keyboard.E = true;
                break;
            case KeyEvent.VK_R:
                Keyboard.R = true;
                break;
            case KeyEvent.VK_T:
                Keyboard.T = true;
                break;
            case KeyEvent.VK_Y:
                Keyboard.Y = true;
                break;
            case KeyEvent.VK_U:
                Keyboard.U = true;
                break;
            case KeyEvent.VK_I:
                Keyboard.I = true;
                break;
            case KeyEvent.VK_O:
                Keyboard.O = true;
                break;
            case KeyEvent.VK_P:
                Keyboard.P = true;
                break;
            case KeyEvent.VK_A:
                Keyboard.A = true;
                break;
            case KeyEvent.VK_S:
                Keyboard.S = true;
                break;
            case KeyEvent.VK_D:
                Keyboard.D = true;
                break;
            case KeyEvent.VK_F:
                Keyboard.F = true;
                break;
            case KeyEvent.VK_G:
                Keyboard.G = true;
                break;
            case KeyEvent.VK_H:
                Keyboard.H = true;
                break;
            case KeyEvent.VK_J:
                Keyboard.J = true;
                break;
            case KeyEvent.VK_K:
                Keyboard.K = true;
                break;
            case KeyEvent.VK_L:
                Keyboard.L = true;
                break;
            case KeyEvent.VK_Z:
                Keyboard.Z = true;
                break;
            case KeyEvent.VK_X:
                Keyboard.X = true;
                break;
            case KeyEvent.VK_C:
                Keyboard.C = true;
                break;
            case KeyEvent.VK_V:
                Keyboard.V = true;
                break;
            case KeyEvent.VK_B:
                Keyboard.B = true;
                break;
            case KeyEvent.VK_N:
                Keyboard.N = true;
                break;
            case KeyEvent.VK_M:
                Keyboard.M = true;
                break;
            case KeyEvent.VK_1:
                Keyboard.K1 = true;
                break;
            case KeyEvent.VK_2:
                Keyboard.K2 = true;
                break;
            case KeyEvent.VK_3:
                Keyboard.K3 = true;
                break;
            case KeyEvent.VK_4:
                Keyboard.K4 = true;
                break;
            case KeyEvent.VK_5:
                Keyboard.K5 = true;
                break;
            case KeyEvent.VK_6:
                Keyboard.K6 = true;
                break;
            case KeyEvent.VK_7:
                Keyboard.K7 = true;
                break;
            case KeyEvent.VK_8:
                Keyboard.K8 = true;
                break;
            case KeyEvent.VK_9:
                Keyboard.K9 = true;
                break;
            case KeyEvent.VK_0:
                Keyboard.K0 = true;
                break;
            case KeyEvent.VK_ESCAPE:
                Keyboard.ESCAPE = true;
                break;
            case KeyEvent.VK_CONTROL:
                Keyboard.LEFT_CONTROL = true;
                Keyboard.RIGHT_CONTROL = true;
                break;
            case KeyEvent.VK_UP:
                Keyboard.UP = true;
                break;
            case KeyEvent.VK_DOWN:
                Keyboard.DOWN = true;
                break;
            case KeyEvent.VK_RIGHT:
                Keyboard.RIGHT = true;
                break;
            case KeyEvent.VK_LEFT:
                Keyboard.LEFT = true;
                break;
            case KeyEvent.VK_SHIFT:
                Keyboard.LEFT_SHIFT = true;
                Keyboard.RIGHT_SHIFT = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_Q:
                Keyboard.Q = false;
                break;
            case KeyEvent.VK_W:
                Keyboard.W = false;
                break;
            case KeyEvent.VK_E:
                Keyboard.E = false;
                break;
            case KeyEvent.VK_R:
                Keyboard.R = false;
                break;
            case KeyEvent.VK_T:
                Keyboard.T = false;
                break;
            case KeyEvent.VK_Y:
                Keyboard.Y = false;
                break;
            case KeyEvent.VK_U:
                Keyboard.U = false;
                break;
            case KeyEvent.VK_I:
                Keyboard.I = false;
                break;
            case KeyEvent.VK_O:
                Keyboard.O = false;
                break;
            case KeyEvent.VK_P:
                Keyboard.P = false;
                break;
            case KeyEvent.VK_A:
                Keyboard.A = false;
                break;
            case KeyEvent.VK_S:
                Keyboard.S = false;
                break;
            case KeyEvent.VK_D:
                Keyboard.D = false;
                break;
            case KeyEvent.VK_F:
                Keyboard.F = false;
                break;
            case KeyEvent.VK_G:
                Keyboard.G = false;
                break;
            case KeyEvent.VK_H:
                Keyboard.H = false;
                break;
            case KeyEvent.VK_J:
                Keyboard.J = false;
                break;
            case KeyEvent.VK_K:
                Keyboard.K = false;
                break;
            case KeyEvent.VK_L:
                Keyboard.L = false;
                break;
            case KeyEvent.VK_Z:
                Keyboard.Z = false;
                break;
            case KeyEvent.VK_X:
                Keyboard.X = false;
                break;
            case KeyEvent.VK_C:
                Keyboard.C = false;
                break;
            case KeyEvent.VK_V:
                Keyboard.V = false;
                break;
            case KeyEvent.VK_B:
                Keyboard.B = false;
                break;
            case KeyEvent.VK_N:
                Keyboard.N = false;
                break;
            case KeyEvent.VK_M:
                Keyboard.M = false;
                break;
            case KeyEvent.VK_1:
                Keyboard.K1 = false;
                break;
            case KeyEvent.VK_2:
                Keyboard.K2 = false;
                break;
            case KeyEvent.VK_3:
                Keyboard.K3 = false;
                break;
            case KeyEvent.VK_4:
                Keyboard.K4 = false;
                break;
            case KeyEvent.VK_5:
                Keyboard.K5 = false;
                break;
            case KeyEvent.VK_6:
                Keyboard.K6 = false;
                break;
            case KeyEvent.VK_7:
                Keyboard.K7 = false;
                break;
            case KeyEvent.VK_8:
                Keyboard.K8 = false;
                break;
            case KeyEvent.VK_9:
                Keyboard.K9 = false;
                break;
            case KeyEvent.VK_0:
                Keyboard.K0 = false;
                break;
            case KeyEvent.VK_ESCAPE:
                Keyboard.ESCAPE = false;
                break;
            case KeyEvent.VK_CONTROL:
                Keyboard.LEFT_CONTROL = false;
                Keyboard.RIGHT_CONTROL = false;
                break;
            case KeyEvent.VK_UP:
                Keyboard.UP = false;
                break;
            case KeyEvent.VK_DOWN:
                Keyboard.DOWN = false;
                break;
            case KeyEvent.VK_RIGHT:
                Keyboard.RIGHT = false;
                break;
            case KeyEvent.VK_LEFT:
                Keyboard.LEFT = false;
                break;
            case KeyEvent.VK_SHIFT:
                Keyboard.LEFT_SHIFT = false;
                Keyboard.RIGHT_SHIFT = false;
                break;
        }
    }
}
