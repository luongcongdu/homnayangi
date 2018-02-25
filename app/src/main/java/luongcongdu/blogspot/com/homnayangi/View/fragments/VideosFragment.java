package luongcongdu.blogspot.com.homnayangi.View.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import luongcongdu.blogspot.com.homnayangi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment {
    View view;

    public VideosFragment() {
        // Required empty public constructor
    }

    public static VideosFragment newInstance() {

        VideosFragment fragment = new VideosFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_videos, container, false);

        return view;
    }

}
