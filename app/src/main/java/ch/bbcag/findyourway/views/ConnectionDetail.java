package ch.bbcag.findyourway.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

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
        ConstraintLayout mConstraintLayout  = (ConstraintLayout)findViewById(R.id.mainConstraint);
        ConstraintSet set = new ConstraintSet();

        TextView from = (TextView)findViewById(R.id.textViewFrom);
        TextView departure = (TextView)findViewById(R.id.textViewDeparture);

        if(connectionDetail.getPassList().size() <= 1){
            View line = (View)findViewById(R.id.line);
            line.getLayoutParams().height = 10;
        }
        else {
            View line = (View)findViewById(R.id.line);
            line.getLayoutParams().height = 0;
            //line.getLayoutParams().height = dpTopixel(getApplicationContext(),(float)0);


            // create first dot
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams dotLayout = new LinearLayout.LayoutParams(dpTopixel(getApplicationContext(), 15), dpTopixel(getApplicationContext(), 15));
            dot.setLayoutParams(dotLayout);
            dot.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);
            dot.setId(View.generateViewId());
            mConstraintLayout.addView(dot);
            // constraint for dot
            set.clone(mConstraintLayout);
            set.connect(dot.getId(), ConstraintSet.LEFT, mConstraintLayout.getId(), ConstraintSet.LEFT, dpTopixel(getApplicationContext(), 69));
            set.connect(dot.getId(), ConstraintSet.TOP, findViewById(R.id.imageViewPoint).getId(), ConstraintSet.BOTTOM, dpTopixel(getApplicationContext(), 24));
            set.applyTo(mConstraintLayout);

            // create departure text
            LinearLayout.LayoutParams TextLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpTopixel(getApplicationContext(), 14));
            TextView time = new TextView(this);
            time.setLayoutParams(TextLayout);
            time.setText(connectionDetail.getPassList().get(1).getDeparture());
            time.setTextSize(12);
            time.setId(View.generateViewId());
            mConstraintLayout.addView(time);

            // create station text
            LinearLayout.LayoutParams Test = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpTopixel(getApplicationContext(), 14));
            TextView name = new TextView(this);
            name.setLayoutParams(Test);
            name.setText(connectionDetail.getPassList().get(1).getStation().getName());
            name.setTextSize(12);
            name.setId(View.generateViewId());
            mConstraintLayout.addView(name);


            set.clone(mConstraintLayout);
            set.connect(time.getId(), ConstraintSet.RIGHT, dot.getId(), ConstraintSet.LEFT, dpTopixel(getApplicationContext(), 2));
            set.connect(time.getId(), ConstraintSet.TOP, dot.getId(), ConstraintSet.TOP, dpTopixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

            set.clone(mConstraintLayout);
            set.connect(name.getId(), ConstraintSet.LEFT, dot.getId(), ConstraintSet.RIGHT, dpTopixel(getApplicationContext(), 2));
            set.connect(name.getId(), ConstraintSet.TOP, dot.getId(), ConstraintSet.TOP, dpTopixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

            // create stops
            CreateDots(dot.getId(), 2, connectionDetail);


            // create destination stop
            line.getLayoutParams().height = line.getLayoutParams().height + dpTopixel(getApplicationContext(),(float)50);

            ImageView endDot = new ImageView(this);
            LinearLayout.LayoutParams endDotLayout = new LinearLayout.LayoutParams(dpTopixel(getApplicationContext(), 16), dpTopixel(getApplicationContext(), 16));
            endDot.setLayoutParams(endDotLayout);
            endDot.setBackgroundResource(R.drawable.ic_brightness_1_black_24dp);
            endDot.setId(View.generateViewId());
            mConstraintLayout.addView(endDot);

            set.clone(mConstraintLayout);
            set.connect(endDot.getId(), ConstraintSet.LEFT, mConstraintLayout.getId(), ConstraintSet.LEFT, dpTopixel(getApplicationContext(), 69));
            set.connect(endDot.getId(), ConstraintSet.TOP, line.getId(), ConstraintSet.BOTTOM, dpTopixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

            LinearLayout.LayoutParams endLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpTopixel(getApplicationContext(), 20));
            // create departure text
            TextView endTime = new TextView(this);
            endTime.setLayoutParams(endLayout);
            endTime.setText(connectionDetail.getArrival());
            endTime.setTextSize(14);
            endTime.setId(View.generateViewId());
            mConstraintLayout.addView(endTime);

            // create departure text
            TextView nameDestination = new TextView(this);
            nameDestination.setLayoutParams(endLayout);
            nameDestination.setText(connectionDetail.getTo());
            nameDestination.setTextSize(14);
            nameDestination.setId(View.generateViewId());
            mConstraintLayout.addView(nameDestination);

            set.clone(mConstraintLayout);
            set.connect(endTime.getId(), ConstraintSet.RIGHT, endDot.getId(), ConstraintSet.LEFT, dpTopixel(getApplicationContext(), 4));
            set.connect(endTime.getId(), ConstraintSet.TOP, endDot.getId(), ConstraintSet.TOP, dpTopixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

            set.clone(mConstraintLayout);
            set.connect(nameDestination.getId(), ConstraintSet.LEFT, endDot.getId(), ConstraintSet.RIGHT, dpTopixel(getApplicationContext(), 4));
            set.connect(nameDestination.getId(), ConstraintSet.TOP, endDot.getId(), ConstraintSet.TOP, dpTopixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

        }

        if(connectionDetail.getPlatform() != null && connectionDetail.getPlatform() != "null"){
            from.setText(connectionDetail.getFrom() + ", Gl. " + connectionDetail.getPlatform());
        }
        else {
            from.setText(connectionDetail.getFrom());
        }
        departure.setText(connectionDetail.getDeparture());



    }

    private void CreateDots(int id, int count, ch.bbcag.findyourway.model.ConnectionDetail connectionDetails){
        View line = (View)findViewById(R.id.line);
        line.getLayoutParams().height = line.getLayoutParams().height + dpTopixel(getApplicationContext(),(float)36);

        ConstraintLayout mConstraintLayout  = (ConstraintLayout)findViewById(R.id.mainConstraint);
        ConstraintSet set = new ConstraintSet();

        if(count < connectionDetails.getPassList().size() -1){
            // create first dot
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams dotLayout = new LinearLayout.LayoutParams(dpTopixel(getApplicationContext(), 15), dpTopixel(getApplicationContext(), 15));
            dot.setLayoutParams(dotLayout);
            dot.setBackgroundResource(R.drawable.ic_radio_button_checked_black_24dp);
            dot.setId(View.generateViewId());
            mConstraintLayout.addView(dot);
            // constraint for dot
            set.clone(mConstraintLayout);
            set.connect(dot.getId(), ConstraintSet.LEFT, mConstraintLayout.getId(), ConstraintSet.LEFT, dpTopixel(getApplicationContext(), 69));
            set.connect(dot.getId(), ConstraintSet.TOP, id, ConstraintSet.BOTTOM, dpTopixel(getApplicationContext(), 24));
            set.applyTo(mConstraintLayout);

            // create departure text
            LinearLayout.LayoutParams TextLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpTopixel(getApplicationContext(), 15));
            TextView time = new TextView(this);
            time.setLayoutParams(TextLayout);
            time.setText(connectionDetails.getPassList().get(count).getDeparture());
            time.setTextSize(12);
            time.setId(View.generateViewId());
            mConstraintLayout.addView(time);

            // create departure text
            LinearLayout.LayoutParams Test = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dpTopixel(getApplicationContext(), 14));
            TextView name = new TextView(this);
            name.setLayoutParams(Test);
            name.setText(connectionDetails.getPassList().get(count).getStation().getName());
            name.setTextSize(12);
            name.setId(View.generateViewId());
            mConstraintLayout.addView(name);


            set.clone(mConstraintLayout);
            set.connect(dot.getId(), ConstraintSet.LEFT, mConstraintLayout.getId(), ConstraintSet.LEFT, dpTopixel(getApplicationContext(), 69));
            set.connect(dot.getId(), ConstraintSet.TOP, id, ConstraintSet.BOTTOM, dpTopixel(getApplicationContext(), 24));
            set.applyTo(mConstraintLayout);

            set.clone(mConstraintLayout);
            set.connect(time.getId(), ConstraintSet.RIGHT, dot.getId(), ConstraintSet.LEFT, dpTopixel(getApplicationContext(), 2));
            set.connect(time.getId(), ConstraintSet.TOP, dot.getId(), ConstraintSet.TOP, dpTopixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

            set.clone(mConstraintLayout);
            set.connect(name.getId(), ConstraintSet.LEFT, dot.getId(), ConstraintSet.RIGHT, dpTopixel(getApplicationContext(), 2));
            set.connect(name.getId(), ConstraintSet.TOP, dot.getId(), ConstraintSet.TOP, dpTopixel(getApplicationContext(), 0));
            set.applyTo(mConstraintLayout);

            count++;

            CreateDots(dot.getId(), count, connectionDetails);

        }
    }

    public static int dpTopixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;
        return (int)pixel;
    }

}
