package ipanelonline.mobile.survey.ui.inquiry;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import ipanelonline.mobile.survey.ui.chat.ChatActivity;

/**
 * 问卷信息页面
 */
public class InQuestInfoAct extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    @BindView(R.id.tv_con_title)
    TextView tvConTitle;
    @BindView(R.id.tv_con)
    TextView tvCon;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_tiem)
    TextView tvEndTiem;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.ll_statusbar_activity_account)
    LinearLayout mLlStatusbarActivityAccount;
    private String link;
    private String title;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(intent != null){
            title = intent.getStringExtra("title");
            link = intent.getStringExtra("link");
            String loi = intent.getStringExtra("loi");
            String pointc = intent.getStringExtra("pointc");
            String startime = intent.getStringExtra("startime");
            String endtime = intent.getStringExtra("endtime");
            String id = intent.getStringExtra("id");
            String content = intent.getStringExtra("content");
            tvTitle.setText(title);
            tvNum.setText("编号：" + id);
            tvConTitle.setText(title);
            tvStartTime.setText("开始：" + startime);
            tvEndTiem.setText("结束：" + endtime);
            tvMoney.setText("奖励：" + pointc + "￥");
            tvTime.setText("时长：" + loi + "min");
            tvCon.setText(content);
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_in_quest_info;
    }

    @OnClick({R.id.img_integral_back, R.id.img_questinfo_group, R.id.bt_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_integral_back://返回按钮
                finish();
                break;
            case R.id.img_questinfo_group://顶部消息按钮
                startActivity(new Intent(InQuestInfoAct.this, ChatActivity.class));
                break;
            case R.id.bt_start://立即参与按钮
                Intent intent = new Intent(InQuestInfoAct.this, InQuestAct.class);
                intent.putExtra("link",link);
                intent.putExtra("title",title);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityAccount.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
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


}
