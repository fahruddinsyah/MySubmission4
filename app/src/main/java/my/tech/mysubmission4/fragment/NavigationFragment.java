package my.tech.mysubmission4.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import my.tech.mysubmission4.R;
import my.tech.mysubmission4.adapter.PagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {

    private ViewPager viewPager;
    private BubbleNavigationLinearView bubbleNav;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        bubbleNav = view.findViewById(R.id.BubbleNav);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        MovieFragment movieFragment = new MovieFragment();
        TvShowFragment tvShowFragment = new TvShowFragment();
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        SearchFragment searchFragment = new SearchFragment();
        adapter.addFragment(movieFragment);
        adapter.addFragment(tvShowFragment);
        adapter.addFragment(searchFragment);
        adapter.addFragment(favoriteFragment);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bubbleNav.setCurrentActiveItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bubbleNav.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position, true);
            }
        });

        int newInitialPosition = 2;
        bubbleNav.setCurrentActiveItem(newInitialPosition);
        viewPager.setCurrentItem(newInitialPosition, false);
    }
}
