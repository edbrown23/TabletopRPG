package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.DMStates;
import com.perceptron.TabletopRPG.Keyboard;
import com.perceptron.TabletopRPG.Mouse;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 1/29/13
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
public class NonCombatDungeonMaster extends DungeonMaster {
    @Override
    public DMStates updateStateMachine(int keyCode, int mouseCode) {
        switch(currentState){
            case Idle:

                break;
            case TileInfo:

                break;

            case PlayerInfo:

                break;
            case EnemyInfo:

                break;
            case PreMove:

                break;
            case Moving:

                break;
            case PostMove:

                break;
            case PreCombat:

                break;
            case Error:

                break;
        }
        return DMStates.Idle;
    }

    private DMStates processIdleState(){
        return DMStates.Idle;
    }
}
