package com.geekmode.marvelcomics.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.injection.InjectionHelper;
import com.geekmode.marvelcomics.model.Character;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PresenterView {

    @BindView(R.id.character_pager)
    ViewPager characterPager;

    @Inject
    MainPresenter mainPresenter;

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
        long intExtra = getIntent().getLongExtra("comic-id", -1);
        mainPresenter.refreshCharacters(String.valueOf(intExtra));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    void displayCharacters(final List<Character> characters) {

        //Since in AppCompat Activity ensure you get the SupportFragmentManager, if using native Api use Fragment Manager
        characterPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {

                final Character character = characters.get(position);
                return CharacterFragment.newInstance(character.getId());
            }

            @Override
            public int getCount() {
                return characters.size();
            }
        });
    }

    void handleError(final Throwable throwable) {
        Toast.makeText(MainActivity.this, "Unexpected Error", Toast.LENGTH_LONG).show();
    }
}
