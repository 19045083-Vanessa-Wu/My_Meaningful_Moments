package sg.edu.rp.c346.id19045083.mymeaningfulmoments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    TextView tvEditTitle, tvEditLocationDate, tvEditDescription, tvEditStars;
    EditText etEditTitle, etEditLocation, etEditDate, etEditDescription;
    RatingBar rbStars;
    Button btnUpdate, btnDelete, btnCancel;
    Moments moment;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvEditTitle = findViewById(R.id.textViewEditTitle);
        tvEditLocationDate = findViewById(R.id.textViewEditLocationDate);
        tvEditDescription = findViewById(R.id.textViewEditDescription);
        tvEditStars = findViewById(R.id.textViewEditStars);

        etEditTitle = findViewById(R.id.editTextEditTitle);
        etEditLocation = findViewById(R.id.editTextEditLocation);
        etEditDate = findViewById(R.id.editTextEditDate);
        etEditDescription = findViewById(R.id.editTextEditDescription);
        rbStars = findViewById(R.id.ratingBarEditStars);

        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnCancel = findViewById(R.id.buttonCancel);

        Intent intent = getIntent();
        moment = (Moments) intent.getSerializableExtra("data");

        etEditTitle.setText(moment.getTitle());
        etEditLocation.setText(moment.getLocation());
        etEditDate.setText(moment.getDate());
        etEditDescription.setText(moment.getDescription());
        rbStars.setRating(moment.getStars());

        // Design for EditText expiry - date picker in edittext
        etEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int mth = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etEditDate.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));
                    }
                }, year, mth, day);
                picker.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the Dialog Builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                // Set the dialog details
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(false);

                // Configure the 'positive' button
                myBuilder.setPositiveButton("Do Not Discard", null);

                // Configure the 'negative' button
                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);

                if (etEditTitle.getText().toString().isEmpty()||
                        etEditLocation.getText().toString().isEmpty()||
                        etEditDate.getText().toString().isEmpty()||
                        etEditDescription.getText().toString().isEmpty()) {
                    Toast.makeText(EditActivity.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    moment.setTitle(etEditTitle.getText().toString());
                    moment.setLocation(etEditLocation.getText().toString());
                    moment.setDate(etEditDate.getText().toString());
                    moment.setDescription(etEditDescription.getText().toString());
                    moment.setStars((int) rbStars.getRating());
                    db.updateMoment(moment);
                    db.close();
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the Dialog Builder
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                // Set the dialog details
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the food named: " + moment.getTitle() + "?");
                myBuilder.setCancelable(false);

                // Configure the 'positive' button
                myBuilder.setPositiveButton("Cancel", null);

                // Configure the 'negative' button
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper db = new DBHelper(EditActivity.this);
                        db.deleteMoment(moment.getId());
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }   // onCreate()
}   // Class
