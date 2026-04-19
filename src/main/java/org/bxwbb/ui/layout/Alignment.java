package org.bxwbb.ui.layout;

public enum Alignment {

    LEFT(-1, 0),
    LEFT_UP(-1, -1),
    CENTER(0, 0),
    RIGHT(1, 0),
    RIGHT_UP(1, -1),
    LEFT_DOWN(-1, 1),
    RIGHT_DOWN(1, 1),
    DOWN(0, 1),
    UP(0, -1);

    public final int x;
    public final int y;

    Alignment(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
