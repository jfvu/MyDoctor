package ipanelonline.mobile.survey.ui.mine;


import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.yanzhenjie.permission.AndPermission;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.UserBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.main.MainActivity;
import ipanelonline.mobile.survey.utils.LoginApi;

/**
 * 说明：应用程序登入页面
 * 作者：郭富东
 * 时间： 17/6/12 上午10:47
 * 邮箱：878749089@qq.com
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.ed_login_user)
    EditText edLoginUser;
    @BindView(R.id.ed_login_pwd)
    EditText edLoginPwd;
    private Intent mIntent;
    private int type;
    private String mJson;

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init();
        AndPermission.with(this)
                .requestCode(200)
                .permission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                )
                .callback(null)
                .start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }



    @OnClick({R.id.bt_login, R.id.tv_login_forget, R.id.tv_login_regist, R.id.img_login_qq, R.id.img_login_wx, R.id.img_login_fb, R.id.img_login_tw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login://登入
                login();
                break;
            case R.id.tv_login_forget://忘记密码
                mIntent = new Intent(this,FindPasswordActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_login_regist://会员注册
                mIntent = new Intent(this,RegisterActivity.class);
                startActivity(mIntent);
                break;
            case R.id.img_login_qq://三方登入-qq
                type = 2;
                thirdLogin(QQ.NAME);
                break;
            case R.id.img_login_wx://三方登入-微信
                type = 1;
                thirdLogin(Wechat.NAME);
                break;
            case R.id.img_login_fb://三方登入-faceBook
                type = 7;
                thirdLogin(Facebook.NAME);
                break;
            case R.id.img_login_tw://三方登入-twitter
                type = 6;
                thirdLogin(Twitter.NAME);
                break;
        }
    }

    private void thirdLogin(String name) {
        LoginApi api = new LoginApi();
        api.setPlatform(name);
        api.setOnLoginListener(new LoginApi.OnLoginListener() {
            @Override
            public void onLogin(String platform, String userId) {
                thirdpartycheck(type,userId);
            }
        });
        api.login(this);
    }

    /**
     * 三方登入验证
     * @param type 1 微信 2QQ 3微博 4支付宝 6 twitter 7 Facebook 8Google
     * @param userId
     */
    private void thirdpartycheck(final int type, String userId) {
        HashMap<String,String> params = new HashMap<>();
        params.put("thirdparty_uid",userId);
        params.put("thirdparty_type",type + "");
        HttpUtil.post(Api.URL_THIRDPARTY_CHECK, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                //登入成功
                UserBean userBean = UserBean.newInstance(result);
                app.loadUserInfo(userBean);

                mIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(mIntent);
                finish();
            }

            @Override
            public void onError(String status, String error) {
                if(status.equals("re_reg")){//没有绑定手机号
                    Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                    intent.putExtra("isThirdLogin",true);
                    intent.putExtra("type",type);
                    startActivity(intent);
                }
            }
        });

    }

    /* 登入逻辑 */
    private void login() {
        String userName = edLoginUser.getText().toString().trim();
        String userPwd = edLoginPwd.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            showTaost("手机号不能不空");
            return;
        }
        if(TextUtils.isEmpty(userPwd)){
            showTaost("密码不能不空");
            return;
        }
        final HashMap<String,String> params = new HashMap<>();
        params.put("mobile",userName);
        params.put("password",userPwd);
        HttpUtil.post(Api.URL_LOGIN, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                showTaost("登入成功");
                mJson = result;
                loadInitData();
            }

            @Override
            public void onError(String statue,String error) {
                showTaost(error);

            }
        });

    }

    private void loadInitData() {
        HttpUtil.post(Api.URL_DICTIONARY, this, null, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                App.saveInitData(result);
                UserBean userBean = UserBean.newInstance(mJson);
                app.loadUserInfo(userBean);
                mIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(mIntent);
                finish();
            }

            @Override
            public void onError(String status, String error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
