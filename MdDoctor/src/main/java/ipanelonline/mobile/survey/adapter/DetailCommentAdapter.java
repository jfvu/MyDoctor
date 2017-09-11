package ipanelonline.mobile.survey.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.CommentBean;
import ipanelonline.mobile.survey.view.CircleImageView;

/**
 * 说明：
 * 作者：郭富东 on 17/8/16 10:29
 * 邮箱：878749089@qq.com
 */
public class DetailCommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CommentBean> datas;
    private ViewHolder holder;

    public DetailCommentAdapter(Context context, ArrayList<CommentBean> datas) {

        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = null;
        if(convertView == null){
            convertView =  View.inflate(context, R.layout.item_detail_comment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            AutoUtils.autoSize(convertView);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        CommentBean commentBean = datas.get(position);
        //Glide.with(context).asBitmap().load(commentBean.target_avatar).into(holder.portrait);
        if (commentBean.target_avatar == null){
            //holder.portrait.setBackgroundResource(R.drawable.icon);
            Glide.with(context).load(R.drawable.icon).into(holder.portrait);

        }else {
            Glide.with(context).load(commentBean.target_avatar).into(holder.portrait);
        }
        if (commentBean.critic_nickname == null){
            holder.tvName.setText("匿名用户");
        }else {
            holder.tvName.setText(commentBean.critic_nickname);
        }

        holder.tvTime.setText(commentBean.comment_time);
        holder.tvContent.setText(commentBean.content);
        holder.line.setVisibility((position == datas.size() - 1) ? View.INVISIBLE : View.VISIBLE);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.line)
        View line;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
