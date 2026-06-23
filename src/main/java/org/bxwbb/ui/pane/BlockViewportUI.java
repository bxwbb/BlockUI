package org.bxwbb.ui.pane;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;

import java.awt.*;

public class BlockViewportUI extends BlockEmptyPaneUI implements UI {

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockViewport blockViewport = (BlockViewport) component;
        blockViewport.getExtent().setLayoutX(blockViewport.getOffsetX());
        blockViewport.getExtent().setLayoutY(blockViewport.getOffsetY());
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
    }
}
