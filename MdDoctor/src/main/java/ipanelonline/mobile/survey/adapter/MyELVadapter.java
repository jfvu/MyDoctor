package ipanelonline.mobile.survey.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.net.URL;
import java.util.List;

import ipanelonline.mobile.survey.R;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MyELVadapter extends BaseExpandableListAdapter {
    public List<String> father;
    public List<String> chilerd;
    private Context mContext;

    public MyELVadapter(List<String> father, List<String> chilerd, Context context) {
        this.father = father;
        this.chilerd = chilerd;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return father.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return father.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return chilerd.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.item_elv_one, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_title_elv);
        textView.setText(father.get(groupPosition));
        AutoUtils.autoSize(view);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_elv_two,null);
        TextView textView = (TextView) view.findViewById(R.id.tv_content_elv);
        if(groupPosition == 0){

            Html.ImageGetter imgGetter = new Html.ImageGetter() {
                public Drawable getDrawable(String source) {
                    Drawable drawable = null;
                    URL url;
                    try {
                        url = new URL(source);
                        drawable = Drawable.createFromStream(url.openStream(), "");
                    } catch (Exception e) {
                        return null;
                    }
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                            .getIntrinsicHeight());
                    return drawable;
                }
            };
            textView.setText(Html.fromHtml(chilerd.get(groupPosition),imgGetter,null));
            Log.e("data",chilerd.get(groupPosition));
        }else{
            textView.setText(Html.fromHtml(chilerd.get(groupPosition)));
        }
        AutoUtils.autoSize(view);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
