package ipanelonline.mobile.survey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.entity.IntergralBean;

/**
 * 说明：
 * 作者：郭富东 on 17/7/13 15:28
 * 邮箱：878749089@qq.com
 */
public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<IntergralBean> datas;
    private final App app;


    public PointListAdapter(Context context, ArrayList<IntergralBean> datas) {
        this.context = context;
        this.datas = datas;
        app = (App) context.getApplicationContext();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        IntergralBean pointBean = datas.get(position);
       holder.mPoint.setText(pointBean.getPoint());
        holder.mTvName.setText(pointBean.getReason());
        holder.mTvTime.setText(pointBean.getPoint_change_time());
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.point)
        TextView mPoint;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
