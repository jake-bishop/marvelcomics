package com.geekmode.marvelcomics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.SchedulerProvider;
import com.geekmode.marvelcomics.model.CharacterModel;
import com.geekmode.marvelcomics.model.CharactersResponse;
import com.geekmode.marvelcomics.services.CharacterService;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Subscription subscription;

    @Inject
    CharacterService characterService;
    @Inject
    ImageUtil imageUtil;
    @Inject
    SchedulerProvider schedulerProvider;

    private TextView titleView;
    private TextView descriptionView;
    private TextView attributionView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MarvelApp) getApplication()).getApplicationComponent().inject(this);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        titleView = (TextView) findViewById(R.id.title_text_view);
        descriptionView = (TextView) findViewById(R.id.description_text_view);
        attributionView = (TextView) findViewById(R.id.attribution_text_view);
        imageView = (ImageView) findViewById(R.id.character_image_view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "MainActivity onStart");

        Observable<CharactersResponse> response = characterService.characters("Thor");

        subscription = response.subscribeOn(schedulerProvider.getIoScheduler())
                .observeOn(schedulerProvider.getMainScheduler())
                .subscribe(this::updateCharacterCard,
                        error -> {
                            Log.e(TAG, "Error: sorry, unable to load character. " + error.getMessage());
                            Toast.makeText(getApplicationContext(), "Service response error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                            imageUtil.loadImage(R.drawable.bishop, imageView);
                        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "onStop");

        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy");
    }

    protected void updateCharacterCard(CharactersResponse charactersResponse) {
        Log.i(TAG, "RxJava: processing characterData: " + charactersResponse.toString());

        CharacterModel firstCharacter = charactersResponse.getFirstCharacter();

        if (firstCharacter != null) {
            String title = firstCharacter.getName();
            String description = firstCharacter.getDescription();
            String attribution = charactersResponse.getAttributionText();

            titleView.setText(title);
            attributionView.setText(attribution);
            imageUtil.loadImage(firstCharacter.getThumbnailPath(), imageView);

            if (description != null && !description.isEmpty()) {
                descriptionView.setText(description);
            } else {
                descriptionView.setText(R.string.character_description);
            }
        } else {
            Log.w(TAG, "RxJava: first character is null!");

            titleView.setText(R.string.character_title);
            descriptionView.setText(R.string.character_description);
            imageUtil.loadImage(R.drawable.bishop, imageView);
        }
    }
}
