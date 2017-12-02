package canvas.control.points.ui.presenter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import canvas.control.points.moduls.Point;
import canvas.control.points.moduls.SmartRect;
import canvas.control.points.ui.view.PitView;

/**
 * Created by User on 02/12/2017.
 */

public class PitPresenterImpl implements PitPresenter{

    private static final int LINE_PAINT_WIDTH = 10;
    private static final int NUM_START_POINTS = 5;
    public static final int MIN_RADIUS = 50;
    public static final int MAX_RADIUS = 100;

    PitView mView;
    // contain all the point in the view
    private List<Point> points;
    // contain all the points we currently touch.
    private Map<Integer,Point> touchedPoints;
    // represent the Canvas area.
    private SmartRect smartRect;
    Paint mLinePaint, mAxisPaint;


    public PitPresenterImpl(PitView view){
        mView = view;
        touchedPoints = new HashMap<>();

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.GREEN);
        mLinePaint.setStrokeWidth(LINE_PAINT_WIDTH);
        mAxisPaint = new Paint();
        mAxisPaint.setColor(Color.BLUE);
        mAxisPaint.setStrokeWidth(LINE_PAINT_WIDTH);

    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom){
        if(changed) {
            // we like to keep the canvas dimensions
            smartRect = new SmartRect(left, top, right, bottom);
            // generate few points
            points = smartRect.getPointsInScope(NUM_START_POINTS);
            // we like to do draw the lines only between close horizontally points
            Collections.sort(points);

        }
    }

    @Override
    public void onDraw(Canvas canvas){
        // draw points
        for (Point point: points) {
            point.draw(canvas);
        }

        // draw lines between close horizontally points
        for(int i = 0 ; i < points.size() - 1 ; i ++){
            canvas.drawLine(points.get(i).getPosX(), points.get(i).getPosY(),
                    points.get(i + 1).getPosX(), points.get(i + 1).getPosY(), mLinePaint);
        }

        // draw axis Y
        canvas.drawLine(smartRect.exactCenterX() , 0, smartRect.exactCenterX() , smartRect.getButton(), mAxisPaint);

        // draw axis X
        canvas.drawLine(0, smartRect.exactCenterY(), smartRect.getRight(), smartRect.exactCenterY(), mAxisPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        // get masked (not specific to a pointer) action
        int maskedAction = motionEvent.getActionMasked();
        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:{
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                for (Point point : points) {
                    // we search on which circle we touch
                    if (inCircle(x, y, point)) {
                        // we add it to touchedPoints Map according his id
                        touchedPoints.put(getTouchedPointId(motionEvent), point);
                        return true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP: {
                int pointId = getTouchedPointId(motionEvent);
                // in case we left point we remove it from the touchedPoints map
                touchedPoints.remove(pointId);
                // for give new lines according the last movements
                Collections.sort(points);
                // for reflect the changes
                mView.invalidate();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                int pointId = getTouchedPointId(motionEvent);
                Point currentPoint = touchedPoints.get(pointId);
                if (currentPoint != null) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    // we make sure the point is in the frame
                    if (smartRect.contains((int) x + smartRect.getLeft(), (int) y + smartRect.getTop(), currentPoint.getRadius())) {
                        // update the point about the new location
                        currentPoint.movePoint(x, y);
                        mView.invalidate();
                    }
                }
                return true;
            }
        }
        return false;
    }

    // get the point touched id for this event
    private int getTouchedPointId(MotionEvent motionEvent){
        // get pointer index from the event object
        int pointerIndex = motionEvent.getActionIndex();

        // get pointer ID
        int pointerId = motionEvent.getPointerId(pointerIndex);
        return pointerId;
    }

    // calculate if (x,y) is in the Point using 'Pythagorean Theorem'
    private boolean inCircle(float x, float y, Point point) {
        double dx = Math.pow(x - point.getPosX(), 2);
        double dy = Math.pow(y - point.getPosY(), 2);

        if ((dx + dy) < Math.pow(point.getRadius(), 2)) {
            return true;
        } else {
            return false;
        }
    }


    // we add new Point in the canter of the axises
    @Override
    public void addPoint(){
        points.add(new Point(smartRect.exactCenterX(), smartRect.exactCenterY() , MIN_RADIUS, Color.RED));
        Collections.sort(points);
        mView.invalidate();
    }

}
