package com.example.kk.arttraining.startpager;

/**
 * 作者：wschenyongyin on 2016/12/20 09:48
 * 说明:
 */
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.chechezhi.ui.guide.FragmentTabAdapter;
import com.chechezhi.ui.guide.SinglePage;
import java.util.List;

public abstract class AbsGuideActivity extends FragmentActivity {
    public AbsGuideActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List guideContent = this.buildGuideContent();
        if(guideContent != null) {
            FrameLayout container = new FrameLayout(this);
            ViewPager pager = new ViewPager(this);
            pager.setId(this.getPagerId());
            container.addView(pager, new LayoutParams(-1, -1));
            this.setContentView(container);
            FragmentTabAdapter adapter = new FragmentTabAdapter(this, guideContent);
            pager.setAdapter(adapter);
            GuideView guideView = new GuideView(this, guideContent, this.drawDot(), this.dotDefault(), this.dotSelected());
            pager.setOnPageChangeListener(guideView);
            container.addView(guideView, new android.widget.FrameLayout.LayoutParams(-1, -1));
        }
    }

    public abstract List<SinglePage> buildGuideContent();

    public abstract boolean drawDot();

    public abstract Bitmap dotDefault();

    public abstract Bitmap dotSelected();

    public abstract int getPagerId();
}
