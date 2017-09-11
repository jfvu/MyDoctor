package ipanelonline.mobile.survey.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.UserBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.main.MainActivity;


public class FindPasswordActivity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_findpassword)
    LinearLayout llStatusbarActivityFindpassword;
    @BindView(R.id.img_back_activity_findpassword)
    ImageView imgBackActivityFindpassword;
    @BindView(R.id.et_find1)
    EditText etFind1;//手机号
    @BindView(R.id.et_find2)
    EditText etFind2;//验证码
    @BindView(R.id.but_code_find)
    Button butCodeFind;
    @BindView(R.id.but_next_find)
    Button butNextFind;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_find3)
    EditText mEtFind3;//第一次密码
    @BindView(R.id.et_pass1)
    EditText mEtPass1;//第二次密码
    private Intent mIntent;
    private CountDownTimer timer;
    private boolean isThirdLogin;
    private int type;
    private int SMS_TYPE = 3;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            isThirdLogin = intent.getBooleanExtra("isThirdLogin", false);
            type = intent.getIntExtra("type", 0);
            if (isThirdLogin) {
                tvTitle.setText("获取验证码");
                SMS_TYPE = 6;
            } else {
                tvTitle.setText("找回密码");
                SMS_TYPE = 2;
            }
        }
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = llStatusbarActivityFindpassword.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        timer = new CountDownTime(60000, 1000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_password;
    }


    @OnClick({R.id.img_back_activity_findpassword, R.id.but_code_find, R.id.but_next_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_activity_findpassword:
                finish();
                break;
            case R.id.but_code_find://获取验证码

                String phone1 = etFind1.getText().toString().trim();
                if (TextUtils.isEmpty(phone1)) {
                    showTaost("手机号不能为空");
                    return;
                }
                timer.start();
                HashMap<String, String> params = new HashMap<>();
                params.put("type", SMS_TYPE + "");
                params.put("mobile", phone1);
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
            case R.id.but_next_find:
                String phone = etFind1.getText().toString().trim();
                String code = etFind2.getText().toString().trim();
                String pwd1 = mEtFind3.getText().toString().trim();
                String pwd2 = mEtPass1.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showTaost("手机号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showTaost("验证码不能为空");
                    return;
                }
                if(!pwd1.equals(pwd2)){
                    showTaost("两次密码不一致，请重新输入");
                    return;
                }
                if (!isThirdLogin) {
                    HashMap<String,String> params1 = new HashMap<>();
                    SharedPreferences sp = App.context.getSharedPreferences("config_lang", App.context.MODE_APPEND);
                    params1.put("mobile",phone);
                    params1.put("password",pwd1);
                    params1.put("vcode",code);
                    params1.put("device_type", App.TYPE);
                    params1.put("device_no",App.deviceNum);
                    params1.put("country",sp.getString("country", ""));
                    params1.put("lang", sp.getString("lang", ""));
                    params1.put("uuid",App.uuid == null ? "1234567" : App.uuid);
                    Log.e("123",params1.toString());
                    HttpUtil.post(Api.URL_RESET, this, params1, new HttpUtil.OnResultListener() {
                        @Override
                        public void onSuccess(String result) {
                            showTaost(result);
                            finish();
                        }
                        @Override
                        public void onError(String status,String error) {
                            showTaost(error);

                        }
                    });
                } else {//三方登入
                    thirdLogin(type, phone, code);
                }
                break;
        }
    }

    /**
     * 三方登入
     */
    private void thirdLogin(int type, String phone, String code) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", phone);
        params.put("vcode", code);
        params.put("thirdparty_uid", app.getAppId(type));
        params.put("thirdparty_type", type + "");
        HttpUtil.post(Api.URL_THIRDPARTY_LOGIN, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                //登入成功
                UserBean userBean = UserBean.newInstance(result);
                app.loadUserInfo(userBean);
                mIntent = new Intent(FindPasswordActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();

            }

            @Override
            public void onError(String status, String error) {
                showTaost(error);
                finish();
            }
        });

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



    class CountDownTime extends CountDownTimer {
        public CountDownTime(long millisInFutre, long countDownInterval) {
            super(millisInFutre, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String phone1 = etFind1.getText().toString().trim();
            if (TextUtils.isEmpty(phone1)) {
                showTaost("手机号不能为空");
                return;
            }else {
                butCodeFind.setClickable(false);
                butCodeFind.setText(millisUntilFinished / 1000 + "秒后重新获取");
            }

        }

        @Override
        public void onFinish() {
            butCodeFind.setClickable(true);
            butCodeFind.setText("重新获取");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
