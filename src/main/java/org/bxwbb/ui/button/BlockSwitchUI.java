package org.bxwbb.ui.button;

import org.bxwbb.animation.EaseType;
import org.bxwbb.animation.ValueAnimation;
import org.bxwbb.ui.BaseUI;

import java.awt.*;

public class BlockSwitchUI extends BlockToggleButtonUI {

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockSwitch blockSwitch = (BlockSwitch) component;
        blockSwitch.getSwitchPane().setBorderInnerWidth(blockSwitch.getBorderInnerWidth());
        blockSwitch.getSwitchPane().setBorderOuterWidth(blockSwitch.getBorderOuterWidth());
        if (blockSwitch.animationChanged) blockSwitch.getSwitchPane().setLayoutX(-(blockSwitch.getBorderLeft() + blockSwitch.getPaddingLeft()) + (blockSwitch.isDown() ? (int) (0.5 * blockSwitch.getWidth()) : 0));
        blockSwitch.getSwitchPane().setLayoutY(-2 * (blockSwitch.getBorderTop() + blockSwitch.getPaddingTop()));
        blockSwitch.getSwitchPane().setPreferredWidth(blockSwitch.getPreferredHeight());
        blockSwitch.getSwitchPane().setPreferredHeight(blockSwitch.getPreferredHeight() + (blockSwitch.getBorderTop() + blockSwitch.getPaddingTop()));
        blockSwitch.getSwitchPane().setShadowInnerHeight((blockSwitch.getBorderTop() + blockSwitch.getPaddingTop()));
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        super.render(g2d, component);
        BlockSwitch blockSwitch = (BlockSwitch) component;
    }
}
