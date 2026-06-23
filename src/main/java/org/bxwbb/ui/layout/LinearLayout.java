package org.bxwbb.ui.layout;

import org.bxwbb.ui.BaseUI;
import java.util.List;

public class LinearLayout extends AbstractLinearLayout {

    public LinearLayout() {
        super();
    }

    public LinearLayout(int spacingX, int spacingY) {
        super();
        setSpacingX(spacingX);
        setSpacingY(spacingY);
    }

    @Override
    public void layout(BaseUI parent) {
        List<BaseUI> children = parent.getChildren();
        int size = children.size();
        if (size == 0) return;

        int spaceX = getSpacingX();
        int spaceY = getSpacingY();

        int pW = parent.getContentWidth();
        int pH = parent.getContentHeight();

        Orientation ori = getOrientation();
        Alignment align = getAlignment();

        if (ori == Orientation.HORIZONTAL) {
            calcFlexH(children, size, pW, spaceX);

            int totalW = 0;
            int maxH = 0;
            int visibleCount = 0;
            for (int i = 0; i < size; i++) {
                BaseUI c = children.get(i);
                if (!c.isVisible() || c.isDontLayout()) continue;
                int w = c.getWidth();
                totalW += w;
                int h = c.getHeight();
                if (h > maxH) maxH = h;
                visibleCount++;
            }
            totalW += spaceX * Math.max(0, visibleCount - 1);

            int startX = 0;
            int ax = align.x;
            if (ax == 1) {
                startX = pW - totalW;
            } else if (ax == 0) {
                startX = (pW - totalW) >> 1;
            }

            int startY = 0;
            int ay = align.y;
            if (ay == 1) {
                startY = pH - maxH;
            } else if (ay == 0) {
                startY = (pH - maxH) >> 1;
            }

            for (BaseUI c : children) {
                if (!c.isVisible() || c.isDontLayout()) continue;
                c.setGreatHeight(pH);
                c.setLayout(startX, startY);
                startX += c.getWidth() + spaceX;
            }

        } else {
            calcFlexV(children, size, pH, spaceY);

            int totalH = 0;
            int maxW = 0;
            int visibleCount = 0;
            for (int i = 0; i < size; i++) {
                BaseUI c = children.get(i);
                if (!c.isVisible() || c.isDontLayout()) continue;
                int h = c.getHeight();
                totalH += h;
                int w = c.getWidth();
                if (w > maxW) maxW = w;
                visibleCount++;
            }
            totalH += spaceY * Math.max(0, visibleCount - 1);

            int startY = 0;
            int ay = align.y;
            if (ay == 1) {
                startY = pH - totalH;
            } else if (ay == 0) {
                startY = (pH - totalH) >> 1;
            }

            int startX = 0;
            int ax = align.x;
            if (ax == 1) {
                startX = pW - maxW;
            } else if (ax == 0) {
                startX = (pW - maxW) >> 1;
            }

            for (BaseUI c : children) {
                if (!c.isVisible() || c.isDontLayout()) continue;
                c.setGreatWidth(pW);
                c.setLayout(startX, startY);
                startY += c.getHeight() + spaceY;
            }
        }
    }

    private void calcFlexH(List<BaseUI> children, int size, int usable, int space) {
        int totalSpace;
        int totalMin = 0;
        int visibleCount = 0;

        for (int i = 0; i < size; i++) {
            BaseUI c = children.get(i);
            if (!c.isVisible() || c.isDontLayout()) continue;
            c.setGreatWidth(c.getMinWidth());
            totalMin += c.getWidth();
            visibleCount++;
        }
        totalSpace = space * Math.max(0, visibleCount - 1);

        int free = usable - totalMin - totalSpace;

        boolean[] frozen = new boolean[size];
        int retry = 6;

        while (free > 0 && retry-- > 0) {
            float totalWeight = 0;
            for (int i = 0; i < size; i++) {
                BaseUI c = children.get(i);
                if (!c.isVisible() || c.isDontLayout() || frozen[i]) continue;
                totalWeight += c.getWeightWidth();
            }
            if (totalWeight <= 0) break;

            int used = 0;
            for (int i = 0; i < size; i++) {
                BaseUI c = children.get(i);
                if (!c.isVisible() || c.isDontLayout() || frozen[i]) continue;

                int add = (int) (free * (c.getWeightWidth() / totalWeight));
                int old = c.getWidth();
                c.setGreatWidth(old + add);
                int now = c.getWidth();

                if (now == old) frozen[i] = true;
                used += now - old;
            }
            free -= used;
        }
    }

    private void calcFlexV(List<BaseUI> children, int size, int usable, int space) {
        int totalSpace;
        int totalMin = 0;
        int visibleCount = 0;

        for (int i = 0; i < size; i++) {
            BaseUI c = children.get(i);
            if (!c.isVisible() || c.isDontLayout()) continue;
            c.setGreatHeight(c.getMinHeight());
            totalMin += c.getHeight();
            visibleCount++;
        }
        totalSpace = space * Math.max(0, visibleCount - 1);

        int free = usable - totalMin - totalSpace;

        boolean[] frozen = new boolean[size];
        int retry = 6;

        while (free > 0 && retry-- > 0) {
            float totalWeight = 0;
            for (int i = 0; i < size; i++) {
                BaseUI c = children.get(i);
                if (!c.isVisible() || c.isDontLayout() || frozen[i]) continue;
                totalWeight += c.getWeightHeight();
            }
            if (totalWeight <= 0) break;

            int used = 0;
            for (int i = 0; i < size; i++) {
                BaseUI c = children.get(i);
                if (!c.isVisible() || c.isDontLayout() || frozen[i]) continue;

                int add = (int) (free * (c.getWeightHeight() / totalWeight));
                int old = c.getHeight();
                c.setGreatHeight(old + add);
                int now = c.getHeight();

                if (now == old) frozen[i] = true;
                used += now - old;
            }
            free -= used;
        }
    }
}