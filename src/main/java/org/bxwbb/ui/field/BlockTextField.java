package org.bxwbb.ui.field;

import org.bxwbb.ui.label.BlockLabel;
import org.bxwbb.ui.layout.Alignment;
import org.bxwbb.ui.layout.LinearLayout;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

public class BlockTextField extends AbstractTextField {
    private final BlockLabel blockLabel = new BlockLabel();
    private StringBuilder text = new StringBuilder();
    protected FontMetrics fm;
    private boolean isDragging = false;
    private boolean pressedInSelection = false;
    private int selectionAnchor = 0;
    private long lastClickTime = 0;
    private int clickCount = 0;
    private static final long MULTI_CLICK_INTERVAL = 400;
    private final Deque<EditSnapshot> undoStack = new LinkedList<>();
    private final Deque<EditSnapshot> redoStack = new LinkedList<>();
    private boolean lockHistory = false;
    private int inputCount = 0;
    private static final int SAVE_INTERVAL = 8;

    private static class EditSnapshot {
        String content;
        int cursor;
        int selectStart;
        int selectEnd;
        int anchor;
        EditSnapshot(String c, int cu, int s, int e, int a) {
            content = c;
            cursor = cu;
            selectStart = s;
            selectEnd = e;
            anchor = a;
        }
    }

    public BlockTextField() {
        super();
        init();
        saveSnapshot();
    }

    public BlockTextField(int layoutX, int layoutY, int width, int height) {
        super(layoutX, layoutY, width, height);
        init();
        saveSnapshot();
    }

    public BlockTextField(String text) {
        super(text);
        init();
        saveSnapshot();
    }

    public BlockTextField(String text, int layoutX, int layoutY, int width, int height) {
        super(text, layoutX, layoutY, width, height);
        init();
        saveSnapshot();
    }

