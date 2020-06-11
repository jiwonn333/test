package com.roopre.simpleboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryDetailFragment extends Fragment {

    TextView msg_tv;

    View rootView;

    public GalleryDetailFragment() {
        // Required empty public constructor
    }


    public static GalleryDetailFragment newInstance(String param1, String param2) {
        GalleryDetailFragment fragment = new GalleryDetailFragment();
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
        rootView = inflater.inflate(R.layout.fragment_gallery_detail, container, false);
        msg_tv = rootView.findViewById(R.id.msg_tv);
        msg_tv.setText(getArguments().getString("msg"));

        return rootView;
    }
}