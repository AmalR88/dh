package com.app.dht.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.os.Bundle;
import com.app.dht.R;
import com.bumptech.glide.Glide;
public class MovieDetailsActivity extends AppCompatActivity {
    AppCompatImageView ivPoster;
    AppCompatTextView tvTitle,tvYear,tvType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        tvTitle=findViewById(R.id.tv_title);
        tvType=findViewById(R.id.tv_type);
        ivPoster=findViewById(R.id.iv_poster);

        setData();
    }

    private void setData()
    {
        Bundle b = getIntent().getExtras();
        if(b!=null)
        {
            String title = (String) b.get("title");
            String year = (String) b.get("year");
            String type = (String) b.get("type");
            String poster = (String) b.get("poster");
            tvTitle.setText(title);
            tvType.setText(type +" : "+ year);
            Glide.with(MovieDetailsActivity.this)
                    .load(poster)
                    .into(ivPoster);
        }


    }
}