package canvas.control.points.ui.presenter;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by User on 02/12/2017.
 */

public interface PitPresenter {
    void onLayout(boolean changed, int left, int top, int right, int bottom);
    void onDraw(Canvas canvas);
    boolean onTouchEvent(MotionEvent motionEvent);
    void addPoint();

}
