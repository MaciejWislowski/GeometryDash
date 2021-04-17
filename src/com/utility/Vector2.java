package com.utility;

public class Vector2 {
    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public  Vector2() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public  Vector2 copy() {
        return new Vector2(this.x,this.y);
    }
}
