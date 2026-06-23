package org.bxwbb.ui;

import org.bxwbb.animation.AnimationManager;
import org.bxwbb.animation.EaseType;
import org.bxwbb.animation.ValueAnimation;
import org.bxwbb.event.EventHandler;
import org.bxwbb.theme.ColorTheme;
import org.bxwbb.ui.layout.Alignment;
import org.bxwbb.ui.layout.Layout;
import org.bxwbb.ui.layout.NullLayout;
import org.bxwbb.util.ShapeUtil;

import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public abstract class BaseUI extends Node {

    protected static BaseUI root = null;
    protected static final ExecutorService UI_UPDATE_EXECUTOR_SERVICE = new ThreadPoolExecutor(
            4,
            10,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(),
            (r) -> {
                Thread t = new Thread(r, "BlockUI-Update");
                t.setDaemon(true);
                return t;
            }
    );

    private final List<BaseUI> children = new ArrayList<>();
    private BaseUI parent = root;

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
    private int extentLayoutX = 0;
    private int extentLayoutY = 0;
    private int extentWidth = 0;
    private int extentHeight = 0;
    private float weightWidth = 1.0f;
    private float weightHeight = 1.0f;
    private boolean isFocus = false;
    private boolean isVisible = true;
    private boolean isEnabled = true;
    private boolean dontLayout = false;
    private boolean clipChildren = true;
    private Layout layout = new NullLayout();
    private Alignment alignment = Alignment.CENTER;
    private Cursor cursor = Cursor.getDefaultCursor();
    private Rectangle clip = new Rectangle();
    public final static Map<BaseUI, Rectangle> parentClips = new HashMap<>();

    protected final AtomicBoolean needUpdate = new AtomicBoolean(true);
    protected final AtomicBoolean needLayout = new AtomicBoolean(true);
    protected boolean isMouseInto = false;
    protected boolean isMouseDownHere = false;
    protected boolean isDragging = false;

    private final List<EventHandler<MouseEvent>> onMouseClicked = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMousePressed = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseReleased = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseEntered = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseExited = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseDragged = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onMouseMoved = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onGetFocus = new ArrayList<>();
    private final List<EventHandler<MouseEvent>> onLostFocus = new ArrayList<>();
    private final List<EventHandler<KeyEvent>> onKeyPressed = new ArrayList<>();
    private final List<EventHandler<KeyEvent>> onKeyReleased = new ArrayList<>();
    private final List<EventHandler<KeyEvent>> onKeyTyped = new ArrayList<>();
    private final List<EventHandler<InputMethodEvent>> onInputMethodTextChanged = new ArrayList<>();

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
        this.needLayout.set(true);
        this.needUpdate.set(true);
    }

    public int getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(int layoutY) {
        this.layoutY = layoutY;
        this.needLayout.set(true);
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
        this.needLayout.set(true);
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        this.needLayout.set(true);
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
        this.needLayout.set(true);
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        this.needLayout.set(true);
    }

    public final void update() {
        if (ui != null) ui.update(this);
        this.needUpdate.set(false);
    }

    public final void render(Graphics2D g2d) {
        if (this.needUpdate.get()) {
            g2d.setColor(ColorTheme.COLOR_PROGRAM_BASE);
            g2d.fillRect(this.getAbsoluteX(), this.getAbsoluteY(), this.getPreferredWidth(), this.getPreferredHeight());
        }
        if (ui != null) ui.render(g2d, this);
    }

    public UI getUI() {
        return ui;
    }

    public void setUI(UI ui) {
        this.ui = ui;
    }

    public boolean isin(int px, int py) {
        return isin(px, py, this.getAbsoluteX(), this.getAbsoluteY(), this.getWidth(), this.getHeight()) && this.getParent() != null && parentClips.containsKey(this.getParent()) && parentClips.get(this.getParent()).contains(px, py);
    }

    public boolean isFocus() {
        return isFocus;
    }

    protected void setFocus(boolean focus) {
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

    public List<EventHandler<MouseEvent>> getOnGetFocus() {
        return onGetFocus;
    }

    public List<EventHandler<MouseEvent>> getOnLostFocus() {
        return onLostFocus;
    }

    public List<EventHandler<KeyEvent>> getOnKeyPressed() {
        return onKeyPressed;
    }

    public List<EventHandler<KeyEvent>> getOnKeyReleased() {
        return onKeyReleased;
    }

    public List<EventHandler<KeyEvent>> getOnKeyTyped() {
        return onKeyTyped;
    }

    public List<EventHandler<InputMethodEvent>> getOnInputMethodTextChanged() {
        return onInputMethodTextChanged;
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

    public void addOnKeyPressed(EventHandler<KeyEvent> handler) {
        this.onKeyPressed.add(handler);
    }

    public void addOnKeyReleased(EventHandler<KeyEvent> handler) {
        this.onKeyReleased.add(handler);
    }

    public void addOnKeyTyped(EventHandler<KeyEvent> handler) {
        this.onKeyTyped.add(handler);
    }

    public void addOnGetFocus(EventHandler<MouseEvent> handler) {
        this.onGetFocus.add(handler);
    }

    public void addOnLostFocus(EventHandler<MouseEvent> handler) {
        this.onLostFocus.add(handler);
    }

    public void addOnInputMethodTextChanged(EventHandler<InputMethodEvent> handler) {
        this.onInputMethodTextChanged.add(handler);
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
            this.getParentWindow().getFocus().onLostFocus(event);
            this.getParentWindow().setFocus(this);
            this.getParentWindow().getFocus().onGetFocus(event);
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
        this.isDragging = false;
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

    protected void onMouseMoved(MouseEvent event, Component component) {
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
            component.setCursor(this.getCursor());
            for (EventHandler<MouseEvent> mouseEventEventHandler : this.getOnMouseMoved()) {
                if (event.isConsumed()) return;
                mouseEventEventHandler.invoke(event);
            }
        }
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            if (this.getChildren().get(i) instanceof BaseUI baseUI) {
                baseUI.onMouseMoved(event, component);
            }
        }
    }

    protected void onMouseDragged(MouseEvent event) {
        if (!this.isEnabled) return;
        if (!this.isMouseInto && this.isin(event.getX(), event.getY())) {
            this.onMouseEntered(event);
            this.isMouseInto = true;
        }
        if (this.isMouseInto && !this.isin(event.getX(), event.getY())) {
            this.onMouseExited(event);
            this.isMouseInto = false;
        }
        if ((this.isin(event.getX(), event.getY())) || isDragging) {
            if (!isDragging) isDragging = true;
            for (EventHandler<MouseEvent> mouseEventEventHandler : this.getOnMouseDragged()) {
                if (event.isConsumed()) return;
                mouseEventEventHandler.invoke(event);
            }
        }
        for (int i = this.getChildren().size() - 1; i >= 0; i--) {
            if (this.getChildren().get(i) instanceof BaseUI baseUI) {
                baseUI.onMouseDragged(event);
            }
        }
    }

    protected void onGetFocus(MouseEvent event) {
        this.setFocus(true);
        for (EventHandler<MouseEvent> getFocus : this.getOnGetFocus()) {
            getFocus.invoke(event);
        }
    }

    protected void onLostFocus(MouseEvent event) {
        this.setFocus(false);
        for (EventHandler<MouseEvent> lostFocus : this.getOnLostFocus()) {
            lostFocus.invoke(event);
        }
    }

    protected void onKeyPressed(KeyEvent event) {
        if (!this.isEnabled) return;
        for (EventHandler<KeyEvent> keyPressed : this.getOnKeyPressed()) {
            keyPressed.invoke(event);
        }
    }

    protected void onKeyReleased(KeyEvent event) {
        if (!this.isEnabled) return;
        for (EventHandler<KeyEvent> keyReleased : this.getOnKeyReleased()) {
            keyReleased.invoke(event);
        }
    }

    protected void onKeyTyped(KeyEvent event) {
        if (!this.isEnabled) return;
        for (EventHandler<KeyEvent> keyTyped : this.getOnKeyTyped()) {
            keyTyped.invoke(event);
        }
    }

    protected void onInputMethodTextChanged(InputMethodEvent event) {
        if (!this.isEnabled) return;
        for (EventHandler<InputMethodEvent> inputMethodTextChanged : this.getOnInputMethodTextChanged()) {
            inputMethodTextChanged.invoke(event);
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
        this.needLayout.set(true);
    }

    public float getWeightWidth() {
        return weightWidth;
    }

    public void setWeightWidth(float weightWidth) {
        this.weightWidth = weightWidth;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public float getWeightHeight() {
        return weightHeight;
    }

    public void setWeightHeight(float weightHeight) {
        this.weightHeight = weightHeight;
        this.needUpdate.set(true);
        this.needLayout.set(true);
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
        this.needLayout.set(true);
    }

    public void addChild(BaseUI child) {
        child.setParent(this);
        this.needUpdate.set(true);
        this.needLayout.set(true);
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

    public void updateChildren() {
        for (BaseUI baseUI : this.getChildren()) {
            if (baseUI.isVisible && baseUI.needUpdate.get()) {
                baseUI.update();
                clip.setRect(
                        getAbsoluteX(),
                        getAbsoluteY(),
                        getPreferredWidth(),
                        getPreferredHeight()
                );
                if (baseUI.getParent() != null)
                    parentClips.put(baseUI, ShapeUtil.intersectRect(parentClips.get(baseUI.getParent()), baseUI.getClip()));
            }
            baseUI.updateChildren();
        }
    }

    public void layout() {
        for (BaseUI child : getChildren()) {
            child.layout();
        }
        this.getLayout().layout(this);
        this.needUpdate.set(true);
        if (!getChildren().isEmpty()) {
            List<BaseUI> children = getChildren();
            BaseUI first = children.getFirst();
            int minX = first.getLayoutX();
            int minY = first.getLayoutY();
            int maxX = first.getEndX();
            int maxY = first.getEndY();
            for (BaseUI child : children) {
                minX = Math.min(minX, child.getLayoutX());
                minY = Math.min(minY, child.getLayoutY());
                maxX = Math.max(maxX, child.getEndX());
                maxY = Math.max(maxY, child.getEndY());
            }
            this.extentLayoutX = minX;
            this.extentLayoutY = minY;
            this.extentWidth = maxX;
            this.extentHeight = maxY;
        } else {
            this.extentLayoutX = 0;
            this.extentLayoutY = 0;
            this.extentWidth = 0;
            this.extentHeight = 0;
        }
    }

    public void renderChildren(Graphics2D g2d) {
        for (BaseUI baseUI : this.getChildren()) {
            if (baseUI.isVisible) {
                if (this.isClipChildren() && baseUI.getParent() != null && parentClips.containsKey(baseUI.getParent()))
                    g2d.setClip(parentClips.get(baseUI.getParent()));
                baseUI.render(g2d);
                baseUI.renderChildren(g2d);
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
        this.needLayout.set(true);
    }

    public int getPreferredHeight() {
        return preferredHeight;
    }

    public void setPreferredHeight(int preferredHeight) {
        this.preferredHeight = preferredHeight;
        this.maxHeight = getPreferredHeight();
        this.minHeight = getPreferredHeight();
        this.needUpdate.set(true);
        this.needLayout.set(true);
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
        this.needLayout.set(true);
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public void setPaddingHorizontal(int paddingHorizontal) {
        this.paddingLeft = paddingHorizontal;
        this.paddingRight = paddingHorizontal;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public void setPaddingVertical(int paddingVertical) {
        this.paddingTop = paddingVertical;
        this.paddingBottom = paddingVertical;
        this.needUpdate.set(true);
        this.needLayout.set(true);
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
        this.needLayout.set(true);
    }

    public int getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(int borderRight) {
        this.borderRight = borderRight;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public int getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(int borderBottom) {
        this.borderBottom = borderBottom;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public void setBorderHorizontal(int borderHorizontal) {
        this.borderLeft = borderHorizontal;
        this.borderRight = borderHorizontal;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public void setBorderVertical(int borderVertical) {
        this.borderTop = borderVertical;
        this.borderBottom = borderVertical;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public void setBorder(int border) {
        this.setBorderHorizontal(border);
        this.setBorderVertical(border);
    }

    public int getExtentLayoutX() {
        return extentLayoutX;
    }

    public int getExtentLayoutY() {
        return extentLayoutY;
    }

    public int getExtentWidth() {
        return extentWidth;
    }

    public int getExtentHeight() {
        return extentHeight;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
        this.needUpdate.set(true);
        this.needLayout.set(true);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
        this.needUpdate.set(true);
    }

    public boolean isClipChildren() {
        return clipChildren;
    }

    public void setClipChildren(boolean clipChildren) {
        this.clipChildren = clipChildren;
        this.needUpdate.set(true);
    }

    public boolean isDragging() {
        return isDragging;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
        if (parent != null) parent.needUpdate.set(true);
    }

    protected Window getParentWindow() {
        BaseUI baseUI = this;
        Window ret = null;
        while (baseUI != null) {
            if (baseUI instanceof Window window) {
                ret = window;
            }
            baseUI = baseUI.getParent();
        }
        return ret;
    }

    protected BaseUI getParentWindowUI() {
        BaseUI baseUI = this;
        while (baseUI != null) {
            if (baseUI instanceof Window) {
                return baseUI;
            }
            baseUI = baseUI.getParent();
        }
        return root;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        this.needUpdate.set(true);
    }

    public Rectangle getClip() {
        return clip;
    }

    public void setClip(Rectangle clip) {
        this.clip = clip;
        this.needUpdate.set(true);
    }

    public void createDefaultDragGestureRecognizer(int action, Cursor cursor, Transferable transferable, Predicate<Point> booleanSupplier) {
        BaseUI self = this;
        this.getParentWindow().createDefaultDragGestureRecognizer(action, dge -> {
            Point dragPoint = dge.getDragOrigin();
            if (self.isin(dragPoint.x, dragPoint.y) && booleanSupplier.test(dragPoint)) {
                dge.startDrag(cursor, transferable);
            }
        });
    }

    public void dropTarget(DropTargetListener dropTargetListener) {
        BaseUI self = this;
        getParentWindow().dropTarget(new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                Point dragPoint = dtde.getLocation();
                if (self.isin(dragPoint.x, dragPoint.y)) {
                    dropTargetListener.dragEnter(dtde);
                }
            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                Point dragPoint = dtde.getLocation();
                if (self.isin(dragPoint.x, dragPoint.y)) {
                    dropTargetListener.dragOver(dtde);
                }
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {
                Point dragPoint = dtde.getLocation();
                if (self.isin(dragPoint.x, dragPoint.y)) {
                    dropTargetListener.dropActionChanged(dtde);
                }
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
                dropTargetListener.dragExit(dte);
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                Point dropPoint = dtde.getLocation();
                if (self.isin(dropPoint.x, dropPoint.y)) {
                    dropTargetListener.drop(dtde);
                }
            }
        });
    }

    public boolean isDontLayout() {
        return dontLayout;
    }

    public void setDontLayout(boolean dontLayout) {
        this.dontLayout = dontLayout;
    }

    public ValueAnimation createValueAnimation(long duration, long delay, EaseType easeType, float from, float to, boolean reverse) {
        return new ValueAnimation(duration, delay, easeType, this, from, to, reverse);
    }

    public ValueAnimation createValueAnimation(long duration, long delay, EaseType easeType, float from, float to) {
        return this.createValueAnimation(duration, delay, easeType, from, to, false);
    }

    public void stopAllAnimation() {
        AnimationManager.getInstance().clearHoseAnimation(this);
    }
}
