package org.bxwbb.ui.other;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.image.BlockImage;
import org.bxwbb.ui.image.ScaleMode;
import org.bxwbb.ui.label.BlockLabel;
import org.bxwbb.ui.layout.Alignment;
import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.ui.layout.Orientation;

import java.awt.*;

public class BlockLabelImage extends BaseUI {

    private BlockLabel blockLabel = new BlockLabel();
    private BlockImage blockImage = new BlockImage();

    public BlockLabelImage() {
        super();
        init();
    }

    public BlockLabelImage(String text) {
        super();
        this.getBlockLabel().setText(text);
        init();
    }

    public BlockLabelImage(String text, Image image) {
        super();
        this.getBlockLabel().setText(text);
        this.getBlockImage().setImage(image);
        init();
    }

    public BlockLabelImage(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    public BlockLabelImage(int layoutX, int layoutY, int width, int height, String text) {
        super(layoutX, layoutY, width, height);
        this.getBlockLabel().setText(text);
        init();
    }

    public BlockLabelImage(int layoutX, int layoutY, int width, int height, Image image) {
        super(layoutX, layoutY, width, height);
        this.getBlockImage().setImage(image);
        init();
    }

    public BlockLabelImage(int layoutX, int layoutY, int width, int height, Image image, String text) {
        super(layoutX, layoutY, width, height);
        this.getBlockImage().setImage(image);
        this.getBlockLabel().setText(text);
        init();
    }

    private void init() {
        this.blockImage.setScaleMode(ScaleMode.SCALE_FIT);
        this.blockImage.verticalExtension();
        this.blockLabel.horizontalExtension();
        this.blockLabel.setPreferredHeight(16);
        blockImage.setBounds(0, 0, 100, 100);
        this.setLayout(new LinearLayout(5, 0));
        moveTextToRight();
        this.addChild(blockImage);
        this.addChild(blockLabel);
    }

    public void moveTextToRight() {
        getLayout().setOrientation(Orientation.HORIZONTAL)
                .setAlignment(Alignment.LEFT);
    }

    public void moveTextToLeft() {
        getLayout().setOrientation(Orientation.HORIZONTAL)
                .setAlignment(Alignment.RIGHT);
    }

    public void moveTextToTop() {
        getLayout().setOrientation(Orientation.VERTICAL)
                .setAlignment(Alignment.UP);
    }

    public void moveTextToBottom() {
        getLayout().setOrientation(Orientation.VERTICAL)
                .setAlignment(Alignment.DOWN);
    }

    public BlockLabel getBlockLabel() {
        return blockLabel;
    }

    public void setBlockLabel(BlockLabel blockLabel) {
        this.blockLabel = blockLabel;
    }

    public BlockImage getBlockImage() {
        return blockImage;
    }

    public void setBlockImage(BlockImage blockImage) {
        this.blockImage = blockImage;
    }
}
