package org.bxwbb.ui.button;

import org.bxwbb.event.EventHandler;
import org.bxwbb.ui.pane.BlockPane;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBlockButton extends BlockPane {

    private boolean isDown = false;
    private boolean isHover = false;

    private Color downColor = null;

    private final List<EventHandler<MouseEvent>> onButtonClick = new ArrayList<>();

    public AbstractBlockButton() {
        super();
    }

    public AbstractBlockButton(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
        this.needUpdate.set(true);
    }

    public boolean isHover() {
        return isHover;
    }

    public void setHover(boolean hover) {
        isHover = hover;
        this.needUpdate.set(true);
    }

    public List<EventHandler<MouseEvent>> getOnButtonClick() {
        return onButtonClick;
    }

    public void addOnButtonClick(EventHandler<MouseEvent> handler) {
        this.onButtonClick.add(handler);
    }

    protected void onButtonClick(MouseEvent event) {
        if (this.isin(event.getX(), event.getY())) {
            for (EventHandler<MouseEvent> handler : this.onButtonClick) {
                handler.invoke(event);
            }
        }
    }

    public Color getDownColor() {
        return downColor;
    }

    public void setDownColor(Color downColor) {
        this.downColor = downColor;
    }

    public Color getDrawColor() {
        return isDown() ? getDownColor() == null ? getBackgroundColor() : getDownColor() : getBackgroundColor();
    }
}
