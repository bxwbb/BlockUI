package org.bxwbb.ui.button;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;

import java.awt.*;

public class BlockCheckBoxUI extends BlockToggleButtonUI implements UI {

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockCheckBox blockCheckBox = (BlockCheckBox) component;
        blockCheckBox.getCheckImage().setVisible(blockCheckBox.isDown());
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        super.render(g2d, component);
    }
}
