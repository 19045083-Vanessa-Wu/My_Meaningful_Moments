package sg.edu.rp.c346.id19045083.mymeaningfulmoments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    TextView tvTitle, tvLocationDate, tvDescription, tvStars;
    EditText etTitle, etLocation, etDate, etDescription;
    RatingBar rbStars;
    Button btnInsert, btnShowList;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tvTitle = findViewById(R.id.textViewTitle);
        tvLocationDate = findViewById(R.id.textViewLocationDate);
        tvDescription = findViewById(R.id.textViewDescription);
        tvStars = findViewById(R.id.textViewStars);

        etTitle = findViewById(R.id.editTextTitle);
        etLocation = findViewById(R.id.editTextLocation);
        etDate = findViewById(R.id.editTextDate);
        etDescription = findViewById(R.id.editTextDescription);

        rbStars = findViewById(R.id.ratingBarStars);

        btnInsert = findViewById(R.id.buttonInsert);
        btnShowList = findViewById(R.id.buttonShowList);

        // Design for EditText expiry - date picker in edittext
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int mth = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                    }
                }, year, mth, day);
                picker.show();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etTitle.getText().toString().isEmpty()|| etLocation.getText().toString().isEmpty()|| etDate.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();

                } else {
                    String title = etTitle.getText().toString();
                    String location = etLocation.getText().toString();
                    String date = etDate.getText().toString();
                    String description = etDescription.getText().toString();
                    int stars = (int) rbStars.getRating();

                    DBHelper dbh = new DBHelper(AddActivity.this);
                    long inserted_id = dbh.insertMoment(title, location, date, description, stars);
                    if (inserted_id > 0) {
                        Toast.makeText(AddActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
                    }
                }

                //Clear Text,
                etTitle.setText("");
                etLocation.setText("");
                etDate.setText("");
                etDescription.setText("");
                rbStars.setRating(0);

                //Hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
