package ch.bbcag.findyourway.views;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.model.Connection;

public class ConnectionListAdapter extends ArrayAdapter<Connection> {

    public ConnectionListAdapter(Context context, List<Connection> connections){
        super(context, 0, connections);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Connection connection = getItem(position);
        // check view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.connection_listitem, parent, false);
        }
        // Lookup view
        TextView number = (TextView)convertView.findViewById(R.id.textViewNumber);
        TextView destination = (TextView)convertView.findViewById(R.id.textViewDestination);
        TextView time = (TextView)convertView.findViewById(R.id.textViewTime);
        TextView plattformNumber = (TextView)convertView.findViewById(R.id.textViewPlattformNumber);
        // set values
        number.setText(connection.getCategory() + connection.getNumber());
        destination.setText(connection.getTo().getName());
        time.setText(connection.getDeparture().toString());
        if(connection.getPlatform() != ""){
            plattformNumber.setText(connection.getPlatform());
        }

        return  convertView;
    }
}
