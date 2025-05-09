package com.example.drawingapp;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private Canvas canvas;
    private float x, y;
    private static final float TOUCH_TOLERANCE = 4;

    private boolean isEraser = false;
    private int brushColor = Color.BLACK;
    private float brushSize = 10f;
    private int backgroundColor = Color.WHITE;

    private List<Path> pathList = new ArrayList<>();
    private List<Paint> paintList = new ArrayList<>();

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(brushSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(brushColor);
    }

    public void setColor(int color) {
        brushColor = color;
        isEraser = false;
        paint.setColor(brushColor);
    }

    public void setStrokeWidth(float width) {
        brushSize = width;
        paint.setStrokeWidth(width);
    }

    public void setEraserMode(boolean eraser) {
        isEraser = eraser;
        if (eraser) {
            paint.setColor(backgroundColor);
        } else {
            paint.setColor(brushColor);
        }
    }

    public void clear() {
        pathList.clear();
        paintList.clear();
        path.reset();
        invalidate();
    }

    public void undo() {
        if (!pathList.isEmpty()) {
            pathList.remove(pathList.size() - 1);
            paintList.remove(paintList.size() - 1);
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(backgroundColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < pathList.size(); i++) {
            canvas.drawPath(pathList.get(i), paintList.get(i));
        }
        canvas.drawPath(path, paint);
    }

    private void touchStart(float x, float y) {
        path.moveTo(x, y);
        this.x = x;
        this.y = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - this.x);
        float dy = Math.abs(y - this.y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(this.x, this.y, (x + this.x)/2, (y + this.y)/2);
            this.x = x;
            this.y = y;
        }
    }

    private void touchUp() {
        path.lineTo(x, y);
        Paint newPaint = new Paint(paint);
        paintList.add(newPaint);
        pathList.add(path);
        path = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}
