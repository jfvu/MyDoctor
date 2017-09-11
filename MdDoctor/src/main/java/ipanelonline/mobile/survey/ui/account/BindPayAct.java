package ipanelonline.mobile.survey.ui.account;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Paypal支付绑定界面
 */
public class  BindPayAct extends BaseActivity {



    @BindView(R.id.ed_bind_pay_num)
    EditText edBindZfbNum;
    @BindView(R.id.ed_bind_pay_name)
    EditText edBindZfbName;
    @BindView(R.id.ed_bind_pay_code)
    EditText edBindZfbCode;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).statusBarDarkFont(true).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_pay;
    }


    @OnClick({R.id.img_integral_back, R.id.tv_get_code, R.id.bt_tx_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回
                finish();
                break;
            case R.id.tv_get_code://获取验证码
                break;
            case R.id.bt_tx_next://下一步
                String account = edBindZfbNum.getText().toString().trim();
                String name = edBindZfbName.getText().toString().trim();
                String code = edBindZfbCode.getText().toString().trim();
                if(TextUtils.isEmpty(account)){
                    showTaost("账号不能为空");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    showTaost("收款人不能为空");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    showTaost("验证码不能为空");
                    return;
                }
                Intent intent = new Intent(BindPayAct.this, PayWithdrawals.class);
                intent.putExtra("account",account);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
