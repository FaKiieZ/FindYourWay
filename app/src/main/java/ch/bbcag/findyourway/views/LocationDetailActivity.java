package ch.bbcag.findyourway.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.dal.FavouriteDataSource;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.Connection;

/**
 * Klasse beinhaltet die Logik für die LocationDetail-Ansicht
 */
public class LocationDetailActivity extends AppCompatActivity {
    private static final String TRANSPORT_OPENDATA_STATIONBOARD_API_URL = "http://transport.opendata.ch/v1/stationboard?station=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);

        int id = getIntent().getIntExtra("locationId", 0);
        String name = getIntent().getStringExtra("locationName");
        int type = getIntent().getIntExtra("locationType", 0);
        final boolean[] isFavourite = {getIntent().getBooleanExtra("locationIsFavourite", false)};
        FavouriteDataSource fds = new FavouriteDataSource(this);

        TextView locationName = findViewById(R.id.locationName);
        locationName.setText(name);

        getConnections(id, 20);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Favorisier Button Funktion
        int iconFavButton = R.drawable.fav_button;
        int iconFavButtonFull = R.drawable.fav_button_full;
        Button favButton = findViewById(R.id.favButton);

        favButton.setBackgroundResource(isFavourite[0] ? iconFavButtonFull : iconFavButton);

        favButton.setOnClickListener(v -> {
            if (isFavourite[0]) {
                fds.open();
                fds.deleteFavouriteLocation(id);
                fds.close();
                isFavourite[0] = false;
                favButton.setBackgroundResource(iconFavButton);
            }else{
                fds.open();
                fds.createFavouriteLocation(type, id, name);
                fds.close();
                isFavourite[0] = true;
                favButton.setBackgroundResource(iconFavButtonFull);
            }
        });
    }

    /**
     * Funktionalität für den "Zurück"-Button
     * @param item Item was geklickt wurde
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Holt alle Verbindungen von ausgehend von dieser Location
     * @param id ID der Location
     * @param limit Anzahl maximaler Verbindungen
     */
    public void getConnections(int id, int limit) {
        String url = TRANSPORT_OPENDATA_STATIONBOARD_API_URL + id + "&limit=" + limit;
        //final ArrayAdapter<Connection> connectionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        List < Connection > connections = TransportOpendataJsonParser.createConnectionsFromJsonString(response);
                        final ConnectionListAdapter connectionAdapter = new ConnectionListAdapter(getBaseContext(), connections);
                        //connectionAdapter.addAll(connections);
                        ListView connectionList = findViewById(R.id.list);
                        connectionList.setAdapter(connectionAdapter);
                        AdapterView.OnItemClickListener mListClickedHandler = (parent, view, position, id1) -> {
                            Intent intent = new Intent(getBaseContext(), ConnectionDetail.class);
                            Connection selected = (Connection) parent.getItemAtPosition(position);
                            intent.putExtra("connection", selected.getJsonString());
                            intent.putExtra("stationId", selected.getFrom().getId());
                            startActivity(intent);
                        };
                        connectionList.setOnItemClickListener(mListClickedHandler);
                        //progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        generateAlertDialog();
                    }
                }, error -> generateAlertDialog());
        queue.add(stringRequest);
    }

    private void generateAlertDialog() {
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setPositiveButton("Ok", (dialog, id) -> {
            // Closes this activity
            finish();
        });
        dialogBuilder.setMessage("Error").setTitle("Error");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}