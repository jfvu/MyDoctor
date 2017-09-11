package ipanelonline.mobile.survey.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import ipanelonline.mobile.survey.R;

/**
 * 说明：
 * 作者：郭富东 on 17/8/18 09:58
 * 邮箱：878749089@qq.com
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> datas;

    public GridAdapter(Context context, ArrayList<String> datas){
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
        View itemView = View.inflate(context, R.layout.item_grid, null);
        ImageView img = (ImageView) itemView.findViewById(R.id.img_grid);
        Glide.with(context).load(datas.get(position)).into(img).onLoadStarted(new ColorDrawable(Color.GRAY));
        return itemView;
    }
}
