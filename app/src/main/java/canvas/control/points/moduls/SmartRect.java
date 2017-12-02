package canvas.control.points.moduls;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import canvas.control.points.ui.presenter.PitPresenterImpl;

/**
 * Created by Elad Ben David on 02/12/2017.
 */

public class SmartRect {
    int mLeft;
    int mTop;
    int mRight;
    int mButton;

    public SmartRect(int left, int top, int right, int bottom){
        mButton = bottom;
        mLeft = left;
        mRight = right;
        mTop = top;
    }

    // for given x,y coordinates, returns true if (x,y) is inside the rectangle.
    // if we know only the center of the graphic object, we can use the offset.
    // for example, if we know the center of the Point we can set the radius as offset.
    public boolean contains(int x, int y, int offset){
        if( x > mLeft + offset && x < mRight - offset &&
            y > mTop + offset && y < mButton - offset)
            return true;
        return false;
    }

    // the exact horizontal center of the rectangle.
    public int exactCenterX() {
        return ((mLeft + mRight) / 2) - mLeft;
    }

    //the exact vertical center of the rectangle.
    public int exactCenterY() {
        return ((mTop + mButton) / 2) - mTop;
    }

    public int getLeft() {
        return mLeft;
    }

    public int getTop() {
        return mTop;
    }

    public int getRight() {
        return mRight;
    }

    public int getButton() {
        return mButton;
    }


    // handle of create random points scattered in space.
    // each point have random color and random radius.
    public List<Point> getPointsInScope( int num ){
        List<Point> points = new ArrayList<>();
        int stepX = (mButton - mTop - PitPresenterImpl.MAX_RADIUS) / (num + 1);
        int stepY = (mRight - mLeft  - PitPresenterImpl.MAX_RADIUS) / (num + 1);
        Random random = new Random();
        for(int i = 1 ;  i <= num  ; i++) {
            int randomRadius = random.nextInt(PitPresenterImpl.MAX_RADIUS - PitPresenterImpl.MIN_RADIUS) + PitPresenterImpl.MIN_RADIUS;
            int randomColor = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            points.add(new Point(stepX * i, stepY * i, randomRadius, randomColor));
        }
        return points;
    }

}
