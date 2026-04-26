package org.bxwbb.ui.button;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;
import org.bxwbb.ui.pane.BlockPaneUI;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockToggleButtonUI extends BlockPaneUI implements UI {

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockToggleButton blockButton = (BlockToggleButton) component;
        blockButton.setBorderOuterColor(blockButton.isHover() ? Color.WHITE : blockButton.baseOuterBorderColor);
        blockButton.setBackgroundColor(blockButton.getDownColor() == null ? blockButton.isDown() ? ColorUtil.brighter(blockButton.baseBackgroundColor, 2) : blockButton.baseBackgroundColor : !blockButton.isDown() ? blockButton.baseBackgroundColor : blockButton.getDownColor());
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        super.render(g2d, component);
    }
}
