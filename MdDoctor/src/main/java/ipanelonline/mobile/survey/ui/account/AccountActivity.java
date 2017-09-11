package ipanelonline.mobile.survey.ui.account;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.AccountBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 说明：绑定提现账户页面
 * 时间： 17/8/8 下午3:00
 */
public class AccountActivity extends BaseActivity {
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;
    @BindView(R.id.img_back_activity_account)
    ImageView mImgBackActivityAccount;
    @BindView(R.id.rl_alipay_activity_account)
    RelativeLayout mRlAlipayActivityAccount;
 /*   @BindView(R.id.rl_wechat_activity_account)
    RelativeLayout mRlWechatActivityAccount;*/
 /*   @BindView(R.id.rl_phone_activity_account)
    RelativeLayout mRlPhoneActivityAccount;*/
    @BindView(R.id.rl_paypal_activity_account)
    RelativeLayout mRlPaypalActivityAccount;
    private Intent mIntent;
    private AccountBean accountBean;

    @Override
    protected void initData() {
      /*  HashMap<String,String> params = new HashMap<>();
        params.put("uid",app.getId());
        params.put("login_token",app.getLoginToken());
        HttpUtil.post(Api.URL_GETEXCHANGE, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String type = obj.getString("type");
                    String account = obj.getString("account");
                    String accountName = obj.getString("account_name");
                    accountBean = new AccountBean(type,account,accountName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String status, String error) {

            }
        });*/
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account;
    }


    @OnClick({R.id.img_back_activity_account, R.id.rl_alipay_activity_account, R.id.rl_paypal_activity_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_activity_account:
                finish();
                break;
            case R.id.rl_alipay_activity_account://支付宝
                mIntent = new Intent(this,BindZfbAct.class);
                startActivity(mIntent);
                break;
            case R.id.rl_paypal_activity_account:
                mIntent = new Intent(this,BindPayAct.class);//paypal
                startActivity(mIntent);
                break;
        }
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
