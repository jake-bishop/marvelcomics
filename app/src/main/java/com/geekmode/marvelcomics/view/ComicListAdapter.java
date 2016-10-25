package com.geekmode.marvelcomics.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.model.ComicModel;

import java.util.ArrayList;
import java.util.List;

public class ComicListAdapter extends RecyclerView.Adapter<ComicListViewHolder> {
    private final List<ComicModel> data = new ArrayList<>();
    private ItemClickedListener listener;

    public ComicListAdapter(ItemClickedListener listener) {
        this.listener = listener;
    }

    public void addComics(final List<ComicModel> comics) {
        data.clear();
        data.addAll(comics);

        notifyDataSetChanged();
    }

    public void addCOmic(ComicModel comic) {
        data.add(comic);
        notifyItemInserted(data.lastIndexOf(comic));
    }

    public void removeComic(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void moveComic(int sourcePosition, int targetPosition) {
        final ComicModel comic = data.get(sourcePosition);
        data.remove(sourcePosition);
        data.add(targetPosition, comic);

        notifyItemMoved(sourcePosition, targetPosition);
    }

    public ComicModel getComic(int position) {
        return data.get(position);
    }

    @Override
    public ComicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_row, parent, false);
        return new ComicListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ComicListViewHolder holder, int position) {
        final ComicModel comic = data.get(position);
        holder.bindView(comic);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
