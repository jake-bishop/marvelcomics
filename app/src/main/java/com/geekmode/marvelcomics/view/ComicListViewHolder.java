package com.geekmode.marvelcomics.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.model.ComicModel;

public class ComicListViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    private final TextView name;
    private final ImageUtil imageLoader;
    private final ComicListPresenter presenter;

    public ComicListViewHolder(final View itemView, final ImageUtil imageLoader, final ComicListPresenter presenter) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.comic_list_image);
        name = (TextView) itemView.findViewById(R.id.comic_list_name);
        this.imageLoader = imageLoader;
        this.presenter = presenter;
    }

    public void bindView(ComicModel comic) {
        imageLoader.loadImage(comic.getThumbnailPath(), image);
        name.setText(comic.getTitle());
        itemView.setOnClickListener(v -> presenter.comicClicked(comic.getId()));
    }
}
