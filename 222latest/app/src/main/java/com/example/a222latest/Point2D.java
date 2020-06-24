package com.example.a222latest;

public class Point2D
{
    private int x;
    private int y;

    Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point2D(int x) {
        this.x = x;
        this.y = 0;
    }

    Point2D() {
        this.x = 0;
        this.y = 0;
    }

    void setScaleX(int x) { this.x = x; }
    void setScaleY(int y) { this.y = y; }
    int getScaleX() { return this.x; }
    int getScaleY() { return this.y; }
}
