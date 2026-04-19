package org.bxwbb.util;

import java.awt.*;

public class ColorUtil {

    private ColorUtil() {
    }

    public static Color brighter(Color color) {
        return brighter(color, 1.3f);
    }

    public static Color brighter(Color color, float factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();

        r = Math.min(255, (int) (r * factor));
        g = Math.min(255, (int) (g * factor));
        b = Math.min(255, (int) (b * factor));

        return new Color(r, g, b, a);
    }

    public static Color brighter(Color color, int count) {
        return brighter(color, count, 1.3f);
    }

    public static Color brighter(Color color, int count, float factor) {
        for (int i = 0; i < count; i++) {
            color = brighter(color, factor);
        }
        return color;
    }

    public static Color darker(Color color) {
        return darker(color, 0.7f);
    }

    public static Color darker(Color color, float factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();

        r = Math.max(0, (int) (r * factor));
        g = Math.max(0, (int) (g * factor));
        b = Math.max(0, (int) (b * factor));

        return new Color(r, g, b, a);
    }

    public static Color darker(Color color, int count, float factor) {
        for (int i = 0; i < count; i++) {
            color = darker(color, factor);
        }
        return color;
    }

    public static Color darker(Color color, int count) {
        return darker(color, count, 0.7f);
    }

}
