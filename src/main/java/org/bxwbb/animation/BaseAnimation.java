package org.bxwbb.animation;

import org.bxwbb.ui.BaseUI;

public abstract class BaseAnimation {

    protected long duration;
    protected long delay;
    protected long runTime;
    protected float progress;
    EaseType easeType;
    protected boolean loop;
    protected boolean running;
    protected boolean finished;
    protected BaseUI host;

    public BaseAnimation(long duration, long delay, EaseType easeType, BaseUI host) {
        this.duration = duration;
        this.delay = delay;
        this.runTime = 0;
        this.easeType = easeType;
        this.host = host;
        this.loop = false;
        this.running = false;
        this.finished = false;
        this.progress = 0;
    }

    public abstract void update(float progress);

    public abstract void end();

    public void start() {
        this.running = true;
        this.runTime = System.currentTimeMillis();
        AnimationManager.getInstance().addAnimation(this);
    }

    public void pause() {
        this.running = false;
    }

    public void resume() {
        this.running = true;
    }

    public void reset() {
        this.progress = 0;
        this.finished = false;
        this.runTime = System.currentTimeMillis();
    }

    public long getDuration() {
        return duration;
    }

    public BaseAnimation setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public long getDelay() {
        return delay;
    }

    public BaseAnimation setDelay(long delay) {
        this.delay = delay;
        return this;
    }

    public long getRunTime() {
        return runTime;
    }

    public BaseAnimation setRunTime(long runTime) {
        this.runTime = runTime;
        return this;
    }

    public float getProgress() {
        return progress;
    }

    public BaseAnimation setProgress(float progress) {
        this.progress = progress;
        return this;
    }

    public EaseType getEaseType() {
        return easeType;
    }

    public BaseAnimation setEaseType(EaseType easeType) {
        this.easeType = easeType;
        return this;
    }

    public boolean isLoop() {
        return loop;
    }

    public BaseAnimation setLoop(boolean loop) {
        this.loop = loop;
        return this;
    }

    public boolean isRunning() {
        return running;
    }

    public BaseAnimation setRunning(boolean running) {
        this.running = running;
        return this;
    }

    public boolean isFinished() {
        return finished;
    }

    public BaseAnimation setFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    public BaseUI getHost() {
        return host;
    }

    public BaseAnimation setHost(BaseUI host) {
        this.host = host;
        return this;
    }
}
