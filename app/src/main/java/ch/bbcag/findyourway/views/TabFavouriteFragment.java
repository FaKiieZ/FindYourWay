package ch.bbcag.findyourway.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.dal.FavouriteDataSource;
import ch.bbcag.findyourway.model.Location;

public class TabFavouriteFragment extends Fragment {
    private static final String TAG = "TabFavouriteFragment";
    private static View view = null;
    public TabFavouriteFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourite, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataSource();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            setDataSource();
        }
    }

    private void setDataSource(){
        if (getContext() == null){
            return;
        }

        FavouriteDataSource dataSource = new FavouriteDataSource(getActivity());
        ListView listView = view.findViewById(R.id.list);
        dataSource.open();
        List<Location> locationList = dataSource.getAllFavouriteLocations();
        dataSource.close();
        LocationListAdapter locationArrayAdapter = new LocationListAdapter(getContext(), locationList);
        listView.setAdapter(locationArrayAdapter);
        locationArrayAdapter.notifyDataSetChanged();

        AdapterView.OnItemClickListener mListClickedHandler = (parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), StationDetailActivity.class);
            Location selected = (Location) parent.getItemAtPosition(position);
            intent.putExtra("locationId", selected.getId());
            intent.putExtra("locationName", selected.getName());
            intent.putExtra("locationType", selected.getType());
            intent.putExtra("locationIsFavourite", selected.isFavourite());
            startActivity(intent);
        };
        listView.setOnItemClickListener(mListClickedHandler);
    }
}
