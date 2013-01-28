package com.perceptron.TabletopRPG;

/**
 * Created with IntelliJ IDEA.
 * User: Eric
 * Date: 1/19/13
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
public class Vector2 implements Comparable<Vector2> {
    private double xComp;
    private double yComp;
    private double magnitude;
    private boolean recalcMag;

    public Vector2(){
        this(0, 0);
    }

    public Vector2(double x, double y){
        xComp = x;
        yComp = y;
        recalcMag = true;
    }

    public double magnitude(){
        // In my desire for efficiency, I try to avoid recomputing the square root, since that's expensive
        if(recalcMag){
            double xSquared = xComp * xComp;
            double ySquared = yComp * yComp;
            magnitude = Math.sqrt(xSquared + ySquared);
            recalcMag = false;
        }
        return magnitude;
    }

    public void scale(double scalor){
        xComp = xComp * scalor;
        yComp = yComp * scalor;
    }

    public void convertToUnitVector(){
        double mag = magnitude();
        xComp = xComp / mag;
        yComp = yComp / mag;
    }

    public double getX(){
        return xComp;
    }

    public double getY(){
        return yComp;
    }

    @Override
    public int compareTo(Vector2 o) {
        return (int) (magnitude() - o.magnitude());
    }
}
