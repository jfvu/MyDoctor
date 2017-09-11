package ipanelonline.mobile.survey.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ipanelonline.mobile.survey.ui.inquiry.InvestFrag;

/**
 * 说明：
 * 作者：郭富东 on 17/7/13 09:18
 * 邮箱：878749089@qq.com
 */
public class PagerInvestAdapter extends FragmentPagerAdapter {

    public PagerInvestAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        InvestFrag investFrag = new InvestFrag();
        Bundle args = new Bundle();
        args.putInt("state",position + 1);
        investFrag.setArguments(args);
        return investFrag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)return "进行中";
        if(position == 1)return "已完成";
        return "";
    }
}
