package org.bxwbb.ui.button;

import org.bxwbb.ui.label.BlockLabel;
import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockLabelButton extends AbstractBlockButton {

    private final BlockLabel blockLabel = new BlockLabel();
    private Color baseBackgroundColor;
    private Color baseOuterBorderColor;

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
        blockLabel.horizontalExtension();
        blockLabel.setPreferredHeight(16);
        baseBackgroundColor = getBackgroundColor();
        Color textBaseColor = blockLabel.getTextColor();
        baseOuterBorderColor = getBorderOuterColor();
        this.setLayout(new LinearLayout());
        this.addChild(blockLabel);
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
            this.getBlockLabel().setTextColor(ColorUtil.darker(textBaseColor));
            this.setDown(true);
        });
        this.addOnMouseReleased(e -> {
            if (this.isin(e.getX(), e.getY())) {
                this.onButtonClick(e);
                this.setDown(false);
            }
            this.setBackgroundColor(baseBackgroundColor);
            this.getBlockLabel().setTextColor(textBaseColor);
        });
    }

    public BlockLabel getBlockLabel() {
        return blockLabel;
    }
}
