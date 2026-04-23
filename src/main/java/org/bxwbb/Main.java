package org.bxwbb;

import org.bxwbb.ui.BlockFrame;
import org.bxwbb.ui.button.BlockButton;
import org.bxwbb.ui.field.BlockTextField;

public class Main {
    public static void main(String[] args) {
        BlockFrame blockFrame = new BlockFrame();
        blockFrame.setWindowPos(100, 100);
        blockFrame.setSize(500, 500);

        BlockTextField blockTextField = new BlockTextField(100, 100, 300, 35);
        blockFrame.addChild(blockTextField);
        blockTextField.setHintText("请输入内容...");

        BlockButton blockPane = new BlockButton(300, 300, 100, 50);
        blockFrame.addChild(blockPane);

        blockFrame.setVisible(true);
    }
}