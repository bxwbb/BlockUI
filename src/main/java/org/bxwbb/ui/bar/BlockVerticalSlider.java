package org.bxwbb.ui.bar;

import org.bxwbb.animation.EaseType;
import org.bxwbb.animation.ValueAnimation;
import org.bxwbb.event.EventHandler;
import org.bxwbb.ui.button.BlockButton;
import org.bxwbb.ui.layout.ExpandLayout;
import org.bxwbb.ui.pane.BlockPane;

import java.awt.*;

public class BlockVerticalSlider extends AbstractBlockSlider {

    private final BlockPane trackPane = new BlockPane();
    private final BlockButton thumbPane = new BlockButton();

    private int thumbDelta;
    private long lastUpdateTime;
    private boolean autoThumbLong = true;
    protected boolean isAnimation = false;

    public BlockVerticalSlider() {
        super();
        init();
    }

    public BlockVerticalSlider(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        this.setUI(new BlockVerticalSliderUI());
        this.setLayout(new ExpandLayout());
        this.setPadding(0);

        trackPane.setPreferredWidth(Math.max(1, this.getContentWidth() - 4));
        trackPane.verticalExtension();
        trackPane.setBorderInnerWidth(0);
        trackPane.setBorderOuterWidth(0);
        trackPane.setBackgroundColor(new Color(87, 87, 89));
        this.addChild(trackPane);

        thumbPane.horizontalExtension();
        thumbPane.setPreferredHeight(25);
        this.addChild(thumbPane);

        thumbPane.addOnMousePressed(mouseEvent -> {
            thumbDelta = thumbPane.getLayoutY() - mouseEvent.getY();
            lastUpdateTime = System.currentTimeMillis();
        });

        thumbPane.addOnMouseDragged(mouseEvent -> {
            int trackH = trackPane.getPreferredHeight();
            int thumbH = thumbPane.getPreferredHeight();
            int maxThumbY = trackH - thumbH;

            int targetY = mouseEvent.getY() + thumbDelta;
            targetY = Math.max(0, Math.min(targetY, maxThumbY));

            thumbPane.setLayoutY(targetY);
            lastUpdateTime = System.currentTimeMillis();

            syncProgress();
        });

        trackPane.addOnMouseReleased(event -> {
            int clickY = event.getY();
            if (getThumbPane().isin(getThumbPane().getAbsoluteX(), clickY)) return;
            int trackH = trackPane.getPreferredHeight();
            int thumbH = thumbPane.getPreferredHeight();
            int maxThumbY = trackH - thumbH;

            int targetY = clickY - thumbH / 2 - getAbsoluteY();
            targetY = Math.max(0, Math.min(targetY, maxThumbY));
            isAnimation = true;
            ValueAnimation valueAnimation = getThumbPane().createValueAnimation(150, 0, EaseType.EASE_OUT, getThumbPane().getLayoutY(), targetY);
            valueAnimation.updateListener = (progress) -> {
                getThumbPane().setLayoutY(Math.round(progress));
                syncProgress();
            };
            valueAnimation.endListener = (progress) -> {
                isAnimation = false;
                syncProgress();
            };
            valueAnimation.start();
        });
    }

    private void syncProgress() {
        int trackH = trackPane.getPreferredHeight();
        int thumbH = thumbPane.getPreferredHeight();
        int maxY = trackH - thumbH;
        if (maxY <= 0) return;

        float percent = (float) thumbPane.getLayoutY() / maxY;
        if (percent * getMaxValue() == value) return;
        this.value = percent * getMaxValue();
        for (EventHandler<Float> floatEventHandler : this.getUpdateValue()) {
            floatEventHandler.invoke(this.value);
        }
    }

    public BlockPane getTrackPane() {
        return trackPane;
    }

    public BlockButton getThumbPane() {
        return thumbPane;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public boolean isAutoThumbLong() {
        return autoThumbLong;
    }

    public void setAutoThumbLong(boolean autoThumbLong) {
        this.autoThumbLong = autoThumbLong;
        this.needUpdate.set(true);
    }
}