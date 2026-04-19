package org.bxwbb.ui;

import java.awt.*;

public interface UI {
    void update(BaseUI component);

    void render(Graphics2D g2d, BaseUI component);
}
