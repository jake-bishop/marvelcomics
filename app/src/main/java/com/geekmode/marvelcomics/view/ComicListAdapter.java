package com.geekmode.marvelcomics.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.model.ComicModel;

import java.util.ArrayList;
import java.util.List;

public class ComicListAdapter extends RecyclerView.Adapter<ComicListViewHolder> {
    private final ComicListActivity comicListActivity;
    private final List<ComicModel> comicList = new ArrayList<>();
    private ImageUtil imageLoader;
    private ComicListPresenter comicListPresenter;

    public ComicListAdapter(final ComicListActivity comicListActivity, final ImageUtil imageLoader, final ComicListPresenter comicListPresenter) {
        this.comicListActivity = comicListActivity;
        this.imageLoader = imageLoader;
        this.comicListPresenter = comicListPresenter;
    }

    public void appendComics(final List<ComicModel> comics) {
        comicList.addAll(comics);
        notifyDataSetChanged();
    }

    public void removeComic(int position) {
        comicList.remove(position);
        notifyItemRemoved(position);
    }

    public void moveComic(int sourcePosition, int targetPosition) {
        final ComicModel comic = comicList.get(sourcePosition);
        comicList.remove(sourcePosition);
        comicList.add(targetPosition, comic);

        notifyItemMoved(sourcePosition, targetPosition);
    }

    @Override
    public void onBindViewHolder(final ComicListViewHolder holder, final int position) {
        holder.bindView(comicList.get(position));
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    @Override
    public ComicListViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_row, parent, false);
        return new ComicListViewHolder(view, imageLoader, comicListPresenter);
    }
}
