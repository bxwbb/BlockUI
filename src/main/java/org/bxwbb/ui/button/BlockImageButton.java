package org.bxwbb.ui.button;

import org.bxwbb.ui.image.BlockImage;
import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.util.ColorUtil;

import java.awt.*;

public class BlockImageButton extends AbstractBlockButton {

    private final BlockImage blockImage = new BlockImage();
    private Color baseBackgroundColor;
    private Color baseOuterBorderColor;

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
        blockImage.horizontalExtension();
        blockImage.verticalExtension();
        blockImage.setMixColor(Color.LIGHT_GRAY);
        baseBackgroundColor = getBackgroundColor();
        Color imageColor = blockImage.getMixColor();
        baseOuterBorderColor = getBorderOuterColor();
        this.setLayout(new LinearLayout());
        this.addChild(blockImage);
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
            this.getBlockImage().setMixColor(ColorUtil.darker(imageColor));
            this.setDown(true);
        });
        this.addOnMouseReleased(e -> {
            if (this.isin(e.getX(), e.getY())) {
                this.onButtonClick(e);
                this.setDown(false);
            }
            this.setBackgroundColor(baseBackgroundColor);
            this.getBlockImage().setMixColor(imageColor);
        });
    }

    public BlockImage getBlockImage() {
        return blockImage;
    }
}
