package ch.bbcag.findyourway.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.json.JSONException;
import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.Stop;

/***
 * Diese Klasse beinhaltet die Logik zum Anzeigen der Detailansicht, einer Verbindung.
 */
public class ConnectionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_detail);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        String JsonConnection = getIntent().getStringExtra("connection");
        try {
            progressBar.setVisibility(View.VISIBLE);
            ch.bbcag.findyourway.model.ConnectionDetail connection = TransportOpendataJsonParser.createConnectionDetailFromJsonString(JsonConnection);
            createPlan(connection);
            progressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    /**
     * Funktion für den "Zurück"-Button im Header
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    /***
     * Diese Funktion erstellt einen Plan mit allen Zwischenhalte einer Verbindung.
     * @param connectionDetail ConnectionDetailActivity von dem ein Plan erstellt werden soll.
     */
    private void createPlan(ch.bbcag.findyourway.model.ConnectionDetail connectionDetail){
        // ConstraintLayout um Constraints aus dem Code heraus zu setzten.
        ConstraintLayout mConstraintLayout  = (ConstraintLayout)findViewById(R.id.mainConstraint);
        ConstraintSet set = new ConstraintSet();

        // Die Startelemente aus dem View werden geladen, um deren Werte später zu verändern
        TextView from = (TextView)findViewById(R.id.textViewFrom);
        TextView departure = (TextView)findViewById(R.id.textViewDeparture);
        TextView locationName = (TextView)findViewById(R.id.locationName);

        String[] prefixes = {"RE", "R", "ICE", "EC", "IC"};
        boolean foundPrefix = false;
        for (String prefix : prefixes){
            if(connectionDetail.getNumber().startsWith(prefix)){
                foundPrefix = true;
                break;
            }
        }

        String numberText;
        if(foundPrefix){
            numberText = connectionDetail.getNumber();
        }
        else {
            numberText = connectionDetail.getCategory() + connectionDetail.getNumber();
        }

        locationName.setText(numberText+ " " +  connectionDetail.getFrom() + " nach " + connectionDetail.getTo());
        View line = (View)findViewById(R.id.line);
        line.getLayoutParams().height = 0;

        // Erstellen den Startpunkt auf der Linie
        ImageView dot = new ImageView(this);
        LinearLayout.LayoutParams dotLayout = new LinearLayout.LayoutParams(dpToPixel(getApplicationContext(), 15), dpToPixel(getApplicationContext(), 15));
        dot.setLayoutParams(dotLayout);
        dot.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);
        dot.setId(View.generateViewId());
        mConstraintLayout.addView(dot);
        // Constraints zum Startpunkt hinzufügen
        set.clone(mConstraintLayout);
        set.connect(dot.getId(), ConstraintSet.LEFT, mConstraintLayout.getId(), ConstraintSet.LEFT, dpToPixel(getApplicationContext(), 69));
        set.connect(dot.getId(), ConstraintSet.TOP, findViewById(R.id.imageViewPoint).getId(), ConstraintSet.BOTTOM, dpToPixel(getApplicationContext(), 24));
        set.applyTo(mConstraintLayout);

        // Erstellen die Startzeit der Verbindung
        LinearLayout.LayoutParams TextLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPixel(getApplicationContext(), 14));
        TextView time = new TextView(this);
        time.setLayoutParams(TextLayout);
        Stop firstStop = connectionDetail.getPassList().get(1);
        if(firstStop.getDeparture() != null) time.setText(firstStop.getDeparture());
        time.setTextSize(12);
        time.setId(View.generateViewId());
        mConstraintLayout.addView(time);

        // Erstellen den Namen des Startpunktes
        LinearLayout.LayoutParams Test = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPixel(getApplicationContext(), 14));
        TextView name = new TextView(this);
        name.setLayoutParams(Test);
        name.setText(connectionDetail.getPassList().get(1).getStation().getName());
        name.setTextSize(12);
        name.setId(View.generateViewId());
        mConstraintLayout.addView(name);

        // Setzt die Constraints für die Abfahrtszeit und den Abfahrtsort
        set.clone(mConstraintLayout);
        set.connect(time.getId(), ConstraintSet.RIGHT, dot.getId(), ConstraintSet.LEFT, dpToPixel(getApplicationContext(), 2));
        set.connect(time.getId(), ConstraintSet.TOP, dot.getId(), ConstraintSet.TOP, dpToPixel(getApplicationContext(), 0));
        set.applyTo(mConstraintLayout);

        set.clone(mConstraintLayout);
        set.connect(name.getId(), ConstraintSet.LEFT, dot.getId(), ConstraintSet.RIGHT, dpToPixel(getApplicationContext(), 2));
        set.connect(name.getId(), ConstraintSet.TOP, dot.getId(), ConstraintSet.TOP, dpToPixel(getApplicationContext(), 0));
        set.applyTo(mConstraintLayout);

        // Erstellt alle Zwischenstops auf der Linie
        createDots(dot.getId(), 2, connectionDetail);

        // Verlänger die Line um noch den Endpunkt hinzuzufügen
        line.getLayoutParams().height = line.getLayoutParams().height + dpToPixel(getApplicationContext(),(float)52);
        // Erstelle den Endpunkt auf der Linie
        ImageView endDot = new ImageView(this);
        LinearLayout.LayoutParams endDotLayout = new LinearLayout.LayoutParams(dpToPixel(getApplicationContext(), 16), dpToPixel(getApplicationContext(), 16));
        endDot.setLayoutParams(endDotLayout);
        endDot.setBackgroundResource(R.drawable.ic_brightness_1_black_24dp);
        endDot.setId(View.generateViewId());
        mConstraintLayout.addView(endDot);
        // Setzt die Constraints
        set.clone(mConstraintLayout);
        set.connect(endDot.getId(), ConstraintSet.LEFT, mConstraintLayout.getId(), ConstraintSet.LEFT, dpToPixel(getApplicationContext(), 69));
        set.connect(endDot.getId(), ConstraintSet.TOP, line.getId(), ConstraintSet.BOTTOM, dpToPixel(getApplicationContext(), 0));
        set.applyTo(mConstraintLayout);

        // Layout für die TextViews des Endpunktes
        LinearLayout.LayoutParams endLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPixel(getApplicationContext(), 20));
        // Erstellt den TextView für die Ankunftszeit
        TextView endTime = new TextView(this);
        endTime.setLayoutParams(endLayout);
        endTime.setText(connectionDetail.getArrival());
        endTime.setTextSize(14);
        endTime.setId(View.generateViewId());
        mConstraintLayout.addView(endTime);

        // Erstellt den TextView für den Ankunftsort
        TextView nameDestination = new TextView(this);
        nameDestination.setLayoutParams(endLayout);
        String destinationText;
        Stop lastStop = connectionDetail.getPassList().get(connectionDetail.getPassList().size()-1);
        if(lastStop.getPlatform() != null && lastStop.getPlatform() != "null") {
            destinationText = connectionDetail.getTo() + ", Gl. " + lastStop.getPlatform();
        }
        else {
            destinationText = connectionDetail.getTo();
        }
        nameDestination.setText(destinationText);
        nameDestination.setTextSize(14);
        nameDestination.setId(View.generateViewId());
        mConstraintLayout.addView(nameDestination);

        // Setzt die Constraints für die beiden TextViews
        set.clone(mConstraintLayout);
        set.connect(endTime.getId(), ConstraintSet.RIGHT, endDot.getId(), ConstraintSet.LEFT, dpToPixel(getApplicationContext(), 4));
        set.connect(endTime.getId(), ConstraintSet.TOP, endDot.getId(), ConstraintSet.TOP, dpToPixel(getApplicationContext(), 0));
        set.applyTo(mConstraintLayout);

        set.clone(mConstraintLayout);
        set.connect(nameDestination.getId(), ConstraintSet.LEFT, endDot.getId(), ConstraintSet.RIGHT, dpToPixel(getApplicationContext(), 4));
        set.connect(nameDestination.getId(), ConstraintSet.TOP, endDot.getId(), ConstraintSet.TOP, dpToPixel(getApplicationContext(), 0));
        set.applyTo(mConstraintLayout);

        // Wenn der Wert "Platform" nicht vorhanden ist, wird er nicht geschrieben
        if(connectionDetail.getPlatform() != null && connectionDetail.getPlatform() != "null"){
            from.setText(connectionDetail.getFrom() + ", Gl. " + connectionDetail.getPlatform());
        }
        else {
            from.setText(connectionDetail.getFrom());
        }
        departure.setText(connectionDetail.getDeparture());
    }

    /***
     * Erstellt für ein @see ConnectionDetails einen Plan aller Zwischenhalte
     * @param id Id des Parents Punktes auf dem Plan
     * @param count Counter für die Liste aller Zwischenhalte
     * @param connectionDetails Objekt um die Informationen zu setzten
     */
    private void createDots(int id, int count, ch.bbcag.findyourway.model.ConnectionDetail connectionDetails){
        // Verlängert die Linie für jeden Durchgang um 36dp
        View line = (View)findViewById(R.id.line);
        line.getLayoutParams().height = line.getLayoutParams().height + dpToPixel(getApplicationContext(),(float)36.5);

        // Holt das ConstraintLayout
        ConstraintLayout mConstraintLayout  = (ConstraintLayout)findViewById(R.id.mainConstraint);
        ConstraintSet set = new ConstraintSet();

        // Ist die Liste noch nicht zu Ende werden weitere Punkte erzeugt.
        if(count < connectionDetails.getPassList().size() -1){
            // Erstellt Punkt für den Zwischenhalt
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams dotLayout = new LinearLayout.LayoutParams(dpToPixel(getApplicationContext(), 15), dpToPixel(getApplicationContext(), 15));
            dot.setLayoutParams(dotLayout);
            dot.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);
            dot.setId(View.generateViewId());
            mConstraintLayout.addView(dot);
            // Constraint für den Punkt
            set.clone(mConstraintLayout);
            set.connect(dot.getId(), ConstraintSet.LEFT, mConstraintLayout.getId(), ConstraintSet.LEFT, dpToPixel(getApplicationContext(), 69));
            set.connect(dot.getId(), ConstraintSet.TOP, id, ConstraintSet.BOTTOM, dpToPixel(getApplicationContext(), 24));
            set.applyTo(mConstraintLayout);

            // Erstellt den TextView für die Abfahrtszeit
            LinearLayout.LayoutParams TextLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPixel(getApplicationContext(), 15));
            TextView time = new TextView(this);
            time.setLayoutParams(TextLayout);
            if(connectionDetails.getPassList().get(count).getDeparture() != null)
                time.setText(connectionDetails.getPassList().get(count).getDeparture());
            time.setTextSize(12);
            time.setId(View.generateViewId());
            mConstraintLayout.addView(time);

            // Erstellt den TextView für den Abfahrtsort
            LinearLayout.LayoutParams Test = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpToPixel(getApplicationContext(), 16));
            TextView name = new TextView(this);
            name.setLayoutParams(Test);

            String locationText;
            if(connectionDetails.getPassList().get(count).getPlatform() != null && connectionDetails.getPassList().get(count).getPlatform() != "null"){
                locationText = connectionDetails.getPassList().get(count).getStation() + ", Gl. " + connectionDetails.getPassList().get(count).getPlatform();
            }
            else {
                locationText = connectionDetails.getPassList().get(count).getStation().getName();
            }
            name.setText(locationText);
            name.setTextSize(12);
            name.setId(View.generateViewId());
            mConstraintLayout.addView(name);

            // Setzt die Constraints für den Haltepunkt auf der Linie
            set.clone(mConstraintLayout);
            set.connect(dot.getId(), ConstraintSet.LEFT, mConstraintLayout.getId(), ConstraintSet.LEFT, dpToPixel(getApplicationContext(), 69));
            set.connect(dot.getId(), ConstraintSet.TOP, id, ConstraintSet.BOTTOM, dpToPixel(getApplicationContext(), 24));
            set.applyTo(mConstraintLayout);

            // Setzt die Constraints für die Abfahrtszeit
            set.clone(mConstraintLayout);
            set.connect(time.getId(), ConstraintSet.RIGHT, dot.getId(), ConstraintSet.LEFT, dpToPixel(getApplicationContext(), 2));
            set.connect(time.getId(), ConstraintSet.TOP, dot.getId(), ConstraintSet.TOP, dpToPixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

            // Setzt die Constraints für den Abfahrtsort
            set.clone(mConstraintLayout);
            set.connect(name.getId(), ConstraintSet.LEFT, dot.getId(), ConstraintSet.RIGHT, dpToPixel(getApplicationContext(), 2));
            set.connect(name.getId(), ConstraintSet.TOP, dot.getId(), ConstraintSet.TOP, dpToPixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

            // Counter um ein erhöhren / durch Liste iterrieren
            count++;

            // rekursiver Aufruf
            createDots(dot.getId(), count, connectionDetails);
        }
    }

    /**
     * Rechnet Pixel(Integer) in DP (abhängig der Density) um.
     * @param c Applicationcontext
     * @param dp Anzahl DP
     * @return  Anzahl Pixel
     */
    public static int dpToPixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;
        return (int)pixel;
    }

}