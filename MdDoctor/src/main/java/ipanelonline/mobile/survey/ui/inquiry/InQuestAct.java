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
import ipanelonline.mobile.survey.ui.main.WebViewAct;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 进入问卷提示页面
 */
public class InQuestAct extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_inquest_con)
    TextView tvInquestCon;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;
    private String link;
    private String title;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(intent != null){
            link = intent.getStringExtra("link");
            title = intent.getStringExtra("title");
            mTvTitle.setText(title);
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_in_quest;
    }


    @OnClick({R.id.img_integral_back, R.id.llay_in_quesrt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回按钮
                finish();
                break;
            case R.id.llay_in_quesrt://进入问卷调查
                Intent intent = new Intent(InQuestAct.this, WebViewAct.class);
                intent.putExtra("link",link);
                startActivity(intent);
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
