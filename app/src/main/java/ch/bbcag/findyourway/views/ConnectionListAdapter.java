package ch.bbcag.findyourway.views;

import android.content.Context;
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
import ch.bbcag.findyourway.model.Connection;

/**
 * Custom ArrayAdapter um Connections in einem ListView anzeigen zu können
 */
public class ConnectionListAdapter extends ArrayAdapter < Connection > {

    public ConnectionListAdapter(Context context, List < Connection > connections) {
        super(context, 0, connections);
    }

    /**
     * Wendet das Layout auf den ListView an
     * @param position im Array
     * @param convertView View welcher angepasst werden soll
     * @param parent Parent-Element
     * @return Angepasster View
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Connection connection = getItem(position);
        // check view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.connection_listitem, parent, false);
        }
        // Lookup view
        ImageView icon = convertView.findViewById(R.id.imageViewDelay);
        TextView number = convertView.findViewById(R.id.textViewNumber);
        TextView destination = convertView.findViewById(R.id.textViewDestination);
        TextView time = convertView.findViewById(R.id.textViewTime);
        TextView plattformNumber = convertView.findViewById(R.id.textViewPlattformNumber);
        TextView plattform = convertView.findViewById(R.id.textViewPlattform);
        // set values
        if (connection.getDelay() != 0 && connection.getDelay() != null) {
            icon.setVisibility(View.VISIBLE);
        }
        //String numberText =  connection.getCategory() + connection.getNumber();
        String[] prefixes = {"RE", "R", "ICE", "EC", "IC"};
        boolean foundPrefix = false;
        for (String prefix : prefixes){
            if(prefix.equals(connection.getCategory())){
                foundPrefix = true;
                break;
            }
        }

        String numberText;
        if(foundPrefix){
            numberText = connection.getNumber();
        }
        else {
            numberText = connection.getCategory() + connection.getNumber();
        }

        number.setText(numberText);
        String destinationText = "Richtung " + connection.getTo().getName();
        destination.setText(destinationText);
        time.setText(connection.getDeparture());
        String plattformText = connection.getPlatform();
        if (plattformText != "null") {
            plattformNumber.setText(connection.getPlatform());
            plattform.setText(R.string.plattform);
        }

        return convertView;
    }
}