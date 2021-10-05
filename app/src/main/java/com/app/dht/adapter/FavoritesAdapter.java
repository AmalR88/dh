package com.app.dht.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.app.dht.R;
import com.app.dht.activity.MovieDetailsActivity;
import com.app.dht.database.Movie;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.TextItemViewHolder> {
    Context context;
    List<Movie> favArrayList;
    public FavoritesAdapter(Activity activity, ArrayList<Movie> favArrayList) {
        context = activity;
        this.favArrayList = favArrayList;
    }
    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movies, parent, false);
        return new TextItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextItemViewHolder holder, final int position) {
        holder.favorite.setVisibility(View.GONE);
        if(favArrayList!=null&&favArrayList.size()>0)
        {
            holder.title.setText(favArrayList.get(position).movieTitle);
            if(favArrayList.get(position).moviePoster!="null")
            {
                Glide.with(context)
                        .load(favArrayList.get(position).moviePoster)
                        .into(holder.poster);
            } }

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, MovieDetailsActivity.class);
                in.putExtra("title",""+favArrayList.get(position).movieTitle);
                in.putExtra("year",""+favArrayList.get(position).movieYear);
                in.putExtra("type",""+favArrayList.get(position).movieType);
                in.putExtra("poster",""+favArrayList.get(position).moviePoster);
                context.startActivity(in);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return favArrayList.size();
    }
    class TextItemViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView poster;
        AppCompatTextView title;
        AppCompatButton favorite;
        public TextItemViewHolder(View itemView) {
            super(itemView);
            poster =  itemView.findViewById(R.id.iv_poster);
            title=itemView.findViewById(R.id.tv_title);
            favorite=itemView.findViewById(R.id.btn_favorite);
        }
    }
}