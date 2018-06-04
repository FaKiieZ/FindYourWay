package ch.bbcag.findyourway.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.model.Location;

public class LocationListAdapter extends ArrayAdapter<Location> {

    public LocationListAdapter(Context context, List<Location> locations){
        super(context, 0, locations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Location location = getItem(position);
        // check view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_listitem, parent, false);
        }
        // Lookup view
        ImageView icon = convertView.findViewById(R.id.imageViewIcon);
        TextView name = convertView.findViewById(R.id.textViewName);
        TextView distance = convertView.findViewById(R.id.textViewDistance);
        // set values
        Drawable train = getContext().getResources().getDrawable(R.drawable.ic_train_black_24dp);
        Drawable bus = getContext().getResources().getDrawable(R.drawable.ic_directions_bus_black_24dp);
        icon.setImageDrawable(location.isBus() ? bus : train);
        name.setText(location.getName());
        distance.setText(location.getDistance() + "m");

        return convertView;
    }
}
