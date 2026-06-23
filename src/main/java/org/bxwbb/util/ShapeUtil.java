package org.bxwbb.util;

import java.awt.Rectangle;

public class ShapeUtil {

    /**
     * 求两个矩形的交集矩形
     * @param r1 矩形1
     * @param r2 矩形2
     * @return 交集矩形；无交集返回宽高为0的空矩形
     */
    public static Rectangle intersectRect(Rectangle r1, Rectangle r2) {
        int x = Math.max(r1.x, r2.x);
        int y = Math.max(r1.y, r2.y);
        int x2 = Math.min(r1.x + r1.width, r2.x + r2.width);
        int y2 = Math.min(r1.y + r1.height, r2.y + r2.height);

        int w = x2 - x;
        int h = y2 - y;

        if (w <= 0 || h <= 0) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(x, y, w, h);
    }
}