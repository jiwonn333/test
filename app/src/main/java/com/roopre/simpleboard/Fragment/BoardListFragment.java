package com.roopre.simpleboard.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roopre.simpleboard.R;

import androidx.fragment.app.Fragment;

public class BoardListFragment extends Fragment {

    View rootView;
    public BoardListFragment() {
        // Required empty public constructor
    }

    public static BoardListFragment newInstance(String param1, String param2) {
        BoardListFragment fragment = new BoardListFragment();
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
        rootView = inflater.inflate(R.layout.fragment_board_list, container, false);;

        return rootView;
    }

}
