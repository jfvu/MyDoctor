package ipanelonline.mobile.survey.ui.mine;

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

/**
 * Created by Administrator on 2017/7/12.
 */

public class IdeaAvtivity extends BaseActivity {
    @BindView(R.id.ll_statusbar_activity_idea)
    LinearLayout mLlStatusbarActivityIdea;
    @BindView(R.id.img_back_activity_idea)
    ImageView mImgBackActivityIdea;
    @BindView(R.id.et_input_activity_idea)
    EditText mEtInputActivityIdea;
    @BindView(R.id.but_submit_activity_idea)
    Button mButSubmitActivityIdea;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityIdea.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_idea;
    }


    @OnClick({R.id.img_back_activity_idea, R.id.but_submit_activity_idea})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_activity_idea:
                finish();
                break;
            case R.id.but_submit_activity_idea://提交
                String content = mEtInputActivityIdea.getText().toString().trim();
                if(TextUtils.isEmpty(content)){
                    showTaost("反馈内容不能为空");
                    return;
                }
                HashMap<String,String> params = new HashMap<>();
                params.put("uid",app.getId());
                params.put("login_token",app.getLoginToken());
                params.put("content",content);
                HttpUtil.post(Api.URL_FREEBACK, this, params, new HttpUtil.OnResultListener() {
                    @Override
                    public void onSuccess(String result) {
                        showTaost(result);
                        finish();
                    }
                    @Override
                    public void onError(String status, String error) {
                        showTaost(error);
                        if(status.equals("relogin")){//令牌失效
                            startActivity(LoginActivity.class,false);
                        }
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
