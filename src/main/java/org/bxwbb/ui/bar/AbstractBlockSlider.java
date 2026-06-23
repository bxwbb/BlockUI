package org.bxwbb.ui.bar;

import org.bxwbb.event.EventHandler;
import org.bxwbb.ui.BaseUI;

import java.util.ArrayList;
import java.util.List;

public class AbstractBlockSlider extends BaseUI {

    public static final float THUMB_LONG = 80;

    protected float maxValue = 100;
    protected float value = 0;
    protected float thumbLong = 0.3f;
    protected boolean hover = false;
    protected boolean pressed = false;
    protected boolean dragging = false;

    private final List<EventHandler<Float>> updateValue = new ArrayList<>();

    public AbstractBlockSlider() {
        super();
    }

    public AbstractBlockSlider(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        this.needUpdate.set(true);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
        this.needUpdate.set(true);
    }

    public boolean isHover() {
        return hover;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isDragging() {
        return dragging;
    }

    public float getThumbLong() {
        return thumbLong;
    }

    public void setThumbLong(float thumbLong) {
        this.thumbLong = thumbLong;
        this.needUpdate.set(true);
    }

    public List<EventHandler<Float>> getUpdateValue() {
        return updateValue;
    }

    public void addUpdateValue(EventHandler<Float> updateValue) {
        this.updateValue.add(updateValue);
    }
}
