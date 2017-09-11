package ipanelonline.mobile.survey.ui.account;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机充值
 */
public class BindPhoneAct extends BaseActivity {


    @BindView(R.id.ed_bind_phone_num)
    EditText edBindZfbNum;
    @BindView(R.id.ed_bind_phone_name)
    EditText edBindZfbName;
    @BindView(R.id.ed_bind_phone_code)
    EditText edBindZfbCode;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;

    @Override
    protected void initData() {

    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
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
                if (TextUtils.isEmpty(account)) {
                    showTaost("手机号号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    showTaost("接受人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showTaost("验证码不能为空");
                    return;
                }
                Intent intent = new Intent(BindPhoneAct.this, PhoneWithdrawals.class);
                intent.putExtra("account", account);
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
    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
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
