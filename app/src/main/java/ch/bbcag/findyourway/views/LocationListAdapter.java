package ch.bbcag.findyourway.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.dal.FavouriteDataSource;
import ch.bbcag.findyourway.model.Location;

public class LocationListAdapter extends ArrayAdapter<Location> {

    private boolean IsLoaded = false;
    private List<Location> FavLocations;

    public LocationListAdapter(Context context, List<Location> locations){
        super(context, 0, locations);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        IsLoaded = false;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final FavouriteDataSource fds = new FavouriteDataSource(getContext());
        final Location location = getItem(position);
        // check view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_listitem, parent, false);
        }

        // Lookup view
        ImageView icon = convertView.findViewById(R.id.imageViewIcon);
        TextView name = convertView.findViewById(R.id.textViewName);
        TextView distance = convertView.findViewById(R.id.textViewDistance);
        final Button favButton = convertView.findViewById(R.id.favButton);

        List<Location> favLocations = getFavouriteLocations();

        // set values
        Drawable train = getContext().getResources().getDrawable(R.drawable.ic_train_black_24dp);
        Drawable bus = getContext().getResources().getDrawable(R.drawable.ic_directions_bus_black_24dp);
        Drawable boat = getContext().getResources().getDrawable(R.drawable.ic_directions_boat_black_24dp);
        final int iconFavButton = R.drawable.fav_button;
        final int iconFavButtonFull = R.drawable.fav_button_full;
        int type = location.getType();
        icon.setImageDrawable(type == 0 ? train : type == 1 ? bus : boat);
        name.setText(location.getName());
        if (location.getDistance() != null){
            distance.setText(location.getDistance() + "m");
        }
        location.setFavourite(containsId(favLocations, location.getId()));
        favButton.setBackgroundResource(location.isFavourite() ? iconFavButtonFull : iconFavButton);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location.isFavourite()) {
                    fds.open();
                    fds.deleteFavouriteLocation(location.getId());
                    fds.close();
                    location.setFavourite(false);
                    favButton.setBackgroundResource(iconFavButton);
                }else{
                    fds.open();
                    fds.createFavouriteLocation(location.getType(), location.getId(), location.getName());
                    fds.close();
                    location.setFavourite(true);
                    favButton.setBackgroundResource(iconFavButtonFull);
                }

                IsLoaded = false;
                getFavouriteLocations();
            }
        });

        return convertView;
    }

    private List<Location> getFavouriteLocations(){
        final FavouriteDataSource fds = new FavouriteDataSource(getContext());

        if (IsLoaded){
            return FavLocations;
        }

        fds.open();
        List<Location> favLocationsNew = fds.getAllFavouriteLocations();
        fds.close();

        FavLocations = favLocationsNew;
        IsLoaded = true;

        return favLocationsNew;
    }

    private boolean containsId(List<Location> locations, int id){
        return locations.stream().anyMatch(o -> o.getId().equals(id));
    }
}
