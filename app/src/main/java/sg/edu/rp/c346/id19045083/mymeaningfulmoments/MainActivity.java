package sg.edu.rp.c346.id19045083.mymeaningfulmoments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnShow5Stars;
    ListView lv;
    Spinner spn;
    FloatingActionButton FABtnAdd;

    ArrayList<Moments> ALmoments;
    final boolean[] checker = {true};
    ArrayList<String> stars;
    ArrayAdapter<String> AAspinner;
    CustomAdapter MomentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow5Stars = findViewById(R.id.buttonShow5Stars);
        spn = findViewById(R.id.spinner);
        lv = findViewById(R.id.lv);
        FABtnAdd = findViewById(R.id.floatingActionButtonAdd);

        DBHelper dbh = new DBHelper(MainActivity.this);
        ALmoments = new ArrayList<Moments>();
        ALmoments.addAll(dbh.getAllMoments());
        MomentAdapter = new CustomAdapter(MainActivity.this, R.layout.row, ALmoments);
        lv.setAdapter(MomentAdapter);

        // Entities in Spinner
        stars = new ArrayList<String>();
        stars.add("All Records");
        for (int i=0; i < ALmoments.size(); i++) {
            String star = String.valueOf(ALmoments.get(i).getStars()) + " Star(s)";
            if (!stars.contains(star)){
                stars.add(star);
            }
        }
        AAspinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stars);
        AAspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(AAspinner);

        btnShow5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn.setSelection(0);
                ALmoments.clear();
                if (checker[0] == true) {   // Show all
                    ALmoments.addAll(dbh.getAllMoments());
                    MomentAdapter.notifyDataSetChanged();
                    checker[0] = false;
                }
                else if (checker[0] == false) { // Show all with 5 stars only
                    ALmoments.addAll(dbh.getAllMomentWithStars(5));
                    MomentAdapter.notifyDataSetChanged();
                    checker[0] = true;
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Moments data = ALmoments.get(position);
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = stars.get(position);
                select = select.split(" ")[0];
                Log.d("Spinner", "Position: " + select);
                if (position!=0) {
                    int star = Integer.parseInt(select);
                    ALmoments.clear();
                    ALmoments.addAll(dbh.getAllMomentWithStars(star));
                }
                else {
                    ALmoments.clear();
                    ALmoments.addAll(dbh.getAllMoments());
                }
                MomentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FABtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });

    } //onCreate Method

    @Override
    protected void onResume() {
        super.onResume();
        checker[0] = true;
        btnShow5Stars.performClick();

        stars.clear();
        stars.add("All Records");
        for (int i=0; i < ALmoments.size(); i++) {
            String star = String.valueOf(ALmoments.get(i).getStars()) + " Star(s)";
            if (!stars.contains(star)){
                stars.add(star);
            }
        }
        AAspinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stars);
        AAspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(AAspinner);

        DBHelper dbh = new DBHelper(MainActivity.this);
        ALmoments.clear();
        ALmoments.addAll(dbh.getAllMoments());
        MomentAdapter = new CustomAdapter(MainActivity.this, R.layout.row, ALmoments);
        lv.setAdapter(MomentAdapter);
    }
}