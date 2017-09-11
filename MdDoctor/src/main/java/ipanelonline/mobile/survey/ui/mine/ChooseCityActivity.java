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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseCityActivity extends BaseActivity {


    @BindView(R.id.ll_statusbar_activity_choosecity)
    LinearLayout mLlStatusbarActivityChoosecity;
    @BindView(R.id.img_next_activity_choosecity)
    ImageView mImgNextActivityChoosecity;
    @BindView(R.id.lv_city_activity_choosecity)
    ListView mLvCityActivityChoosecity;
    private List<City> mCities;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams params = mLlStatusbarActivityChoosecity.getLayoutParams();
        params.height = getStatusBarHeight();
        ImmersionBar.with(this).statusBarDarkFont(true).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();

        mCities = new ArrayList<>();
        City city = new City("中国","+86");
        City city1 = new City("韩国","+82");
        City city2 = new City("日本","+81");
        mCities.add(city);
        mCities.add(city1);
        mCities.add(city2);
        mLvCityActivityChoosecity.setAdapter(new MyLvadapter());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_city;
    }

    @OnClick(R.id.img_next_activity_choosecity)
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
    class City{
        private String name;
        private String number;

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public City(String name, String number) {
            this.name = name;
            this.number = number;
        }
    }
    class MyLvadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mCities.size();
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

            name.setText(mCities.get(position).getName());
            phone.setText(mCities.get(position).getNumber());

            return convertView;
        }
    }
}
