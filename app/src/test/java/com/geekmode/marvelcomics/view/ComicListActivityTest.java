package com.geekmode.marvelcomics.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.geekmode.marvelcomics.BaseInjectedRobolectricTest;
import com.geekmode.marvelcomics.R;
import com.geekmode.marvelcomics.model.ComicModel;
import com.geekmode.marvelcomics.model.Thumbnail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;

import butterknife.ButterKnife;
import edu.emory.mathcs.backport.java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

public class ComicListActivityTest extends BaseInjectedRobolectricTest {

    @Mock private ComicListPresenter presenter;

    private ComicListActivity testObject;

    @Before
    public void setUp() throws Exception {
        testObject = Robolectric.buildActivity(ComicListActivity.class).create().get();
    }

    @Test
    public void presenterIsBoundOnCreate() throws Exception {
        verify(presenter).attachView(testObject);
    }

    @Test
    public void startComicCardsActivityHasCorrectIntent() throws Exception {
        final int expectedComicId = 348796;

        testObject.startComicCardsActivity(expectedComicId);

        final Intent intent = shadowOf(testObject).peekNextStartedActivity();
        assertNotNull(intent);
        assertEquals(expectedComicId, intent.getIntExtra("comic-id", -99));
        assertEquals(MainActivity.class.getName(), intent.getComponent().getClassName());
    }

    @Test
    public void showComics() throws Exception {
        ComicModel comic1 = new ComicModel();
        comic1.setTitle("comic 1 title");
        comic1.setDescription("comic 1 description");
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setExtension("png");
        thumbnail.setPath("comic_1_image");
        comic1.setThumbnail(thumbnail);
        testObject.showComics(Collections.singletonList(comic1));

        RecyclerView recyclerView = ButterKnife.findById(testObject, R.id.comic_list_recyclerview);

        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertTrue(adapter instanceof ComicListAdapter);
        assertEquals(1, adapter.getItemCount());
    }
}