package go.faddy.foodfornation.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import go.faddy.foodfornation.R;
import go.faddy.foodfornation.utils.behaviors.BottomNavigationViewBehavior;
import go.faddy.foodfornation.adapters.ViewPagerAdapter;
import go.faddy.foodfornation.utils.storage.SharedPrefManager;

public class CategoryDetailsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView bottomNavigation;
    private ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private Toolbar toolbar;
    private ImageButton profilebtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        profilebtn = findViewById(R.id.profile_button);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            profilebtn.setVisibility(View.VISIBLE);
        }else{
            profilebtn.setVisibility(View.GONE);
        }

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));

            }
        });
        bottomNavigation = findViewById(R.id.footer);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.getMenu().findItem(R.id.menu_category).setChecked(true);
                        break;
                    case 1:
                        bottomNavigation.getMenu().findItem(R.id.menu_latest).setChecked(true);
                        break;
                    case 2:
                        bottomNavigation.getMenu().findItem(R.id.menu_region).setChecked(true);
                        break;
                    case 3:
                        bottomNavigation.getMenu().findItem(R.id.menu_cities).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_category:
                viewPager.setCurrentItem(0);
                break;
            case R.id.menu_latest:
                viewPager.setCurrentItem(1);
                break;
            case R.id.menu_region:
                viewPager.setCurrentItem(2);
                break;
            case R.id.menu_cities:
                viewPager.setCurrentItem(3);
                break;
        }
        return false;
    }

}

