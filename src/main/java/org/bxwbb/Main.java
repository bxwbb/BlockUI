package org.bxwbb;

import org.bxwbb.ui.BlockFrame;
import org.bxwbb.ui.button.BlockLabelButton;
import org.bxwbb.ui.layout.Alignment;
import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.ui.pane.BlockScrollPane;

public class Main {
    public static void main(String[] args) {
        BlockFrame blockFrame = new BlockFrame();
        blockFrame.setWindowPos(100, 100);
        blockFrame.setSize(500, 500);
        blockFrame.setLayout(new LinearLayout());

        BlockScrollPane blockScrollPane = new BlockScrollPane(50, 50, 200, 200);
        blockScrollPane.getMainPane().setLayout(new LinearLayout().setAlignment(Alignment.LEFT_UP));

        BlockLabelButton blockButton = new BlockLabelButton("点击我复制", 0, 0, 100, 100);
        blockButton.addOnButtonClick(event -> {
            blockScrollPane.getMainPane().addChild(new BlockLabelButton("点击我复制", 0, 0, 100, 100));
            blockButton.setPreferredHeight(blockButton.getPreferredHeight() + 100);
        });
        blockScrollPane.getMainPane().addChild(blockButton);
        blockScrollPane.horizontalExtension();
        blockScrollPane.verticalExtension();
        blockFrame.addChild(blockScrollPane);

        blockFrame.setVisible(true);
    }
}