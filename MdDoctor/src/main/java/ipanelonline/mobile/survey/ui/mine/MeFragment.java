package ipanelonline.mobile.survey.ui.mine;


import android.content.Intent;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.PersonalData;
import ipanelonline.mobile.survey.ui.account.AccountActivity;
import ipanelonline.mobile.survey.ui.base.BaseFragment;
import ipanelonline.mobile.survey.view.CircleImageView;

public class MeFragment extends BaseFragment {

    @BindView(R.id.ll_statusbar_fragment_me)
    LinearLayout mLlStatusbarFragmentMe;
    @BindView(R.id.img_head_fragment_me)
    CircleImageView mImgHeadFragmentMe;
    @BindView(R.id.tv_name_fragment_me)
    TextView mTvNameFragmentMe;
    @BindView(R.id.rl_data_fragment_me)
    RelativeLayout mRlDataFragmentMe;
    @BindView(R.id.rl_help_fragment_me)
    RelativeLayout mRlHelpFragmentMe;
    @BindView(R.id.rl_set_fragment_me)
    RelativeLayout mRlSetFragmentMe;
    @BindView(R.id.rl_idea_fragment_me)
    RelativeLayout mRlIdeaFragmentMe;
    @BindView(R.id.rl_withdraw_fragment_me)
    RelativeLayout mRlWithdrawFragmentMe;
    Unbinder unbinder;
    private Intent mIntent;
    private PersonalData mPersonalData;

    public static MeFragment newInstance() {
        return new MeFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(mPersonalData.getHead() )){
            Glide.with(this).load(R.drawable.fragment_me_head).into(mImgHeadFragmentMe);
        }else {
            Glide.with(this).load(app.getAvatar()).into(mImgHeadFragmentMe);
        }

        mTvNameFragmentMe.setText(mPersonalData.getNickname());
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarFragmentMe.getLayoutParams();
        params.height = getStatusBarHeight();
        mLlStatusbarFragmentMe.setVisibility(View.GONE);
        //ImmersionBar.with(this).statusBarDarkFont(true).init();
        mPersonalData = new PersonalData(app.getAvatar(), app.getNickname(), app.getCity(), app.getName(),
                app.getBirthday(), (app.getGender().equals("1") ? "男" : "女"), app.getMobile(), app.getEmail(),
                app.getAddress(), app.getZipCode(), app.getVocation(), app.getEducation(), app.getDuty(), app.getSalary(),
                app.getProperty(), app.getScope(), app.getEmployee(), app.getHouseIncome());
if (TextUtils.isEmpty(mPersonalData.getHead() )){
    Glide.with(this).load(R.drawable.fragment_me_head).into(mImgHeadFragmentMe);
}else {
    Glide.with(this).load(mPersonalData.getHead()).into(mImgHeadFragmentMe);
}

        mTvNameFragmentMe.setText(mPersonalData.getNickname());
    }


    @OnClick({R.id.rl_data_fragment_me, R.id.rl_help_fragment_me, R.id.rl_set_fragment_me, R.id.rl_idea_fragment_me, R.id.rl_withdraw_fragment_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_data_fragment_me://个人资料
                mIntent = new Intent(getActivity(), PersonalDataActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_help_fragment_me://帮助
                mIntent = new Intent(getActivity(), HelpActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_set_fragment_me://设置
                mIntent = new Intent(getActivity(), SetActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rl_idea_fragment_me://意见反馈
                mIntent = new Intent(getActivity(),IdeaAvtivity.class );
                startActivity(mIntent);
                break;
            case R.id.rl_withdraw_fragment_me://提现账户
                mIntent = new Intent(getActivity(), AccountActivity.class);
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
    public void onDestroy() {
        super.onDestroy();
        //ImmersionBar.with(this).destroy();
    }
}
