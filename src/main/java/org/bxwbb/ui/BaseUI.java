package org.bxwbb.ui;

import org.bxwbb.event.EventHandler;
import org.bxwbb.ui.layout.Alignment;
import org.bxwbb.ui.layout.Layout;
import org.bxwbb.ui.layout.NullLayout;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseUI extends Node {

    private final List<BaseUI> children = new ArrayList<>();
    private BaseUI parent = null;

    private int layoutX = 0;
    private int layoutY = 0;
    private int preferredWidth = 0;
    private int preferredHeight = 0;
    private int maxWidth = 0;
    private int maxHeight = 0;
    private int minWidth = 0;
    private int minHeight = 0;
    private int paddingTop = 4;
    private int paddingLeft = 4;
    private int paddingRight = 4;
    private int paddingBottom = 4;
    private int borderTop = 0;
    private int borderLeft = 0;
    private int borderRight = 0;
    private int borderBottom = 0;
    private float weightWidth = 1.0f;
    private float weightHeight = 1.0f;
    private boolean isFocus = false;
    private boolean isVisible = true;
    private boolean isEnabled = true;
    private Layout layout = new NullLayout();
    private Alignment alignment = Alignment.CENTER;

    protected final AtomicBoolean needUpdate = new AtomicBoolean(true);
    protected boolean isMouseInto = false;
    protected boolean isMouseDownHere = false;

    private final List<EventHandler<MouseEvent>> onMouseClicked = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMousePressed = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseReleased = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseEntered = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseExited = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseDragged = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseMoved = new ArrayList<>();

    protected UI ui;

    protected BaseUI() {
        super();
    }

    protected BaseUI(int x, int y, int width, int height) {
        super();
        setBounds(x, y, width, height);
    }

    public int getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(int layoutX) {
        this.layoutX = layoutX;
        this.needUpdate.set(true);
    }

    public int getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(int layoutY) {
        this.layoutY = layoutY;
        this.needUpdate.set(true);
    }

    public void setLayout(int layoutX, int layoutY) {
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
    }

    public int getWidth() {
        return getPreferredWidth();
    }

    public int getHeight() {
        return getPreferredHeight();
    }

    public void setSize(int width, int height) {
        this.setPreferredWidth(width);
        this.setPreferredHeight(height);
    }

    public void setBounds(int layoutX, int layoutY, int width, int height) {
        this.setLayout(layoutX, layoutY);
        this.setSize(width, height);
    }

    public int getEndX() {
        return this.getLayoutX() + this.getWidth();
    }

    public void setEndX(int endX) {
        this.setPreferredWidth(endX - this.getLayoutX());
    }

    public int getEndY() {
        return this.getLayoutY() + this.getHeight();
    }

    public void setEndY(int endY) {
        this.setPreferredHeight(endY - this.getLayoutY());
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public final void update() {
        this.getLayout().layout(this);
        if (ui != null) ui.update(this);
        this.needUpdate.set(false);
    }

    public final void render(Graphics2D g2d) {
        if (ui != null) ui.render(g2d, this);
    }

    public UI getUI() {
        return ui;
    }

    public void setUI(UI ui) {
        this.ui = ui;
    }

    public boolean isin(int px, int py) {
        return isin(px, py, this.getAbsoluteX(), this.getAbsoluteY(), this.getWidth(), this.getHeight());
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
        this.needUpdate.set(true);
    }

    public static boolean isin(int px, int py, int x, int y, int w, int h) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }

    public List<EventHandler<MouseEvent>> getOnMouseClicked() {
        return onMouseClicked;
    }

    public List<EventHandler<MouseEvent>> getOnMousePressed() {
        return onMousePressed;
    }

    public List<EventHandler<MouseEvent>> getOnMouseReleased() {
        return onMouseReleased;
    }

    public List<EventHandler<MouseEvent>> getOnMouseEntered() {
        return onMouseEntered;
    }

    public List<EventHandler<MouseEvent>> getOnMouseExited() {
        return onMouseExited;
    }

    public List<EventHandler<MouseEvent>> getOnMouseDragged() {
        return onMouseDragged;
    }

    public List<EventHandler<MouseEvent>> getOnMouseMoved() {
        return onMouseMoved;
    }

    public void addOnMouseClicked(EventHandler<MouseEvent> handler) {
        this.onMouseClicked.add(handler);
    }

    public void addOnMousePressed(EventHandler<MouseEvent> handler) {
        this.onMousePressed.add(handler);
    }

    public void addOnMouseReleased(EventHandler<MouseEvent> handler) {
        this.onMouseReleased.add(handler);
    }

    public void addOnMouseEntered(EventHandler<MouseEvent> handler) {
        this.onMouseEntered.add(handler);
    }

    public void addOnMouseExited(EventHandler<MouseEvent> handler) {
        this.onMouseExited.add(handler);
    }

    public void addOnMouseDragged(EventHandler<MouseEvent> handler) {
        this.onMouseDragged.add(handler);
    }

    public void addOnMouseMoved(EventHandler<MouseEvent> handler) {
        this.onMouseMoved.add(handler);
    }

    protected void onMouseClicked(MouseEvent event) {
        if (!this.isEnabled) return;
        if (this.isin(event.getX(), event.getY())) {
            for (EventHandler<MouseEvent> mouseEventEventHandler : this.getOnMouseClicked()) {
                if (event.isConsumed()) return;
                mouseEventEventHandler.invoke(event);
            }
        }
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            if (this.getChildren().get(i) instanceof BaseUI baseUI) {
                baseUI.onMouseClicked(event);
            }
        }
    }

    protected void onMousePressed(MouseEvent event) {
        if (!this.isEnabled) return;
        if (this.isin(event.getX(), event.getY())) {
            this.isMouseDownHere = true;
            for (EventHandler<MouseEvent> mouseEventEventHandler : this.getOnMousePressed()) {
                if (event.isConsumed()) return;
                mouseEventEventHandler.invoke(event);
            }
        }
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            if (this.getChildren().get(i) instanceof BaseUI baseUI) {
                baseUI.onMousePressed(event);
            }
        }
    }

    protected void onMouseReleased(MouseEvent event) {
        if (!this.isEnabled) return;
        if (this.isMouseDownHere) {
            this.isMouseDownHere = false;
            for (EventHandler<MouseEvent> mouseEventEventHandler : this.getOnMouseReleased()) {
                if (event.isConsumed()) return;
                mouseEventEventHandler.invoke(event);
            }
        }
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            if (this.getChildren().get(i) instanceof BaseUI baseUI) {
                baseUI.onMouseReleased(event);
            }
        }
    }

    protected void onMouseEntered(MouseEvent event) {
        if (!this.isEnabled) return;
        for (EventHandler<MouseEvent> mouseEventEventHandler : this.getOnMouseEntered()) {
            if (event.isConsumed()) return;
            mouseEventEventHandler.invoke(event);
        }
    }

    protected void onMouseExited(MouseEvent event) {
        if (!this.isEnabled) return;
        for (EventHandler<MouseEvent> mouseEventEventHandler : this.getOnMouseExited()) {
            if (event.isConsumed()) return;
            mouseEventEventHandler.invoke(event);
        }
    }

    protected void onMouseMoved(MouseEvent event) {
        if (!this.isEnabled) return;
        if (!this.isMouseInto && this.isin(event.getX(), event.getY())) {
            this.onMouseEntered(event);
            this.isMouseInto = true;
        }
        if (this.isMouseInto && !this.isin(event.getX(), event.getY())) {
            this.onMouseExited(event);
            this.isMouseInto = false;
        }
        if (this.isin(event.getX(), event.getY())) {
            for (EventHandler<MouseEvent> mouseEventEventHandler : this.getOnMouseMoved()) {
                if (event.isConsumed()) return;
                mouseEventEventHandler.invoke(event);
            }
        }
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            if (this.getChildren().get(i) instanceof BaseUI baseUI) {
                baseUI.onMouseMoved(event);
            }
        }
    }

    protected void onMouseDragged(MouseEvent event) {
        if (!this.isEnabled) return;
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            if (this.getChildren().get(i) instanceof BaseUI baseUI) {
                if (baseUI.isin(event.getX(), event.getY())) {
                    for (EventHandler<MouseEvent> mouseEventEventHandler : baseUI.getOnMouseDragged()) {
                        if (event.isConsumed()) return;
                        mouseEventEventHandler.invoke(event);
                    }
                }
                baseUI.onMouseDragged(event);
            }
        }
    }

    protected boolean isGreatWidth(int width) {
        return width >= this.getMinWidth() && width <= this.getMaxWidth();
    }

    protected boolean isGreatHeight(int height) {
        return height >= this.getMinHeight() && height <= this.getMaxHeight();
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
        this.needUpdate.set(true);
    }

    public float getWeightWidth() {
        return weightWidth;
    }

    public void setWeightWidth(float weightWidth) {
        this.weightWidth = weightWidth;
        this.needUpdate.set(true);
    }

    public float getWeightHeight() {
        return weightHeight;
    }

    public void setWeightHeight(float weightHeight) {
        this.weightHeight = weightHeight;
        this.needUpdate.set(true);
    }

    public int setGreatWidth(int width) {
        if (width < getMinWidth()) {
            this.preferredWidth = getMinWidth();
            return this.getMinWidth();
        }
        if (width > getMaxWidth()) {
            this.preferredWidth = getMaxWidth();
            return this.getMaxWidth();
        }
        if (this.preferredWidth == width) {
            return width;
        }
        this.preferredWidth = width;
        this.needUpdate.set(true);
        return width;
    }

    public int setGreatHeight(int height) {
        if (height < getMinHeight()) {
            this.preferredHeight = getMinHeight();
            return this.getMinHeight();
        }
        if (height > getMaxHeight()) {
            this.preferredHeight = getMaxHeight();
            return this.getMaxHeight();
        }
        this.preferredHeight = height;
        this.needUpdate.set(true);
        return height;
    }

    @SuppressWarnings("unchecked")
    public static <T extends BaseUI> T createNewUI(T ui) {
        try {
            T t = (T) ui.getClass().getDeclaredConstructor().newInstance();
            t.setBounds(ui.getLayoutX(), ui.getLayoutY(), ui.getWidth(), ui.getHeight());
            t.setMaxWidth(ui.getMaxWidth());
            t.setMaxHeight(ui.getMaxHeight());
            t.setMinWidth(ui.getMinWidth());
            t.setMinHeight(ui.getMinHeight());
            t.setWeightWidth(ui.getWeightWidth());
            t.setWeightHeight(ui.getWeightHeight());
            t.setPaddingTop(ui.getPaddingTop());
            t.setPaddingLeft(ui.getPaddingLeft());
            t.setPaddingRight(ui.getPaddingRight());
            t.setPaddingBottom(ui.getPaddingBottom());
            t.setBorderTop(ui.getBorderTop());
            t.setBorderLeft(ui.getBorderLeft());
            t.setBorderRight(ui.getBorderRight());
            t.setBorderBottom(ui.getBorderBottom());
            t.setAlignment(ui.getAlignment());
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<BaseUI> getChildren() {
        return children;
    }

    public BaseUI getParent() {
        return parent;
    }

    public void setParent(BaseUI parent) {
        if (this.parent != null) {
            this.parent.getChildren().remove(this);
        }
        this.parent = parent;
        if (this.parent != null) {
            this.parent.getChildren().add(this);
        }
    }

    public void addChild(BaseUI child) {
        child.setParent(this);
        this.needUpdate.set(true);
    }

    public void removeChild(BaseUI child) {
        child.setParent(null);
    }

    public void addAllChildren(BaseUI... children) {
        for (BaseUI child : children) {
            child.setParent(this);
        }
    }

    public void horizontalExtension() {
        setMaxWidth(Integer.MAX_VALUE);
    }

    public void verticalExtension() {
        setMaxHeight(Integer.MAX_VALUE);
    }

    public void updateChildren(Graphics2D g2d) {
        for (BaseUI baseUI : this.getChildren()) {
            baseUI.update();
            if (baseUI.isVisible) {
                baseUI.render(g2d);
                baseUI.updateChildren(g2d);
            }
        }
    }

    public int getPreferredWidth() {
        return preferredWidth;
    }

    public void setPreferredWidth(int preferredWidth) {
        this.preferredWidth = preferredWidth;
        this.maxWidth = getPreferredWidth();
        this.minWidth = getPreferredWidth();
        this.needUpdate.set(true);
    }

    public int getPreferredHeight() {
        return preferredHeight;
    }

    public void setPreferredHeight(int preferredHeight) {
        this.preferredHeight = preferredHeight;
        this.maxHeight = getPreferredHeight();
        this.minHeight = getPreferredHeight();
        this.needUpdate.set(true);
    }

    public int getAbsoluteX() {
        if (parent == null) return getLayoutX();
        return parent.getAbsoluteX() + parent.getPaddingLeft() + getLayoutX() + parent.getBorderLeft();
    }

    public int getAbsoluteY() {
        if (parent == null) return getLayoutY();
        return parent.getAbsoluteY() + parent.getPaddingTop() + getLayoutY() + parent.getBorderTop();
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        this.needUpdate.set(true);
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        this.needUpdate.set(true);
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        this.needUpdate.set(true);
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        this.needUpdate.set(true);
    }

    public void setPaddingHorizontal(int paddingHorizontal) {
        this.paddingLeft = paddingHorizontal;
        this.paddingRight = paddingHorizontal;
        this.needUpdate.set(true);
    }

    public void setPaddingVertical(int paddingVertical) {
        this.paddingTop = paddingVertical;
        this.paddingBottom = paddingVertical;
        this.needUpdate.set(true);
    }

    public void setPadding(int padding) {
        this.setPaddingHorizontal(padding);
        this.setPaddingVertical(padding);
    }

    public int getContentWidth() {
        return this.getWidth() - this.getPaddingLeft() - this.getPaddingRight() - this.getBorderLeft() - this.getBorderRight();
    }

    public int getContentHeight() {
        return this.getHeight() - this.getPaddingTop() - this.getPaddingBottom() - this.getBorderTop() - this.getBorderBottom();
    }

    public int getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(int borderTop) {
        this.borderTop = borderTop;
        this.needUpdate.set(true);
    }

    public int getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(int borderLeft) {
        this.borderLeft = borderLeft;
        this.needUpdate.set(true);
    }

    public int getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(int borderRight) {
        this.borderRight = borderRight;
        this.needUpdate.set(true);
    }

    public int getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(int borderBottom) {
        this.borderBottom = borderBottom;
        this.needUpdate.set(true);
    }

    public void setBorderHorizontal(int borderHorizontal) {
        this.borderLeft = borderHorizontal;
        this.borderRight = borderHorizontal;
        this.needUpdate.set(true);
    }

    public void setBorderVertical(int borderVertical) {
        this.borderTop = borderVertical;
        this.borderBottom = borderVertical;
        this.needUpdate.set(true);
    }

    public void setBorder(int border) {
        this.setBorderHorizontal(border);
        this.setBorderVertical(border);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
        this.needUpdate.set(true);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
        this.needUpdate.set(true);
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
        if (parent != null) parent.needUpdate.set(true);
    }
}
