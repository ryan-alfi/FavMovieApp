package net.ryanalfi.favmovie.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.ryanalfi.favmovie.R;
import net.ryanalfi.favmovie.models.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by imac on 8/22/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<Movie> mMoview;
    private int[] arrFavorite;
    private Context context;
    final private ListItemClickListener mOnClickListener;

    public DataAdapter(List<Movie> mMoview,int[] arrFavorite, Context context, ListItemClickListener listener) {
        this.mMoview = mMoview;
        this.context = context;
        this.arrFavorite = arrFavorite;
        this.mOnClickListener = listener;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(mMoview.get(position).getMovie_title());
        Glide.with(context).load(mMoview.get(position).getMovie_poster()).override(200,300).centerCrop().into(holder.img_poster);

        if (useLoop(arrFavorite,mMoview.get(position).getMovie_id())){
            holder.img_favorite.setVisibility(View.VISIBLE);
        }else{
            holder.img_favorite.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mMoview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        private TextView tv_title;
        private ImageView img_poster;
        private ImageView img_favorite;
        public ViewHolder(View view){
            super(view);

            tv_title = (TextView)view.findViewById(R.id.tv_title);
            img_poster = (ImageView)view.findViewById(R.id.img_poster);
            img_favorite = (ImageView)view.findViewById(R.id.img_favorite_main);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(mMoview.get(clickedPosition).getMovie_id());
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public static boolean useLoop(int[] arr, int targetValue) {
        for(int s: arr){
            if(s==targetValue)
                return true;
        }
        return false;
    }
}
