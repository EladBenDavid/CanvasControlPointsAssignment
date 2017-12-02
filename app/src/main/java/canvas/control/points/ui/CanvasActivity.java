package canvas.control.points.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import canvas.control.points.ui.view.PitViewImpl;
import canvas.control.points.R;

public class CanvasActivity extends AppCompatActivity {
    @BindView(R.id.pitLayout)
    PitViewImpl pitLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.addPointButton)
    public void addPointOnPitLayout(){
        pitLayout.addPoint();
    }


}
