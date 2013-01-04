package com.perceptron.TabletopRPG;

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
 * Date: 1/3/13
 */
public class IntegerPoint2D {
    public int x;
    public int y;

    public IntegerPoint2D(){
        this(0, 0);
    }

    public IntegerPoint2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double euclideanDistance(IntegerPoint2D otherPoint){
        double xSquared = (x - otherPoint.x) * (x - otherPoint.x);
        double ySquared = (y - otherPoint.y) * (y - otherPoint.y);
        return Math.sqrt(xSquared + ySquared);
    }

    public double euclideanDistanceSquared(IntegerPoint2D otherPoint){
        double xSquared = (x - otherPoint.x) * (x - otherPoint.x);
        double ySquared = (y - otherPoint.y) * (y - otherPoint.y);
        return xSquared + ySquared;
    }

    public int manhattanDistance(IntegerPoint2D otherPoint){
        int xDist = Math.abs(x - otherPoint.x);
        int yDist = Math.abs(y - otherPoint.y);
        return xDist + yDist;
    }

    public int hashCode(){
        // TODO Ascertain how secure this is for a hashing function
        String hashString = Integer.toString(x) + Integer.toString(y);
        return hashString.hashCode();
    }
}
