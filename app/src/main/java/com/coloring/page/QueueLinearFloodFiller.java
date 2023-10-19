package com.coloring.page;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

public class QueueLinearFloodFiller {
    protected int a;
    protected int b;
    protected Bitmap c;
    protected boolean[] d;
    protected Queue<FloodFillRange> e;
    protected int[] f;
    protected int[] g;
    protected int h;

    protected class FloodFillRange {
        public int endX;
        public int f4Y;
        public int startX;

        public FloodFillRange(QueueLinearFloodFiller queueLinearFloodFiller, int i, int i2, int i3) {
            this.startX = i;
            this.endX = i2;
            this.f4Y = i3;
        }
    }

    public QueueLinearFloodFiller(int i, int i2) {
        this.c = null;
        this.g = new int[]{0, 0, 0};
        this.h = 0;
        this.b = 0;
        this.a = 0;
        this.f = new int[]{0, 0, 0};
        this.h = com.coloring.page.MyConstant.drawWidth;
        this.b = com.coloring.page.MyConstant.drawHeight;
        setFillColor(i2);
        setTargetColor(i);
    }

    public QueueLinearFloodFiller(Bitmap bitmap) {
        this.c = null;
        this.g = new int[]{0, 0, 0};
        this.h = 0;
        this.b = 0;
        this.a = 0;
        this.f = new int[]{0, 0, 0};
        copyImage(bitmap);
    }


    public void a() {
        this.d = new boolean[com.coloring.page.MyConstant.pixels.length];
        this.e = new LinkedList();
    }


    public void a(int i, int i2) {
        int i3 = (this.h * i2) + i;
        int i4 = i;
        do {
            com.coloring.page.MyConstant.pixels[i3] = this.a;
            boolean[] zArr = this.d;
            zArr[i3] = true;
            i4--;
            i3--;
            if (i4 < 0 || zArr[i3] || !a(i3)) {
                int i5 = i4 + 1;
                int i6 = (this.h * i2) + i;
            }
            com.coloring.page.MyConstant.pixels[i3] = this.a;
            boolean[] zArr2 = this.d;
            zArr2[i3] = true;
            i4--;
            i3--;
            break;
        } while (!a(i3));
        int i52 = i4 + 1;
        int i62 = (this.h * i2) + i;
        do {
            try{
            com.coloring.page.MyConstant.pixels[i62] = this.a;
            boolean[] zArr3 = this.d;
            zArr3[i62] = true;
            i++;
            i62++;
            if (i >= this.h || zArr3[i62] || !a(i62)) {
                this.e.offer(new FloodFillRange(this, i52, i - 1, i2));
            }
            if(i62<MyConstant.pixels.length)
            {
            MyConstant.pixels[i62] = this.a;
            boolean[] zArr32 = this.d;
            zArr32[i62] = true;
            i++;
            i62++;
            break;}
            }catch (Exception e){
                Log.e("aaaaaaaaa",e.toString());
                break;
            }
            break;
        } while (!a(i62));
        this.e.offer(new FloodFillRange(this, i52, i - 1, i2));
    }


    public boolean a(int i) {
        int[] iArr = com.coloring.page.MyConstant.pixels;
        int i2 = (iArr[i] >>> 16) & 255;
        int i3 = (iArr[i] >>> 8) & 255;
        int i4 = iArr[i] & 255;
        int[] iArr2 = this.f;
        int i5 = iArr2[0];
        int[] iArr3 = this.g;
        return i2 >= i5 - iArr3[0] && i2 <= iArr2[0] + iArr3[0] && i3 >= iArr2[1] - iArr3[1] && i3 <= iArr2[1] + iArr3[1] && i4 >= iArr2[2] - iArr3[2] && i4 <= iArr2[2] + iArr3[2];
    }

    public void copyImage(Bitmap bitmap) {
        this.h = bitmap.getWidth();
        this.b = bitmap.getHeight();
        this.c = Bitmap.createBitmap(this.h, this.b, Bitmap.Config.RGB_565);
        new Canvas(this.c).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        int i = this.h;
        int i2 = this.b;
        com.coloring.page.MyConstant.pixels = new int[(i * i2)];
        this.c.getPixels(com.coloring.page.MyConstant.pixels, 0, i, 1, 1, i - 1, i2 - 1);
    }

    public void floodFill(int i, int i2) {
        System.err.println("boook:: floodFill");
        a();
        int[] iArr = this.f;
        if (iArr[0] == 0) {
            int i3 = com.coloring.page.MyConstant.pixels[(this.h * i2) + i];
            iArr[0] = (i3 >> 16) & 255;
            iArr[1] = (i3 >> 8) & 255;
            iArr[2] = i3 & 255;
        }
        a(i, i2);
        while (this.e.size() > 0) {
            FloodFillRange remove = this.e.remove();
            int i4 = this.h;
            int i5 = remove.f4Y;
            int i6 = remove.startX;
            int i7 = ((i5 + 1) * i4) + i6;
            int i8 = (i4 * (i5 - 1)) + i6;
            int i9 = i5 - 1;
            int i10 = i5 + 1;
            while (i6 <= remove.endX) {
                if (remove.f4Y > 0 && !this.d[i8] && a(i8)) {
                    a(i6, i9);
                }
                if (remove.f4Y < this.b - 1 && !this.d[i7] && a(i7)) {
                    try {
                        a(i6, i10);
                    }catch (Exception e){}
                }
                i7++;
                i8++;
                i6++;
            }
        }
    }

    public int getFillColor() {
        return this.a;
    }

    public Bitmap getImage() {
        return this.c;
    }

    public int[] getTolerance() {
        return this.g;
    }

    public void setFillColor(int i) {
        this.a = i;
    }

    public void setTargetColor(int i) {
        this.f[0] = Color.red(i);
        this.f[1] = Color.green(i);
        this.f[2] = Color.blue(i);
    }

    public void setTolerance(int i) {
        System.err.println("boook:: setTolerance");
        this.g = new int[]{i, i, i};
    }

    public void setTolerance(int[] iArr) {
        this.g = iArr;
    }

    public void useImage(Bitmap bitmap) {
        this.h = bitmap.getWidth();
        this.b = bitmap.getHeight();
        this.c = bitmap;
        int i = this.h;
        int i2 = this.b;
        com.coloring.page.MyConstant.pixels = new int[(i * i2)];
        this.c.getPixels(com.coloring.page.MyConstant.pixels, 0, i, 1, 1, i - 1, i2 - 1);
    }
}
