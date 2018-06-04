package ch.bbcag.findyourway.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ch.bbcag.findyourway.R;

public class ConnectionDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_detail);

        String JsonConnection = getIntent().getStringExtra("connection");

    }
}
