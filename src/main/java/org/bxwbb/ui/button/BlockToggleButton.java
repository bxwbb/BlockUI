package org.bxwbb.ui.button;

import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockToggleButton extends AbstractBlockButton {

    private Color baseBackgroundColor;
    private Color baseOuterBorderColor;

    public BlockToggleButton() {
        super();
        init();
    }

    public BlockToggleButton(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        horizontalExtension();
        verticalExtension();
        baseBackgroundColor = getBackgroundColor();
        baseOuterBorderColor = getBorderOuterColor();
        this.setLayout(new LinearLayout());
        this.addOnMouseEntered(e -> {
            this.setBorderOuterColor(Color.WHITE);
            this.setHover(true);
        });
        this.addOnMouseExited(e -> {
            this.setBorderOuterColor(baseOuterBorderColor);
            this.setHover(false);
        });
        this.addOnMousePressed(e -> this.setBackgroundColor(ColorUtil.darker(baseBackgroundColor)));
        this.addOnMouseReleased(e -> {
            if (this.isin(e.getX(), e.getY())) {
                this.onButtonClick(e);
            } else {
                this.setBorderOuterColor(baseOuterBorderColor);
                this.setHover(false);
            }
            this.setDown(!this.isDown());
            this.setBackgroundColor(this.isDown() ? ColorUtil.brighter(baseBackgroundColor, 2) : baseBackgroundColor);
        });
    }
}
