package ch.bbcag.findyourway.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.Connection;

public class StationDetailActivity extends AppCompatActivity {
    private static final String TRANSPORT_OPENDATA_STATIONBOARD_API_URL = "http://transport.opendata.ch/v1/stationboard?station=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);

        String name = getIntent().getStringExtra("locationName");
        TextView locationName = findViewById(R.id.locationName);
        locationName.setText(name);

        int id = getIntent().getIntExtra("locationId", 0);
        getConnections(id, 20);
    }

    public void getConnections(int id,int limit){
        String url = TRANSPORT_OPENDATA_STATIONBOARD_API_URL + id + "&limit=" + limit;
        //final ArrayAdapter<Connection> connectionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Connection> connections = TransportOpendataJsonParser.CreateConnectionsFromJsonString(response);
                            final ConnectionListAdapter connectionAdapter = new ConnectionListAdapter(getBaseContext(),connections);
                            //connectionAdapter.addAll(connections);
                            ListView connectionList = findViewById(R.id.list);
                            connectionList.setAdapter(connectionAdapter);
                            AdapterView.OnItemClickListener mListClickedHandler = new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getBaseContext(), ConnectionDetail.class);
                                    Connection selected = (Connection)parent.getItemAtPosition(position);
                                    intent.putExtra("connection", selected.getJsonString());
                                    intent.putExtra("stationId", selected.getFrom().getId());
                                    startActivity(intent);
                                }
                            };
                            connectionList.setOnItemClickListener(mListClickedHandler);
                            //progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            generateAlertDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                generateAlertDialog();
            }
        });
        queue.add(stringRequest);
    }

    private void generateAlertDialog() {
        //progressBar.setVisibility(View.GONE);
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Closes this activity
                finish();
            }
        });
        dialogBuilder.setMessage("Error").setTitle("Error");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
