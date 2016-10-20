package com.geekmode.marvelcomics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.InjectionHelper;
import com.geekmode.marvelcomics.model.ComicModel;
import com.geekmode.marvelcomics.view.PresenterView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicListActivity extends AppCompatActivity  implements PresenterView {

    @BindView(R.id.comic_list_view)
    ListView comicListView;
    @BindView(R.id.empty_view)
    View emptyView;

    @Inject
    ComicListPresenter presenter;
    @Inject
    ImageUtil imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        InjectionHelper.getApplicationComponent(this).inject(this);
        ButterKnife.bind(this);

        presenter.attachView(this);
        comicListView.setEmptyView(emptyView);

        presenter.refreshCharacters();
    }

    public void showError() {
        Toast.makeText(this, "Error Loading comics", Toast.LENGTH_LONG).show();
    }

    public void showComics(List<ComicModel> data) {
        comicListView.setAdapter(new ComicListAdapter(this, data, imageLoader));
        comicListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ComicListActivity.this, MainActivity.class);
            intent.putExtra("comic-id", data.get(position).getId());
            startActivity(intent);
        });
    }
}
