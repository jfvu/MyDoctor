package ipanelonline.mobile.survey.ui.mine;

import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

/**
 * Created by jiaofeng on 2017/7/13.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.ll_statusbar_activity_register)
    LinearLayout llStatusbarActivityRegister;
    @BindView(R.id.img_back_activity_register)
    ImageView imgBackActivityRegister;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.but_code)
    Button butCode;
    @BindView(R.id.but_register)
    Button butRegister;
    @BindView(R.id.tv_deal)
    TextView mTvDeal;
    private Intent mIntent;
    private CountDownTimer timer;
    private String country = "1";
    private String lang = "zh_CN";
    private final String DEVICE_TYPE = "3";

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = llStatusbarActivityRegister.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();

        mTvDeal.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvDeal.getPaint().setAntiAlias(true);//抗锯齿
        timer = new CountDownTime(60000, 1000);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String code = etCode.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String phone2 = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(code) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone2)) {
                    butRegister.setEnabled(false);
                } else {
                    butRegister.setEnabled(true);
                }
            }
        };
        etPhone.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);
        etCode.addTextChangedListener(watcher);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.img_back_activity_register, R.id.but_code, R.id.but_register,R.id.tv_deal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_activity_register:
                finish();
                break;
            case R.id.but_code:
                String phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showTaost("手机号不能为空");
                    return;
                }
                timer.start();
                HashMap<String, String> params = new HashMap<>();
                params.put("mobile", phone);
                params.put("country", country);
                params.put("lang", lang);
                params.put("type", "1");
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
            case R.id.but_register:
                String code = etCode.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String phone2 = etPhone.getText().toString().trim();
                HashMap<String, String> param = new HashMap<>();
                param.put("mobile", phone2);
                param.put("password", password);
                param.put("vcode", code);
                HttpUtil.post(Api.URL_REGISTER, this, param, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        showTaost(result);
                        mIntent = new Intent();
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mIntent.setClass(RegisterActivity.this, LoginActivity.class);
                        mIntent.putExtra("123", true);
                        startActivity(mIntent);
                    }

                    @Override
                    public void onError(String status, String error) {
                        showTaost(error);

                    }
                });
                break;
            case R.id.tv_deal:
                mIntent = new Intent(RegisterActivity.this,DealActivity.class);
                mIntent.putExtra("DealActivity",1);
                startActivity(mIntent);
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



    class CountDownTime extends CountDownTimer {
        public CountDownTime(long millisInFutre, long countDownInterval) {
            super(millisInFutre, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            butCode.setClickable(false);
            butCode.setWidth(288);
            butCode.setText(millisUntilFinished / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            butCode.setClickable(true);
            butCode.setWidth(168);
            butCode.setText("重新获取");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
