package org.bxwbb.ui.pane;

import org.bxwbb.ui.label.BlockLabel;
import org.bxwbb.ui.layout.LinearLayout;

public class BlockLabelPane extends BlockPane {

    private final BlockLabel blockLabel = new BlockLabel();

    public BlockLabelPane() {
        super();
        init();
    }

    public BlockLabelPane(int x, int y, int width, int height) {
        super(x, y, width, height);
        init();
    }

    private void init() {
        blockLabel.horizontalExtension();
        blockLabel.setPreferredHeight(16);
        this.setLayout(new LinearLayout());
        this.addChild(blockLabel);
        this.setUI(new BlockPaneUI());
    }

    public BlockLabelPane(String text) {
        this(0, 0, 0, 0);
        blockLabel.setText(text);
    }

    public BlockLabelPane(String text, int x, int y, int width, int height) {
        this(x, y, width, height);
        blockLabel.setText(text);
    }

    public BlockLabel getBlockLabel() {
        return blockLabel;
    }
}
