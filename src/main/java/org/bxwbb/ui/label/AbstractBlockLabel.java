package org.bxwbb.ui.label;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.layout.Alignment;

import java.awt.*;

public abstract class AbstractBlockLabel extends BaseUI {

    private Color textColor = Color.WHITE;

    private String text = "Label";
    private String font = "微软雅黑";

    private Alignment align = Alignment.CENTER;

    // 自动换行
    private boolean wrapText = true;
    // 溢出省略
    private boolean ellipsis = true;
    // 可被选中
    private boolean selectable = true;
    // 可以被复制
    private boolean copyable = true;

    public AbstractBlockLabel() {
        super();
    }

    public AbstractBlockLabel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        this.needUpdate.set(true);
    }

    public Alignment getAlign() {
        return align;
    }

    public void setAlign(Alignment align) {
        this.align = align;
        this.needUpdate.set(true);
    }

    public boolean isWrapText() {
        return wrapText;
    }

    public void setWrapText(boolean wrapText) {
        this.wrapText = wrapText;
        this.needUpdate.set(true);
    }

    public boolean isEllipsis() {
        return ellipsis;
    }

    public void setEllipsis(boolean ellipsis) {
        this.ellipsis = ellipsis;
        this.needUpdate.set(true);
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
        this.needUpdate.set(true);
    }

    public boolean isCopyable() {
        return copyable;
    }

    public void setCopyable(boolean copyable) {
        this.copyable = copyable;
        this.needUpdate.set(true);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.needUpdate.set(true);
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
        this.needUpdate.set(true);
    }
}
