package org.bxwbb.ui.button;

import org.bxwbb.ui.image.BlockImage;
import org.bxwbb.ui.image.ScaleMode;
import org.bxwbb.ui.layout.LinearLayout;

import java.awt.*;

public class BlockCheckBox extends BlockToggleButton {

    private final BlockImage checkImage = new BlockImage(Toolkit.getDefaultToolkit().getImage(BlockCheckBoxUI.class.getResource("/ui/image/check.png")));

    public BlockCheckBox() {
        super();
        init();
    }

    public BlockCheckBox(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        setUI(new BlockCheckBoxUI());
        this.setDownColor(new Color(81, 163, 53));
        this.setLayout(new LinearLayout());
        getCheckImage().horizontalExtension();
        getCheckImage().verticalExtension();
        getCheckImage().setScaleMode(ScaleMode.SCALE_FIT);
        this.addChild(getCheckImage());
    }

    public BlockImage getCheckImage() {
        return checkImage;
    }
}
