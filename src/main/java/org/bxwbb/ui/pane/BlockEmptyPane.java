package org.bxwbb.ui.pane;

public class BlockEmptyPane extends BlockPane {

    public BlockEmptyPane() {
        super();
        setUI(null);
    }

    public BlockEmptyPane(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        setUI(null);
    }
}
