package pdesigns.com.soa;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


/**
 * The type Around custom list adapter.
 */
public class MovieCustomListAdapter extends ArrayAdapter<Movie> {

    private final Activity context;
    private final ArrayList<Movie> itemname;



    /**
     * Instantiates a new Around custom list adapter.
     *
     * @param context  the context
     * @param itemname the itemname
     */
    public MovieCustomListAdapter(Activity context, ArrayList<Movie> itemname) {
        super(context, R.layout.each_venue, itemname);
        // Auto-generated constructor stu
        this.context = context;

        this.itemname = itemname;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.each_venue, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.singleBar);
        TextView txtDis = (TextView) rowView.findViewById(R.id.disBox);
        txtTitle.setText(itemname.get(position).getTitle());
        txtDis.setText(itemname.get(position).getGenre());



        return rowView;

    }


}