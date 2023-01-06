package br.gov.ma.detran.examespraticosmobile.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.CoordenadasFaltaCroqui;
import br.gov.ma.detran.examespraticosmobile.modeloEspecializada.FingerPath;

public class CroquiPaintView extends View {

    public int BRUSH_SIZE = 10;
    public static final int COLOR_PEN = Color.RED;
    public static final int COLOR_ERASER = Color.WHITE;
    //public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;

    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private int currentColor;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private ArrayList<CoordenadasFaltaCroqui> coordenadasFaltas = new ArrayList<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private String itemAlineaCorrente;

    public CroquiPaintView(Context context) {
        super(context);
    }

    public CroquiPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(COLOR_PEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics){
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = COLOR_PEN;
    }

    public void pen(){
        currentColor = COLOR_PEN;
    }

    public void eraser(){
        currentColor = COLOR_ERASER;
    }

    public void clear(){
        paths.clear();
        pen();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        for(CoordenadasFaltaCroqui coord : coordenadasFaltas){
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(12);
            mPaint.setMaskFilter(null);
            mCanvas.drawPath(coord.getPath(), mPaint);

            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(1);
            mCanvas.drawText( coord.getItemAlinea(), coord.getX()+10, coord.getY(), mPaint );
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    private void touchStart(float x, float y){
        mPath = new Path();
        FingerPath fp = new FingerPath(currentColor, BRUSH_SIZE, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

        coordenadasFaltas.add(new CoordenadasFaltaCroqui(x, y, itemAlineaCorrente, fp));
    }

   /* private void touchMove(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }*/

    private void touchUp(){
        mPath.lineTo(mX, mY);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE :
                //touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP :
                touchUp();
                invalidate();
                break;
        }


        return true;
    }

    public ArrayList<FingerPath> getPaths(){
        return paths;
    }

    public void setPaths(ArrayList<FingerPath> paths){
        this.paths = paths;
    }

    public ArrayList<CoordenadasFaltaCroqui> getCoordenadasFaltas() {
        return coordenadasFaltas;
    }

    public void setCoordenadasFaltas(ArrayList<CoordenadasFaltaCroqui> coordenadasFaltas) {
        this.coordenadasFaltas = coordenadasFaltas;
    }

    public String getItemAlineaCorrente() {
        return itemAlineaCorrente;
    }

    public void setItemAlineaCorrente(String itemAlineaCorrente) {
        this.itemAlineaCorrente = itemAlineaCorrente;
    }
}