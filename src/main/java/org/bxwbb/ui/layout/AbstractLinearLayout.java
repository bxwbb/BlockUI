package org.bxwbb.ui.layout;

public abstract class AbstractLinearLayout extends Layout {


    private int spacingX = 0;
    private int spacingY = 0;

    public int getSpacingX() {
        return spacingX;
    }

    public AbstractLinearLayout setSpacingX(int spacingX) {
        this.spacingX = spacingX;
        return this;
    }

    public int getSpacingY() {
        return spacingY;
    }

    public AbstractLinearLayout setSpacingY(int spacingY) {
        this.spacingY = spacingY;
        return this;
    }

}
