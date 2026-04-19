package org.bxwbb.ui.image;

import org.bxwbb.ui.BaseUI;

import java.awt.*;

public abstract class AbstractBlockImage extends BaseUI {

    private Image image;

    private ScaleMode scaleMode = ScaleMode.SCALE_STRETCH;
    private int offsetX = 0;
    private int offsetY = 0;
    private float alpha = 1.0f;

    private Color mixColor = Color.WHITE;

    public AbstractBlockImage() {
        super();
    }

    public AbstractBlockImage(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
    }

    public AbstractBlockImage(Image image) {
        this();
        this.image = image;
    }

    public AbstractBlockImage(Image image, int layoutX, int layoutY, int width, int height) {
        this(layoutX, layoutY, width, height);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        this.needUpdate.set(true);
    }

    public ScaleMode getScaleMode() {
        return scaleMode;
    }

    public void setScaleMode(ScaleMode scaleMode) {
        this.scaleMode = scaleMode;
        this.needUpdate.set(true);
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
        this.needUpdate.set(true);
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
        this.needUpdate.set(true);
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        this.needUpdate.set(true);
    }

    public Color getMixColor() {
        return mixColor;
    }

    public void setMixColor(Color mixColor) {
        this.mixColor = mixColor;
        this.needUpdate.set(true);
    }
}
