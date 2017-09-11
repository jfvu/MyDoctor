package ipanelonline.mobile.survey.ui.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import butterknife.OnClick;

/**
 * 签到页面
 */
public class SignAct extends BaseActivity {


    @Override
    protected void init(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.init(savedInstanceState);
    }

    @Override
    protected void initData() {
        showTaost("签到成功");
    }
    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }


    @OnClick(R.id.img_sign)
    public void onViewClicked() {//签到图片
        finish();
    }
}
