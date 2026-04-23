package org.bxwbb.ui.field;

import org.bxwbb.ui.button.BlockToggleButton;

import java.awt.*;

public abstract class AbstractTextField extends BlockToggleButton {

    private int cursorIndex = 0;
    private int cursorWidth = 2;
    private int maxLength = -1;
    private boolean editable = true;
    private boolean onlyNumber = false;
    private boolean onlyLetter = false;
    private boolean blockSpecialChar = false;

    private String hintText = "Input text";

    private Color cursorColor = Color.LIGHT_GRAY;
    private Color hintTextColor = Color.GRAY;
    private Color SelectColor = Color.BLUE;
    private Color textColor = Color.WHITE;

    protected int selectStart = -1;
    protected int selectEnd = -1;

    public AbstractTextField() {
        super();
    }

    public AbstractTextField(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
    }

    public AbstractTextField(String text) {
        super();
        setText(text);
    }

    public AbstractTextField(String text, int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        setText(text);
    }

    public abstract String getText();

    public abstract void setText(String text);

    public int getCursorIndex() {
        return cursorIndex;
    }

    public void setCursorIndex(int cursorIndex) {
        this.cursorIndex = cursorIndex;
        this.needUpdate.set(true);
    }

    public int getCursorWidth() {
        return cursorWidth;
    }

    public void setCursorWidth(int cursorWidth) {
        this.cursorWidth = cursorWidth;
        this.needUpdate.set(true);
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        this.needUpdate.set(true);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        this.needUpdate.set(true);
    }

    public boolean isOnlyNumber() {
        return onlyNumber;
    }

    public void setOnlyNumber(boolean onlyNumber) {
        this.onlyNumber = onlyNumber;
        this.needUpdate.set(true);
    }

    public boolean isOnlyLetter() {
        return onlyLetter;
    }

    public void setOnlyLetter(boolean onlyLetter) {
        this.onlyLetter = onlyLetter;
        this.needUpdate.set(true);
    }

    public boolean isBlockSpecialChar() {
        return blockSpecialChar;
    }

    public void setBlockSpecialChar(boolean blockSpecialChar) {
        this.blockSpecialChar = blockSpecialChar;
        this.needUpdate.set(true);
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
        this.needUpdate.set(true);
    }

    public Color getCursorColor() {
        return cursorColor;
    }

    public void setCursorColor(Color cursorColor) {
        this.cursorColor = cursorColor;
        this.needUpdate.set(true);
    }

    public Color getHintTextColor() {
        return hintTextColor;
    }

    public void setHintTextColor(Color hintTextColor) {
        this.hintTextColor = hintTextColor;
        this.needUpdate.set(true);
    }

    public Color getSelectColor() {
        return SelectColor;
    }

    public void setSelectColor(Color selectColor) {
        SelectColor = selectColor;
        this.needUpdate.set(true);
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        this.needUpdate.set(true);
    }

    public int getSelectStart() {
        return selectStart;
    }

    public void setSelectStart(int selectStart) {
        this.selectStart = selectStart;
        this.needUpdate.set(true);
    }

    public int getSelectEnd() {
        return selectEnd;
    }

    public void setSelectEnd(int selectEnd) {
        this.selectEnd = selectEnd;
        this.needUpdate.set(true);
    }
}
