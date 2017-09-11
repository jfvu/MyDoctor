package ipanelonline.mobile.survey.ui.mine;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseLanguageActivity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_chooselanguage)
    LinearLayout mLlStatusbarActivityChooselanguage;
    @BindView(R.id.img_next_activity_chooselanguage)
    ImageView mImgNextActivityChooselanguage;
    @BindView(R.id.lv_city_activity_chooselanguage)
    ListView mLvCityActivityChooselanguage;
    private ArrayList<String> mList;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityChooselanguage.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
        mList = new ArrayList<>();
        mList.add("简体中文");
        mList.add("英文");
        mList.add("日语");
        mLvCityActivityChooselanguage.setAdapter(new MyLvadapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_language;
    }



    @OnClick(R.id.img_next_activity_chooselanguage)
    public void onViewClicked() {
        finish();
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
    class MyLvadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
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
            if(convertView == null)
            {
                convertView = View.inflate(getBaseContext(),R.layout.item_city_lv,null);
            }

            TextView name = (TextView)convertView.findViewById(R.id.tv_name_item_citylv);
            TextView phone = (TextView)convertView.findViewById(R.id.tv_phone_item_citylv);

            name.setText(mList.get(position));
            phone.setVisibility(View.GONE);

            return convertView;
        }
    }
}
