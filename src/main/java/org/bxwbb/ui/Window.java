package org.bxwbb.ui;

import java.awt.*;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DropTargetListener;

public interface Window {

    BaseUI getFocus();

    void setFocus(BaseUI focus);

    void setVisible(boolean visible);

    Component getComponent();

    void createDefaultDragGestureRecognizer(int action, DragGestureListener dragGestureListener);

    void dropTarget(DropTargetListener dropTargetListener);

}
