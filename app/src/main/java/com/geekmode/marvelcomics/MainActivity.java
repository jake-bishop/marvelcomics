package com.geekmode.marvelcomics;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

//        final ImageButton phoneButton = (ImageButton) findViewById(R.id.phone_button);
//        preparePhoneButton(phoneButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "MainActivity onStart");

        processNetworkingOffMain();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy");
    }

    private void processNetworkingOffMain() {
        final CharacterService characterService = ServiceGenerator.createService(CharacterService.class);
        characterService.characters("Bishop").enqueue(new Callback<CharactersResponse>() {
            @Override
            public void onResponse(final Call<CharactersResponse> call, final Response<CharactersResponse> response) {
                if (response.isSuccessful()) {
                    CharactersResponse cr = response.body();
                    updateCharacterCard(cr);
                    Log.i(TAG, cr.toString());
                } else {
                    Log.e(TAG, "Service error: " + response.message());
                    Toast.makeText(getApplicationContext(), "Service response error: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(final Call<CharactersResponse> call, final Throwable t) {
                Log.e(TAG, t.getLocalizedMessage(), t);
                Toast.makeText(getApplicationContext(), "Service failure error: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateCharacterCard(final CharactersResponse charactersResponse) {
        final CharacterModel firstCharacter = charactersResponse != null ? charactersResponse.getFirstCharacter() : null;
        TextView titleView = (TextView) findViewById(R.id.title_text_view);
        TextView descriptionView = (TextView) findViewById(R.id.description_text_view);
        TextView attributionView = (TextView) findViewById(R.id.attribution_text_view);
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
    }

    private void preparePhoneButton(ImageButton phoneButton) {
        if (phoneButton != null) {
            phoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "phoneButton::onClick");

                    final Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:7083598601"));

                    PackageManager packageManager = getPackageManager();
                    final List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
                    if (resolveInfoList != null && resolveInfoList.size() > 0) {
                        Log.i(TAG, "opening phone app");
                        startActivity(intent);
                    } else {
                        Log.i(TAG, "no phone app!");
                        Toast.makeText(getApplicationContext(), "No phone app available", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
