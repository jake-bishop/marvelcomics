package com.geekmode.marvelcomics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.InjectionHelper;
import com.geekmode.marvelcomics.view.PresenterView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PresenterView {

    @Inject
    MainPresenter mainPresenter;
    @Inject
    ImageUtil imageUtil;

    @BindView(R.id.title_text_view)
    TextView titleView;
    @BindView(R.id.description_text_view)
    TextView descriptionView;
    @BindView(R.id.attribution_text_view)
    TextView attributionView;
    @BindView(R.id.character_image_view)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectionHelper.getApplicationComponent(this).inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.refreshCharacterData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    void updateName(final String name) {
        titleView.setText(name);
    }

    void updateImage(final String url) {
        imageUtil.loadImage(url, imageView);
    }

    void updateDescription(final String description) {
        descriptionView.setText(description);
    }

    void updateDescription(final int resourceId) {
        descriptionView.setText(resourceId);
    }

    void updateAttribution(final String attribution) {
        attributionView.setText(attribution);
    }

    void handleError(final Throwable throwable) {
        Toast.makeText(MainActivity.this, "Unexpected Error", Toast.LENGTH_LONG).show();
    }
}
