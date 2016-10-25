package com.geekmode.marvelcomics.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.InjectionHelper;
import com.geekmode.marvelcomics.model.ComicModel;

import javax.inject.Inject;

public class ComicListViewHolder extends RecyclerView.ViewHolder {

    @Inject
    ImageUtil imageUtil;

    private final ImageView imageView;
    private final TextView nameView;

    @Inject
    public ComicListViewHolder(View itemView, final ItemClickedListener listener) {
        super(itemView);
        InjectionHelper.getApplicationComponent(itemView.getContext()).inject(this);

        imageView = (ImageView) itemView.findViewById(R.id.comic_list_image);
        nameView = (TextView) itemView.findViewById(R.id.comic_list_name);

        itemView.setOnClickListener(v -> listener.itemClicked(getAdapterPosition()));
    }

    public void bindView(ComicModel comic) {
        nameView.setText(comic.getTitle());
        imageUtil.loadImage(itemView.getContext(), comic.getThumbnailPath(), imageView);
    }
}
