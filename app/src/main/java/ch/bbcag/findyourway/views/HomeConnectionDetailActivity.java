package ch.bbcag.findyourway.views;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.model.HomeConnectionDetail;

public class HomeConnectionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_connection_detail);
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


        CreateStops(0,0, connection);

    }

    private void CreateStops(int id, int count, HomeConnectionDetail connection){
        //TODO create stops
    }
}
