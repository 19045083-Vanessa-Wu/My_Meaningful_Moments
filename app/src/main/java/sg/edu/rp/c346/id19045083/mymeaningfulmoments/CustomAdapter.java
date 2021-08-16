package sg.edu.rp.c346.id19045083.mymeaningfulmoments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.time.Month;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Moments> Listmoments;

    public CustomAdapter(Context context, int resource, ArrayList<Moments> objects) {
        super(context, resource, objects);
        this.parent_context = context;
        this.layout_id = resource;
        this.Listmoments = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.textViewDisplayTitle);
        TextView tvLocationDate = rowView.findViewById(R.id.textViewDisplayLocationDate);
        RatingBar rgStars = rowView.findViewById(R.id.ratingBarDisplayStars);
        ImageView img = rowView.findViewById(R.id.imageView);

        // Obtain the Android Version information based on the position
        Moments currentInfo = Listmoments.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentInfo.getTitle());
        tvLocationDate.setText(String.format("%s (%s)", currentInfo.getLocation(), currentInfo.getDate()));
        rgStars.setRating(currentInfo.getStars());
        img.setImageResource(R.drawable.bestmoment);

        if (currentInfo.getStars() == 5){
            img.setVisibility(View.VISIBLE);
        } else {
            img.setVisibility(View.INVISIBLE);
        }

        return rowView;
    }

}
