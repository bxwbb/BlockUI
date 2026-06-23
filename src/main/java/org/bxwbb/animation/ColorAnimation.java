package org.bxwbb.animation;

import org.bxwbb.ui.BaseUI;

import java.awt.*;
import java.util.function.Consumer;

public class ColorAnimation extends BaseAnimation {

    public Color from;
    public Color to;
    public Consumer<Color> updateListener;

    public ColorAnimation(long duration, long delay, EaseType easeType, BaseUI host, Color from, Color to) {
        super(duration, delay, easeType, host);
        this.from = from;
        this.to = to;
    }

    @Override
    public void update(float progress) {
        updateListener.accept(this.getColor(progress));
    }

    @Override
    public void end() {
        updateListener.accept(this.getColor(progress));
    }

    private Color getColor(float progress) {
        return new Color(
                (int) (from.getRed() + (to.getRed() - from.getRed()) * progress),
                (int) (from.getGreen() + (to.getGreen() - from.getGreen()) * progress),
                (int) (from.getBlue() + (to.getBlue() - from.getBlue()) * progress),
                (int) (from.getAlpha() + (to.getAlpha() - from.getAlpha()) * progress)
        );
    }
}
