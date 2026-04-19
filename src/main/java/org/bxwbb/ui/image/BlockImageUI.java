package org.bxwbb.ui.image;

import org.bxwbb.ui.BaseUI;
import org.bxwbb.ui.UI;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BlockImageUI implements UI {

    @Override
    public void update(BaseUI component) {}

    @Override
    public void render(Graphics2D g2d, BaseUI component) {
        BlockImage blockImage = (BlockImage) component;
        Image image = blockImage.getImage();
        if (image == null) return;

        int absX = blockImage.getAbsoluteX();
        int absY = blockImage.getAbsoluteY();
        int w = blockImage.getWidth();
        int h = blockImage.getHeight();
        g2d.setClip(absX, absY, w, h);

        int imgW = image.getWidth(null);
        int imgH = image.getHeight(null);
        if (imgW <= 0 || imgH <= 0) return;

        int offsetX = blockImage.getOffsetX();
        int offsetY = blockImage.getOffsetY();
        ScaleMode scaleMode = blockImage.getScaleMode();

        int drawX, drawY, drawW, drawH;

        switch (scaleMode) {
            case SCALE_STRETCH:
                drawX = absX + offsetX;
                drawY = absY + offsetY;
                drawW = w;
                drawH = h;
                break;
            case SCALE_FIT:
                float scale = Math.min((float)w / imgW, (float)h / imgH);
                drawW = Math.round(imgW * scale);
                drawH = Math.round(imgH * scale);
                drawX = absX + (w - drawW)/2 + offsetX;
                drawY = absY + (h - drawH)/2 + offsetY;
                break;
            case SCALE_CENTER:
                drawW = imgW;
                drawH = imgH;
                drawX = absX + (w - drawW)/2 + offsetX;
                drawY = absY + (h - drawH)/2 + offsetY;
                break;
            default:
                drawX = absX + offsetX;
                drawY = absY + offsetY;
                drawW = imgW;
                drawH = imgH;
                break;
        }

        Composite old = g2d.getComposite();
        float alpha = blockImage.getAlpha();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        Color tint = blockImage.getMixColor();
        if(tint != null){
            BufferedImage result = tintImage(image, tint);
            g2d.drawImage(result, drawX, drawY, drawW, drawH, null);
        }else{
            g2d.drawImage(image, drawX, drawY, drawW, drawH, null);
        }

        g2d.setComposite(old);
    }

    private BufferedImage tintImage(Image original, Color tint) {
        BufferedImage img = toBufferedImage(original);
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        float tr = tint.getRed() / 255f;
        float tg = tint.getGreen() / 255f;
        float tb = tint.getBlue() / 255f;
        float ta = tint.getAlpha() / 255f;

        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                int argb = img.getRGB(x,y);
                int a = (argb>>24)&0xFF;
                if(a==0){
                    res.setRGB(x,y,0);
                    continue;
                }
                int r = (int)((((argb>>16)&0xFF)/255f)*tr*255f);
                int g = (int)((((argb>>8)&0xFF)/255f)*tg*255f);
                int b = (int)(((argb&0xFF)/255f)*tb*255f);
                a = (int)(a*ta);

                r = clamp(r);
                g = clamp(g);
                b = clamp(b);
                a = clamp(a);

                res.setRGB(x,y,(a<<24)|(r<<16)|(g<<8)|b);
            }
        }
        return res;
    }

    private BufferedImage toBufferedImage(Image img) {
        if(img instanceof BufferedImage) return (BufferedImage)img;
        BufferedImage buf = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buf.createGraphics();
        g.drawImage(img,0,0,null);
        g.dispose();
        return buf;
    }

    private int clamp(int v) {
        return Math.max(0,Math.min(255,v));
    }
}