package org.bxwbb.ui.pane;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;

public class BlockScrollPaneUI extends BlockPaneUI implements UI {

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockScrollPane blockScrollPane = (BlockScrollPane) component;
        blockScrollPane.getHorizontalSlider().setMaxWidth(blockScrollPane.horizontalPane.getPreferredWidth() - 12);
        blockScrollPane.getMainPane().setPreferredWidth(blockScrollPane.contextPane.getPreferredWidth());
        blockScrollPane.getMainPane().setPreferredHeight(blockScrollPane.contextPane.getPreferredHeight());
        int mvX = blockScrollPane.getMainPane().getExtentWidth() - blockScrollPane.getMainPane().getPreferredWidth();
        int mvY = blockScrollPane.getMainPane().getExtentHeight() - blockScrollPane.getMainPane().getPreferredHeight();
        if (mvX <= 0) {
            blockScrollPane.getHorizontalSlider().setVisible(false);
        } else {
            blockScrollPane.getHorizontalSlider().setVisible(true);
            blockScrollPane.getHorizontalSlider().setMaxValue(mvX);
        }
        if (mvY <= 0) {
            blockScrollPane.getVerticalSlider().setVisible(false);
        } else {
            blockScrollPane.getVerticalSlider().setVisible(true);
            blockScrollPane.getVerticalSlider().setMaxValue(mvY);
        }
    }
}
