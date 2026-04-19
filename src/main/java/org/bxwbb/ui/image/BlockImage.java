package org.bxwbb.ui.image;

import java.awt.*;

public class BlockImage extends AbstractBlockImage {

    public BlockImage() {
        super();
        setUI(new BlockImageUI());
    }

    public BlockImage(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        setUI(new BlockImageUI());
    }

    public BlockImage(Image image) {
        super(image);
        setUI(new BlockImageUI());
    }

    public BlockImage(Image image, int layoutX, int layoutY, int width, int height) {
        super(image, layoutX, layoutY, width, height);
        setUI(new BlockImageUI());
    }

}
