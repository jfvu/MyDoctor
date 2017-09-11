package ipanelonline.mobile.survey.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.adapter.PagerInvestAdapter;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 调查页面
 */
public class InvestigationAct extends BaseActivity {


    @BindView(R.id.tab_invest)
    TabLayout tabInvest;
    @BindView(R.id.pager_invest)
    ViewPager pagerInvest;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        tabInvest.addTab(tabInvest.newTab().setText("进行中"));
        tabInvest.addTab(tabInvest.newTab().setText("已完成"));
        tabInvest.setupWithViewPager(pagerInvest);
        pagerInvest.setAdapter(new PagerInvestAdapter(getSupportFragmentManager()));
        setTabLineWidth();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_investigation;
    }

    @OnClick(R.id.img_integral_back)
    public void onViewClicked() {//返回按钮
        finish();
    }

    private void setTabLineWidth() {
        try {
            //拿到tabLayout的mTabStrip属性
            Field mTabStripField = tabInvest.getClass().getDeclaredField("mTabStrip");
            mTabStripField.setAccessible(true);

            LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabInvest);

            int dp10 = 60;

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);

                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dp10;
                params.rightMargin = dp10;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


}
