package org.bxwbb.ui.bar;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;

import java.awt.*;

public class BlockVerticalSliderUI implements UI {

    @Override
    public void update(BaseUI component) {
        BlockVerticalSlider blockVerticalSlider = (BlockVerticalSlider) component;
        blockVerticalSlider.getTrackPane().setPreferredWidth(Math.min(Math.max(1, blockVerticalSlider.getContentWidth() - 4), 8));
        blockVerticalSlider.getTrackPane().setPreferredHeight(blockVerticalSlider.getContentHeight());
        blockVerticalSlider.getTrackPane().verticalExtension();
        blockVerticalSlider.getTrackPane().setBorderInnerWidth(0);
        blockVerticalSlider.getTrackPane().setBorderOuterWidth(0);
        blockVerticalSlider.getTrackPane().setBackgroundColor(new Color(87, 87, 89));
        blockVerticalSlider.getTrackPane().setLayoutX(blockVerticalSlider.getContentWidth() / 2 - blockVerticalSlider.getTrackPane().getPreferredWidth() / 2);
        if (blockVerticalSlider.isAutoThumbLong()) blockVerticalSlider.thumbLong = Math.min(0.9f, Math.max((float) 15 / blockVerticalSlider.getTrackPane().getPreferredHeight(), AbstractBlockSlider.THUMB_LONG / blockVerticalSlider.getMaxValue()));
        blockVerticalSlider.getThumbPane().setPreferredHeight(Math.round(blockVerticalSlider.getTrackPane().getPreferredHeight() * blockVerticalSlider.getThumbLong()));
        if (!blockVerticalSlider.isAnimation) blockVerticalSlider.getThumbPane().setLayoutY(Math.round((blockVerticalSlider.getTrackPane().getPreferredHeight() - blockVerticalSlider.getThumbPane().getPreferredHeight()) * (blockVerticalSlider.getValue() / blockVerticalSlider.getMaxValue())));
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {

    }
}
