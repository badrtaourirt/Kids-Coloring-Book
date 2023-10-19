package com.coloring.page;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.plattysoft.leonids.ParticleSystem;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DrawingPictureGlow extends View {
    public static Bitmap canvasBitmap;
    com.coloring.page.DrawActivityGlow a;
    int b;
    private Paint canvasPaint;
    private Paint circlePaint;
    private Canvas drawCanvas;
    private boolean drawEraser;
    private Path drawPath;
    private DrawingPictureGlow drawingPicture;
    public int gapPlaySound = 0;
    public Bitmap kidBitmap;
    public boolean kidBitmapDrawn;
    public boolean kidBitmapNeedDrawn;
    private List<Integer> mColoursList;
    private int mCurrentColorIndex = 0;
    private Paint mPaintLazer;
    private Paint mPaintMain;
    private float mX;
    private float mY;

    public DrawingPictureGlow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = (com.coloring.page.DrawActivityGlow) context;
        this.drawingPicture = this;
        this.drawEraser = false;
        setupDrawing();
    }

    private int getColorIndex() {
        int size = this.mColoursList.size();
        PrintStream printStream = System.err;
        printStream.println("sizeee ::" + size + "--" + this.b);
        if (this.mCurrentColorIndex == size) {
            this.mCurrentColorIndex = 0;
        }
        this.mCurrentColorIndex = this.b;
        return this.mCurrentColorIndex;
    }

    private List<Integer> initRainbowColors() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 100; i++) {
            arrayList.add(Integer.valueOf(Color.rgb((i * 255) / 100, 255, 0)));
        }
        for (int i2 = 100; i2 > 0; i2--) {
            arrayList.add(Integer.valueOf(Color.rgb(255, (i2 * 255) / 100, 0)));
        }
        for (int i3 = 0; i3 < 100; i3++) {
            arrayList.add(Integer.valueOf(Color.rgb(255, 0, (i3 * 255) / 100)));
        }
        for (int i4 = 100; i4 > 0; i4--) {
            arrayList.add(Integer.valueOf(Color.rgb((i4 * 255) / 100, 0, 255)));
        }
        for (int i5 = 0; i5 < 100; i5++) {
            arrayList.add(Integer.valueOf(Color.rgb(0, (i5 * 255) / 100, 255)));
        }
        for (int i6 = 100; i6 > 0; i6--) {
            arrayList.add(Integer.valueOf(Color.rgb(0, 255, (i6 * 255) / 100)));
        }
        arrayList.add(Integer.valueOf(Color.rgb(0, 255, 0)));
        Collections.shuffle(arrayList);
        return arrayList;
    }

    private void setBrushSize() {
        this.mPaintMain.setStrokeWidth((float) com.coloring.page.MyConstant.BRUSH_SIZE);
        this.mPaintLazer.setStrokeWidth((float) (com.coloring.page.MyConstant.BRUSH_SIZE / 3));
    }

    private void setDefaultBrushSize() {
        com.coloring.page.MyConstant.BRUSH_SIZE = 20;
        this.mPaintMain.setStrokeWidth((float) com.coloring.page.MyConstant.BRUSH_SIZE);
        this.mPaintLazer.setStrokeWidth((float) (com.coloring.page.MyConstant.BRUSH_SIZE / 3));
    }

    private void setPathEffect() {
        PathEffect pathEffect;
        Paint paint;
        int i = com.coloring.page.MyConstant.TYPE_GLOW_PEN;
        if (i == 2) {
            Path path = new Path();
            path.addCircle(0.0f, 0.0f, (float) com.coloring.page.MyConstant.TYPE_GLOW_RADIUS, Path.Direction.CCW);
            int i2 = com.coloring.page.MyConstant.TYPE_GLOW_RADIUS;
            this.mPaintLazer.setPathEffect(new PathDashPathEffect(path, i2 == 5 ? 15.0f : (i2 != 8 && i2 == 10) ? 35.0f : 20.0f, 20.0f, PathDashPathEffect.Style.ROTATE));
            return;
        }
        if (i == 3) {
            int i3 = com.coloring.page.MyConstant.TYPE_GLOW_RADIUS;
            if (i3 == 5) {
                paint = this.mPaintLazer;
                pathEffect = new DashPathEffect(new float[]{15.0f, 15.0f, 15.0f, 15.0f}, 0.0f);
            } else if (i3 == 8) {
                paint = this.mPaintLazer;
                pathEffect = new DashPathEffect(new float[]{25.0f, 25.0f, 25.0f, 25.0f}, 0.0f);
            } else if (i3 == 10) {
                paint = this.mPaintLazer;
                pathEffect = new DashPathEffect(new float[]{35.0f, 35.0f, 35.0f, 35.0f}, 0.0f);
            } else {
                paint = this.mPaintLazer;
                pathEffect = new DashPathEffect(new float[]{15.0f, 15.0f, 15.0f, 15.0f}, 0.0f);
            }
        } else if (i == 1) {
            paint = this.mPaintLazer;
            pathEffect = new CornerPathEffect(100.0f);
        } else {
            return;
        }
        paint.setPathEffect(pathEffect);
    }

    private void setPointOfSparkImage(float f, float f2) {
        com.coloring.page.DrawActivityGlow.iv.setX(f);
        com.coloring.page.DrawActivityGlow.iv.setY(f2);
    }

    private void setupDrawing() {
        this.mColoursList = initRainbowColors();
        this.drawPath = new Path();
        this.canvasPaint = new Paint(4);
        this.circlePaint = new Paint(1);
        this.circlePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mPaintMain = new Paint();
        this.mPaintMain.setAntiAlias(true);
        this.mPaintMain.setPathEffect(new CornerPathEffect(100.0f));
        this.mPaintMain.setStyle(Paint.Style.STROKE);
        this.mPaintMain.setStrokeJoin(Paint.Join.ROUND);
        this.mPaintMain.setStrokeCap(Paint.Cap.ROUND);
        this.mPaintMain.setMaskFilter(new BlurMaskFilter(17.0f, BlurMaskFilter.Blur.NORMAL));
        this.mPaintMain.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        this.mPaintLazer = new Paint();
        this.mPaintLazer.setAntiAlias(true);
        this.mPaintLazer.setStyle(Paint.Style.STROKE);
        this.mPaintLazer.setStrokeJoin(Paint.Join.ROUND);
        this.mPaintLazer.setStrokeCap(Paint.Cap.ROUND);
        this.mPaintLazer.setColor(-1);
        this.mPaintLazer.setMaskFilter(new BlurMaskFilter(1.0f, BlurMaskFilter.Blur.NORMAL));
        this.mPaintLazer.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        setDefaultBrushSize();
    }


    @Override
    public void dispatchDraw(Canvas canvas) {
        this.gapPlaySound++;
        if (this.gapPlaySound == 100) {
            this.gapPlaySound = 0;
        }
        canvas.save();
        canvas.drawBitmap(canvasBitmap, 0.0f, 0.0f, this.canvasPaint);
        canvas.drawPath(this.drawPath, this.mPaintMain);
        canvas.drawPath(this.drawPath, this.mPaintLazer);
        Bitmap bitmap = this.kidBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.canvasPaint);
        }
        if (com.coloring.page.MyConstant.SELECTED_TOOL == 2 && this.drawEraser) {
            if (this.gapPlaySound % 30 == 0) {
                this.a.mediaPlayer.playSound(R.raw.eraser);
            }
            this.circlePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            canvas.drawCircle(com.coloring.page.MyConstant.eraseX, com.coloring.page.MyConstant.eraseY, com.coloring.page.MyConstant.eraseR, this.circlePaint);
            this.circlePaint.setColor(-1);
            float f = com.coloring.page.MyConstant.eraseX;
            float f2 = com.coloring.page.MyConstant.eraseY;
            double d = (double) com.coloring.page.MyConstant.eraseR;
            Double.isNaN(d);
            canvas.drawCircle(f, f2, (float) (d * 0.9d), this.circlePaint);
        }
        canvas.restore();
        super.dispatchDraw(canvas);
    }


    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        com.coloring.page.MyConstant.onSizeCalled++;
        if (com.coloring.page.MyConstant.onSizeCalled < 2) {
            if (com.coloring.page.MyConstant.drawWidth == 0 || com.coloring.page.MyConstant.drawHeight == 0) {
                com.coloring.page.MyConstant.drawWidth = 700;
                com.coloring.page.MyConstant.drawHeight = 700;
            }
            if (canvasBitmap == null) {
                canvasBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
                this.drawCanvas = new Canvas(canvasBitmap);
            }
            com.coloring.page.MyConstant.drawWidth = i;
            com.coloring.page.MyConstant.drawHeight = i2;
            com.coloring.page.MyConstant.pixels = new int[(com.coloring.page.MyConstant.drawWidth * com.coloring.page.MyConstant.drawHeight)];
            com.coloring.page.DrawActivityGlow.getDrawActivity().insertBitmap();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        com.coloring.page.MyConstant.eraseX = x;
        com.coloring.page.MyConstant.eraseY = y;
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mX = motionEvent.getX();
            this.mY = motionEvent.getY();
            this.mPaintMain.setStrokeWidth((float) com.coloring.page.MyConstant.BRUSH_SIZE);
            setPathEffect();
            if (com.coloring.page.MyConstant.SELECTED_TOOL == 2) {
                int color = ContextCompat.getColor(this.a, R.color.black1);
                this.mPaintMain.setColor(color);
                this.mPaintLazer.setColor(color);
                this.mPaintMain.setStrokeWidth((float) com.coloring.page.MyConstant.ERASER_WIDTH);
            } else if (com.coloring.page.MyConstant.IS_RAINBOW_COLOR_SELECTED) {
                this.mPaintLazer.setColor(-1);
                this.b = new Random().nextInt(600) + 1;
                this.mPaintMain.setColor(this.mColoursList.get(getColorIndex()).intValue());
            } else {
                setPathColor(com.coloring.page.MyConstant.DRAW_COLOR);
                setBrushSize();
            }
            this.drawPath.moveTo(x, y);
            this.drawEraser = true;
        } else if (action == 1) {
            setPointOfSparkImage(x, y);
            startOneShotParticle(com.coloring.page.DrawActivityGlow.iv);
            this.a.mediaPlayer.playColorRandomSound();
            this.kidBitmapNeedDrawn = true;
            this.drawPath.lineTo(this.mX, this.mY);
            this.drawCanvas.drawPath(this.drawPath, this.mPaintMain);
            this.drawCanvas.drawPath(this.drawPath, this.mPaintLazer);
            this.drawPath.reset();
            this.drawEraser = false;
            Bitmap bitmap = this.kidBitmap;
        } else if (action != 2) {
            return false;
        } else {
            if (com.coloring.page.MyConstant.SELECTED_TOOL == 2) {
                int color2 = ContextCompat.getColor(this.a, R.color.black1);
                this.mPaintMain.setColor(color2);
                this.mPaintLazer.setColor(color2);
                this.mPaintMain.setStrokeWidth((float) com.coloring.page.MyConstant.ERASER_WIDTH);
            } else {
                setBrushSize();
                invalidate();
            }
            float abs = Math.abs(x - this.mX);
            float abs2 = Math.abs(y - this.mY);
            if (abs >= 0.0f || abs2 >= 0.0f) {
                Path path = this.drawPath;
                float f = this.mX;
                float f2 = this.mY;
                path.quadTo(f, f2, (f + x) / 2.0f, (f2 + y) / 2.0f);
                this.mX = x;
                this.mY = y;
            }
        }
        invalidate();
        return true;
    }

    public void setKidsImage() {
        invalidate();
        Bitmap bitmap = canvasBitmap;
        if (bitmap != null) {
            bitmap.eraseColor(-1);
        } else {
            onSizeChanged(com.coloring.page.MyConstant.drawWidth, com.coloring.page.MyConstant.drawHeight, com.coloring.page.MyConstant.drawWidth, com.coloring.page.MyConstant.drawHeight);
        }
        this.kidBitmapDrawn = false;
    }

    public void setPathColor(int i) {
        if (!com.coloring.page.MyConstant.IS_RAINBOW_COLOR_SELECTED) {
            this.mPaintLazer.setColor(-1);
            this.mPaintMain.setColor(i);
            this.mPaintMain.setStrokeWidth((float) com.coloring.page.MyConstant.BRUSH_SIZE);
            this.mPaintLazer.setStrokeWidth((float) (com.coloring.page.MyConstant.BRUSH_SIZE / 3));
        }
    }

    public void startNew() {
        this.kidBitmap = null;
        this.drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void startOneShotParticle(View view) {
        new ParticleSystem((Activity) this.a, 5, (int) R.drawable.spark_bluedot, 200).setSpeedRange(0.45f, 0.75f).oneShot(view, 4);
        new ParticleSystem((Activity) this.a, 5, (int) R.drawable.effect_star1, 300).setSpeedRange(0.35f, 0.7f).oneShot(view, 3);
        new ParticleSystem((Activity) this.a, 5, (int) R.drawable.effect_star2, 400).setSpeedRange(0.3f, 0.68f).oneShot(view, 2);
        new ParticleSystem((Activity) this.a, 5, (int) R.drawable.effect_star3, 250).setSpeedRange(0.42f, 0.6f).oneShot(view, 4);
        new ParticleSystem((Activity) this.a, 5, (int) R.drawable.spark_yellowdot, 350).setSpeedRange(0.37f, 0.65f).oneShot(view, 3);
    }
}
