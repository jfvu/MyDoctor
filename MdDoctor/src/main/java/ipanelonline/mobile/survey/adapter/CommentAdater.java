package ipanelonline.mobile.survey.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.CommentBean;

import java.util.ArrayList;

/**
 * 说明：
 * 作者：郭富东 on 17/8/9 10:40
 * 邮箱：878749089@qq.com
 */
public class CommentAdater extends BaseAdapter {

    private Context context;
    private ArrayList<CommentBean> datas;

    public CommentAdater(Context context, ArrayList<CommentBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View itemView = View.inflate(context, R.layout.item_comment, null);
        TextView tvName = (TextView) itemView.findViewById(R.id.tv_comment_name);
        TextView tvText = (TextView) itemView.findViewById(R.id.tv_comment_text);
        TextView tvTime = (TextView) itemView.findViewById(R.id.tv_comment_time);
        CommentBean commentBean = datas.get(position);
        tvName.setText(commentBean.critic_nickname + "：");
        tvText.setText(commentBean.content);
        tvTime.setText(commentBean.comment_time);
        return itemView;
    }
}
