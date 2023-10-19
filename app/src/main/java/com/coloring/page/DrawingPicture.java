package com.coloring.page;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.ViewCompat;

import com.plattysoft.leonids.ParticleSystem;

import java.io.PrintStream;

public class DrawingPicture extends View {
    public static Bitmap canvasBitmap;
    DrawActivity a;
    private Paint canvasPaint;
    private Paint circlePaint;
    private Canvas drawCanvas;
    private boolean drawEraser;
    private Paint drawPaint;
    private Path drawPath;
    private DrawingPicture drawingPicture;
    public int gapPlaySound = 0;
    public Bitmap kidBitmap;
    public boolean kidBitmapDrawn;
    public boolean kidBitmapNeedDrawn;
    private float mX;
    private float mY;

    public DrawingPicture(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = (DrawActivity) context;
        this.drawingPicture = this;
        this.drawEraser = false;
        setupDrawing();

    }

    private void setDefaultBrushSize() {
        com.coloring.page.MyConstant.BRUSH_SIZE = 20;
        this.drawPaint.setStrokeWidth((float) com.coloring.page.MyConstant.BRUSH_SIZE);
    }

    private void setPointOfSparkImage(float f, float f2) {
        DrawActivity.iv.setX(f);
        DrawActivity.iv.setY(f2);
    }

    private void setupDrawing() {
        this.drawPath = new Path();
        this.drawPaint = new Paint();
        this.drawPaint.setAntiAlias(true);
        this.drawPaint.setStyle(Paint.Style.STROKE);
        this.drawPaint.setStrokeJoin(Paint.Join.ROUND);
        this.drawPaint.setStrokeCap(Paint.Cap.ROUND);
        this.drawPaint.setPathEffect(new CornerPathEffect(10.0f));
        this.canvasPaint = new Paint(4);
        this.circlePaint = new Paint(1);
        this.circlePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
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
        canvas.drawPath(this.drawPath, this.drawPaint);
        Bitmap bitmap = this.kidBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.canvasPaint);
        }
        if (com.coloring.page.MyConstant.SELECTED_TOOL == 2 && this.drawEraser) {
            if (this.gapPlaySound % 30 == 0) {
                this.a.mediaPlayer.playSound(R.raw.eraser);
            }
            this.drawPaint.setShader((Shader) null);
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
            System.err.println("  MyConstant.pixels:" + com.coloring.page.MyConstant.pixels.length);
            DrawActivity.getDrawActivity().insertBitmap();
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
            if (com.coloring.page.MyConstant.SELECTED_TOOL != 0) {
                this.drawPaint.setStrokeWidth((float) com.coloring.page.MyConstant.BRUSH_SIZE);
                if (com.coloring.page.MyConstant.SELECTED_TOOL != 2 && !DrawActivity.ispatternClicked) {
                    setPathColor(com.coloring.page.MyConstant.DRAW_COLOR);
                } else if (com.coloring.page.MyConstant.SELECTED_TOOL == 2) {
                    this.drawPaint.setShader((Shader) null);
                    this.drawPaint.setColor(-1);
                    this.drawPaint.setStrokeWidth((float) com.coloring.page.MyConstant.ERASER_WIDTH);
                } else {
                    this.drawPaint.setColor(-1);
                }
                this.drawPath.moveTo(x, y);
                this.drawEraser = true;
            }
        } else if (action == 1) {
            setPointOfSparkImage(this.mX, this.mY);
            this.a.mediaPlayer.playColorRandomSound();
            startOneShotParticle(DrawActivity.iv);
            if (com.coloring.page.MyConstant.SELECTED_TOOL == 0) {
                if (this.kidBitmap != null && (!this.kidBitmapDrawn || this.kidBitmapNeedDrawn)) {
                    PrintStream printStream = System.err;
                    printStream.println("ooo::" + this.drawCanvas + "--" + this.kidBitmap + "---" + this.canvasPaint);
                    this.drawCanvas.drawBitmap(this.kidBitmap, 0.0f, 0.0f, this.canvasPaint);
                    this.kidBitmapDrawn = true;
                    this.kidBitmapNeedDrawn = false;
                }
                Bitmap bitmap = canvasBitmap;
                int[] iArr = com.coloring.page.MyConstant.pixels;
                int i = com.coloring.page.MyConstant.drawWidth;
                bitmap.getPixels(iArr, 0, i, 0, 0, i, com.coloring.page.MyConstant.drawHeight);
                int pixel = canvasBitmap.getPixel((int) this.mX, (int) this.mY);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                if (red < 255 && red == green && red == blue) {
                    if (red <= 0) {
                        return false;
                    }
                    pixel = -1;
                }
                invalidate();
                QueueLinearFloodFiller queueLinearFloodFiller = new QueueLinearFloodFiller(pixel, com.coloring.page.MyConstant.DRAW_COLOR);
                queueLinearFloodFiller.setTolerance(60);
                try {
                    queueLinearFloodFiller.floodFill((int) this.mX, (int) this.mY);
                }catch (Exception e){}
                Bitmap bitmap2 = canvasBitmap;
                int[] iArr2 = com.coloring.page.MyConstant.pixels;
                int i2 = com.coloring.page.MyConstant.drawWidth;
                bitmap2.setPixels(iArr2, 0, i2, 0, 0, i2, com.coloring.page.MyConstant.drawHeight);
            } else {
                this.kidBitmapNeedDrawn = true;
                this.drawPath.lineTo(this.mX, this.mY);
                this.drawCanvas.drawPath(this.drawPath, this.drawPaint);
                this.drawPath.reset();
                this.drawEraser = false;
            }
            Bitmap bitmap3 = this.kidBitmap;
        } else if (action != 2) {
            return false;
        } else {
            if (com.coloring.page.MyConstant.SELECTED_TOOL != 0) {
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
        System.err.println("color cliked inside color");
        this.drawPaint.setShader((Shader) null);
        this.drawPaint.setColor(i);
    }

    public void setPattern(String str) {
        System.err.println("color cliked inside pattern");
        invalidate();
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(str, "drawable", this.a.getPackageName()));
        Shader.TileMode tileMode = Shader.TileMode.REPEAT;
        BitmapShader bitmapShader = new BitmapShader(decodeResource, tileMode, tileMode);
        this.drawPaint.setColor(-1);
        this.drawPaint.setShader(bitmapShader);
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
