package go.faddy.foodfornation.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import go.faddy.foodfornation.ui.fragments.CategoryFragment;
import go.faddy.foodfornation.ui.fragments.TransportationFragment;
import go.faddy.foodfornation.ui.fragments.LatestItemsFragment;
import go.faddy.foodfornation.ui.fragments.LocationFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CategoryFragment();
            case 1:
                return new LatestItemsFragment();
            case 2:
                return new LocationFragment();
            case 3:
                return  new TransportationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
