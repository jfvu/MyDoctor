package ipanelonline.mobile.survey.ui.account;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * 微信提现页面
 */
public class WxWithdrawals extends BaseActivity {


    @BindView(R.id.ed_tx_money)
    EditText edTxMoney;
    @BindView(R.id.tv_tx_ye)
    TextView tvTxYe;
    @BindView(R.id.bt_tx_next)
    Button btNext;
    @BindView(R.id.img_qr_code)
    ImageView imgQrCode;
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
        edTxMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    btNext.setEnabled(true);
                } else {
                    btNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wx_withdrawals;
    }

    @OnClick({R.id.img_integral_back, R.id.tv_bt_tx, R.id.bt_tx_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回
                finish();
                break;
            case R.id.tv_bt_tx://余额全部提现
                break;
            case R.id.bt_tx_next://提现
                String money = edTxMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    showTaost("提现余额不能为空");
                    return;
                }
                Intent intent = new Intent(WxWithdrawals.this, WithdrawalsSuccessAct.class);
                intent.putExtra("tag", "weixin");
                startActivity(intent);
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
