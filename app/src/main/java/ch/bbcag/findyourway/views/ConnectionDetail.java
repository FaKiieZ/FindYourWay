package ch.bbcag.findyourway.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.Connection;

public class ConnectionDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_detail);

        String JsonConnection = getIntent().getStringExtra("connection");
        try {
            ch.bbcag.findyourway.model.ConnectionDetail connection = TransportOpendataJsonParser.CreateConnectionDetailFromJsonString(JsonConnection);
            CreatePlan(connection);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void CreatePlan(ch.bbcag.findyourway.model.ConnectionDetail connectionDetail){
        TextView from = (TextView)findViewById(R.id.textViewFrom);
        TextView departure = (TextView)findViewById(R.id.textViewDeparture);

        departure.setText(connectionDetail.getDeparture());
        from.setText(connectionDetail.getFrom());
    }
}
