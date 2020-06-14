package com.roopre.simpleboard.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roopre.simpleboard.Adapter.MainAdapter;
import com.roopre.simpleboard.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import devlight.io.library.ntb.NavigationTabBar;

public class MainFragment extends Fragment {

    View rootView;

    ViewPager viewPager;
    MainAdapter mainAdapter;
    List<Fragment> fragments = new ArrayList<>();
    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_main, container, false);;

        viewPager = rootView.findViewById(R.id.viewPager);

        PopBoardFragment popBoardFragment = new PopBoardFragment();
        BoardListFragment boardListFragment = new BoardListFragment();
        AlertFragment alertFragment = new AlertFragment();


        fragments.clear();
        fragments.add(popBoardFragment);
        fragments.add(boardListFragment);
        fragments.add(alertFragment);

        mainAdapter = new MainAdapter(getChildFragmentManager(), fragments);
        mainAdapter.notifyDataSetChanged();

        viewPager.setAdapter(mainAdapter);


        final NavigationTabBar navigationTabBar = (NavigationTabBar) rootView.findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_star),
                        Color.parseColor("#FF0000")
                ).title("인기게시물")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_board),
                        Color.parseColor("#00FF00")
                ).title("게시판")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_noti),
                        Color.parseColor("#0000FF")
                ).title("알")
                        .badgeTitle("state")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager);


//        navigationTabBar.setViewPager(viewPager, 2);


        return rootView;
    }
}
