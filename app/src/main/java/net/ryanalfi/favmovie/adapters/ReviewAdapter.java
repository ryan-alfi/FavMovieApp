package net.ryanalfi.favmovie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.ryanalfi.favmovie.R;
import net.ryanalfi.favmovie.models.Review;

import java.util.List;

/**
 * Created by imac on 9/4/17.
 */

public class ReviewAdapter extends BaseAdapter {
    List<Review> mReview;
    Context context;

    public ReviewAdapter(List<Review> mReview, Context context) {
        this.mReview = mReview;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mReview.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = layoutInflater.inflate(R.layout.item_movie_review, null);

        TextView authorTxt = (TextView) viewRow.findViewById(R.id.tv_author);
        TextView contentTxt = (TextView) viewRow.findViewById(R.id.tv_content);

        authorTxt.setText(mReview.get(i).getAuthor());
        contentTxt.setText(mReview.get(i).getContent());
        return viewRow;
    }
}
