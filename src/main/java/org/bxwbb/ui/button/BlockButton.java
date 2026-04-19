package org.bxwbb.ui.button;

import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockButton extends AbstractBlockButton {

    private Color baseBackgroundColor;
    private Color baseOuterBorderColor;

    public BlockButton() {
        super();
        init();
    }

    public BlockButton(int layoutX, int layoutY, int width, int height) {
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
        this.addOnMousePressed(e -> {
            this.setBackgroundColor(ColorUtil.darker(baseBackgroundColor));
            this.setDown(true);
        });
        this.addOnMouseReleased(e -> {
            if (this.isin(e.getX(), e.getY())) {
                this.onButtonClick(e);
                this.setDown(false);
            }
            this.setBackgroundColor(baseBackgroundColor);
        });
    }
}
