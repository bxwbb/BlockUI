package org.bxwbb.ui.pane;

public class BlockEmptyPane extends BlockPane {

    public BlockEmptyPane() {
        super();
        init();
    }

    public BlockEmptyPane(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        setUI(new BlockEmptyPaneUI());
        setBorder(0);
        setPadding(0);
    }
}
