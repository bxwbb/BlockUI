package org.bxwbb.ui.label;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;

import java.awt.*;

public class BlockLabelUI implements UI {
    @Override
    public void update(BaseUI component) {

    }

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        BlockLabel blockLabel = (BlockLabel) component;
        StringBuilder sb = new StringBuilder();
        g2d.setColor(blockLabel.getTextColor());
        g2d.setFont(new Font(blockLabel.getFont(), Font.PLAIN, blockLabel.getHeight()));
        FontMetrics fm = g2d.getFontMetrics();
        String text = blockLabel.getText();
        String repeatText = "...";
        int repeatWidth = fm.stringWidth(repeatText);
        if (fm.stringWidth(text) > blockLabel.getWidth()) {
            if (repeatWidth <= blockLabel.getWidth()) {
                int width = 0;
                for (int index = 0; index < text.length(); index++) {
                    char c = text.charAt(index);
                    if (width + fm.charWidth(c) > blockLabel.getWidth() - repeatWidth) break;
                    width += fm.charWidth(c);
                    sb.append(c);
                }
                sb.append(repeatText);

                int delta = 0;
                if (blockLabel.getAlign().x >= 0) {
                    delta = blockLabel.getWidth() - fm.stringWidth(sb.toString());
                    if (blockLabel.getAlign().x == 0) delta /= 2;
                }
                g2d.drawString(sb.toString(), blockLabel.getAbsoluteX() + delta, blockLabel.getAbsoluteY() + fm.getMaxAscent() - fm.getDescent() / 2);
            }
        } else {
            int delta = 0;
            if (blockLabel.getAlign().x >= 0) {
                delta = blockLabel.getWidth() - fm.stringWidth(text);
                if (blockLabel.getAlign().x == 0) delta /= 2;
            }
            g2d.drawString(text, blockLabel.getAbsoluteX() + delta, blockLabel.getAbsoluteY() + fm.getMaxAscent() - fm.getDescent() / 2);
        }
    }
}
