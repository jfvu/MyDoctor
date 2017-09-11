package ipanelonline.mobile.survey.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.LangBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.utils.AppPhoneMgr;

import com.google.gson.Gson;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 启动页面
 */
public class SplashAct extends BaseActivity {


    @BindView(R.id.relay_root)
    RelativeLayout relayRoot;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initData() {
        HttpUtil.post(Api.URL_LANG_VERSION, this, null, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                if (!app.getLangVersion().equals(result)) {//语言包有更新
                    app.saveLangVersion(result);
                    HttpUtil.post(Api.URL_LANG_PACKAGE, this, null, new HttpUtil.OnResultListener() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            LangBean langBean = gson.fromJson(result, LangBean.class);



                            SharedPreferences sp = getSharedPreferences("lang", MODE_PRIVATE);
                            SharedPreferences.Editor editor =  sp.edit();
                            saveSP(editor,langBean);
                            editor.commit();



                        }

                        @Override
                        public void onError(String status, String error) {

                        }
                    });
                }

            }

            @Override
            public void onError(String status, String error) {

            }
        });

    }

    @Override
    protected void initView() {
        startAnimation();
    }
    private void startAnimation() {
        AlphaAnimation anim = new AlphaAnimation(0.3f, 1f);
        anim.setFillAfter(true);
        anim.setDuration(2000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashAct.this, GuideActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        relayRoot.setAnimation(anim);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    private void saveSP(SharedPreferences.Editor editor,LangBean langBean){
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ACCOUNT",langBean.ACCOUNT);
        editor.putString("ACCOUNT_RECEIVABLE",langBean.ACCOUNT_RECEIVABLE);
        editor.putString("ACCOUNT_SETTING",langBean.ACCOUNT_SETTING);
        editor.putString("ADDRESS",langBean.ADDRESS);
        editor.putString("AFFIRM",langBean.AFFIRM);
        editor.putString("AGREED_REGISTER_TREATY",langBean.AGREED_REGISTER_TREATY);
        editor.putString("ALIPAY",langBean.ALIPAY);
        editor.putString("AREA",langBean.AREA);
        editor.putString("ARTICLE",langBean.ARTICLE);
        editor.putString("AVATAR",langBean.AVATAR);
        editor.putString("AWARD",langBean.AWARD);
        editor.putString("BIND_BANK",langBean.BIND_BANK);
        editor.putString("BIND_TIPS",langBean.BIND_TIPS);
        editor.putString("BIRTHDAY",langBean.BIRTHDAY);
        editor.putString("BLOG",langBean.BLOG);
        editor.putString("CAN_USE_POINT",langBean.CAN_USE_POINT);
        editor.putString("CANCEL",langBean.CANCEL);
        editor.putString("CHANGE_BALANCE",langBean.CHANGE_BALANCE);
        editor.putString("CHECK_IN",langBean.CHECK_IN);
        editor.putString("CHECK_OUT",langBean.CHECK_OUT);
        editor.putString("CONSUMER_HOTLINE",langBean.CONSUMER_HOTLINE);
        editor.putString("DATA_EMPTY",langBean.DATA_EMPTY);
        editor.putString("EDUCATION",langBean.EDUCATION);
        editor.putString("EMAIL",langBean.EMAIL);
        editor.putString("EMPLOYEE",langBean.EMPLOYEE);
        editor.putString("END",langBean.END);
        editor.putString("FACEBOOK",langBean.FACEBOOK);
        editor.putString("FEEDBACK",langBean.FEEDBACK);
        editor.putString("FIND_PASSWORD",langBean.FIND_PASSWORD);
        editor.putString("FINISH",langBean.FINISH);
        editor.putString("FORGET_PASSWORD",langBean.FORGET_PASSWORD);
        editor.putString("FROM_ALBUM",langBean.FROM_ALBUM);
        editor.putString("FULL_CONTENT",langBean.FULL_CONTENT);
        editor.putString("GENDER",langBean.GENDER);
        editor.putString("GET_ALL_CASH",langBean.GET_ALL_CASH);
        editor.putString("GET_CASH",langBean.GET_CASH);
        editor.putString("GET_CASH_ACCOUNT",langBean.GET_CASH_ACCOUNT);
        editor.putString("GET_CASH_NUM",langBean.GET_CASH_NUM);
        editor.putString("GET_PASSWORD",langBean.GET_PASSWORD);
        editor.putString("HAVE_IN_HAND",langBean.HAVE_IN_HAND);
        editor.putString("HELP",langBean.HELP);
        editor.putString("HOUSE_SALARY",langBean.HOUSE_SALARY);
        editor.putString("IN_SURVEY",langBean.IN_SURVEY);
        editor.putString("INDEX",langBean.INDEX);
        editor.putString("INDUSTRY",langBean.INDUSTRY);
        editor.putString("INPUT_ACCOUNT",langBean.INPUT_ACCOUNT);
        editor.putString("INPUT_ALIPAY_ACCOUNT",langBean.INPUT_ALIPAY_ACCOUNT);
        editor.putString("INPUT_MOBILE",langBean.INPUT_MOBILE);
        editor.putString("INPUT_NAME",langBean.INPUT_NAME);
        editor.putString("INPUT_NEW_PASSWORD",langBean.INPUT_NEW_PASSWORD);
        editor.putString("INPUT_PASSWORD",langBean.INPUT_PASSWORD);
        editor.putString("INPUT_VCODE",langBean.INPUT_VCODE);
        editor.putString("INPUT_YOU_FEEDBACK",langBean.INPUT_YOU_FEEDBACK);
        editor.putString("INVITE_FRIEND",langBean.INVITE_FRIEND);
        editor.putString("INVITE_TO_GET",langBean.INVITE_TO_GET);
        editor.putString("JOIN_NOW",langBean.JOIN_NOW);
        editor.putString("LOGIN",langBean.LOGIN);
        editor.putString("MEMBER_REGISTER",langBean.MEMBER_REGISTER);
        editor.putString("MESSAGE",langBean.MESSAGE);
        editor.putString("MINE",langBean.MINE);
        editor.putString("MOBILE",langBean.MOBILE);
        editor.putString("MY_POINT",langBean.MY_POINT);
        editor.putString("NAME",langBean.NAME);
        editor.putString("NETWORK_ANOMALY",langBean.NETWORK_ANOMALY);
        editor.putString("NEW_REGISTER",langBean.NEW_REGISTER);
        editor.putString("NICKNAME",langBean.NICKNAME);
        editor.putString("NOTE_BIND_BANK",langBean.NOTE_BIND_BANK);
        editor.putString("NUMBER",langBean.NUMBER);
        editor.putString("ONLINE_SURVEY",langBean.ONLINE_SURVEY);
        editor.putString("OTHER",langBean.OTHER);
        editor.putString("PASSWORD",langBean.PASSWORD);
        editor.putString("PAYEE",langBean.PAYEE);
        editor.putString("PAYMENT_METHOD",langBean.PAYMENT_METHOD);
        editor.putString("POINT",langBean.POINT);
        editor.putString("PRIVACY_POLICY",langBean.PRIVACY_POLICY);
        editor.putString("PROFILE",langBean.PROFILE);
        editor.putString("PROPERTY",langBean.PROPERTY);
        editor.putString("QQ_FRIEND",langBean.QQ_FRIEND);
        editor.putString("QQ_ZONE",langBean.QQ_ZONE);
        editor.putString("REBATE",langBean.REBATE);
        editor.putString("RECOMMEND",langBean.RECOMMEND);
        editor.putString("RECOMMENDED_ALIPAY",langBean.RECOMMENDED_ALIPAY);
        editor.putString("REGISTER",langBean.REGISTER);
        editor.putString("REINPUT_PASSWORD",langBean.REINPUT_PASSWORD);
        editor.putString("RELEASE_BLOG",langBean.RELEASE_BLOG);
        editor.putString("RELOGIN",langBean.RELOGIN);
        editor.putString("SALARY",langBean.SALARY);
        editor.putString("SAVN",langBean.SAVN);
        editor.putString("SCOPE",langBean.SCOPE);
        editor.putString("SCRAPPY_GET_CASH",langBean.SCRAPPY_GET_CASH);
        editor.putString("SEARCH",langBean.SEARCH);
        editor.putString("SEND",langBean.SEND);
        editor.putString("SEND_COMMENT",langBean.SEND_COMMENT);
        editor.putString("SET_PASSWORD",langBean.SET_PASSWORD);
        editor.putString("SETTING",langBean.SETTING);
        editor.putString("SHARE",langBean.SHARE);
        editor.putString("SHARE_IPANEL",langBean.SHARE_IPANEL);
        editor.putString("START",langBean.START);
        editor.putString("SUBMIT",langBean.SUBMIT);
        editor.putString("SURVEY",langBean.SURVEY);
        editor.putString("SYSTEM_NOTE",langBean.SYSTEM_NOTE);
        editor.putString("TAKE_PICTURE",langBean.TAKE_PICTURE);
        editor.putString("TASK",langBean.TASK);
        editor.putString("TOP_COMMENTS",langBean.TOP_COMMENTS);
        editor.putString("TWITTER",langBean.TWITTER);
        editor.putString("VCODE",langBean.VCODE);
        editor.putString("WANT_TO_SAY",langBean.WANT_TO_SAY);
        editor.putString("WECHAT_CIRCLE_FRIEND",langBean.WECHAT_CIRCLE_FRIEND);
        editor.putString("WEIBO",langBean.WEIBO);
        editor.putString("WHEN_LONG",langBean.WHEN_LONG);
        editor.putString("WRITE_COMMENT",langBean.WRITE_COMMENT);
        editor.putString("ZIP_CODE",langBean.ZIP_CODE);
        /*editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);
        editor.putString("ABOUT_US",langBean.ABOUT_US);*/

    }

}
