package ipanelonline.mobile.survey.ui.home;

import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import static ipanelonline.mobile.survey.App.context;


public class ShareActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.ll_statusbar_activity_share)
    LinearLayout mLlStatusbarActivityShare;
    @BindView(R.id.img_back_activity_share)
    ImageView mImgBackActivityShare;
    @BindView(R.id.img_share)
    TextView mImgShare;
    private PopupWindow pw;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityShare.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
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


    private void showPopupWindows() {
        View view = View.inflate(this, R.layout.popup_share, null);
        view.findViewById(R.id.ll_wx_share).setOnClickListener(this);
        view.findViewById(R.id.ll_friend_share).setOnClickListener(this);
        view.findViewById(R.id.ll_space_share).setOnClickListener(this);
        view.findViewById(R.id.ll_sina_share).setOnClickListener(this);
        view.findViewById(R.id.ll_twitter_share).setOnClickListener(this);
        view.findViewById(R.id.ll_qq_share).setOnClickListener(this);
        view.findViewById(R.id.ll_fb_share).setOnClickListener(this);
        view.findViewById(R.id.but_cancal).setOnClickListener(this);
        pw = new PopupWindow(view);
        // 设置弹出窗体的宽和高
        pw.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        pw.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        pw.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xffffff);
        // 设置弹出窗体的背景
        pw.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        pw.setAnimationStyle(R.style.share_popup_anim);
        pw.showAtLocation(findViewById(R.id.root),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    @Override
    public void onClick(View view) {
        String imageUrl = "http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg";
        String titleUrl = "http://www.baidu.com";
        String text = "测试消息";
        String title = "测试分享";
        String platFrom = null;
        switch (view.getId()) {
            case R.id.ll_wx_share:
                platFrom = Wechat.NAME;
                break;
            case R.id.ll_friend_share:
                platFrom = WechatMoments.NAME;
                break;
            case R.id.ll_space_share:
                platFrom = QZone.NAME;
                break;
            case R.id.ll_sina_share:
                platFrom = SinaWeibo.NAME;
                break;
            case R.id.ll_twitter_share:
                platFrom = Twitter.NAME;
                break;
            case R.id.ll_qq_share:
                platFrom = QQ.NAME;
                break;
            case R.id.ll_fb_share:
                platFrom = Facebook.NAME;
                break;
            case R.id.but_cancal://取消
                pw.dismiss();
                pw = null;
                break;
        }
        showShare(imageUrl, titleUrl, text, title, platFrom);
        if (pw != null && pw.isShowing())
            pw.dismiss();
    }

    /**
     * share SDK分享
     *
     * @param imgeUrl
     * @param titleUrl
     * @param text
     * @param title
     * @param platForm
     */
    public static void showShare(String imgeUrl, String titleUrl, String text, String title, String platForm) {
        OnekeyShare oks = new OnekeyShare();
        if (!TextUtils.isEmpty(imgeUrl)) {
            oks.setImageUrl(imgeUrl);
        }
        if (!TextUtils.isEmpty(titleUrl)) {
            oks.setTitleUrl(titleUrl);
        }
        if (!TextUtils.isEmpty(text)) {
            oks.setText(text);
        }
        if (!TextUtils.isEmpty(title)) {
            oks.setTitle(title);
        }
        if (!TextUtils.isEmpty(platForm)) {
            oks.setPlatform(platForm);
            oks.disableSSOWhenAuthorize();
            oks.show(context);
        }
    }

    @OnClick({R.id.img_back_activity_share, R.id.img_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_activity_share:
                finish();
                break;
            case R.id.img_share:
                showPopupWindows();
                break;
        }
    }
}
