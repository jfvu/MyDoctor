package ipanelonline.mobile.survey.ui.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.CountyBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_setpassword)
    LinearLayout llStatusbarActivitySetpassword;
    @BindView(R.id.img_back_activity_setpassword)
    ImageView imgBackActivitySetpassword;
    @BindView(R.id.et_pass1)
    EditText etPass1;
    @BindView(R.id.et_pass2)
    EditText etPass2;
    @BindView(R.id.but_finish_setpassword)
    Button butFinishSetpassword;
    private Intent mIntent;
    private String phone;
    private String code;
    private String country = "1";
    private String lang = "zh_CN";
    private final static String DEVICE_TYPE = "3";

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(intent != null){
            phone = intent.getStringExtra("phone");
            code = intent.getStringExtra("code");
        }

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = llStatusbarActivitySetpassword.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_password;
    }



    @OnClick({R.id.img_back_activity_setpassword, R.id.but_finish_setpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_activity_setpassword:
                finish();
                break;
            case R.id.but_finish_setpassword:
                String pwd1 = etPass1.getText().toString().trim();
                String pwd2 = etPass2.getText().toString().trim();
                if(TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
                    showTaost("密码不能为空");
                    return;
                }
                if(!pwd1.equals(pwd2)){
                    showTaost("两次密码不一致，请重新输入");
                    return;
                }
                HashMap<String,String> params = new HashMap<>();
                params.put("mobile",phone);
                params.put("password",pwd1);
                params.put("vcode",code);
                HttpUtil.post(Api.URL_RESET, this, params, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        showTaost(result);
                        mIntent = new Intent();
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mIntent.setClass(SetPasswordActivity.this,LoginActivity.class);
                        startActivity(mIntent);
                        finish();
                    }
                    @Override
                    public void onError(String status,String error) {
                        showTaost(error);

                    }
                });
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
