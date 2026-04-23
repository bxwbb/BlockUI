package org.bxwbb.ui.button;

import org.bxwbb.ui.image.BlockImage;
import org.bxwbb.ui.layout.LinearLayout;

import java.awt.*;

public class BlockImageButton extends AbstractBlockButton {

    private final BlockImage blockImage = new BlockImage();
    protected Color baseBackgroundColor;
    protected Color baseOuterBorderColor;
    protected Color baseImageColor;

    public BlockImageButton() {
        super();
        init();
    }

    public BlockImageButton(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    public BlockImageButton(Image image) {
        this();
        getBlockImage().setImage(image);
    }

    public BlockImageButton(Image image, int layoutX, int layoutY, int width, int height) {
        this(layoutX, layoutY, width, height);
        getBlockImage().setImage(image);
    }

    private void init() {
        this.setUI(new BlockImageButtonUI());
        blockImage.horizontalExtension();
        blockImage.verticalExtension();
        blockImage.setMixColor(Color.LIGHT_GRAY);
        blockImage.setEnabled(false);
        baseBackgroundColor = getBackgroundColor();
        baseImageColor = blockImage.getMixColor();
        baseOuterBorderColor = getBorderOuterColor();
        this.setLayout(new LinearLayout());
        this.addChild(blockImage);
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

    public BlockImage getBlockImage() {
        return blockImage;
    }
}
