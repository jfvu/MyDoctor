package ipanelonline.mobile.survey.ui.account;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.CountyBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定支付宝页面
 */
public class BindZfbAct extends BaseActivity {


    @BindView(R.id.ed_bind_zfb_num)
    EditText edBindZfbNum;
    @BindView(R.id.ed_bind_zfb_name)
    EditText edBindZfbName;
    @BindView(R.id.ed_bind_zfb_code)
    EditText edBindZfbCode;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;
    private String account;

    @Override
    protected void initData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_zfb;
    }


    @OnClick({R.id.img_integral_back, R.id.tv_get_code, R.id.bt_tx_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回
                finish();
                break;
            case R.id.tv_get_code://获取验证码
                HashMap<String,String> params = new HashMap<>();
                params.put("mobile",app.getMobile());
                params.put("type","8");// 8 :绑定提现账号
                HttpUtil.post(Api.URL_SIM, this, params, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        showTaost(result);

                    }

                    @Override
                    public void onError(String status, String error) {
                        showTaost(error);
                    }
                });
                break;
            case R.id.bt_tx_next://下一步
                account = edBindZfbNum.getText().toString().trim();
                String name = edBindZfbName.getText().toString().trim();
                String code = edBindZfbCode.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    showTaost("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    showTaost("收款人不能为空");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showTaost("验证码不能为空");
                    return;
                }
                //绑定账号
                HashMap<String,String> params1 = new HashMap<>();
                params1.put("login_token",app.getLoginToken());
                params1.put("type","1");// 1：支付宝
                params1.put("uid",app.getId());
                params1.put("account", account);
                params1.put("account_name",name);
                params1.put("vcode",code);
                HttpUtil.post(Api.URL_SETEXCHANGE, this, params1, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        showTaost(result);
                        startActivity(ZfbWithdrawals.class,true);

                    }

                    @Override
                    public void onError(String status, String error) {
                        showTaost(error);
                        if(status.equals("relogin")){//令牌失效
                            startActivity(LoginActivity.class,true);
                        }
                    }
                });
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
