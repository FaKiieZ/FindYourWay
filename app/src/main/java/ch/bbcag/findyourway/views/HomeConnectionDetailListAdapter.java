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
import ch.bbcag.findyourway.model.HomeConnectionDetail;

/**
 * ListAdapter für zum Darstellen der Verbindungen (einer Route)
 */
public class HomeConnectionDetailListAdapter extends ArrayAdapter<HomeConnectionDetail> {

    public HomeConnectionDetailListAdapter(Context context, List<HomeConnectionDetail> connections){
        super(context, 0, connections);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HomeConnectionDetail connection = getItem(position);

        // kontrolliere View
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.connection_listitem, parent, false);
        }
        // Lookup view
        TextView number = convertView.findViewById(R.id.textViewNumber);
        TextView destination = convertView.findViewById(R.id.textViewDestination);
        TextView time = convertView.findViewById(R.id.textViewTime);
        TextView departure = convertView.findViewById(R.id.textViewDeparture);
        TextView plattform = convertView.findViewById(R.id.textViewPlattform);

        // set values
        String[] prefixes = {"RE", "R", "ICE", "EC", "IC"};
        boolean foundPrefix = false;
        String numberText = "Wert nicht vorhanden";
        if(connection.getSections() != null){
            if (connection.getSections().get(0).getCategory() == null && connection.getSections().get(0).getNumber() == null){
                numberText = "Fussweg";
            }else{
                for (String prefix : prefixes){
                    String sectionNumber = connection.getSections().get(0).getNumber();
                    if (sectionNumber != null){
                        if(sectionNumber.startsWith(prefix)){
                            foundPrefix = true;
                            break;
                        }
                    }
                }

                if(foundPrefix){
                    numberText = connection.getSections().get(0).getNumber();
                }
                else {
                    numberText = connection.getSections().get(0).getCategory() + connection.getSections().get(0).getNumber();
                }
            }
        }
        number.setText(numberText);

        String destinationText = "Richtung " + connection.getTo().getStation().getName();
        destination.setText(destinationText);
        time.setText("Dauer: " + connection.getDurationTotal());
        departure.setText(connection.getFrom().getDeparture());
        plattform.setText(connection.getTo().getArrivalTime());

        return convertView;
    }
}
