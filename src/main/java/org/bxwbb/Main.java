package org.bxwbb;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.BlockFrame;
import org.bxwbb.ui.button.BlockButton;
import org.bxwbb.ui.button.BlockLabelButton;
import org.bxwbb.ui.button.BlockToggleButton;
import org.bxwbb.ui.label.BlockLabel;
import org.bxwbb.ui.layout.Alignment;
import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.ui.layout.Orientation;
import org.bxwbb.ui.pane.BlockPane;

public class Main {
    public static void main(String[] args) {
        BlockFrame blockFrame = new BlockFrame();
        blockFrame.setWindowPos(100, 100);
        blockFrame.setSize(500, 500);

        BlockPane blockPane = new BlockPane(100, 100, 300, 310);
        blockPane.setLayout(new LinearLayout(0, 5).setOrientation(Orientation.VERTICAL)); // 使用线性纵向布局

        BlockLabelButton blockLabelButton1 = new BlockLabelButton("Button1");
        blockLabelButton1.setSize(10, 0); // 设置宽高，也可在创建时设置
        blockLabelButton1.horizontalExtension(); // 横向无线延伸
        blockLabelButton1.verticalExtension(); // 纵向无线延伸
        blockLabelButton1.addOnMouseMoved(e-> System.out.print("鼠标移动到blockLabelButton1中"));
        blockLabelButton1.addOnButtonClick(e -> System.out.println("有效点击blockLabelButton1")); // 独特事件
        blockLabelButton1.addOnMouseReleased(e -> System.out.println("点击blockLabelButton1"));
        blockPane.addChild(blockLabelButton1);

        BlockToggleButton blockToggleButton = new BlockToggleButton(0, 0, 100, 100); // Toggle型按钮
        blockToggleButton.setMaxWidth(200); // 锁定最大宽度
        blockToggleButton.verticalExtension();
        blockToggleButton.setWeightHeight(2.0f); // 设置高度占比
        BlockLabel blockLabel = new BlockLabel("ToggleButton", 0, 0, 0, 32); // 高度为字体大小
        blockLabel.setAlign(Alignment.RIGHT); // 右对齐
        blockToggleButton.setPadding(0); // 取消内边距
        blockLabel.horizontalExtension();
        blockToggleButton.addChild(blockLabel); // 显示溢出自动省略(...)
        blockPane.addChild(blockToggleButton);

        BlockButton blockButton = new BlockButton();
        blockButton.verticalExtension();
        blockButton.horizontalExtension();
        blockButton.setLayout(new LinearLayout(5, 0));
        BlockButton blockButton1 = BaseUI.createNewUI(blockButton); // 实验性功能：复制UI控件
        blockButton1.horizontalExtension();
        blockButton1.verticalExtension();
        blockButton.addChild(blockButton1);
        BlockButton blockButton2 = BaseUI.createNewUI(blockButton);
        blockButton2.horizontalExtension();
        blockButton2.verticalExtension();
        blockButton2.setWeightWidth(3.0f);
        blockButton.addChild(blockButton2);
        blockPane.addChild(blockButton); // 控件内部嵌套

        blockPane.addOnMouseEntered(e-> System.out.println("进入blockPane中")); // 普通组件事件监听

        blockFrame.addChild(blockPane);

        blockFrame.setVisible(true);
    }
}