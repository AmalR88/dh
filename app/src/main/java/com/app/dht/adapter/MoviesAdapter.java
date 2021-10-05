package com.app.dht.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.app.dht.R;
import com.app.dht.activity.MovieDetailsActivity;
import com.app.dht.database.AppDatabase;
import com.app.dht.database.Movie;
import com.app.dht.model.Movies;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.TextItemViewHolder> {
    Context context;
    ArrayList<Movies> moviesArrayList;

    public MoviesAdapter(Activity activity, ArrayList<Movies> moviesArrayList) {
        context = activity;
        this.moviesArrayList = moviesArrayList;
    }
    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movies, parent, false);
        return new TextItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextItemViewHolder holder, final int position) {
        if(moviesArrayList!=null&&moviesArrayList.size()>0)
        {
            holder.title.setText(moviesArrayList.get(position).getTitle());
            if(moviesArrayList.get(position).getPoster()!="null")
            {
                Glide.with(context)
                        .load(moviesArrayList.get(position).getPoster())
                        .into(holder.poster);
            } }

        holder.llrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, MovieDetailsActivity.class);
                in.putExtra("title",""+moviesArrayList.get(position).getTitle());
                in.putExtra("year",""+moviesArrayList.get(position).getYear());
                in.putExtra("type",""+moviesArrayList.get(position).getType());
                in.putExtra("poster",""+moviesArrayList.get(position).getPoster());
                context.startActivity(in);
            }
        });
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Movies has been added to favorites",Toast.LENGTH_SHORT).show();
                saveMovie(moviesArrayList.get(position).getTitle(),moviesArrayList.get(position).getYear(),moviesArrayList.get(position).getType(),moviesArrayList.get(position).getPoster());

            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    class TextItemViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView poster;
        AppCompatTextView title;
        AppCompatButton favorite;
        LinearLayoutCompat llrow;
        public TextItemViewHolder(View itemView) {
            super(itemView);
            poster =  itemView.findViewById(R.id.iv_poster);
            title=itemView.findViewById(R.id.tv_title);
            favorite=itemView.findViewById(R.id.btn_favorite);
            llrow=itemView.findViewById(R.id.ll_row);

        }
    }

    private void saveMovie(String mTitle, String mYear,String mType,String mPoster) {
        AppDatabase db  = AppDatabase.getDbInstance(context);
        Movie m = new Movie();
        m.movieTitle = mTitle;
        m.movieYear = mYear;
        m.movieType = mType;
        m.moviePoster = mPoster;
        db.movieDao().insertUser(m);
    }

}