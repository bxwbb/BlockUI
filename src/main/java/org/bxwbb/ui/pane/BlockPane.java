package org.bxwbb.ui.pane;

import java.awt.*;

public class BlockPane extends AbstractBlockPane {

    private int shadowInnerHeight = 0;

    public BlockPane() {
        super();
        setUI(new BlockPaneUI());
    }

    public BlockPane(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        setUI(new BlockPaneUI());
    }

    public int getShadowInnerHeight() {
        return shadowInnerHeight;
    }

    @Override
    public void setBorderInnerWidth(int borderInnerWidth) {
        super.setBorderInnerWidth(borderInnerWidth);
        this.setBorderBottom(this.getBorderInnerWidth() + this.getBorderInnerWidth() + shadowInnerHeight);
    }

    @Override
    public void setBorderOuterColor(Color borderOuterColor) {
        super.setBorderOuterColor(borderOuterColor);
        this.setBorderBottom(this.getBorderInnerWidth() + this.getBorderInnerWidth() + shadowInnerHeight);
    }

    public void setShadowInnerHeight(int shadowInnerHeight) {
        this.shadowInnerHeight = shadowInnerHeight;
        this.setBorder(this.getBorderInnerWidth() + this.getBorderInnerWidth());
        this.setBorderBottom(this.getBorderInnerWidth() + this.getBorderInnerWidth() + shadowInnerHeight);
        this.needUpdate.set(true);
    }
}
