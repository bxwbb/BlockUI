package org.bxwbb.ui.field;

import org.bxwbb.ui.label.BlockLabel;
import org.bxwbb.ui.layout.Alignment;
import org.bxwbb.ui.layout.LinearLayout;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class BlockTextField extends AbstractTextField {

    private final BlockLabel blockLabel = new BlockLabel();
    private StringBuilder text = new StringBuilder();
    protected FontMetrics fm;
    private boolean dragging = false;
    private int selectionAnchor = -1;

    private long lastClickTime = 0;
    private int clickCount = 0;
    private static final long MULTI_CLICK_INTERVAL = 400;

    public BlockTextField() {
        super();
        init();
    }

    public BlockTextField(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
    }

    public BlockTextField(String text) {
        super(text);
        init();
    }

    public BlockTextField(String text, int layoutX, int layoutY, int width, int height) {
        super(text, layoutX, layoutY, width, height);
        init();
    }

    private void init() {
        this.setUI(new BlockTextFieldUI());
        this.setLayout(new LinearLayout());
        blockLabel.horizontalExtension();
        blockLabel.verticalExtension();
        blockLabel.setAlign(Alignment.LEFT);
        blockLabel.setEnabled(false);
        blockLabel.setShowAllText(true);
        this.addChild(getBlockLabel());

        this.addOnGetFocus(focusEvent -> this.setDown(isFocus()));
        this.addOnLostFocus(focusEvent -> {
            this.setDown(isFocus());
            setSelectStart(-1);
            setSelectEnd(-1);
            selectionAnchor = -1;
            blockLabel.setOffsetX(0);
        });

        this.addOnMousePressed(mouseEvent -> {
            if (!isEditable()) return;
            boolean shiftDown = mouseEvent.isShiftDown();
            updateCursorByMouse(mouseEvent);
            int clickIndex = getCursorIndex();
            String content = getText();

            long now = System.currentTimeMillis();
            if (now - lastClickTime < MULTI_CLICK_INTERVAL) {
                clickCount++;
            } else {
                clickCount = 1;
            }
            lastClickTime = now;

            if (shiftDown) {
                if (selectionAnchor != -1) {
                    setSelectStart(selectionAnchor);
                    setSelectEnd(clickIndex);
                } else {
                    selectionAnchor = clickIndex;
                    setSelectStart(selectionAnchor);
                    setSelectEnd(selectionAnchor);
                }
                return;
            }

            if (clickCount == 1) {
                selectionAnchor = clickIndex;
                setSelectStart(-1);
                setSelectEnd(-1);
            } else if (clickCount == 2) {
                selectWordAt(clickIndex);
            } else if (clickCount >= 3) {
                selectionAnchor = 0;
                setSelectStart(0);
                setSelectEnd(content.length());
                clickCount = 0;
            }
        });

        this.addOnMouseDragged(mouseEvent -> {
            if (!isEditable()) return;
            dragging = true;
            updateCursorByMouse(mouseEvent);
            setSelectEnd(getCursorIndex());
            if(getSelectStart() == -1) setSelectStart(getCursorIndex());
            updateScroll();
        });

        this.addOnMouseReleased(mouseEvent -> dragging = false);

        this.addOnKeyPressed(keyEvent -> {
            if (!isEditable()) return;
            int code = keyEvent.getKeyCode();
            String current = getText();
            boolean shiftDown = keyEvent.isShiftDown();
            boolean ctrlDown = keyEvent.isControlDown();

            if (code == KeyEvent.VK_LEFT) {
                if (ctrlDown) {
                    int cur = getCursorIndex();
                    while (cur > 0 && Character.isLetterOrDigit(current.charAt(cur - 1))) {
                        cur--;
                    }
                    while (cur > 0 && !Character.isLetterOrDigit(current.charAt(cur - 1))) {
                        cur--;
                    }
                    setCursorIndex(cur);
                } else {
                    if (getCursorIndex() > 0) {
                        setCursorIndex(getCursorIndex() - 1);
                    }
                }

                if (shiftDown) {
                    if (getSelectStart() == -1) {
                        selectionAnchor = getCursorIndex() + 1;
                        setSelectStart(selectionAnchor);
                    }
                    setSelectEnd(getCursorIndex());
                } else {
                    selectionAnchor = getCursorIndex();
                    setSelectStart(-1);
                    setSelectEnd(-1);
                }
            } else if (code == KeyEvent.VK_RIGHT) {
                if (ctrlDown) {
                    int cur = getCursorIndex();
                    int len = current.length();
                    while (cur < len && Character.isLetterOrDigit(current.charAt(cur))) {
                        cur++;
                    }
                    while (cur < len && !Character.isLetterOrDigit(current.charAt(cur))) {
                        cur++;
                    }
                    setCursorIndex(cur);
                } else {
                    if (getCursorIndex() < current.length()) {
                        setCursorIndex(getCursorIndex() + 1);
                    }
                }

                if (shiftDown) {
                    if (getSelectStart() == -1) {
                        selectionAnchor = getCursorIndex() - 1;
                        setSelectStart(selectionAnchor);
                    }
                    setSelectEnd(getCursorIndex());
                } else {
                    selectionAnchor = getCursorIndex();
                    setSelectStart(-1);
                    setSelectEnd(-1);
                }
            } else if (code == KeyEvent.VK_HOME) {
                if (shiftDown) {
                    if (getSelectStart() == -1) {
                        selectionAnchor = getCursorIndex();
                        setSelectStart(selectionAnchor);
                    }
                    setCursorIndex(0);
                    setSelectEnd(getCursorIndex());
                } else {
                    selectionAnchor = 0;
                    setCursorIndex(0);
                    setSelectStart(-1);
                    setSelectEnd(-1);
                }
            } else if (code == KeyEvent.VK_END) {
                if (shiftDown) {
                    if (getSelectStart() == -1) {
                        selectionAnchor = getCursorIndex();
                        setSelectStart(selectionAnchor);
                    }
                    setCursorIndex(current.length());
                    setSelectEnd(getCursorIndex());
                } else {
                    selectionAnchor = current.length();
                    setCursorIndex(current.length());
                    setSelectStart(-1);
                    setSelectEnd(-1);
                }
            } else if (code == KeyEvent.VK_BACK_SPACE) {
                if (getSelectStart() != -1 && getSelectEnd() != -1) {
                    int s = Math.min(getSelectStart(), getSelectEnd());
                    int maxed = Math.max(getSelectStart(), getSelectEnd());
                    text.delete(s, maxed);
                    setCursorIndex(s);
                    selectionAnchor = s;
                    setSelectStart(-1);
                    setSelectEnd(-1);
                } else if (getCursorIndex() > 0) {
                    text.delete(getCursorIndex() - 1, getCursorIndex());
                    setCursorIndex(getCursorIndex() - 1);
                    selectionAnchor = getCursorIndex();
                }
            } else if (code == KeyEvent.VK_DELETE) {
                if (getSelectStart() != -1 && getSelectEnd() != -1) {
                    int s = Math.min(getSelectStart(), getSelectEnd());
                    int maxed = Math.max(getSelectStart(), getSelectEnd());
                    text.delete(s, maxed);
                    setCursorIndex(s);
                    selectionAnchor = s;
                    setSelectStart(-1);
                    setSelectEnd(-1);
                } else if (getCursorIndex() < current.length()) {
                    text.delete(getCursorIndex(), getCursorIndex() + 1);
                    selectionAnchor = getCursorIndex();
                }
            } else if (code == KeyEvent.VK_ENTER) {
                this.setDown(false);
            } else if (keyEvent.isControlDown()) {
                if (code == KeyEvent.VK_A) {
                    selectionAnchor = 0;
                    setSelectStart(0);
                    setSelectEnd(current.length());
                } else if (code == KeyEvent.VK_C) {
                    int s = Math.min(getSelectStart(), getSelectEnd());
                    int maxed = Math.max(getSelectStart(), getSelectEnd());
                    if (s >= 0 && maxed >= 0 && s < maxed) {
                        String copyText = getText().substring(s, maxed);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(new StringSelection(copyText), null);
                    }
                } else if (code == KeyEvent.VK_X) {
                    int s = Math.min(getSelectStart(), getSelectEnd());
                    int maxed = Math.max(getSelectStart(), getSelectEnd());
                    if (s >= 0 && maxed >= 0 && s < maxed) {
                        String cutText = getText().substring(s, maxed);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(new StringSelection(cutText), null);
                        text.delete(s, maxed);
                        setCursorIndex(s);
                        selectionAnchor = s;
                        setSelectStart(-1);
                        setSelectEnd(-1);
                    }
                } else if (code == KeyEvent.VK_V) {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable contents = clipboard.getContents(null);
                    if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        try {
                            String pasteText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                            int s = Math.min(getSelectStart(), getSelectEnd());
                            int maxed = Math.max(getSelectStart(), getSelectEnd());
                            if (s != maxed && s >= 0 && maxed >= 0) {
                                text.replace(s, maxed, pasteText);
                                setCursorIndex(s + pasteText.length());
                            } else {
                                text.insert(getCursorIndex(), pasteText);
                                setCursorIndex(getCursorIndex() + pasteText.length());
                            }
                            selectionAnchor = getCursorIndex();
                            setSelectStart(-1);
                            setSelectEnd(-1);
                        } catch (UnsupportedFlavorException | IOException ignored) {
                        }
                    }
                }
            } else {
                if (!shiftDown) {
                    selectionAnchor = getCursorIndex();
                    setSelectStart(-1);
                    setSelectEnd(-1);
                }
            }
            blockLabel.setText(getText());
            updateScroll();
        });

        this.addOnKeyTyped(keyEvent -> {
            if (!isEditable()) return;
            char c = keyEvent.getKeyChar();
            if (c <= 31 || c == 127) {
                keyEvent.consume();
                return;
            }
            if (getMaxLength() > 0 && text.length() >= getMaxLength()) return;
            if (isOnlyNumber() && !Character.isDigit(c)) return;
            if (isOnlyLetter() && !Character.isLetter(c)) return;
            if (isBlockSpecialChar() && !Character.isLetterOrDigit(c)) return;

            if (getSelectStart() != -1 && getSelectEnd() != -1) {
                int s = Math.min(getSelectStart(), getSelectEnd());
                int maxed = Math.max(getSelectStart(), getSelectEnd());
                text.delete(s, maxed);
                setCursorIndex(s);
                selectionAnchor = s;
                setSelectStart(-1);
                setSelectEnd(-1);
            }

            text.insert(getCursorIndex(), c);
            setCursorIndex(getCursorIndex() + 1);
            selectionAnchor = getCursorIndex();
            blockLabel.setText(getText());
            updateScroll();
            keyEvent.consume();
        });

        this.addOnMouseEntered(mouseEvent -> this.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)));
        this.addOnMouseExited(mouseEvent -> this.setCursor(Cursor.getDefaultCursor()));

        this.addOnMouseClicked(mouseEvent -> {
            if (fm == null) return;
            updateScroll();
        });
    }

    private void selectWordAt(int index) {
        String content = getText();
        int len = content.length();
        if (len == 0 || index < 0 || index > len) return;

        int start = index;
        int end = index;

        while (start > 0) {
            char c = content.charAt(start - 1);
            if (!Character.isLetterOrDigit(c)) break;
            start--;
        }
        while (end < len) {
            char c = content.charAt(end);
            if (!Character.isLetterOrDigit(c)) break;
            end++;
        }

        setSelectStart(start);
        setSelectEnd(end);
        selectionAnchor = start;
    }

    private void updateCursorByMouse(MouseEvent mouseEvent) {
        if (fm == null) return;
        int clickX = mouseEvent.getX() - blockLabel.getAbsoluteX() - blockLabel.getOffsetX();
        String txt = getText();
        int currentX = 0;

        for (int i = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            int w = fm.charWidth(c);
            int center = currentX + w / 2;

            if (clickX < center) {
                setCursorIndex(i);
                return;
            }
            currentX += w;
        }
        setCursorIndex(txt.length());
    }

    private void updateScroll() {
        if (fm == null) return;
        String txt = getText();
        int idx = getCursorIndex();
        int labelW = getBlockLabel().getWidth();
        int cursorPos = fm.stringWidth(txt.substring(0, Math.min(idx, txt.length())));
        int offset = blockLabel.getOffsetX();
        int margin = 8;

        if (cursorPos - offset > labelW - margin) {
            blockLabel.setOffsetX(-(cursorPos - labelW + margin));
        }
        if (cursorPos - offset < margin) {
            blockLabel.setOffsetX(-cursorPos + margin);
        }
        if (cursorPos == 0) {
            blockLabel.setOffsetX(0);
        }
        this.needUpdate.set(true);
    }

    @Override
    public String getText() {
        return text.toString();
    }

    @Override
    public void setText(String text) {
        this.text = new StringBuilder(text == null ? "" : text);
        this.needUpdate.set(true);
        selectionAnchor = 0;
        setCursorIndex(0);
        blockLabel.setText(getText());
        blockLabel.setOffsetX(0);
    }

    public BlockLabel getBlockLabel() {
        return blockLabel;
    }

    public boolean isDragging() {
        return dragging;
    }
}