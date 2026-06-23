package org.bxwbb.ui.button;

import org.bxwbb.animation.EaseType;
import org.bxwbb.animation.ValueAnimation;
import org.bxwbb.ui.image.BlockImage;
import org.bxwbb.ui.image.ScaleMode;
import org.bxwbb.ui.layout.LinearLayout;
import org.bxwbb.ui.pane.BlockPane;

import java.awt.*;

public class BlockSwitch extends BlockToggleButton {

    private final BlockImage switchImage = new BlockImage(Toolkit.getDefaultToolkit().getImage(BlockSwitch.class.getResource("/ui/image/switch.png")));
    private final BlockPane switchPane = new BlockPane();

    protected boolean animationChanged = true;

    public BlockSwitch() {
        super();
        init();
    }

    public BlockSwitch(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        this.setLayout(new LinearLayout());
        this.setPadding(0);
        switchImage.setScaleMode(ScaleMode.SCALE_STRETCH);
        switchImage.horizontalExtension();
        switchImage.verticalExtension();
        switchPane.setDontLayout(true);
        switchPane.setBackgroundColor(new Color(206, 207, 210));
        this.addChild(switchImage);
        this.addChild(switchPane);
        this.setUI(new BlockSwitchUI());
        this.addOnButtonClick(e -> {
            ValueAnimation valueAnimation = BlockSwitch.this.getSwitchPane().createValueAnimation(250L, 0L, EaseType.ELASTIC_OUT, (float) -(BlockSwitch.this.getBorderLeft() + BlockSwitch.this.getPaddingLeft()), (float) (0.5 * BlockSwitch.this.getWidth()), this.isDown());
            valueAnimation.updateListener = (progress) -> BlockSwitch.this.getSwitchPane().setLayoutX(Math.round(progress));
            valueAnimation.endListener = (progress) -> BlockSwitch.this.animationChanged = true;
            valueAnimation.start();
            BlockSwitch.this.animationChanged = false;
        });
    }

    public BlockImage getSwitchImage() {
        return switchImage;
    }

    public BlockPane getSwitchPane() {
        return switchPane;
    }
}
