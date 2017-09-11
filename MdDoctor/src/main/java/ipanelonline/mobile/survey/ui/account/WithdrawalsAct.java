package ipanelonline.mobile.survey.ui.account;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现方式页面
 */
public class WithdrawalsAct extends BaseActivity {


    @BindView(R.id.rb_tx_wx)
    RadioButton rbTxWx;
    @BindView(R.id.rb_tx_zfb)
    RadioButton rbTxZfb;
    @BindView(R.id.rb_tx_sj)
    RadioButton rbTxSj;
    @BindView(R.id.rb_tx_pay)
    RadioButton rbTxPay;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;
    private int[] ids = {R.id.rb_tx_wx, R.id.rb_tx_zfb, R.id.rb_tx_sj, R.id.rb_tx_pay};
    private long currentSelct = -1;

    @Override
    protected void initData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawals;
    }


    @OnClick({R.id.img_integral_back, R.id.bt_tx_next, R.id.rb_tx_zfb, R.id.rb_tx_wx, R.id.rb_tx_sj, R.id.rb_tx_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回
                finish();
                break;
            case R.id.rb_tx_zfb://支付宝
                setCheck(R.id.rb_tx_zfb);
                break;
            case R.id.rb_tx_sj://手机充值
                setCheck(R.id.rb_tx_sj);
                break;
            case R.id.rb_tx_pay://Paypal
                setCheck(R.id.rb_tx_pay);
                break;
            case R.id.rb_tx_wx://微信
                setCheck(R.id.rb_tx_wx);
                break;
            case R.id.bt_tx_next://下一步
                if (currentSelct == R.id.rb_tx_zfb) {//支付宝支付
                    startActivity(new Intent(WithdrawalsAct.this, BindZfbAct.class));

                } else if (currentSelct == R.id.rb_tx_wx) {//微信支付
                    startActivity(new Intent(WithdrawalsAct.this, BindWxAct.class));
                } else if (currentSelct == R.id.rb_tx_sj) {//手机充值
                    startActivity(new Intent(WithdrawalsAct.this, BindPhoneAct.class));
                } else if (currentSelct == R.id.rb_tx_pay) {//Paypal支付
                    startActivity(new Intent(WithdrawalsAct.this, BindPayAct.class));
                }
                break;
        }
    }

    private void setCheck(int id) {
        currentSelct = id;
        for (int i : ids) {
            RadioButton rb = (RadioButton) findViewById(i);
            rb.setChecked(id == i ? true : false);
        }
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        rbTxZfb.setChecked(true);
        currentSelct = R.id.rb_tx_zfb;
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
