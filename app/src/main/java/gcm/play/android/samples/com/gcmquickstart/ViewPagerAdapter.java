package gcm.play.android.samples.com.gcmquickstart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by manuel on 21.11.15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter (FragmentManager manager){
        super(manager);

    }

    String[] tabNames = {"Notifications", "Stocks", "Portfolio"};

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new NotificationFragment();
            case 1: return new StockFollowFragment();
            case 2: return new PortfolioFragment();
            default: return new NotificationFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabNames[position];
    }
}
