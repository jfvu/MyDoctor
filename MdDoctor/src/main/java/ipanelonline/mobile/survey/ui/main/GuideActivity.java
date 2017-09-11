package ipanelonline.mobile.survey.ui.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import ipanelonline.mobile.survey.App;
import ipanelonline.mobile.survey.R;
import ipanelonline.mobile.survey.ui.mine.LoginActivity;

/**
 * 说明：应用指导页面
 */
public class GuideActivity extends AutoLayoutActivity {


    private App app;
    private int[] iamges = {R.drawable.guide_02,R.drawable.guide_03,R.drawable.guide_04};
    private TextView tvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        app = (App) getApplicationContext();
        //boolean isFirst = sp.getBoolean("isFirst", true);
        if(!true){//不是第一次运行
            startActivity(new Intent(GuideActivity.this, SplashAct.class));
            finish();
            return;
        }
        //是第一次运行
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        final ViewPager mPager = (ViewPager) findViewById(R.id.vp_guide_container);
        tvStart = (TextView) findViewById(R.id.tv_guide_start);
        //存储进入过的状态
        //sp.edit().putBoolean("isFirst",false).commit();
        mPager.setAdapter(new MyPagerAdapter(this));
       /* tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(app.getCountryFirst() == 0){
                    startActivity(new Intent(GuideActivity.this,CountryActivity.class));
                }else{
                    startActivity(new Intent(GuideActivity.this, app.isLogin() ? MainActivity.class : LoginActivity.class));
                }
                finish();
            }
        });*/

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2){
                    tvStart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(app.getCountryFirst() == 0){
                                startActivity(new Intent(GuideActivity.this,CountryActivity.class));
                            }else{
                                startActivity(new Intent(GuideActivity.this, app.isLogin() ? MainActivity.class : LoginActivity.class));
                            }
                            finish();
                        }
                    });
                }else {
                    tvStart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPager.setCurrentItem(position+1);
                        }
                    });
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyPagerAdapter extends PagerAdapter {

        private Context context;

        public MyPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return iamges.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = LayoutInflater.from(GuideActivity.this).inflate(R.layout.item_guide_vp,null);
            ImageView imgGuide = (ImageView) itemView.findViewById(R.id.img_guide);
            imgGuide.setBackgroundResource(iamges[position]);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
