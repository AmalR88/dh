package com.app.dht.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.dht.R;
import com.app.dht.adapter.FavoritesAdapter;
import com.app.dht.adapter.MoviesAdapter;
import com.app.dht.constant.Config;
import com.app.dht.database.AppDatabase;
import com.app.dht.database.Movie;
import com.app.dht.model.Movies;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Movies> moviesArrayList=new ArrayList();
    MoviesAdapter moviesAdapter;
    RecyclerView recyclerView;
    AppCompatEditText editTextSearch;
    AppCompatTextView tvBottomBarText;
    AppCompatImageView imageViewSearch,imageViewMovies,imageViewFavorite;
    String searchKey="movies";
    View bottombarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextSearch=findViewById(R.id.et_search);
        imageViewSearch=findViewById(R.id.img_search);
        recyclerView=findViewById(R.id.recyclerView);
        bottombarView=findViewById(R.id.v_bottom_bar);
        imageViewFavorite=findViewById(R.id.img_favourite);
        imageViewMovies=findViewById(R.id.img_movies);
        tvBottomBarText=findViewById(R.id.tv_bottom_text);
        initRecyclerView();

        imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBottomBarText.setText("FAVORITES");
                favoriteMovies();
            }
        });
        imageViewMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBottomBarText.setText("MOVIES");
                searchKey="movies";
                getMoviesData();
            }
        });

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(editTextSearch.getText().toString().length()>0)
              {
                  searchKey=editTextSearch.getText().toString();
                  getMoviesData();
              }
              else
              {
                  Toast.makeText(MainActivity.this,"Please type something in search box",Toast.LENGTH_SHORT).show();

              }

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged( RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling up
                    bottombarView.setVisibility(View.GONE);
                    imageViewFavorite.setVisibility(View.GONE);
                    imageViewMovies.setVisibility(View.GONE);
                    tvBottomBarText.setVisibility(View.GONE);
                } else {
                    // Scrolling down
                    bottombarView.setVisibility(View.VISIBLE);
                    imageViewFavorite.setVisibility(View.VISIBLE);
                    imageViewMovies.setVisibility(View.VISIBLE);
                    tvBottomBarText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecyclerView()
    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getMoviesData();
    }
    private void getMoviesData() {
        moviesArrayList.clear();
        String url =Config.BASE_URL+"?apikey="+Config.API_KEY+"&s="+searchKey;
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response!=null){

                            parseData(response);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,"Error : Unable to load data",Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Config.TIME_OUT_DURATION, Config.MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


    }

    private void parseData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("Search");
            if (jsonArray!=null&&jsonArray.length() > 0) {
                String title="";
                String year="";
                String poster="";
                String type="";
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    title=jsonObject1.getString("Title");
                    poster=jsonObject1.getString("Poster");
                    type=jsonObject1.getString("Type");
                    year=jsonObject1.getString("Year");
                    moviesArrayList.add(new Movies(title,year,poster,type));
                }
            }
            else {
                Toast.makeText(MainActivity.this, "Error : No data found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(moviesArrayList.size()>0)
        {
            moviesAdapter = new MoviesAdapter(MainActivity.this,moviesArrayList);
            recyclerView.setAdapter(moviesAdapter);
        }
        else
        {
            Toast.makeText(MainActivity.this,"Error : No data found",Toast.LENGTH_SHORT).show();
        }
    }

    private void favoriteMovies() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<Movie> list =db.movieDao().getAllMovies();
        recyclerView.setAdapter(new FavoritesAdapter(MainActivity.this, (ArrayList<Movie>) list));
    }


}