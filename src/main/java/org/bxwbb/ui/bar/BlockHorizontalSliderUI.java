package org.bxwbb.ui.bar;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;

import java.awt.*;

public class BlockHorizontalSliderUI implements UI {

    @Override
    public void update(BaseUI component) {
        BlockHorizontalSlider slider = (BlockHorizontalSlider) component;

        slider.getTrackPane().setPreferredHeight(Math.min(Math.max(1, slider.getContentHeight() - 4), 8));
        slider.getTrackPane().setPreferredWidth(slider.getContentWidth());
        slider.getTrackPane().horizontalExtension();
        slider.getTrackPane().setBorderInnerWidth(0);
        slider.getTrackPane().setBorderOuterWidth(0);
        slider.getTrackPane().setBackgroundColor(new Color(87, 87, 89));
        slider.getTrackPane().setLayoutY(slider.getContentHeight() / 2 - slider.getTrackPane().getPreferredHeight() / 2);

        if (slider.isAutoThumbLong()) {
            slider.thumbLong = Math.min(0.9f, Math.max(
                    (float) 15 / slider.getTrackPane().getPreferredWidth(),
                    AbstractBlockSlider.THUMB_LONG / slider.getMaxValue()
            ));
        }
        slider.getThumbPane().setPreferredWidth(Math.round(
                slider.getTrackPane().getPreferredWidth() * slider.getThumbLong()
        ));

        if (!slider.isAnimation) {
            int trackW = slider.getTrackPane().getPreferredWidth();
            int thumbW = slider.getThumbPane().getPreferredWidth();
            float ratio = slider.getValue() / slider.getMaxValue();
            slider.getThumbPane().setLayoutX(Math.round((trackW - thumbW) * ratio));
        }
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
    }
}