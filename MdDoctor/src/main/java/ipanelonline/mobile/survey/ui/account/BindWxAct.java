package ipanelonline.mobile.survey.ui.account;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
 * 绑定微信支付页面
 */
public class BindWxAct extends BaseActivity {


    @BindView(R.id.img_bind_wx_code)
    ImageView imgBindWxCode;
    @BindView(R.id.ed_bind_wx_name)
    EditText edBindWxName;
    @BindView(R.id.ed_bind_wx_code)
    EditText edBindWxCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;

    @Override
    protected void initData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_wx;
    }


    @OnClick({R.id.img_integral_back, R.id.ed_bind_wx_code, R.id.tv_get_code, R.id.bt_tx_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回
                finish();
                break;
            case R.id.ed_bind_wx_code://绑定二维码
                break;
            case R.id.tv_get_code://获取验证码
                break;
            case R.id.bt_tx_next://下一步
                String name = edBindWxName.getText().toString().trim();
                String code = edBindWxCode.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showTaost("收款人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showTaost("验证码不能为空");
                    return;
                }
                Drawable background = imgBindWxCode.getBackground();
                startActivity(new Intent(BindWxAct.this, WxWithdrawals.class));
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
