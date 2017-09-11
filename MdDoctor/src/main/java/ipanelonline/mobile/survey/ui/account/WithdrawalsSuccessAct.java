package ipanelonline.mobile.survey.ui.account;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现成功页面
 */
public class WithdrawalsSuccessAct extends BaseActivity {


    @BindView(R.id.tv_resh)
    TextView tvResh;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.img_info)
    ImageView imgInfo;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(intent != null){
            String money = intent.getStringExtra("money");
        }

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        Intent intent = getIntent();
        if (intent != null) {
            String tag = intent.getStringExtra("tag");
            if (!TextUtils.isEmpty(tag) && tag.equals("weixin")) {
                tvResh.setTextColor(Color.argb(255, 34, 172, 26));
                tvMoney.setTextColor(Color.argb(255, 34, 172, 26));
                imgInfo.setImageResource(R.drawable.tx_icon_we);
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawals_success;
    }


    @OnClick({R.id.img_back, R.id.tv_resh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回
                finish();
                break;
            case R.id.tv_resh://刷新
                break;
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
