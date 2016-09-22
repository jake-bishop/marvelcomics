package com.geekmode.marvelcomics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "MainActivity onStart");

        CharacterService characterService = ServiceGenerator.getService(CharacterService.class, getApplicationContext());
        Observable<CharactersResponse> response = characterService.characters("Bishop");

        subscription = response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .first()
                .subscribe(this::updateCharacterCard,
                        error -> {
                            Log.e(TAG, "Error: sorry, unable to load character. " + error.getMessage());
                            Toast.makeText(getApplicationContext(), "Service response error: " + error.getMessage(), Toast.LENGTH_LONG).show();
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

    private void updateCharacterCard(final CharactersResponse charactersResponse) {
        Log.i(TAG, "RxJava: processing characterData: " + charactersResponse.toString());
        final CharacterModel firstCharacter = charactersResponse.getFirstCharacter();
        TextView titleView = (TextView) findViewById(R.id.title_text_view);
        TextView descriptionView = (TextView) findViewById(R.id.description_text_view);
        TextView attributionView = (TextView) findViewById(R.id.attribution_text_view);
        ImageView imageView = (ImageView) findViewById(R.id.character_image_view);
        if (firstCharacter != null) {
            final String title = firstCharacter.getName();
            final String description = firstCharacter.getDescription();
            final String attribution = charactersResponse.getAttributionText();
            if (titleView != null) {
                titleView.setText(title);
            }
            if (descriptionView != null) {
                if (description != null && !description.isEmpty()) {
                    descriptionView.setText(description);
                } else {
                    descriptionView.setText(R.string.character_description);
                }
            }
            if (attributionView != null) {
                attributionView.setVisibility(View.VISIBLE);
                attributionView.setText(attribution);
            }
        } else {
            if (titleView != null) {
                titleView.setText(R.string.character_title);
            }
            if (descriptionView != null) {
                descriptionView.setText(R.string.character_description);
            }
            if (attributionView != null) {
                attributionView.setVisibility(View.INVISIBLE);
            }
        }

        if(firstCharacter.getThumbnailPath() != null && !firstCharacter.getThumbnailPath().isEmpty()) {
            Picasso.with(this)
                    .load(firstCharacter.getThumbnailPath())
                    .into(imageView);
        }
    }
}
