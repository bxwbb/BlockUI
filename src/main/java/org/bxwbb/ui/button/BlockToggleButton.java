package org.bxwbb.ui.button;

import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockToggleButton extends AbstractBlockButton {

    protected Color baseBackgroundColor;
    protected Color baseOuterBorderColor;

    private boolean setChangeDown = true;

    public BlockToggleButton() {
        super();
        init();
    }

    public BlockToggleButton(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        setUI(new BlockToggleButtonUI());
        horizontalExtension();
        verticalExtension();
        baseBackgroundColor = getBackgroundColor();
        baseOuterBorderColor = getBorderOuterColor();
        this.setLayout(new LinearLayout());
        this.addOnMouseEntered(e -> this.setHover(true));
        this.addOnMouseExited(e -> this.setHover(false));
        this.addOnMousePressed(e -> this.setBackgroundColor(ColorUtil.darker(baseBackgroundColor)));
        this.addOnMouseReleased(e -> {
            if (this.isin(e.getX(), e.getY())) {
                this.onButtonClick(e);
                if (this.isSetChangeDown()) this.setDown(!this.isDown());
            } else {
                this.setHover(false);
            }
        });
    }

    public boolean isSetChangeDown() {
        return setChangeDown;
    }

    public void setSetChangeDown(boolean setChangeDown) {
        this.setChangeDown = setChangeDown;
    }
}
