package org.bxwbb.animation;

import org.bxwbb.ui.BaseUI;

import java.util.function.Consumer;

public class ValueAnimation extends BaseAnimation {

    public float from;
    public float to;
    public boolean reverse;
    public Consumer<Float> updateListener;
    public Consumer<Float> endListener;

    public ValueAnimation(long duration, long delay, EaseType easeType, BaseUI host, float from, float to) {
        this(duration, delay, easeType, host, from, to, false);
    }

    public ValueAnimation(long duration, long delay, EaseType easeType, BaseUI host, float from, float to, boolean reverse) {
        super(duration, delay, easeType, host);
        this.from = from;
        this.to = to;
        this.reverse = reverse;
    }

    @Override
    public void update(float progress) {
        updateListener.accept(this.reverse ? to + progress * (from - to) : from + progress * (to - from));
    }

    @Override
    public void end() {
        if (endListener != null) endListener.accept(this.reverse ? to + progress * (from - to) : from + progress * (to - from));
    }
}
