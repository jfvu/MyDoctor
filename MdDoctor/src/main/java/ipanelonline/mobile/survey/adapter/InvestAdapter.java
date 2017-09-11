package ipanelonline.mobile.survey.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.InvestItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 说明：
 * 作者：郭富东 on 17/7/13 09:59
 * 邮箱：878749089@qq.com
 */
public class InvestAdapter extends RecyclerView.Adapter<InvestAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<InvestItem> datas;

    public InvestAdapter(Context context,ArrayList<InvestItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invest_list, parent, false);
        MyViewHolder holder = new MyViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        InvestItem item = datas.get(position);
        holder.tvInvserItemTime.setText(item.startime.substring(0,item.startime.length() - 3));
        holder.tvInvserItemCon.setText(item.content);
        holder.tvInvserItemType.setText(item.title);
        //Glide.with(context).load(item.avatar).into(holder.imgInvestIcon);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_invest_icon)
        ImageView imgInvestIcon;
        @BindView(R.id.tv_invser_item_type)
        TextView tvInvserItemType;
        @BindView(R.id.tv_invser_item_time)
        TextView tvInvserItemTime;
        @BindView(R.id.tv_invser_item_con)
        TextView tvInvserItemCon;
        @BindView(R.id.cardView)
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
