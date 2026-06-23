package org.bxwbb.ui.field;

public class BlockPasswordField extends BlockTextField {

    public BlockPasswordField() {
        super();
        init();
    }

    public BlockPasswordField(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    public BlockPasswordField(String text) {
        super(text);
        init();
    }

    public BlockPasswordField(String text, int layoutX, int layoutY, int width, int height) {
        super(text, layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        getBlockLabel().setUI(new BlockPasswordFieldUI());
    }
}
