package com.perceptron.TabletopRPG.Controllers;

import com.perceptron.TabletopRPG.*;
import com.perceptron.TabletopRPG.Models.ActiveUnit;
import com.perceptron.TabletopRPG.Models.Cell;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
    public DMStates updateStateMachine(int keyCode, MouseState mouseCode) {
        // Just a reset for testing purposes
        if(keyCode == KeyEvent.VK_BACK_SPACE) currentState = DMStates.Idle;
        switch(currentState){
            case Idle:
                currentState = processIdleState(keyCode, mouseCode);
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

    private DMStates processIdleState(int keyCode, MouseState mouseCode){
        if(mouseCode != null && mouseCode.button == MouseEvent.BUTTON1 && mouseCode.down){
            IntegerPoint2D layerCoord = decodeClickToLayerCoords(mouseCode);
            selectedPlayer = checkForPlayer(layerCoord);
            selectedEnemy = checkForEnemy(layerCoord);
            if(selectedPlayer != null){
                return DMStates.PlayerInfo;
            }else if(selectedEnemy != null){
                return DMStates.EnemyInfo;
            }else{
                if(layerCoord.x != -1 && layerCoord.y != -1){
                    selectedCell = layer.getCell(layerCoord.x, layerCoord.y);
                }
                return DMStates.TileInfo;
            }
        }
        return DMStates.Idle;
    }

    private ActiveUnit checkForPlayer(IntegerPoint2D coords){
        ArrayList<ActiveUnit> players = layer.getPlayers();
        for(ActiveUnit player : players){
            if(player.getX() == coords.x && player.getY() == coords.y){
                return player;
            }
        }
        return null;
    }

    private ActiveUnit checkForEnemy(IntegerPoint2D coords){
        ArrayList<ActiveUnit> enemies = layer.getEnemies();
        for(ActiveUnit enemy : enemies){
            if(enemy.getX() == coords.x && enemy.getY() == coords.y){
                return enemy;
            }
        }
        return null;
    }
    
    private IntegerPoint2D decodeClickToLayerCoords(MouseState mouseCode){
        // These are the mouse's x and y in screen coordinates
        int x = mouseCode.x;
        int y = mouseCode.y;
        // Now we get layer coordinates
        int lx = convertXToLayerCoords(x);
        int ly = convertYToLayerCoords(y);
        return new IntegerPoint2D(lx, ly);
    }

    private int convertXToLayerCoords(int screenX){
        int layerX = (screenX + camera.getX()) / camera.getZoomLevel();
        //System.out.println(camera.getX() + " " + camera.getY());
        if(layerX < 0 || layerX >= layer.getWidth()){
            return -1;
        }else{
            return layerX;
        }
    }

    private int convertYToLayerCoords(int screenY){
        int layerY = (screenY + camera.getY()) / camera.getZoomLevel();
        if(layerY < 0 || layerY >= layer.getHeight()){
            return -1;
        }else{
            return layerY;
        }
    }


}
