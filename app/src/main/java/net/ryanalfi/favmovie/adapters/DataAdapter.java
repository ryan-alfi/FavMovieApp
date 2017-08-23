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
import java.util.List;

/**
 * Created by imac on 8/22/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<Movie> mMoview;
    private Context context;
    final private ListItemClickListener mOnClickListener;

    public DataAdapter(List<Movie> mMoview, Context context, ListItemClickListener listener) {
        this.mMoview = mMoview;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return mMoview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        private TextView tv_title;
        private ImageView img_poster;
        public ViewHolder(View view){
            super(view);

            tv_title = (TextView)view.findViewById(R.id.tv_title);
            img_poster = (ImageView)view.findViewById(R.id.img_poster);
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
}
