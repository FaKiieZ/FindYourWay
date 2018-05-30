package ch.bbcag.findyourway.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.bbcag.findyourway.R;

public class TabSearchFragment extends android.support.v4.app.Fragment{
    private static final String TAG = "TabSearchFragment";

    public TabSearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }
}

