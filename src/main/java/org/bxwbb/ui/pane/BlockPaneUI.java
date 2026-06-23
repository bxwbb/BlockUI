package org.bxwbb.ui.pane;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockPaneUI implements UI {
    @Override
    public void update(BaseUI component) {
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        BlockPane blockPane = (BlockPane) component;
        Color r = blockPane.getBackgroundColor();
        if (!blockPane.isEnabled()) blockPane.setBackgroundColor(ColorUtil.brighter(r));
        g2d.setColor(blockPane.getBorderOuterColor());
        g2d.fillRect(blockPane.getAbsoluteX(), blockPane.getAbsoluteY(), blockPane.getWidth(), blockPane.getHeight());
        g2d.setColor(ColorUtil.darker(blockPane.getBackgroundColor()));
        g2d.fillRect(blockPane.getAbsoluteX() + blockPane.getBorderOuterWidth(), blockPane.getAbsoluteY() + blockPane.getHeight() - blockPane.getBorderOuterWidth() - blockPane.getShadowInnerHeight(), blockPane.getWidth() - blockPane.getBorderOuterWidth() * 2, blockPane.getShadowInnerHeight());
        g2d.setColor(ColorUtil.brighter(ColorUtil.brighter(ColorUtil.brighter(blockPane.getBackgroundColor()))));
        g2d.fillRect(blockPane.getAbsoluteX() + blockPane.getBorderOuterWidth(), blockPane.getAbsoluteY() + blockPane.getBorderOuterWidth(), blockPane.getWidth() - blockPane.getBorderOuterWidth() * 2, blockPane.getHeight() - blockPane.getBorderOuterWidth() * 2 - blockPane.getShadowInnerHeight());
        g2d.setColor(ColorUtil.brighter(ColorUtil.brighter(blockPane.getBackgroundColor())));
        g2d.fillRect(blockPane.getAbsoluteX() + blockPane.getBorderOuterWidth(), blockPane.getAbsoluteY() + blockPane.getBorderOuterWidth(), blockPane.getWidth() - blockPane.getBorderOuterWidth() * 2 - blockPane.getBorderInnerWidth(), blockPane.getHeight() - blockPane.getBorderOuterWidth() * 2 - blockPane.getBorderInnerWidth() - blockPane.getShadowInnerHeight());
        g2d.setColor(ColorUtil.brighter(blockPane.getBackgroundColor()));
        g2d.fillRect(blockPane.getAbsoluteX() + blockPane.getBorderOuterWidth() + blockPane.getBorderInnerWidth(), blockPane.getAbsoluteY() + blockPane.getBorderOuterWidth() + blockPane.getBorderInnerWidth(), blockPane.getWidth() - blockPane.getBorderOuterWidth() * 2 - blockPane.getBorderInnerWidth(), blockPane.getHeight() - blockPane.getBorderOuterWidth() * 2 - blockPane.getBorderInnerWidth() - blockPane.getShadowInnerHeight());
        g2d.setColor(blockPane.getBackgroundColor());
        g2d.fillRect(blockPane.getAbsoluteX() + blockPane.getBorderOuterWidth() + blockPane.getBorderInnerWidth(), blockPane.getAbsoluteY() + blockPane.getBorderOuterWidth() + blockPane.getBorderInnerWidth(), blockPane.getWidth() - blockPane.getBorderOuterWidth() * 2 - blockPane.getBorderInnerWidth() * 2, blockPane.getHeight() - blockPane.getBorderOuterWidth() * 2 - blockPane.getBorderInnerWidth() * 2 - blockPane.getShadowInnerHeight());
        blockPane.setBackgroundColor(r);
    }
}
