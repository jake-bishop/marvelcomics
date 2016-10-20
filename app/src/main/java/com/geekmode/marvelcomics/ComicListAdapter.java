package com.geekmode.marvelcomics;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.model.ComicModel;

import java.util.List;

public class ComicListAdapter extends ArrayAdapter {
    private final ComicListActivity comicListActivity;
    private final List<ComicModel> data;
    private ImageUtil imageLoader;

    public ComicListAdapter(final ComicListActivity comicListActivity, final List<ComicModel> data, final ImageUtil imageLoader) {
        super(comicListActivity, R.layout.comic_row, data);
        this.comicListActivity = comicListActivity;
        this.data = data;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(comicListActivity, R.layout.comic_row, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.comic_list_image);
        TextView textView = (TextView) view.findViewById(R.id.comic_list_name);

        ComicModel comic = data.get(position);
        imageLoader.loadImage(comic.getThumbnailPath(), imageView);
        textView.setText(comic.getTitle());

        return view;
    }
}
