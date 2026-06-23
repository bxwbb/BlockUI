package org.bxwbb.animation;

public class AnimationContext {

    private static final AnimationContext animationContext = new AnimationContext();

    public static AnimationContext getInstance() {
        return animationContext;
    }

    protected static long laseFrameTime;
    protected static float delta;
    private static boolean globalAnimEnable;

    public static long getLaseFrameTime() {
        return laseFrameTime;
    }

    public static float getDelta() {
        return delta;
    }

    public static boolean isGlobalAnimEnable() {
        return globalAnimEnable;
    }

    public static void setGlobalAnimEnable(boolean g) {
        globalAnimEnable = g;
    }
}
