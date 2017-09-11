package ipanelonline.mobile.survey.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/12.
 */

public class SetActivity extends BaseActivity {
    @BindView(R.id.ll_statusbar_activity_set)
    LinearLayout mLlStatusbarActivitySet;
    @BindView(R.id.img_back_activity_set)
    ImageView mImgBackActivitySet;
    @BindView(R.id.tb_message_activity_set)
    ToggleButton mTbMessageActivitySet;
    @BindView(R.id.rl_clause_activity_set)
    RelativeLayout mRlClauseActivitySet;
    @BindView(R.id.rl_privacy_activity_set)
    RelativeLayout mRlPrivacyActivitySet;
    @BindView(R.id.rl_about_activity_set)
    RelativeLayout mRlAboutActivitySet;
    @BindView(R.id.rl_phone_activity_set)
    RelativeLayout mRlPhoneActivitySet;
    @BindView(R.id.rl_idea_activity_set)
    RelativeLayout mRlIdeaActivitySet;
    @BindView(R.id.but_out_activity_set)
    Button mButOutActivitySet;
    private Intent mIntent;

    @Override
    protected void initData() {
        mTbMessageActivitySet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){}else {}
    }
});
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivitySet.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back_activity_set, R.id.rl_clause_activity_set, R.id.rl_privacy_activity_set, R.id.rl_about_activity_set, R.id.rl_phone_activity_set, R.id.rl_idea_activity_set, R.id.but_out_activity_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_activity_set:
                finish();
                break;
            case R.id.rl_clause_activity_set:
               /* mIntent = new Intent(SetActivity.this,IdeaAvtivity.class);
                startActivity(mIntent);*/
                break;
            case R.id.rl_privacy_activity_set:
                mIntent = new Intent(SetActivity.this,DealActivity.class);
                mIntent.putExtra("DealActivity",1);
                startActivity(mIntent);
                break;
            case R.id.rl_about_activity_set:
                mIntent = new Intent(SetActivity.this,DealActivity.class);
                mIntent.putExtra("DealActivity",2);
                startActivity(mIntent);
                break;
            case R.id.rl_phone_activity_set:
                /*mIntent = new Intent(SetActivity.this,IdeaAvtivity.class);
                startActivity(mIntent);*/
                break;
            case R.id.rl_idea_activity_set:
                mIntent = new Intent(SetActivity.this,IdeaAvtivity.class);
                startActivity(mIntent);
                break;
            case R.id.but_out_activity_set:
                mIntent = new Intent();
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mIntent.setClass(this,LoginActivity.class);
                startActivity(mIntent);
                finish();
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
