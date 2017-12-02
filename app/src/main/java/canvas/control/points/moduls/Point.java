package canvas.control.points.moduls;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;


/**
 * Created by Elad Ben David on 02/12/2017.
 *
 * Point is object that represent graphic point data.
 * holds two integer coordinates and the radius.
 * It Also hold his Paint for drawing method.
 * he know how to draw himself and also Its location relative to others.
 */
public class Point  implements Comparable{
    private float mPosX,mPosY;
    private int mRadius;
    private Paint mPaint;

    public Point(float x, float y, int radius, int color){
        mPosX = x;
        mPosY = y;
        mRadius = radius;
        mPaint = new Paint();
        mPaint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(mPosX, mPosY, mRadius, mPaint);
    }

    public void movePoint(float x, float y){
        mPosX = x;
        mPosY = y;
    }

    // when we drawing line between 2 points
    // we like to do draw the lines only between close horizontally points.
    // therefor we measure the distance in x axis
    @Override
    public int compareTo(@NonNull Object object) {
        return (int)(mPosX - ((Point)object).mPosX) ;
    }

    public float getPosX() {
        return mPosX;
    }

    public float getPosY() {
        return mPosY;
    }

    public int getRadius() {
        return mRadius;
    }

    public Paint getPaint() {
        return mPaint;
    }
}
