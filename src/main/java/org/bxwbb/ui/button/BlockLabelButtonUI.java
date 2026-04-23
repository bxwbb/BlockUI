package org.bxwbb.ui.button;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;
import org.bxwbb.ui.pane.BlockPaneUI;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockLabelButtonUI extends BlockPaneUI implements UI {

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockLabelButton blockButton = (BlockLabelButton) component;
        blockButton.setBorderOuterColor(blockButton.isHover() ? Color.WHITE : blockButton.baseOuterBorderColor);
        blockButton.setBackgroundColor(blockButton.isDown() ? ColorUtil.darker(blockButton.baseBackgroundColor) : blockButton.baseBackgroundColor);
        blockButton.getBlockLabel().setTextColor(blockButton.isDown() ? ColorUtil.darker(blockButton.baseTextColor) : blockButton.baseTextColor);
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        super.render(g2d, component);
    }
}
