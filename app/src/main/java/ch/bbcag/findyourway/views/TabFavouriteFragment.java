package ch.bbcag.findyourway.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.dal.FavouriteDataSource;
import ch.bbcag.findyourway.model.Location;

public class TabFavouriteFragment extends Fragment {
    private static final String TAG = "TabFavouriteFragment";
    public TabFavouriteFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        return view;
    }
}
