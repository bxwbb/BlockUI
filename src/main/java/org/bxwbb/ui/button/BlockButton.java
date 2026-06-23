package org.bxwbb.ui.button;

import org.bxwbb.ui.layout.LinearLayout;

import java.awt.*;

public class BlockButton extends AbstractBlockButton {

    protected Color baseBackgroundColor;
    protected Color baseOuterBorderColor;

    public BlockButton() {
        super();
        init();
    }

    public BlockButton(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        this.setUI(new BlockButtonUI());
        baseBackgroundColor = getBackgroundColor();
        baseOuterBorderColor = getBorderOuterColor();
        this.setLayout(new LinearLayout());
        this.addOnMouseEntered(e -> {
            if (this.isDragging()) this.setDown(true);
            this.setHover(true);
        });
        this.addOnMouseExited(e -> {
            if (this.isDragging()) this.setDown(false);
            this.setHover(false);
        });
        this.addOnMousePressed(e -> this.setDown(true));
        this.addOnMouseReleased(e -> {
            if (this.isin(e.getX(), e.getY())) {
                this.onButtonClick(e);
                this.setDown(false);
            }
        });
    }
}
