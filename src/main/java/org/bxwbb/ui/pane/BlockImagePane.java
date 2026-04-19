package org.bxwbb.ui.pane;

import org.bxwbb.ui.image.BlockImage;
import org.bxwbb.ui.layout.LinearLayout;

import java.awt.*;

public class BlockImagePane extends BlockPane {

    private final BlockImage blockImage = new BlockImage();

    public BlockImagePane() {
        super();
        init();
    }

    public BlockImagePane(int x, int y, int width, int height) {
        super(x, y, width, height);
        init();
    }

    private void init() {
        blockImage.horizontalExtension();
        blockImage.verticalExtension();
        this.setLayout(new LinearLayout());
        this.addChild(blockImage);
        this.setUI(new BlockPaneUI());
    }

    public BlockImagePane(Image image) {
        this(0, 0, 0, 0);
        blockImage.setImage(image);
    }

    public BlockImagePane(Image image, int x, int y, int width, int height) {
        this(x, y, width, height);
        blockImage.setImage(image);
    }

    public BlockImage getBlockImage() {
        return blockImage;
    }
}
