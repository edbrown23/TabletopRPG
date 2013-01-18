package com.perceptron.TabletopRPG;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

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
public class Keyboard {
    private static HashMap<Integer, Boolean> keyFlags;
    private static Queue<Integer> typingEventQueue;
    private static boolean collectASCIIKeys;

    // Initialization
    static{
        keyFlags = new HashMap<Integer, Boolean>();
        typingEventQueue = new LinkedList<Integer>();
    }

    public static void startCollectingASCIIKeys(){
        if(!collectASCIIKeys){
            typingEventQueue.clear();
        }
        collectASCIIKeys = true;
    }

    public static void stopCollectingASCIIKeys(){
        collectASCIIKeys = false;
        typingEventQueue.clear();
    }

    public static void clearKey(int keyCode){
        keyFlags.put(keyCode, false);
    }

    public static void setKey(int keyCode){
        keyFlags.put(keyCode, true);
    }

    public static void enqueueKey(int keyCode){
        if(collectASCIIKeys){
            typingEventQueue.offer(keyCode);
        }
    }

    public static int dequeueKey(){
        if(!typingEventQueue.isEmpty()){
            return typingEventQueue.poll();
        }
        return -1;
    }

    public static boolean checkKey(int keyCode){
        if(keyFlags.containsKey(keyCode)){
            return keyFlags.get(keyCode);
        }else{
            return false;
        }
    }

    public static String convertCodeToChar(int keyCode){
        if(keyCode == -1){
            return "";
        }
        return KeyEvent.getKeyText(keyCode);
    }
}
