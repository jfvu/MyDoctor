package ipanelonline.mobile.survey.ui.journal;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.adapter.DetailCommentAdapter;
import ipanelonline.mobile.survey.entity.BlogBean;
import ipanelonline.mobile.survey.entity.CommentBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.base.BaseActivity;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

import static ipanelonline.mobile.survey.App.context;

/**
 * 日志详情页面
 */

public class DetailActivity extends BaseActivity {

    @BindView(R.id.ll_statusbar_activity_detail)
    LinearLayout llStatusbarActivityDetail;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.nine_img)
    NineGridView nineImg;
    @BindView(R.id.list_comment)
    ListView listComment;
    @BindView(R.id.ed_comment)
    EditText edComment;
    @BindView(R.id.comment_info)
    TextView tvInfo;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.ll_nocomment)
    LinearLayout mLlNocomment;
    private BlogBean blogBean;
    ArrayList<CommentBean> datas = new ArrayList();
    private DetailCommentAdapter adapter;
    private InputMethodManager imm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = llStatusbarActivityDetail.getLayoutParams();
        params.height = 0;
        ImmersionBar.with(this).statusBarDarkFont(true).fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor("#ffffff") .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        //imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //int mode=WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        //getWindow().setSoftInputMode(mode);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String json = intent.getStringExtra("json");
            Gson gson = new Gson();
            blogBean = gson.fromJson(json, BlogBean.class);
            tvContent.setText(blogBean.content);
            nineImg.setAdapter(new NineGridViewClickAdapter(DetailActivity.this, blogBean.getImageData()));
            //设置评论
            adapter = new DetailCommentAdapter(context, datas);
            listComment.setAdapter(adapter);
            loadCommentList();
        }
    }

    /**
     * 获取评论列表
     */
    private void loadCommentList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", app.getId());
        params.put("login_token", app.getLoginToken());
        params.put("blog_id", blogBean.id);
        params.put("page", blogBean.page);
        HttpUtil.post(Api.URL_COMMENT, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                datas.clear();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        CommentBean bean = new CommentBean();
                        bean.comment_time = obj.getString("comment_time");//时间
                        bean.content = obj.getString("content");//内容
                        if (!obj.isNull("critic_nickname")) {
                            bean.critic_nickname = obj.getString("critic_nickname");//呢称
                        }
                        if (!obj.isNull("critic_avatar")) {
                            bean.target_avatar = obj.getString("critic_avatar");//头像
                        }
                        datas.add(bean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (datas.size() == 0) {
                        mLlNocomment.setVisibility(View.VISIBLE);
                        listComment.setVisibility(View.GONE);
                        mViewLine.setVisibility(View.GONE);
                    }else {
                        mLlNocomment.setVisibility(View.GONE);
                        listComment.setVisibility(View.VISIBLE);
                        mViewLine.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(listComment);
                    //tvInfo.setVisibility((datas.size() == 0) ? View.GONE : View.VISIBLE);
                }
            }

            @Override
            public void onError(String status, String error) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


    @OnClick({R.id.img_back_activity_detail, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_activity_detail://返回
                finish();
                break;
            case R.id.tv_send://发送评论
                String comment = edComment.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {
                    showTaost("评论内容不能为空");
                    return;
                }
                sendComment(comment);
                break;
        }
    }


    /**
     * 发送评论
     *
     * @param comment ：评论的内容
     */
    private void sendComment(String comment) {
        if (blogBean == null) {
            showTaost("发送失败");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", app.getId());
        params.put("login_token", app.getLoginToken());
        params.put("blog_id", blogBean.id);
        params.put("content", comment);
        HttpUtil.post(Api.URL_ADD_COMMENT, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                showTaost(result);
                //更新评论列表
                loadCommentList();
                edComment.setText("");
                //收起键盘
                if (imm.isActive()){
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }

            @Override
            public void onError(String status, String error) {
                showTaost(error);
                if (status.equals("re_st")) {
                    startActivity(LoginActivity.class, true);
                }
            }
        });

    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        View view = listAdapter.getView(listAdapter.getCount() - 1, null, listView);
        view.measure(0,0);
        totalHeight += view.getMeasuredHeight();
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
