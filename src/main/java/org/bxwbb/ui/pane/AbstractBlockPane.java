package org.bxwbb.ui.pane;

import org.bxwbb.theme.ColorTheme;
import org.bxwbb.ui.BaseUI;

import java.awt.*;

public abstract class AbstractBlockPane extends BaseUI {

    private Color backgroundColor = ColorTheme.COLOR_BACKGROUND;
    private Color borderOuterColor = ColorTheme.COLOR_BORDER_OUTER;

    private int borderOuterWidth = 2;
    private int borderInnerWidth = 2;

    public AbstractBlockPane() {
        super();
        this.setBorder(4);
    }

    public AbstractBlockPane(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        this.setBorder(4);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.needUpdate.set(true);
    }

    public int getBorderOuterWidth() {
        return borderOuterWidth;
    }

    public void setBorderOuterWidth(int borderOuterWidth) {
        this.borderOuterWidth = borderOuterWidth;
        this.setBorder(borderOuterWidth + borderInnerWidth);
        this.needUpdate.set(true);
    }

    public int getBorderInnerWidth() {
        return borderInnerWidth;
    }

    public void setBorderInnerWidth(int borderInnerWidth) {
        this.borderInnerWidth = borderInnerWidth;
        this.setBorder(borderOuterWidth + borderInnerWidth);
        this.needUpdate.set(true);
    }

    public Color getBorderOuterColor() {
        return borderOuterColor;
    }

    public void setBorderOuterColor(Color borderOuterColor) {
        this.borderOuterColor = borderOuterColor;
        this.needUpdate.set(true);
    }

}
