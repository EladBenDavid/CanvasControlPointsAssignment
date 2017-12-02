package canvas.control.points.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import canvas.control.points.moduls.Point;
import canvas.control.points.moduls.SmartRect;
import canvas.control.points.ui.presenter.PitPresenter;
import canvas.control.points.ui.presenter.PitPresenterImpl;


/**
 * Created by Elad Ben David on 02/12/2017.
 */

public class PitViewImpl extends ViewGroup implements PitView {

    PitPresenter presenter;
    public PitViewImpl(Context context, AttributeSet attributes) {
        super(context, attributes);
        //make the onDraw of  ViewGroup being called.
        setWillNotDraw(false);
        presenter = new PitPresenterImpl(this);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        presenter.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        presenter.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return presenter.onTouchEvent(motionEvent);
    }

    public void addPoint(){
        presenter.addPoint();
    }

}