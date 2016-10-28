package pdesigns.com.soa;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * The type Around you fragment.
 */
public class ListAllMovies extends Fragment implements Serializable, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    //JSON node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PID = "MovieId";
    private static final String TAG_NAME = "MovieName";
    private static final String TAG_PGENRE = "MovieGenre";
    //private static final String TAG_WHEELCHAR = "wheelchair";
    private static final String TAG_LENGTH = "MovieLength";
    /**
     * The Prgm images.
     */
    public static ArrayList<String> prgmImages;
    /**
     * The Prgm fbs.
     */
    public static ArrayList<String> prgmFbs;
    // the url get all bars list, at the moment, its just running off local host, but can easily be changed to use an online server.
    private static String url_all_bars = Server_Connections.SERVER ;
    /**
     * The Json parser.
     */
// create a json parser object
    JSONParser jsonParser = new JSONParser();
    /**
     * The Bar list.
     */
    ArrayList<HashMap<Movie, String>> barList;
    /**
     * The B.
     */
    Movie m;
    /**
     * The Sc.
     */
    Server_Connections sc = new Server_Connections();


// images JSON Array
    JSONArray moviesJson = null;
    /**
     * The Refresh toggle.
     */
    boolean refreshToggle = false;
    /**
     * The Loc 1.
     */
    Location loc1;
    /**
     * The Session.
     */
// Session Manager Class
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private int mActionBarHeight = 0;
    //Diaglog to show db connections status
    private ProgressDialog myDialog;
    private MovieCustomListAdapter mAdapter;
    private ArrayList<Movie> mStrings = new ArrayList<Movie>();
    private ArrayList<Movie> mBars;
    private ArrayList<Integer> dis;
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ListView simpleList = null;
    //SwipeView Stuffs
    private SwipeRefreshLayout swipeView;
    /**
     * The Handler.
     */
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //hashmap for GridView
            mStrings = new ArrayList<Movie>();

            refreshToggle = true;
            mAdapter.notifyDataSetChanged();
            mAdapter.clear();
            new LoadAllMovies().execute();


            mAdapter = new MovieCustomListAdapter(getActivity(), mStrings);
            simpleList.setAdapter(new MovieCustomListAdapter(getActivity(), mStrings));


            swipeView.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setAction("pdesigns.com.lastorders.ClientSide");
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                    getActivity().sendBroadcast(intent);

                    Toast.makeText(getContext(),
                            "Refreshing.....", Toast.LENGTH_SHORT).show();
                    swipeView.setRefreshing(false);
                }
            }, 1000);
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_around_you, container, false);

        swipeView = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeView.setOnRefreshListener(this);
        swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeView.setDistanceToTriggerSync(20);// in dips
        swipeView.setSize(SwipeRefreshLayout.DEFAULT);// LARGE also can be used


        //hashmap for GridView
        barList = new ArrayList<HashMap<Movie, String>>();
        loc1 = new Location("");



        new LoadAllMovies().execute();


        Log.d("MStrings", mStrings.toString());


        //   mAdapter = new AroundCustomListAdapter(this.getActivity(), mStrings, prgmImages, dis);
        mAdapter = new MovieCustomListAdapter(this.getActivity(), mStrings);

        simpleList = (ListView) v.findViewById(R.id.listallaround);
        simpleList.setAdapter(new MovieCustomListAdapter(this.getActivity(), mStrings));

        simpleList.setOnItemClickListener(this);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Log.d("list item selected was ", 1 + "");
;
    }

    @Override
    public void onRefresh() {
        swipeView.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeView.setRefreshing(true);
                handler.sendEmptyMessage(0);
            }
        }, 1000);

    }

    // A background async task to load all bars by making http request
    private class LoadAllMovies extends AsyncTask<String, String, String> {
        // Before starting background threads, show the progress dialog
        @Override
        protected void onPreExecute() {
            Log.d("onPreExecute", "on the onPreExecute part!!");
            super.onPreExecute();
            myDialog = new ProgressDialog(getActivity());
            myDialog.setMessage("Loading some awesome images.  Please wait....");
            myDialog.setIndeterminate(false);
            myDialog.setCancelable(false);
            myDialog.show();
        }

        // getting all the images from url
        @Override
        protected String doInBackground(String... args) {
            // Building parameters
            Log.d("doInBackground", "on the doInBackground part!!");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            try {
                //getting json string from url

                JSONArray jsonObject = jsonParser.makeHttpRequest(url_all_bars, "GET", params);

                // log information for the jspon responce
                Log.d("All images", jsonObject.toString());


                // check for success tag
                System.out.println("CONNNECTION --------------------------------------------------");
              // // int success = jsonObject.getInt(TAG_SUCCESS);
                //System.out.println(success + "");
                // the images was found
                // Getting the array of the images
               // moviesJson = jsonObject.getJSONArray("");


                Log.d("jsonLength", jsonObject.toString() + "");
                // time to loop through all the images
                for (int i = 0; i < jsonObject.length(); i++) {
                    Log.d("i aye ", i + "");
                    JSONObject c = jsonObject.getJSONObject(i);
                    Log.d("ccc", jsonObject.getJSONObject(i).toString());
                    //Storing each json item in varible


                    String name = c.getString(TAG_NAME);
                    Log.d("cccName", name);

                    String lenth = c.getString(TAG_LENGTH);
                    Log.d("cccGenre", lenth);

                    String genre = c.getString(TAG_PGENRE);
                    Log.d("ccclenth", genre);

                    int id = c.getInt(TAG_PID);
                    Log.d("cccid", id +"");



                    m = new Movie(id, name, lenth, genre);

                    Log.d("m", m.toString());


                    mStrings.add(m);
                    Log.d("Array", m.toString());


                }

            } catch (Exception e) {
                myDialog.dismiss();

                // If you need update UI, simply do this:
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // update your UI component here. in order to display friendly error report
                        Toast.makeText(getActivity(), R.string.server_is_down,
                                Toast.LENGTH_SHORT).show();
                    }
                });


                // e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            // dismiss the dialog after getting all products
            myDialog.dismiss();
            // updating UI from Background Thread
            // updating UI from Background Thread


            // updating UI from Background Thread
            getActivity().runOnUiThread(
                    new Runnable() {
                        public void run() {
                            simpleList.setAdapter(mAdapter);

                        }
                    });
        }


    }


}
