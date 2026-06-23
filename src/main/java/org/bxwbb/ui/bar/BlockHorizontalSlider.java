package org.bxwbb.ui.bar;

import org.bxwbb.animation.EaseType;
import org.bxwbb.animation.ValueAnimation;
import org.bxwbb.event.EventHandler;
import org.bxwbb.ui.button.BlockButton;
import org.bxwbb.ui.layout.ExpandLayout;
import org.bxwbb.ui.pane.BlockPane;

import java.awt.*;

public class BlockHorizontalSlider extends AbstractBlockSlider {

    private final BlockPane trackPane = new BlockPane();
    private final BlockButton thumbPane = new BlockButton();

    private int thumbDelta;
    private long lastUpdateTime;
    private boolean autoThumbLong = true;
    protected boolean isAnimation = false;

    public BlockHorizontalSlider() {
        super();
        init();
    }

    public BlockHorizontalSlider(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        this.setUI(new BlockHorizontalSliderUI());
        this.setLayout(new ExpandLayout());
        this.setPadding(0);

        trackPane.setPreferredHeight(Math.max(1, this.getContentHeight() - 4));
        trackPane.horizontalExtension();
        trackPane.setBorderInnerWidth(0);
        trackPane.setBorderOuterWidth(0);
        trackPane.setBackgroundColor(new Color(87, 87, 89));
        this.addChild(trackPane);

        thumbPane.verticalExtension();
        thumbPane.setPreferredWidth(25);
        this.addChild(thumbPane);

        thumbPane.addOnMousePressed(mouseEvent -> {
            thumbDelta = thumbPane.getLayoutX() - mouseEvent.getX();
            lastUpdateTime = System.currentTimeMillis();
        });

        thumbPane.addOnMouseDragged(mouseEvent -> {
            int trackW = trackPane.getPreferredWidth();
            int thumbW = thumbPane.getPreferredWidth();
            int maxThumbX = trackW - thumbW;

            int targetX = mouseEvent.getX() + thumbDelta;
            targetX = Math.max(0, Math.min(targetX, maxThumbX));

            thumbPane.setLayoutX(targetX);
            lastUpdateTime = System.currentTimeMillis();
            syncProgress();
        });

        trackPane.addOnMouseReleased(event -> {
            int clickX = event.getX();
            if (getThumbPane().isin(clickX, getThumbPane().getAbsoluteY())) return;

            int trackW = trackPane.getPreferredWidth();
            int thumbW = thumbPane.getPreferredWidth();
            int maxThumbX = trackW - thumbW;

            int targetX = clickX - thumbW / 2 - getAbsoluteX();
            targetX = Math.max(0, Math.min(targetX, maxThumbX));

            isAnimation = true;
            ValueAnimation valueAnimation = getThumbPane().createValueAnimation(
                    150, 0, EaseType.EASE_OUT,
                    getThumbPane().getLayoutX(), targetX
            );
            valueAnimation.updateListener = (progress) -> {
                getThumbPane().setLayoutX(Math.round(progress));
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
        int trackW = trackPane.getPreferredWidth();
        int thumbW = thumbPane.getPreferredWidth();
        int maxX = trackW - thumbW;
        if (maxX <= 0) return;

        float percent = (float) thumbPane.getLayoutX() / maxX;
        float newVal = percent * getMaxValue();
        if (Math.abs(newVal - this.value) < 0.0001f) return;

        this.value = newVal;
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