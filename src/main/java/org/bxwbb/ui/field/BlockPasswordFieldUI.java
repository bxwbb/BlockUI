package org.bxwbb.ui.field;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;
import org.bxwbb.ui.label.BlockLabel;

import java.awt.*;

public class BlockPasswordFieldUI implements UI {
    @Override
    public void update(BaseUI component) {
    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        BlockLabel label = (BlockLabel) component;
        g2d.setColor(label.getTextColor());
        String text = label.getText();
        int squareSize = 6;
        int spacing = 5;
        int contentWidth = text.length() * squareSize + (text.length() - 1) * spacing;
        int baseX = label.getAbsoluteX() + label.getOffsetX();
        int centerY = label.getAbsoluteY() + label.getHeight() / 2 - squareSize / 2;
        int drawX = baseX;

        if(label.getAlign().x == 0){
            drawX += (label.getWidth() - contentWidth) / 2;
        }else if(label.getAlign().x > 0){
            drawX += label.getWidth() - contentWidth;
        }

        for(int i = 0;i < text.length();i++){
            g2d.fillRect(drawX,centerY,squareSize,squareSize);
            drawX += squareSize + spacing;
        }
    }
}