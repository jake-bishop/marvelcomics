package com.geekmode.marvelcomics.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.model.ComicModel;

import java.util.List;

public class ComicListAdapter extends RecyclerView.Adapter<ComicListViewHolder> {
    private final ComicListActivity comicListActivity;
    private final List<ComicModel> data;
    private ImageUtil imageLoader;
    private ComicListPresenter comicListPresenter;

    public ComicListAdapter(final ComicListActivity comicListActivity, final List<ComicModel> data, final ImageUtil imageLoader, final ComicListPresenter comicListPresenter) {
        this.comicListActivity = comicListActivity;
        this.data = data;
        this.imageLoader = imageLoader;
        this.comicListPresenter = comicListPresenter;
    }

    @Override
    public void onBindViewHolder(final ComicListViewHolder holder, final int position) {
        holder.bindView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ComicListViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_row, parent, false);
        return new ComicListViewHolder(view, imageLoader, comicListPresenter);
    }
}
