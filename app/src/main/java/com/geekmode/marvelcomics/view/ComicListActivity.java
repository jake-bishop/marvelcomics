package com.geekmode.marvelcomics.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

    private LinearLayoutManager layoutManager;
    private ComicListAdapter adapter;

    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            final int sourcePosition = source.getAdapterPosition();
            final int targetPosition = target.getAdapterPosition();
            adapter.moveComic(sourcePosition, targetPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final int adapterPosition = viewHolder.getAdapterPosition();
            adapter.removeComic(adapterPosition);
        }
    };

    final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            System.out.println("PAGER - onScrolled");
            if (dy > 0) {
                System.out.println("PAGER - scrolled DOWN");
                System.out.println("PAGER - last visible pos: " + layoutManager.findLastVisibleItemPosition());
                if ((layoutManager.findLastVisibleItemPosition() + 2) >= adapter.getItemCount()) {
                    System.out.println("PAGER - last visible >= item count");
                    presenter.getMoreCharacters();
                }
            }
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        InjectionHelper.getApplicationComponent(this).inject(this);
        ButterKnife.bind(this);

        presenter.attachView(this);

        comicRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        comicRecyclerView.setLayoutManager(layoutManager);

        adapter = new ComicListAdapter(this, imageLoader, presenter);
        comicRecyclerView.setAdapter(adapter);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(comicRecyclerView);

        comicRecyclerView.addOnScrollListener(scrollListener);

        presenter.refreshCharacters();
    }

    public void showError() {
        Toast.makeText(this, "Error Loading comics", Toast.LENGTH_LONG).show();
    }

    public void showComics(List<ComicModel> comics) {
        adapter.appendComics(comics);

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
