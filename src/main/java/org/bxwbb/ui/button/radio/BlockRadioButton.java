package org.bxwbb.ui.button.radio;

import org.bxwbb.ui.button.BlockToggleButton;

public class BlockRadioButton extends BlockToggleButton {

    public BlockRadioButton() {
        super();
        init();
    }

    public BlockRadioButton(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        this.setBorderInnerWidth(0);
        this.setSetChangeDown(false);
    }
}
