package com.example.metronome;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Klasa odpowiedzialna za przeliczanie wymiarów kółek, zwracanie ich ilości,
 * współrzędnych, promienia, oznaczenia wypełnionego (aktualnego) kółka
 */
class DotsSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private int count, max;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count < 0 && count > max) throw new IllegalArgumentException();
        this.count = count;
        this.update();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max < 0) throw new IllegalArgumentException();
        this.max = max;
        this.update();
    }

    public void setParameters (int count, int max) {
        if (count < 0 && count > max) throw new IllegalArgumentException();
        this.count = count;
        if (max < 0) throw new IllegalArgumentException();
        this.max = max;
        this.update();
    }

    public DotsSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    private void update() {
        Canvas canvas = getHolder().lockCanvas();
        this.onDraw(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        this.update();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        Paint paintFill = new Paint();
        Paint paintStroke = new Paint();
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(Color.WHITE);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setColor(Color.WHITE);

        int w = canvas.getWidth();
        int d = w/7;
        int r = d/3;

        for (int i=0; i<max; ++i) {
            Paint paint = i < count ? paintFill : paintStroke;
            canvas.drawCircle((w-(max-1)*d)/2+i*d, d/2, r, paint);
        }
    }
}
