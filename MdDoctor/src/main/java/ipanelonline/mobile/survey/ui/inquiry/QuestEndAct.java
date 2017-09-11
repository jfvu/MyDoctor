package ipanelonline.mobile.survey.ui.inquiry;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.home.InvestigationAct;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 问卷结束页面
 */
public class QuestEndAct extends BaseActivity {


    @BindView(R.id.tv_inquest_con)
    TextView tvInquestCon;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;

    @Override
    protected void initData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_quest_end;
    }


    @OnClick({R.id.img_integral_back, R.id.llay_in_quesrt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回按钮
                finish();
                break;
            case R.id.llay_in_quesrt://继续参与按钮
                startActivity(new Intent(QuestEndAct.this, InvestigationAct.class));
                break;
        }
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
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
