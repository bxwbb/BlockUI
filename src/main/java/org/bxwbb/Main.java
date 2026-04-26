package org.bxwbb;

import org.bxwbb.ui.BlockFrame;
import org.bxwbb.ui.field.BlockTextField;

public class Main {
    public static void main(String[] args) {
        BlockFrame blockFrame = new BlockFrame();
        blockFrame.setWindowPos(100, 100);
        blockFrame.setSize(500, 500);

        BlockTextField blockTextField = new BlockTextField(100, 100, 300, 40);
        blockFrame.addChild(blockTextField);

        blockFrame.setVisible(true);
    }
}