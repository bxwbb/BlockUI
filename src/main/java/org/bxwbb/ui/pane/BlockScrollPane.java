package org.bxwbb.ui.pane;

import org.bxwbb.ui.bar.BlockHorizontalSlider;
import org.bxwbb.ui.bar.BlockVerticalSlider;
import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.ui.layout.Orientation;

public class BlockScrollPane extends BlockPane {

    private final BlockEmptyPane mainPane = new BlockEmptyPane();
    protected final BlockViewport contextPane = new BlockViewport(mainPane);
    protected final BlockEmptyPane horizontalPane = new BlockEmptyPane();
    protected final BlockEmptyPane verticalPane = new BlockEmptyPane();
    private final BlockHorizontalSlider horizontalSlider = new BlockHorizontalSlider(0, 0, 100, 12);
    private final BlockVerticalSlider verticalSlider = new BlockVerticalSlider(0, 0, 12, 100);

    public BlockScrollPane() {
        super();
        init();
    }

    public BlockScrollPane(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        setUI(new BlockScrollPaneUI());
        setLayout(new LinearLayout(0, 0));
        contextPane.horizontalExtension();
        contextPane.verticalExtension();
        getMainPane().setPreferredWidth(Integer.MAX_VALUE);
        getMainPane().setPreferredHeight(Integer.MAX_VALUE);
        getVerticalSlider().verticalExtension();
        verticalPane.setLayout(new LinearLayout(0, 0));
        verticalPane.addChild(contextPane);
        verticalPane.addChild(getVerticalSlider());
        verticalPane.horizontalExtension();
        verticalPane.verticalExtension();
        LinearLayout linearLayout = new LinearLayout(0, 0);
        linearLayout.setOrientation(Orientation.VERTICAL);
        horizontalPane.setLayout(linearLayout);
        horizontalPane.addChild(verticalPane);
        horizontalPane.addChild(getHorizontalSlider());
        horizontalPane.horizontalExtension();
        horizontalPane.verticalExtension();
        this.addChild(horizontalPane);
        verticalSlider.addUpdateValue(value -> contextPane.setOffsetY((int) -value));
        horizontalSlider.addUpdateValue(value -> contextPane.setOffsetX((int) -value));
    }

    public BlockEmptyPane getMainPane() {
        return mainPane;
    }

    public BlockHorizontalSlider getHorizontalSlider() {
        return horizontalSlider;
    }

    public BlockVerticalSlider getVerticalSlider() {
        return verticalSlider;
    }
}
