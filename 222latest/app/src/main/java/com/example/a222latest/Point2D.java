package com.example.a222latest;

/**
 * Point2D class that keeps point that has x and y
 */
public class Point2D
{
    /**
     * x- value
     */
    private int x;

    /**
     * y- value
     */
    private int y;

    /**
     * constructor
     * @param x, initial x value
     * @param y, initial y value
     */
    Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * constructor
     * @param x, initial x value
     * to be assigned 0 to y
     */
    Point2D(int x) {
        this.x = x;
        this.y = 0;
    }

    /**
     * constructor
     * to be assigned 0 to x and y
     */
    Point2D() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * setter function
     * @param x, set x- value
     */
    void setScaleX(int x) { this.x = x; }

    /**
     * setter function
     * @param y, set y- value
     */
    void setScaleY(int y) { this.y = y; }

    /**
     * getter function
     * @param x, g x- value
     */
    int getScaleX() { return this.x; }

    /**
     * getter function
     * @param y, g y- value
     */
    int getScaleY() { return this.y; }
}

