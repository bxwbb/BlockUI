package org.bxwbb.ui;

import java.awt.*;

public interface Window {

    BaseUI getFocus();

    void setFocus(BaseUI focus);

    void setVisible(boolean visible);

    Component getComponent();

}
