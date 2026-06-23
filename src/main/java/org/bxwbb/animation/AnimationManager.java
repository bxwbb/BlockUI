package org.bxwbb.animation;

import org.bxwbb.ui.BaseUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnimationManager {

    private static final AnimationManager instance = new AnimationManager();
    private final List<BaseAnimation> animations = new ArrayList<>();

    private AnimationManager() {
        Thread animationThread = new Thread(() -> {
            while (true) {
                synchronized (animations) {
                    this.tick();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        animationThread.setDaemon(true);
        animationThread.start();
    }

    public static AnimationManager getInstance() {
        return instance;
    }

    // 不再暴露原集合，防止外部无锁操作；需要遍历请提供拷贝快照
    public List<BaseAnimation> getAnimationsSnapshot() {
        synchronized (animations) {
            return new ArrayList<>(animations);
        }
    }

    public void addAnimation(BaseAnimation animation) {
        synchronized (animations) {
            animations.add(animation);
        }
    }

    public void removeAnimation(BaseAnimation animation) {
        synchronized (animations) {
            animations.remove(animation);
        }
    }

    public void tick() {
        long now = System.currentTimeMillis();
        Iterator<BaseAnimation> iterator = animations.iterator();

        while (iterator.hasNext()) {
            BaseAnimation animation = iterator.next();

            if (animation.finished) {
                iterator.remove();
                continue;
            }

            if (!animation.running) {
                continue;
            }

            long elapsed = now - animation.runTime;

            if (elapsed < animation.delay) {
                continue;
            }

            long activeElapsed = elapsed - animation.delay;
            float progress = Math.min((float) activeElapsed / animation.duration, 1.0f);
            animation.progress = animation.getEaseType().calculate(progress);

            animation.update(animation.progress);

            if (progress >= 1.0f) {
                if (animation.loop) {
                    animation.reset();
                } else {
                    animation.end();
                    animation.finished = true;
                }
            }
        }
    }

    public void clearHoseAnimation(BaseUI host) {
        synchronized (animations) {
            animations.removeIf(animation -> animation.getHost() == host);
        }
    }

    public void clearAll() {
        synchronized (animations) {
            animations.clear();
        }
    }
}