    private void init() {
        this.setUI(new BlockTextFieldUI());
        this.setLayout(new LinearLayout());
        this.setSetChangeDown(false);
        blockLabel.horizontalExtension();
        blockLabel.verticalExtension();
        blockLabel.setAlign(Alignment.LEFT);
        blockLabel.setEnabled(false);
        blockLabel.setShowAllText(true);
        this.addChild(getBlockLabel());

        this.addOnGetFocus(focusEvent -> this.setDown(isFocus()));
        this.addOnLostFocus(focusEvent -> {
            this.setDown(isFocus());
            blockLabel.setOffsetX(0);
        });

        this.addOnMousePressed(mouseEvent -> {
            if (!isEditable()) return;
            updateCursorByMouse(mouseEvent);
            int clickPos = getCursorIndex();
            boolean shiftDown = mouseEvent.isShiftDown();
            pressedInSelection = isInSelection(clickPos);
            String content = getText();
            long now = System.currentTimeMillis();

            if (now - lastClickTime < MULTI_CLICK_INTERVAL) {
                clickCount++;
            } else {
                clickCount = 1;
            }
            lastClickTime = now;

            if (shiftDown) {
                setSelectStart(selectionAnchor);
                setSelectEnd(clickPos);
                return;
            }

            if (clickCount == 3) {
                selectionAnchor = 0;
                setSelectStart(0);
                setSelectEnd(content.length());
                mouseEvent.consume();
                return;
            }

            if (clickCount == 2) {
                selectWordAt(clickPos);
                return;
            }

            if (!pressedInSelection) {
                selectionAnchor = clickPos;
                setSelectStart(-1);
                setSelectEnd(-1);
            }
        });

        this.addOnMouseDragged(mouseEvent -> {
            if (!isEditable()) return;
            isDragging = true;
            updateCursorByMouse(mouseEvent);
            int curr = getCursorIndex();

            if (pressedInSelection) {
                return;
            }

            int min = Math.min(selectionAnchor, curr);
            int max = Math.max(selectionAnchor, curr);
            setSelectStart(min);
            setSelectEnd(max);
            updateScroll();
        });

        this.addOnMouseReleased(mouseEvent -> {
            if (!isEditable()) {
                isDragging = false;
                pressedInSelection = false;
                return;
            }

            if (pressedInSelection && !isDragging && clickCount != 3) {
                selectionAnchor = getCursorIndex();
                setSelectStart(-1);
                setSelectEnd(-1);
            }

            if(clickCount == 3){
                clickCount = 0;
            }

            isDragging = false;
            pressedInSelection = false;

            if (getSelectStart() == getSelectEnd()) {
                setSelectStart(-1);
                setSelectEnd(-1);
            }
        });

        this.addOnKeyPressed(keyEvent -> {
            if (!isEditable()) return;
            int code = keyEvent.getKeyCode();
            String current = getText();
            boolean shiftDown = keyEvent.isShiftDown();
            boolean ctrlDown = keyEvent.isControlDown();

            if (ctrlDown) {
                if (code == KeyEvent.VK_Z) {
                    undo();
                    keyEvent.consume();
                    return;
                }
                if (code == KeyEvent.VK_Y) {
                    redo();
                    keyEvent.consume();
                    return;
                }
            }

            if (code == KeyEvent.VK_BACK_SPACE || code == KeyEvent.VK_DELETE || code == KeyEvent.VK_ENTER) {
                saveSnapshot();
            }

            if (code == KeyEvent.VK_LEFT) {
                if (ctrlDown) {
                    int cur = getCursorIndex();
                    while (cur > 0 && Character.isLetterOrDigit(current.charAt(cur-1))) cur--;
                    while (cur > 0 && !Character.isLetterOrDigit(current.charAt(cur-1))) cur--;
                    setCursorIndex(cur);
                } else {
                    if (getCursorIndex() > 0) setCursorIndex(getCursorIndex()-1);
                }
                if (shiftDown) {
                    setSelectStart(selectionAnchor);
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
                    while (cur < len && Character.isLetterOrDigit(current.charAt(cur))) cur++;
                    while (cur < len && !Character.isLetterOrDigit(current.charAt(cur))) cur++;
                    setCursorIndex(cur);
                } else {
                    if (getCursorIndex() < current.length()) setCursorIndex(getCursorIndex()+1);
                }
                if (shiftDown) {
                    setSelectStart(selectionAnchor);
                    setSelectEnd(getCursorIndex());
                } else {
                    selectionAnchor = getCursorIndex();
                    setSelectStart(-1);
                    setSelectEnd(-1);
                }
            } else if (code == KeyEvent.VK_HOME) {
                setCursorIndex(0);
                if (shiftDown) {
                    setSelectStart(selectionAnchor);
                    setSelectEnd(0);
                } else {
                    selectionAnchor = 0;
                    setSelectStart(-1);
                    setSelectEnd(-1);
                }
            } else if (code == KeyEvent.VK_END) {
                setCursorIndex(current.length());
                if (shiftDown) {
                    setSelectStart(selectionAnchor);
                    setSelectEnd(current.length());
                } else {
                    selectionAnchor = current.length();
                    setSelectStart(-1);
                    setSelectEnd(-1);
                }
            } else if (code == KeyEvent.VK_BACK_SPACE) {
                if (hasSelection()) {
                    deleteSelection();
                } else if (getCursorIndex() > 0) {
                    text.delete(getCursorIndex()-1, getCursorIndex());
                    setCursorIndex(getCursorIndex()-1);
                    selectionAnchor = getCursorIndex();
                }
            } else if (code == KeyEvent.VK_DELETE) {
                if (hasSelection()) {
                    deleteSelection();
                } else if (getCursorIndex() < current.length()) {
                    text.delete(getCursorIndex(), getCursorIndex()+1);
                    selectionAnchor = getCursorIndex();
                }
            } else if (code == KeyEvent.VK_ENTER) {
                this.setDown(false);
            } else if (ctrlDown) {
                if (code == KeyEvent.VK_A) {
                    selectionAnchor = 0;
                    setSelectStart(0);
                    setSelectEnd(current.length());
                } else if (code == KeyEvent.VK_C) {
                    copySelection();
                } else if (code == KeyEvent.VK_X) {
                    saveSnapshot();
                    copySelection();
                    deleteSelection();
                } else if (code == KeyEvent.VK_V) {
                    saveSnapshot();
                    pasteText();
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

            if (hasSelection()) {
                saveSnapshot();
                deleteSelection();
            }

            if (inputCount++ >= SAVE_INTERVAL) {
                saveSnapshot();
                inputCount = 0;
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
        this.addOnMouseClicked(mouseEvent -> { if (fm != null) updateScroll(); });

        BlockTextField self = this;
        this.createDefaultDragGestureRecognizer(DnDConstants.ACTION_COPY_OR_MOVE, DragSource.DefaultCopyDrop, new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.stringFlavor};
            }
            @Override
            public boolean isDataFlavorSupported(DataFlavor f) {
                return f.equals(DataFlavor.stringFlavor);
            }
            @Override
            public Object getTransferData(DataFlavor f) {
                return hasSelection() ? getText().substring(Math.min(getSelectStart(), getSelectEnd()), Math.max(getSelectStart(), getSelectEnd())) : "";
            }
        }, point -> self.isInSelected(point.x));

        this.dropTarget(new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent e) {}
            @Override
            public void dragOver(DropTargetDragEvent e) {
                Point p = e.getLocation();
                MouseEvent fake = new MouseEvent(BlockTextField.this.getParentWindow().getComponent(), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, p.x, p.y, 0, false);
                updateCursorByMouse(fake);
                updateScroll();
            }
            @Override
            public void dropActionChanged(DropTargetDragEvent e) {}
            @Override
            public void dragExit(DropTargetEvent e) {}
            @Override
            public void drop(DropTargetDropEvent e) {
                try {
                    e.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable t = e.getTransferable();
                    if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                        String str = (String) t.getTransferData(DataFlavor.stringFlavor);
                        saveSnapshot();
                        Point p = e.getLocation();
                        MouseEvent fake = new MouseEvent(BlockTextField.this.getParentWindow().getComponent(), MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, p.x, p.y, 1, false);
                        updateCursorByMouse(fake);
                        text.insert(getCursorIndex(), str);
                        setCursorIndex(getCursorIndex() + str.length());
                        selectionAnchor = getCursorIndex();
                        blockLabel.setText(getText());
                        updateScroll();
                    }
                    e.dropComplete(true);
                } catch (Exception ex) {
                    e.dropComplete(false);
                }
            }
        });
    }

    private boolean isInSelection(int pos) {
        if (!hasSelection()) return false;
        int s = Math.min(getSelectStart(), getSelectEnd());
        int e = Math.max(getSelectStart(), getSelectEnd());
        return pos >= s && pos <= e;
    }

    private boolean hasSelection() {
        return getSelectStart() != -1 && getSelectEnd() != -1 && getSelectStart() != getSelectEnd();
    }

    private void deleteSelection() {
        int s = Math.min(getSelectStart(), getSelectEnd());
        int e = Math.max(getSelectStart(), getSelectEnd());
        text.delete(s, e);
        setCursorIndex(s);
        selectionAnchor = s;
        setSelectStart(-1);
        setSelectEnd(-1);
    }

    private void copySelection() {
        if (!hasSelection()) return;
        int s = Math.min(getSelectStart(), getSelectEnd());
        int e = Math.max(getSelectStart(), getSelectEnd());
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        clip.setContents(new StringSelection(getText().substring(s, e)), null);
    }

    private void pasteText() {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable cnt = clip.getContents(null);
        if (cnt != null && cnt.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String t = (String) cnt.getTransferData(DataFlavor.stringFlavor);
                if (hasSelection()) deleteSelection();
                text.insert(getCursorIndex(), t);
                setCursorIndex(getCursorIndex() + t.length());
                selectionAnchor = getCursorIndex();
                setSelectStart(-1);
                setSelectEnd(-1);
            } catch (UnsupportedFlavorException | IOException ignored) {}
        }
    }

    private void saveSnapshot() {
        if (lockHistory) return;
        undoStack.push(new EditSnapshot(getText(), getCursorIndex(), getSelectStart(), getSelectEnd(), selectionAnchor));
        redoStack.clear();
        if (undoStack.size() > 50) undoStack.removeLast();
    }

    private void undo() {
        if (undoStack.isEmpty()) return;
        lockHistory = true;
        redoStack.push(new EditSnapshot(getText(), getCursorIndex(), getSelectStart(), getSelectEnd(), selectionAnchor));
        restoreSnapshot(undoStack.pop());
        lockHistory = false;
        inputCount = 0;
    }

    private void redo() {
        if (redoStack.isEmpty()) return;
        lockHistory = true;
        undoStack.push(new EditSnapshot(getText(), getCursorIndex(), getSelectStart(), getSelectEnd(), selectionAnchor));
        restoreSnapshot(redoStack.pop());
        lockHistory = false;
        inputCount = 0;
    }

    private void restoreSnapshot(EditSnapshot snap) {
        text = new StringBuilder(snap.content);
        setCursorIndex(snap.cursor);
        setSelectStart(snap.selectStart);
        setSelectEnd(snap.selectEnd);
        selectionAnchor = snap.anchor;
        blockLabel.setText(getText());
        updateScroll();
    }

    private void selectWordAt(int idx) {
        String t = getText();
        int len = t.length();
        if (len == 0 || idx < 0 || idx > len) return;
        int s = idx;
        int e = idx;
        while (s > 0 && Character.isLetterOrDigit(t.charAt(s-1))) s--;
        while (e < len && Character.isLetterOrDigit(t.charAt(e))) e++;
        setSelectStart(s);
        setSelectEnd(e);
        selectionAnchor = s;
    }

    private void updateCursorByMouse(MouseEvent e) {
        if (fm == null) return;
        int x = e.getX() - blockLabel.getAbsoluteX() - blockLabel.getOffsetX();
        String t = getText();
        int w = 0;
        int i = 0;
        for (; i < t.length(); i++) {
            int cw = this instanceof BlockPasswordField ? 6 + 5 : fm.charWidth(t.charAt(i));
            if (x < w + cw / 2) break;
            w += cw;
        }
        setCursorIndex(i);
    }

    private void updateScroll() {
        if (fm == null) return;
        int cursorPos = fm.stringWidth(getText().substring(0, Math.min(getCursorIndex(), getText().length())));
        int viewW = blockLabel.getWidth();
        int margin = 12;
        int targetOffset = 0;

        if (cursorPos > viewW + blockLabel.getOffsetX() - margin) {
            targetOffset = cursorPos - viewW + margin;
        } else if (cursorPos < blockLabel.getOffsetX() + margin) {
            targetOffset = cursorPos - margin;
        }
        if (cursorPos == 0) targetOffset = 0;

        blockLabel.setOffsetX(-targetOffset);
        this.needUpdate.set(true);
    }

    @Override
    public String getText() {
        return text.toString();
    }

    @Override
    public void setText(String t) {
        text = new StringBuilder(t == null ? "" : t);
        needUpdate.set(true);
        selectionAnchor = 0;
        setCursorIndex(0);
        blockLabel.setText(getText());
        blockLabel.setOffsetX(0);
    }

    public BlockLabel getBlockLabel() {
        return blockLabel;
    }

    private boolean isInSelected(int px) {
        if (!hasSelection()) return false;
        int s = Math.min(getSelectStart(), getSelectEnd());
        int e = Math.max(getSelectStart(), getSelectEnd());
        int x1 = blockLabel.getAbsoluteX() + blockLabel.getOffsetX() + fm.stringWidth(getText().substring(0, s));
        int x2 = blockLabel.getAbsoluteX() + blockLabel.getOffsetX() + fm.stringWidth(getText().substring(0, e));
        return px >= x1 && px <= x2;
    }
}