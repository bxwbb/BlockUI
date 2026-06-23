package org.bxwbb.ui.layout;

import org.bxwbb.ui.BaseUI;

public class ExpandLayout extends Layout {

    public ExpandLayout() {
        super();
        this.setOrientation(null);
    }

    @Override
    public void layout(BaseUI ui) {
        for (BaseUI child : ui.getChildren()) {
            if (!child.isVisible() || child.isDontLayout()) continue;
            if (this.getOrientation() == null || this.getOrientation().equals(Orientation.HORIZONTAL)) {
                int w = ui.getContentWidth() - child.getLayoutX();
                child.setGreatWidth(w);
            }
            if (this.getOrientation() == null || this.getOrientation().equals(Orientation.VERTICAL)) {
                int h = ui.getContentHeight() - child.getLayoutY();
                child.setGreatHeight(h);
            }
        }
    }
}
