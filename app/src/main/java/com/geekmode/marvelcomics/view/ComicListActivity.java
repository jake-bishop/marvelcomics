package com.geekmode.marvelcomics.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.InjectionHelper;
import com.geekmode.marvelcomics.model.ComicModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicListActivity extends AppCompatActivity implements PresenterView {

    @BindView(R.id.comic_list_recyclerview)
    RecyclerView comicRecyclerView;
    @BindView(R.id.empty_view)
    TextView emptyView;

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

        comicRecyclerView.setHasFixedSize(true);
        comicRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter.refreshCharacters();
    }

    public void showError() {
        Toast.makeText(this, "Error Loading comics", Toast.LENGTH_LONG).show();
    }

    public void showComics(List<ComicModel> comics) {
        comicRecyclerView.setAdapter(new ComicListAdapter(this, comics, imageLoader, presenter));

        if (emptyView != null) {
            if (comics == null || comics.isEmpty()) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void startComicCardsActivity(long comicId) {
        final Intent intent = new Intent(ComicListActivity.this, MainActivity.class);
        intent.putExtra("comic-id", comicId);
        startActivity(intent);
    }
}
