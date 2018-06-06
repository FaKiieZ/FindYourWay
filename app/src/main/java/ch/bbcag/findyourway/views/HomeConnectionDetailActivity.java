package ch.bbcag.findyourway.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.HomeConnectionDetail;

public class HomeConnectionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_connection_detail);


        String intentResponse = getIntent().getStringExtra("connection");
        try {
            HomeConnectionDetail connection = TransportOpendataJsonParser.createDetailHomeConnectionFromJsonString(intentResponse).get(0);
            TextView locationName = (TextView)findViewById(R.id.locationName);
            locationName.setText(connection.getFrom().getStation().getName() + " nach " + connection.getTo().getStation().getName());
            TextView textDuration = (TextView)findViewById(R.id.textViewDuration);
            textDuration.setText("Dauer: " + connection.getDurationTotal());
            TextView textPlatform = (TextView)findViewById(R.id.textViewPlatformHeader);
            TextView platformText = (TextView)findViewById(R.id.textViewText);
            if(connection.getFrom().getPlatform() != null){
                textPlatform.setText(connection.getFrom().getPlatform());
                platformText.setText("Gl.");
            }
            else {
                platformText.setText("");
            }


            CreatePlan(connection);
        } catch (JSONException e){
            e.printStackTrace();
        }

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
     * Erstellt einen Plan der gesamten Reise/Connection
     * @param connection Objekt der Verbindung
     */
    private void CreatePlan(HomeConnectionDetail connection){
        // hole die Line
        View line = (View)findViewById(R.id.line);
        // Hole das ContraintLayout und erstelle ein neues Constraintset
        ConstraintLayout mConstraintLayout = (ConstraintLayout)findViewById(R.id.mainConstraint);
        ConstraintSet set = new ConstraintSet();

        // Line setzten
        line.getLayoutParams().height = 0;

        // Startpunkt erstellen
        //TextView from = (TextView)findViewById(R.id.textViewFrom);
        //TextView departure = (TextView)findViewById(R.id.textViewDeparture);
        //from.setText(connection.getFrom().getStation().getName());
        //departure.setText(connection.getFrom().getDeparture());
//
        int parentId = line.getId();
        for(int i = 0; i < connection.getSections().size()-1; i++){
            parentId = CreateSection(connection, i, parentId);
        }

    }

    private int CreateSection(HomeConnectionDetail connectionDetail, int sectionIndex, int parentElement){
        ConstraintLayout mConstraintLayout  = (ConstraintLayout)findViewById(R.id.mainConstraint);
        View line = new View(this);
        line.getLayoutParams().width = dpTopixel(getApplication(), 3);
        line.getLayoutParams().height = 10;
        line.setId(View.generateViewId());
        line.setBackgroundResource(R.color.colorPrimaryDark);
        mConstraintLayout.addView(line);
        return line.getId();
    }

    private void CreateStops(int id, int count, HomeConnectionDetail connection){
        //TODO create stops
    }

    /**
     * Rechnet Pixel(Integer) in DP (abhängig der Density) um.
     * @param c Applicationcontext
     * @param dp Anzahl DP
     * @return  Anzahl Pixel
     */
    public static int dpTopixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;
        return (int)pixel;
    }
}
