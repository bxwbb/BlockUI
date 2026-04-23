package org.bxwbb.ui.button;

import org.bxwbb.ui.label.BlockLabel;
import org.bxwbb.ui.layout.LinearLayout;

import java.awt.*;

public class BlockLabelButton extends AbstractBlockButton {

    private final BlockLabel blockLabel = new BlockLabel();
    protected Color baseBackgroundColor;
    protected Color baseOuterBorderColor;
    protected Color baseTextColor;

    public BlockLabelButton() {
        super();
        init();
    }

    public BlockLabelButton(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    public BlockLabelButton(String text) {
        this();
        getBlockLabel().setText(text);
    }

    public BlockLabelButton(String text, int layoutX, int layoutY, int width, int height) {
        this(layoutX, layoutY, width, height);
        getBlockLabel().setText(text);
    }

    private void init() {
        setUI(new BlockLabelButtonUI());
        blockLabel.horizontalExtension();
        blockLabel.setPreferredHeight(16);
        blockLabel.setEnabled(false);
        baseBackgroundColor = getBackgroundColor();
        baseTextColor = blockLabel.getTextColor();
        baseOuterBorderColor = getBorderOuterColor();
        this.setLayout(new LinearLayout());
        this.addChild(blockLabel);
        this.addOnMouseEntered(e -> this.setHover(true));
        this.addOnMouseExited(e -> this.setHover(false));
        this.addOnMousePressed(e -> this.setDown(true));
        this.addOnMouseReleased(e -> {
            if (this.isin(e.getX(), e.getY())) {
                this.onButtonClick(e);
                this.setDown(false);
            }
        });
    }

    public BlockLabel getBlockLabel() {
        return blockLabel;
    }
}
