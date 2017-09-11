package ipanelonline.mobile.survey.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.BlogBean;
import ipanelonline.mobile.survey.entity.CommentBean;
import ipanelonline.mobile.survey.net.Api;
import ipanelonline.mobile.survey.net.HttpUtil;
import ipanelonline.mobile.survey.ui.journal.DetailActivity;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

import static ipanelonline.mobile.survey.ui.home.ShareActivity.showShare;

/**
 * 说明：
 * 作者：郭富东 on 17/7/13 15:28
 * 邮箱：878749089@qq.com
 */
public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.MyViewHolder>implements View.OnClickListener {

    private Context context;
    private ArrayList<BlogBean> datas;
    private final App app;
    private OnItemClickListener itemClickListener;
    private boolean isRun = false;
    private HashMap<Integer, Integer> likeMap = new HashMap<>();
    private Integer type;
    private PopupWindow pw;
    private View mView;
    public DiaryListAdapter(Context context, ArrayList<BlogBean> datas,View view) {
        this.context = context;
        this.datas = datas;
        this.mView = view;
        app = (App) context.getApplicationContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        BlogBean blogBean = datas.get(position);
        final String id = blogBean.id;
        if (blogBean.is_praise.equals("1")) {//点赞过
            type = 1;
            holder.imgZan.setChecked(true);
        } else if (blogBean.is_praise.equals("0")) { //没点赞过
            type = 2;
            holder.imgZan.setChecked(false);
        }
        likeMap.put(position, type);
        if (itemClickListener != null) {
            holder.llayClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, position);
                }
            });
        }
        holder.imgZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRun) {
                    return;
                }
                commentLike(id, holder.tvGoodNum, position, holder.imgZan);
            }


        });
        holder.imgZf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindows();
            }
        });
        if (blogBean.avatar.equals("")){
            holder.imgHead.setBackgroundResource(R.drawable.icon);

        }else {
            Glide.with(context).load(blogBean.avatar).into(holder.imgHead);
        }
        if (blogBean.nickname.equals("")){
            holder.tvEquipment.setText("匿名用户");
        }else {
            holder.tvEquipment.setText(blogBean.nickname);//用户呢称
        }
        if (blogBean.easy_address.equals("")){
            holder.tvLocal.setText("未知");//位置信息
        }else {
            holder.tvLocal.setText(blogBean.easy_address);//位置信息
            Log.e("easy_address",blogBean.easy_address);
        }


        holder.tvText.setText(blogBean.content);//详细内容
        holder.tvCommentNum.setText(blogBean.comment_num+"  comments");//评论数
        holder.tvGoodNum.setText(blogBean.praise_num + "");//点赞数

        holder.tvTime.setText(blogBean.create_time);//创建时间
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", app.getId());
        params.put("login_token", app.getLoginToken());
        params.put("blog_id", blogBean.id);
        params.put("page", blogBean.page);
        HttpUtil.post(Api.URL_COMMENT, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                ArrayList<CommentBean> datas = new ArrayList();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        CommentBean bean = new CommentBean();
                        bean.comment_time = obj.getString("comment_time");
                        bean.content = obj.getString("content");
                        bean.critic_nickname = obj.getString("critic_nickname");
                        datas.add(bean);
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if(datas.size() == 0){
                        holder.getTvCommentName.setVisibility(View.GONE);
                        holder.getTvCommentText.setVisibility(View.GONE);
                        holder.getTvCommentTime.setVisibility(View.GONE);
                    }else{
                        CommentBean commentBean = datas.get(0);
                        holder.getTvCommentName.setText(commentBean.critic_nickname);
                        holder.getTvCommentText.setText(commentBean.content);
                        holder.getTvCommentTime.setText(commentBean.comment_time);
                    }
                }

            }

            @Override
            public void onError(String status, String error) {
            }
        });
        ArrayList<ImageInfo> imageData = blogBean.getImageData();
        if (imageData != null)
            Log.e("size", imageData.size() + "");
        holder.girdView.setVisibility(imageData == null ? View.GONE : View.VISIBLE);
        //nineImg.setAdapter(new NineGridViewClickAdapter(DetailActivity.this, blogBean.getImageData()));
        holder.girdView.setAdapter(new NineGridViewClickAdapter(context, imageData) {
        });

    }

    /**
     * 点赞
     *
     * @param id
     * @param tvGoodNum
     * @param position
     * @param imgZan
     */
    private void commentLike(String id, final TextView tvGoodNum, int position, CheckBox imgZan) {
        isRun = true;
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", app.getId());
        params.put("login_token", app.getLoginToken());
        params.put("blog_id", id);
        type = likeMap.get(position);
        if (type == null || type == 2) {
            type = 1;
            imgZan.setChecked(true);
        } else if (type == 1) {
            type = 2;
            imgZan.setChecked(false);
            likeMap.put(position, type);
        }
        likeMap.put(position, type);
        params.put("type", type + "");
        final Integer finalType = type;
        HttpUtil.post(Api.URL_PRAISE, this, params, new HttpUtil.OnResultListener() {
            @Override
            public void onSuccess(String result) {
                isRun = false;
                int count = Integer.parseInt(tvGoodNum.getText().toString().trim());
                if (finalType == 1) {
                    tvGoodNum.setText(count + 1 + "");
                } else if (finalType == 2) {
                    tvGoodNum.setText(count - 1 + "");
                }
            }

            @Override
            public void onError(String status, String error) {
                if (status.equals("relogin")) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
                isRun = false;
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_head)
        RoundedImageView imgHead;
        @BindView(R.id.tv_equipment)
        TextView tvEquipment;
        @BindView(R.id.tv_local)
        TextView tvLocal;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.tv_good_num)
        TextView tvGoodNum;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.llay_click)
        LinearLayout llayClick;
        @BindView(R.id.img_zan)
        CheckBox imgZan;
        @BindView(R.id.img_zhuanfa)
        ImageView imgZf;
        @BindView(R.id.gridview)
        NineGridView girdView;
        @BindView(R.id.tv_comment_name)
        TextView getTvCommentName;
        @BindView(R.id.tv_comment_text)
        TextView getTvCommentText;
        @BindView(R.id.tv_comment_time)
        TextView getTvCommentTime;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }
    private void showPopupWindows() {
        View view = View.inflate(context, R.layout.popup_share, null);
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
        pw.showAtLocation(mView,
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

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
        showShare(imageUrl,titleUrl,text,title,platFrom);
        if(pw != null && pw.isShowing())pw.dismiss();
    }
}
