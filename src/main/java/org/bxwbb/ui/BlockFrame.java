package org.bxwbb.ui;

import org.bxwbb.event.RenderLoop;
import org.bxwbb.theme.ColorTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BlockFrame extends BaseUI implements Window {

    final JFrame jFrame;
    final CanvasPanel canvasPanel = new CanvasPanel(this);
    RenderLoop renderLoop = null;
    protected BaseUI focus = this;

    public BlockFrame() {
        this("窗口标题");
    }

    public BlockFrame(String title) {
        super();
        BaseUI.root = this;
        jFrame = new JFrame(title);
        jFrame.add(canvasPanel);
        jFrame.setBackground(ColorTheme.COLOR_PROGRAM_BASE);
        BlockFrame self = this;
        jFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                self.setSize(jFrame.getContentPane().getWidth(), jFrame.getContentPane().getHeight());
            }
        });
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                renderLoop.stop();
            }
        });
        canvasPanel.setFocusable(true);
        canvasPanel.requestFocus();
        canvasPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                self.onMouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                self.onMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                self.onMouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        canvasPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                self.onMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                self.onMouseMoved(e, canvasPanel);
            }
        });
        canvasPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                getFocus().onKeyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                getFocus().onKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                getFocus().onKeyReleased(e);
            }
        });
        canvasPanel.addInputMethodListener(new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {
                getFocus().onInputMethodTextChanged(event);
                event.consume();
            }

            @Override
            public void caretPositionChanged(InputMethodEvent event) {}
        });
        this.renderLoop = new RenderLoop(this.canvasPanel::repaint);
    }

    @Override
    public void setVisible(boolean visible) {
        jFrame.getContentPane().setPreferredSize(new Dimension(getWidth(), getHeight()));
        jFrame.pack();
        jFrame.setVisible(visible);
    }

    @Override
    public Component getComponent() {
        return canvasPanel;
    }

    public void setWindowPos(int x, int y) {
        jFrame.setLocation(x, y);
    }

    static class CanvasPanel extends JPanel {

        private final BlockFrame window;

        public CanvasPanel(BlockFrame window) {
            this.window = window;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(ColorTheme.COLOR_PROGRAM_BASE);
            g.fillRect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

            window.update();
            window.render((Graphics2D) g);
            g.setClip(window.getClip());
            window.updateChildren((Graphics2D) g);
        }
    }

    public BaseUI getFocus() {
        return focus;
    }

    @Override
    public void setFocus(BaseUI focus) {
        this.focus = focus;
    }
}
