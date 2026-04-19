package org.bxwbb.ui.label;

public class BlockLabel extends AbstractBlockLabel {

    public BlockLabel() {
        super();
        setUI(new BlockLabelUI());
    }

    public BlockLabel(String text, int x, int y, int width, int height) {
        super(x, y, width, height);
        setText(text);
        setUI(new BlockLabelUI());
    }

}
