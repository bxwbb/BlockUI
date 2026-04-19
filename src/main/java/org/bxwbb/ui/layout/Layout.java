package org.bxwbb.ui.layout;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.Node;

public abstract class Layout extends Node {

    private int paddingX = 0;
    private int paddingY = 0;
    private Alignment alignment = Alignment.CENTER;
    private Orientation orientation = Orientation.HORIZONTAL;
    private float weight = 1.0f;

    public Layout() {
        super();
    }

    public abstract void layout(BaseUI ui);

    public int getPaddingX() {
        return paddingX;
    }

    public Layout setPaddingX(int paddingX) {
        this.paddingX = paddingX;
        return this;
    }

    public int getPaddingY() {
        return paddingY;
    }

    public Layout setPaddingY(int paddingY) {
        this.paddingY = paddingY;
        return this;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public Layout setAlignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Layout setOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public float getWeight() {
        return weight;
    }

    public Layout setWeight(float weight) {
        this.weight = weight;
        return this;
    }
}
