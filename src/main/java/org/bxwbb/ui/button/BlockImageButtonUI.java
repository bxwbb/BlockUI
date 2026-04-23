package org.bxwbb.ui.button;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;
import org.bxwbb.ui.pane.BlockPaneUI;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockImageButtonUI extends BlockPaneUI implements UI {

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockImageButton blockButton = (BlockImageButton) component;
        blockButton.setBorderOuterColor(blockButton.isHover() ? Color.WHITE : blockButton.baseOuterBorderColor);
        blockButton.setBackgroundColor(blockButton.isDown() ? ColorUtil.darker(blockButton.baseBackgroundColor) : blockButton.baseBackgroundColor);
        blockButton.getBlockImage().setMixColor(blockButton.isDown() ? ColorUtil.darker(blockButton.baseImageColor) : blockButton.baseImageColor);
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        super.render(g2d, component);
    }
}
