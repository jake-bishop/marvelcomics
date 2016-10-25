package com.geekmode.marvelcomics.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.images.ImageUtil;
import com.geekmode.marvelcomics.injection.InjectionHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterFragment extends Fragment implements PresenterView {

    private static final String ARG_ID = "ARG_ID";

    @BindView(R.id.character_image_view)
    ImageView imageView;
    @BindView(R.id.title_text_view)
    TextView nameView;
    @BindView(R.id.description_text_view)
    TextView descriptionView;
    @BindView(R.id.attribution_text_view)
    TextView attributionView;

    @Inject
    CharacterPresenter characterPresenter;
    @Inject
    ImageUtil imageLoader;

    public static Fragment newInstance(long id) {
        final Bundle arguments = new Bundle();
        arguments.putString(ARG_ID, String.valueOf(id));
        final CharacterFragment fragment = new CharacterFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_character, container, false);
        ButterKnife.bind(this, view);
        characterPresenter.attachView(this);

        if (getArguments() != null) {
            characterPresenter.setCharacterId(getArguments().getString(ARG_ID));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        characterPresenter.refreshCharacterData();
    }

    void updateName(final String name) {
        nameView.setText(name);
    }

    void updateImage(final String url) {
        imageLoader.loadImage(url, imageView);
    }

    void updateDescription(final String description) {
        descriptionView.setText(description);
    }

    void updateAttribution(final String attribution) {
        attributionView.setText(attribution);
    }

    void handleError(final Throwable throwable) {
        Toast.makeText(getActivity(), "Unexpected Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        characterPresenter.detachView();
    }

}
