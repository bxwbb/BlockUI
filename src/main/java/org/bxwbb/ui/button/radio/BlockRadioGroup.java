package org.bxwbb.ui.button.radio;

public class BlockRadioGroup extends AbstractBlockRadioGroup {

    @Override
    public void add(BlockRadioButton button) {
        super.add(button);
        if (getSelected() == null) select(button);
    }
}
