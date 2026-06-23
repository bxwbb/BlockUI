package org.bxwbb.ui.button;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockLabelButtonUI extends BlockButtonUI implements UI {

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockLabelButton blockButton = (BlockLabelButton) component;
        blockButton.getBlockLabel().setTextColor(blockButton.isDown() ? ColorUtil.darker(blockButton.baseTextColor) : blockButton.baseTextColor);
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        super.render(g2d, component);
    }
}
