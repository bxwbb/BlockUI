package org.bxwbb.ui.layout;

import org.bxwbb.ui.BaseUI;

public class FrameLayout extends Layout {

    @Override
    public void layout(BaseUI ui) {
        if (ui == null) return;

        int pw = ui.getWidth();
        int ph = ui.getHeight();

        for (BaseUI child : ui.getChildren()) {
            if (!child.isVisible()) continue;

            int cw = child.getWidth();
            int ch = child.getHeight();
            Alignment align = child.getAlignment();

            int x = switch (align.x) {
                case 0 -> (pw - cw) / 2;
                case 1 -> pw - cw;
                default -> 0;
            };

            int y = switch (align.y) {
                case -1 -> 0;
                case 0 -> (ph - ch) / 2;
                case 1 -> ph - ch;
                default -> 0;
            };

            child.setLayoutX(x);
            child.setLayoutY(y);
        }
    }
}