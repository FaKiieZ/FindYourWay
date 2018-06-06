package ch.bbcag.findyourway.views;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.HomeConnectionDetail;

/**
 * Klasse dient dazu alle Verbindungen zwischen "from" und "to" zu suchen / Logik für View
 */
public class HomeDetailActivity extends AppCompatActivity {

    private static final String TRANSPORT_OPENDATA_STATIONBOARD_API_URL = "http://transport.opendata.ch/v1/connections?from=";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
        
        int from = getIntent().getIntExtra("fromId", 0);
        int to = getIntent().getIntExtra("toId", 0);
        String fromName = getIntent().getStringExtra("fromName");
        String toName = getIntent().getStringExtra("toName");

        TextView locationName = findViewById(R.id.locationName);
        locationName.setText(fromName + " nach " + toName);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        getConnections(from, to);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
     * Holt alle Verbindungen für diese Strecke
     * @param from Location Id from
     * @param to Location Id to
     */
    public void getConnections(int from, int to){
        String url = TRANSPORT_OPENDATA_STATIONBOARD_API_URL + from + "&to=" + to;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            response -> {
                try {
                    List<HomeConnectionDetail> homeConnectionDetails = TransportOpendataJsonParser.createDetailHomeConnectionFromJsonString(response);
                    final HomeConnectionDetailListAdapter homeConnectionDetailListAdapter = new HomeConnectionDetailListAdapter(getBaseContext(), homeConnectionDetails);
                    ListView connectionListView = findViewById(R.id.list);
                    progressBar.setVisibility(View.GONE);
                    connectionListView.setAdapter(homeConnectionDetailListAdapter);
                    AdapterView.OnItemClickListener mListClickListener = (parent, view, position, id) -> {
                        Toast.makeText(this, "Diese Funktion ist noch in Arbeit... :P", Toast.LENGTH_LONG).show();
//                        HomeConnectionDetail selected = (HomeConnectionDetail)parent.getItemAtPosition(position);
//                        Intent intent = new Intent(getBaseContext(), HomeConnectionDetailActivity.class);
//                        intent.putExtra("connection", selected.getJSON());
//                        startActivity(intent);
                    };
                    connectionListView.setOnItemClickListener(mListClickListener);
                }
                catch (JSONException e){
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
