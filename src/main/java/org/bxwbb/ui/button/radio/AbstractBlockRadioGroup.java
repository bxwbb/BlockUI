package org.bxwbb.ui.button.radio;

import org.bxwbb.ui.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBlockRadioGroup extends Node {

    private final List<BlockRadioButton> buttonList = new ArrayList<>();
    private BlockRadioButton selected;

    public void add(BlockRadioButton button) {
        buttonList.add(button);
        button.addOnButtonClick(e -> select(button));
    }

    public void select(BlockRadioButton button) {
        button.setDown(true);
        if (getSelected() != null) getSelected().setDown(false);
        selected = button;
    }

    public void clear() {
        buttonList.clear();
    }

    public List<BlockRadioButton> getButtonList() {
        return buttonList;
    }

    public BlockRadioButton getSelected() {
        return selected;
    }
}
