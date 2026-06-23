# BlockUI(V0.3.1)

feat:
\ + 增加了横向，纵向滑动条和滑动条面板

\ + 增加了密码框控件

\ + 增加了开关控件

\ + 增加了动画系统

fix:

#### 介绍
BlockUI是一个模仿我的世界（大部分为基岩版）UI的UI库，使用原生Java Swing编写，目前处于早起开发阶段，可扩展。

### 使用例子
创建三个竖向叠放的按钮
```java
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
```

### 特性
1. 有基本控件：BlockPane、BlockLabel、BlockButton等
2. 有Layout布局类
3. 可装卸的UI委托

### 控件介绍
#### BlockPane
一个基本面板，有内外两种边框，内边框自动设置颜色和高光，还可以设置内部阴影高度。
#### BlockLabelPane
含有一个BlockLabel组件的面板
#### BlockImagePane
含有一个BlockImage组件的面板
#### BlockLabel
可以显示单行文字的组件，不支持富文本，默认居中对齐，默认字体为微软雅黑，隐藏超出部分
#### BlockButton
一个按钮组件
#### BlockImage
一个图片组件，可设置压缩方式，可设置颜色蒙版
#### BlockImageButton
一个图片按钮组件
#### BlockLabelButton
一个文字按钮组件
#### BlockToggleButton
一个点击后才切换状态的按钮组件
#### BlockTextField
一个输入框组件
#### BlockRadioButton
一个单选按钮组件
#### BlockCheckBox
一个复选框组件
#### BlockPasswordField
一个密码输入框组件
#### BlockSwitch
一个开关控件

### 布局介绍
#### NullLayout
自由布局，完全不管理子组件的位置，子组件会按照添加的顺序依次显示
#### LinearLayout
线性布局，子组件会按照添加的顺序依次显示，可设置横向或纵向排列，宽度（或高度）自动根据权重与最大最小值调整，溢出控件裁剪显示。

### 贡献方式
直接在github上提交pr即可，感谢您的贡献

### 使用方法
在控件都实现的差不多之后会发布到maven的中央仓库中

感谢使用