package org.bxwbb.ui.pane;

import org.bxwbb.ui.BaseUI;

public class BlockViewport extends BlockEmptyPane {

    private BaseUI extent;
    private int offsetX;
    private int offsetY;

    public BlockViewport(BaseUI extent) {
        super();
        this.extent = extent;
        init();
    }

    public BlockViewport(BaseUI extent, int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        this.extent = extent;
        init();
    }

    private void init() {
        setUI(new BlockViewportUI());
        this.addChild(getExtent());
    }

    public BaseUI getExtent() {
        return extent;
    }

    public void setExtent(BaseUI extent) {
        this.extent = extent;
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
}
