package org.bxwbb.ui.field;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;
import org.bxwbb.ui.button.BlockToggleButtonUI;

import java.awt.*;

public class BlockTextFieldUI extends BlockToggleButtonUI implements UI {

    private int curseCount = 0;
    private boolean curseShow = true;

    @Override
    public void update(BaseUI component) {
        super.update(component);
        BlockTextField blockTextField = (BlockTextField) component;
        if (blockTextField.getText().isEmpty() && !blockTextField.isDown()) {
            blockTextField.getBlockLabel().setText(blockTextField.getHintText());
            blockTextField.getBlockLabel().setTextColor(blockTextField.getHintTextColor());
        } else {
            blockTextField.getBlockLabel().setText(blockTextField.getText());
            blockTextField.getBlockLabel().setTextColor(blockTextField.getTextColor());
        }
        blockTextField.getBlockLabel().setClip(new Rectangle(blockTextField.getBlockLabel().getAbsoluteX(), blockTextField.getBlockLabel().getAbsoluteY(), blockTextField.getContentWidth(), blockTextField.getHeight()));
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        super.render(g2d, component);
        BlockTextField blockTextField = (BlockTextField) component;
        if (blockTextField.isDown()) {
            if (curseCount > 30) {
                curseCount = 0;
                curseShow = !curseShow;
            } else {
                curseCount++;
            }
            g2d.setFont(new Font(blockTextField.getBlockLabel().getFont(), Font.PLAIN, blockTextField.getBlockLabel().getHeight()));
            FontMetrics fm = g2d.getFontMetrics();
            if (blockTextField.selectEnd != blockTextField.selectStart && blockTextField.selectEnd >= 0 && blockTextField.selectStart >= 0) {
                int startX = blockTextField.getBlockLabel().getAbsoluteX() + fm.stringWidth(blockTextField.getText().substring(0, blockTextField.selectStart)) + blockTextField.getBlockLabel().getOffsetX();
                int endX = blockTextField.getBlockLabel().getAbsoluteX() + fm.stringWidth(blockTextField.getText().substring(0, blockTextField.selectEnd)) + blockTextField.getBlockLabel().getOffsetX();
                g2d.setColor(blockTextField.getSelectColor());
                if (startX < endX) {
                    g2d.fillRect(startX, blockTextField.getBlockLabel().getAbsoluteY(), endX - startX, blockTextField.getBlockLabel().getHeight());
                } else {
                    g2d.fillRect(endX, blockTextField.getBlockLabel().getAbsoluteY(), startX - endX, blockTextField.getBlockLabel().getHeight());
                }
            }
            if (curseShow) {
                int cursePosX = blockTextField.getBlockLabel().getAbsoluteX() + fm.stringWidth(blockTextField.getText().substring(0, blockTextField.getCursorIndex())) + blockTextField.getBlockLabel().getOffsetX();
                g2d.setColor(blockTextField.getCursorColor());
                g2d.fillRect(cursePosX, blockTextField.getBlockLabel().getAbsoluteY(), blockTextField.getCursorWidth(), blockTextField.getBlockLabel().getHeight());
                blockTextField.fm = fm;
            }
        } else {
            curseCount = 0;
        }
    }
}
